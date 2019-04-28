package sharecookie;

import com.chilkatsoft.CkHttpRequest;
import ssh.Connection;
import ssh.SocksHttpRequest;
import ssh.SocksHttpResponse;
import ssh.Ssh;
import utils.ParseRaw;
import utils.RandomUserAgent;
import utils.Request;

import com.chilkatsoft.CkHttp;
import com.chilkatsoft.CkHttpRequest;
import com.chilkatsoft.CkHttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TeshSsh {


	private static final int SSH_TIMEOUT = 5000;
	private static final int HTTP_REQUEST_TIMEOUT = 10000;
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
	private static Random rand = new Random();
	private static List<String> listSSH;


	public static void main(String[] args) throws Exception {
		String cookie = "c_user=100015421359945;xs=20%3A_pfSLsmRykB2LA%3A2%3A1555843915%3A17609%3A8125;";
		String videoId = "994187577445639";
		String groupId = "106960679638365";
		String uid = "100004139261394";
		String ua = RandomUserAgent.getRandomUserAgent();
		Connection conn = new Connection(new Ssh("123", "123", "123"));

		//CkHttp http = new CkHttp();
		SocksHttpRequest req = new SocksHttpRequest(cookie, ua);
//		String urlGetInfo = BASE_URL + "composer/mbasic/?c_src=share&referrer=feed&sid="+ videoId +"&m=group&target=" + groupId;
//		String html = req.get(urlGetInfo).getBody();
//		String pathShare = ParseRaw.getShareUrl(html);
//		System.out.println(pathShare);
//		pathShare = pathShare.replaceAll("amp;", "");
//		String dtsg = ParseRaw.getFbDtsg(html);
//		System.out.println(dtsg);
//		String csid = ParseRaw.getCsid(pathShare);

		String html2 = req.get(LIST_GROUP_URL).getBody();
		System.out.println(html2);
		System.out.println(ParseRaw.getFbDtsg(html2));
		System.out.println(ParseRaw.getShareUrl(html2));

//
//		Map<String, String> formData = new HashMap<>();
//		formData.put("fb_dtsg", dtsg);
//		formData.put("jazoest", ParseRaw.getJazoest(html));
//		formData.put("at", "");
//		formData.put("target", groupId);
//		formData.put("csid", csid);
//		formData.put("c_src", "share");
//		formData.put("referrer", "feed");
//		formData.put("ctype", "advanced");
//		formData.put("cver", "amber_share");
//		formData.put("users_with", "");
//		formData.put("album_id", "");
//		formData.put("waterfall_source", "advanced_composer_group");
//		formData.put("appid", "0");
//		formData.put("sid", videoId);
//		formData.put("linkUrl", "");
//		formData.put("m", "group");
//		formData.put("xc_message", "cac2");
//		formData.put("view_post", "");
//		formData.put("shared_from_post_id", videoId);
//
//		String htmlPost = req.postMultipart("mbasic.facebook.com", pathShare, formData, cookie, ua).getBody();
//		System.out.println(htmlPost);



	}
}
