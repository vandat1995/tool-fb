package ssh;

import com.chilkatsoft.CkHttp;
import com.chilkatsoft.CkHttpRequest;
import com.chilkatsoft.CkHttpResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SocksHttpRequest {

	private int socksPort;
	private CkHttp http;
	private CkHttpRequest request;
	private CkHttpResponse response;

	public SocksHttpRequest() {
		this.http = new CkHttp();
		this.request = new CkHttpRequest();
	}

	public SocksHttpRequest(int socksPort) {
		this();
		this.socksPort = socksPort;
		this.http.put_SocksHostname("localhost");
		this.http.put_SocksPort(this.socksPort);
		this.http.put_SocksVersion(5);
	}

	public Map<String, String> get(String url) {
		HashMap<String, String> result = new HashMap<>();
		try {
			this.request.SetFromUrl(url);
			this.request.UseGet();
			URI uri = new URI(url);
			int port = 80;
			boolean ssl = false;
			response = http.SynchronousRequest(uri.getHost(), port, ssl, this.request);
			if (null == response) {
				return result;
			}

			result.put("statusCode", String.valueOf(response.get_StatusCode()));
			result.put("body", response.bodyStr());
			return result;

		} catch (Exception ex) {
			System.err.println(ex);
		}
		return result;

	}


	public Map<String, String> get(String url, String cookie, String userAgent) throws URISyntaxException {
		HashMap<String, String> result = new HashMap<>();
		try {
			this.http.put_UserAgent(userAgent);
			this.request.SetFromUrl(url);
			this.request.UseGet();
			this.request.AddHeader("Cookie", cookie);
			URI uri = new URI(url);
			int port = 80;
			boolean ssl = false;
			response = http.SynchronousRequest(uri.getHost(), port, ssl, this.request);
			if (null == response) {
				result.put("success", "false");
				return result;
			}
			int statusCode = response.get_StatusCode();
			if (200 == statusCode) {
				result.put("success", "true");
				result.put("body", response.bodyStr());
				result.put("statusCode", "200");
				return result;
			}
			result.put("statusCode", String.valueOf(response.get_StatusCode()));
			result.put("success", "false");
			System.out.println(response.bodyStr());
			System.out.println(response.bodyQP());

		} catch (Exception ex) {
			result.put("success", "false");
		}
		return result;

	}

}
