package sharecookie;
/**
 * Share video vao group
 */

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import utils.ParseRaw;
import utils.RandomUserAgent;
import utils.Request;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static final String BASE_URL = "https://mbasic.facebook.com/";
	private static final String SHARE_URL = "https://www.facebook.com/webgraphql/mutation/?doc_id=1740513229408093";
	private static final String LIST_GROUP_URL = "https://mbasic.facebook.com/groups/?seemore";
	private static final String COOKIE_FILE_PATH = "./cookie.txt";
	private static final String MESSAGE_PATH = "./message.txt";
	private static String videoId;
	private static List<String> message;
	private static int limit;
	private static int delay;
	private static int nThread;
	private static Random rand = new Random();

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

			System.out.println("Đang tiến hành share...");
			List<String> listCookie = FileUtils.readLines(new File(COOKIE_FILE_PATH), "UTF-8");
			message = FileUtils.readLines(new File(MESSAGE_PATH), "UTF-8");

			ExecutorService executor = Executors.newFixedThreadPool(nThread);

			for (String cookie : listCookie) {
				String uid = ParseRaw.getUidFromCookie(cookie);
				ShareDto obj = new ShareDto(uid, cookie);
				executor.submit(new Task(obj));
			}
			executor.shutdown();

		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}
	}

	private static List<String> getListGroup(String cookie) {
		try {
			String html = Request.get(LIST_GROUP_URL, cookie, "");
			return ParseRaw.getListIdGroup(html);

		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}

		return new ArrayList<String>();
	}

	private static void shareToGroup(ShareDto obj) {

		//boolean success = false;
		long startTime = System.currentTimeMillis();
		int i = 0;
		String userAgent = RandomUserAgent.getRandomUserAgent();
		for (String groupId : obj.getListGroup()) {
			if (i == limit) {
				break;
			} else i++;

			try {
				String urlGetInfo = BASE_URL + videoId;
				String rawHtml = Request.get(urlGetInfo, obj.getCookie(), userAgent);
				String dtsg = ParseRaw.getFbDtsg(rawHtml);
				if (dtsg.equals("")) {
					System.out.println(obj.getUid() + " => Cookie die.");
					break;
				}
				String postId = ParseRaw.getPostIdOfVideo(rawHtml);
				String pageId = ParseRaw.getPageIdOfVideo(rawHtml);
				String uuid = UUID.randomUUID().toString();

				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("__spin_t", "1555738356"));
				params.add(new BasicNameValuePair("__spin_b", "trunk"));
				params.add(new BasicNameValuePair("__spin_r", "1000624815"));
				params.add(new BasicNameValuePair("jazoest", "22158"));
				params.add(new BasicNameValuePair("__comet_req", "false"));
				params.add(new BasicNameValuePair("__rev", "1000624815"));
				params.add(new BasicNameValuePair("dpr", "2"));
				params.add(new BasicNameValuePair("__pc", "PHASED:ufi_home_page_pkg"));
				params.add(new BasicNameValuePair("__be", "1"));
				params.add(new BasicNameValuePair("__req", "15"));
				params.add(new BasicNameValuePair("__dyn", "7AgNe-4amaWxd2u5bGSF8CC5EWq2W8GAdyediheCHxG7Uqzob4q6oF7yWCHxC7oG5VEc8yEGbyRUC48G5WAxamjDKaxeAcUeWDxqfx12KdwJJ4hKe-4UggoDAyF8O49Elwzx22-HBy8G5p8hwj8lg8-i4bg42E-ezFK5e9wg8lDBg4CdyFFEy2iu4rGbKdxyho-6VECm8QWwTgCmfxKUKVEkyoW4E-1uUkGE-WUW8CG2-4oK4t3ku4FeawG-UlBy4hxfyopAx3yBz9rBKuufxbwAyE-t3efxSh7G5WxK4VoOh4AjzequV8y7EK5ojyHGfwPxe8KuFpWxq8x6GK3yeCUO5Aby8kwwz8Om78K8wioozo98qzUmw"));
				params.add(new BasicNameValuePair("__a", "1"));
				params.add(new BasicNameValuePair("__user", obj.getUid()));
				params.add(new BasicNameValuePair("fb_dtsg", dtsg));
				params.add(new BasicNameValuePair("variables", "{\"client_mutation_id\":\"" + UUID.randomUUID().toString() + "\",\"actor_id\":\"" + obj.getUid() + "\",\"input\":{\"actor_id\":\"" + obj.getUid() + "\",\"client_mutation_id\":\"" + UUID.randomUUID().toString() + "\",\"source\":\"WWW\",\"audience\":{\"to_id\":\"" + groupId + "\"},\"message\":{\"text\":\"" + message.get(rand.nextInt(message.size())) + "\",\"ranges\":[]},\"logging\":{\"composer_session_id\":\"" + uuid + "\",\"ref\":\"group\"},\"with_tags_ids\":[],\"multilingual_translations\":[],\"camera_post_context\":{\"deduplication_id\":\"" + uuid + "\",\"source\":\"composer\"},\"composer_source_surface\":\"group\",\"composer_entry_point\":\"group\",\"composer_entry_time\":6,\"composer_session_events_log\":{\"composition_duration\":37,\"number_of_keystrokes\":12,\"number_of_copy_pastes\":1},\"direct_share_status\":\"NOT_SHARED\",\"sponsor_relationship\":\"WITH\",\"web_graphml_migration_params\":{\"is_also_posting_video_to_feed\":false,\"target_type\":\"group\",\"xhpc_composerid\":\"rc.u_0_1u\",\"xhpc_context\":\"profile\",\"xhpc_publish_type\":\"FEED_INSERT\"},\"external_movie_data\":{},\"place_attachment_setting\":\"HIDE_ATTACHMENT\",\"detection_analytics_data\":{\"detection_id\":\"" + UUID.randomUUID().toString() + "\",\"device_advertising_id\":null,\"product_id\":\"54\"},\"attachments\":[{\"link\":{\"share_scrape_data\":\"{\\\"share_type\\\":22,\\\"share_params\\\":[" + pageId + "," + postId + "],\\\"shared_from_post_id\\\":" + videoId + "}\"}}]}}"));

				Request.post(SHARE_URL, obj.getCookie(), params, userAgent);
				System.out.println(obj.getUid() + " share to group " + groupId + " success");
				Thread.sleep(delay * 1000);

			} catch (Exception ex) {
				System.out.println(ex.getStackTrace());
				System.out.println(obj.getUid() + " share to group " + groupId + " fail");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + " - Total execution time of " + obj.getUid() + ": " + (endTime - startTime) + "ms");

	}

	public static class Task implements Runnable {

		private ShareDto obj;

		public Task(ShareDto value) {
			this.obj = value;
		}

		@Override
		public void run() {
			List<String> listGroup = getListGroup(this.obj.getCookie());
			this.obj.setListGroup(listGroup);
			shareToGroup(this.obj);
		}
	}

}
