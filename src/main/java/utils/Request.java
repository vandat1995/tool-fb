package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class Request {

	public static String get(String url, String cookie) throws IOException {

		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.addHeader("Cookie", cookie);

		HttpResponse res = client.execute(get);

		if (res.getStatusLine().getStatusCode() == 200) {

			result = new BasicResponseHandler().handleResponse(res);
		}

		return result;
	}

	public static String post(String url, String cookie, List<NameValuePair> params, String userAgent) throws Exception {

		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(params);
		post.addHeader("cookie", cookie);
		post.addHeader("user-agent:", userAgent);
		post.setEntity(entity);

		HttpResponse res = client.execute(post);

		if (res.getStatusLine().getStatusCode() == 200) {
			result = new BasicResponseHandler().handleResponse(res);
		}

		return result;
	}

}
