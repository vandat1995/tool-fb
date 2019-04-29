package sharecookie;
/**
 * Share video vao group moi cookie 1 ssh
 */

import org.apache.commons.io.FileUtils;
import ssh.Connection;
import ssh.SocksHttpRequest;
import ssh.Ssh;
import utils.ParseRaw;
import utils.RandomUserAgent;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainSsh {

	private static final int SSH_TIMEOUT = 10000;
	private static final int HTTP_REQUEST_TIMEOUT = 15000;
	private static final Lock locker = new ReentrantLock();
	private static final String BASE_URL = "http://mbasic.facebook.com/";
	private static final String BASE_URI_FB = "mbasic.facebook.com";
	private static final String SHARE_URL = "https://www.facebook.com/webgraphql/mutation/?doc_id=1740513229408093";
	private static final String LIST_GROUP_URL = "http://mbasic.facebook.com/groups/?seemore";
	private static final String COOKIE_FILE_PATH = "./cookie.txt";
	private static final String MESSAGE_PATH = "./message.txt";
	private static final String SSH_PATH = "./ssh.txt";
	private static String videoId;
	private static List<String> message;
	private static int limit;
	private static int delay;
	private static int nThread;
	private static int _useSsh;
	private static Random rand = new Random();
	private static List<String> listSSH;

	public static void main(String[] args) {

		try {

			System.out.print("Nhập ID của video: ");
			videoId = new Scanner(System.in).nextLine();
			System.out.print("Nhập số thread muốn chạy: ");
			nThread = Integer.parseInt(new Scanner(System.in).nextLine());
			System.out.print("Nhập delay giữa mỗi lần (giây): ");
			delay = Integer.parseInt(new Scanner(System.in).nextLine());
			System.out.print("Nhập số group / acc muốn chạy: ");
			limit = Integer.parseInt(new Scanner(System.in).nextLine());
			System.out.print("Có sử dụng SSH hay không? (1: ssh, 0: ip máy): ");
			_useSsh = Integer.parseInt(new Scanner(System.in).nextLine());

			System.out.println("Đang tiến hành share...");
			List<String> listCookie = FileUtils.readLines(new File(COOKIE_FILE_PATH), "UTF-8");
			listSSH = FileUtils.readLines(new File(SSH_PATH), "UTF-8");
			message = FileUtils.readLines(new File(MESSAGE_PATH), "UTF-8");

			ExecutorService executor = Executors.newFixedThreadPool(nThread);

			for (String cookie : listCookie) {
				String uid = ParseRaw.getUidFromCookie(cookie);
				ShareDto obj = new ShareDto(uid, cookie);
				executor.submit(new Task(obj));
			}
			executor.shutdown();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static List<String> getListGroup(SocksHttpRequest req) {
		try {
			String html = req.get(LIST_GROUP_URL).getBody();
			return ParseRaw.getListIdGroup(html);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<String>();
	}

	private static void shareToGroup(ShareDto obj) {
		int i = 0;
		String userAgent = RandomUserAgent.getRandomUserAgent();

		// Nếu không sài ssh thì thôi
		if (_useSsh == 0) {
			Connection conn = new Connection(new Ssh("123", "123", "123"));
		}
		int socksPort = _useSsh == 1 ? getSocksPort() : 0;
		SocksHttpRequest request = _useSsh == 1 ? new SocksHttpRequest(socksPort, HTTP_REQUEST_TIMEOUT, obj.getCookie(), userAgent) : new SocksHttpRequest(obj.getCookie(), userAgent);

		List<String> listGroup = getListGroup(request);
		obj.setListGroup(listGroup);
		if (listGroup.size() < 1) {
			System.out.println(Thread.currentThread().getName() + " - " + obj.getUid() + " => Cookie die.");
			return;
		}
		for (String groupId : obj.getListGroup()) {
			if (i == limit) {
				break;
			} else i++;

			try {
				String urlGetInfo = BASE_URL + "composer/mbasic/?c_src=share&referrer=feed&sid="+ videoId +"&m=group&target=" + groupId;
				String rawHtml = request.get(urlGetInfo).getBody();

				String dtsg = ParseRaw.getFbDtsg(rawHtml);
				if (dtsg.equals("")) {
					System.out.println(Thread.currentThread().getName() + " - " + obj.getUid() + " => Cookie die.");
					break;
				}
				String sharePath = ParseRaw.getShareUrl(rawHtml);
				sharePath = sharePath.replaceAll("amp;", "");

				String csid = ParseRaw.getCsid(sharePath);

				Map<String, String> formData = new HashMap<>();
				formData.put("fb_dtsg", dtsg);
				formData.put("jazoest", ParseRaw.getJazoest(rawHtml));
				formData.put("at", "");
				formData.put("target", groupId);
				formData.put("csid", csid);
				formData.put("c_src", "share");
				formData.put("referrer", "feed");
				formData.put("ctype", "advanced");
				formData.put("cver", "amber_share");
				formData.put("users_with", "");
				formData.put("album_id", "");
				formData.put("waterfall_source", "advanced_composer_group");
				formData.put("appid", "0");
				formData.put("sid", videoId);
				formData.put("linkUrl", "");
				formData.put("m", "group");
				formData.put("xc_message", message.get(rand.nextInt(message.size())));
				formData.put("view_post", "");
				formData.put("shared_from_post_id", videoId);

				String resultShare = request.postMultipart(BASE_URI_FB, sharePath, formData, obj.getCookie(), userAgent).getBody();
				System.out.println(Thread.currentThread().getName() + " - " + obj.getUid() + " share to group " + groupId + " success");
				Thread.sleep(delay * 1000);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(Thread.currentThread().getName() + " - " + obj.getUid() + " share to group " + groupId + " fail");
			}
		}
		System.out.println(Thread.currentThread().getName() + " - " + obj.getUid() + " => Done");
	}


	public static class Task implements Runnable {

		private ShareDto obj;

		public Task(ShareDto value) {
			this.obj = value;
		}

		@Override
		public void run() {
			shareToGroup(this.obj);
		}
	}

	public static Ssh getSsh() {
		if (null == listSSH) return null;
		Ssh ssh = null;
		try {
			locker.lock();
			String strSsh = listSSH.get(0);
			listSSH.remove(0);
			String[] arrSsh = strSsh.split("\\|");
			ssh = new Ssh(arrSsh[0], arrSsh[1], arrSsh[2]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			locker.unlock();
		}
		return ssh;
	}

	public static int getSocksPort() {
		int port = 0;
		try {
			Ssh ssh = getSsh();
			if (null == ssh) {
				System.out.println("Hết SSH.");
				System.exit(1);
			}
			Connection conn = new Connection(ssh);
			port = conn.openDynamicPortForwarding(SSH_TIMEOUT);
			System.out.println(Thread.currentThread().getName() + " - Connected ssh: " + ssh.getHost() + ":" + port);
		} catch (Exception e) {
			e.printStackTrace();
			port = getSocksPort();
		}

		return port;
	}
}
