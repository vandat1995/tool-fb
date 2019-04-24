package ssh;

import com.chilkatsoft.CkHttp;
import com.chilkatsoft.CkHttpRequest;
import com.chilkatsoft.CkHttpResponse;

import java.net.URI;

public class SocksHttpRequest {

	private int socksPort;
	private boolean ssl = false;
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

	public SocksHttpRequest(String socksHost, int socksPort) {
		this();
		this.socksPort = socksPort;
		this.http.put_SocksHostname(socksHost);
		this.http.put_SocksPort(this.socksPort);
		this.http.put_SocksVersion(5);
	}

	public SocksHttpResponse get(String url) {
		SocksHttpResponse res = new SocksHttpResponse();
		try {
			this.request.SetFromUrl(url);
			this.request.UseGet();
			URI uri = new URI(url);
			int port = 80;
			response = http.SynchronousRequest(uri.getHost(), port, this.ssl, this.request);
			if (null == response) {
				return null;
			}
			int statusCode = response.get_StatusCode();
			res.setStatusCode(statusCode);
			res.setBody(response.bodyStr());

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return res;
	}


	public SocksHttpResponse get(String url, String cookie, String userAgent) {
		SocksHttpResponse res = new SocksHttpResponse();
		try {
			this.http.put_UserAgent(userAgent);
			this.request.SetFromUrl(url);
			this.request.UseGet();
			this.request.AddHeader("Cookie", cookie);
			URI uri = new URI(url);
			int port = 80;
			response = http.SynchronousRequest(uri.getHost(), port, this.ssl, this.request);
			if (null == response) {
				return null;
			}
			int statusCode = response.get_StatusCode();
			res.setStatusCode(statusCode);
			res.setBody(response.bodyStr());

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return res;

	}

}
