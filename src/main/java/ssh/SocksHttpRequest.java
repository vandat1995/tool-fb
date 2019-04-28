package ssh;

import com.chilkatsoft.CkHttp;
import com.chilkatsoft.CkHttpRequest;
import com.chilkatsoft.CkHttpResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.URI;
import java.util.Map;

public class SocksHttpRequest {

	private int socksPort;
	private boolean ssl = false;
	private CkHttp http;
	//private CkHttpRequest request;
	private CkHttpResponse response;

	public SocksHttpRequest() {
		this.http = new CkHttp();
		//this.request = new CkHttpRequest();
	}

	public SocksHttpRequest(int socksPort) {
		this();
		this.socksPort = socksPort;
		this.http.put_SocksHostname("127.0.0.1");
		this.http.put_SocksPort(this.socksPort);
		this.http.put_SocksVersion(5);
	}

	public SocksHttpRequest(int socksPort, int timeout) {
		this();
		this.http.put_ConnectTimeout(timeout);
		this.socksPort = socksPort;
		this.http.put_SocksHostname("127.0.0.1");
		this.http.put_SocksPort(this.socksPort);
		this.http.put_SocksVersion(5);
	}

	public SocksHttpRequest(int socksPort, int timeout, String cookie, String userAgent) {
		this();
		this.http.put_ConnectTimeout(timeout);
		this.socksPort = socksPort;
		this.http.put_SocksHostname("127.0.0.1");
		this.http.put_SocksPort(this.socksPort);
		this.http.put_SocksVersion(5);
		this.http.put_UserAgent(userAgent);
		this.http.AddQuickHeader("Cookie", cookie);
	}

	public SocksHttpRequest(String cookie, String userAgent) {
		this();
		this.http.put_UserAgent(userAgent);
		this.http.AddQuickHeader("Cookie", cookie);
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
			String html = this.http.quickGetStr(url);
			res.setStatusCode(this.http.get_LastStatus());
			res.setBody(html);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return res;
	}

	public SocksHttpResponse postMultipart(String uri, String path, Map<String, String> formData, String cookie, String ua) {
		SocksHttpResponse res = new SocksHttpResponse();
		CkHttpRequest request = new CkHttpRequest();
		try {
			request.put_Path(path);
			request.UsePost();
			request.AddHeader("Cookie", cookie);
			request.AddHeader("User-agent", ua);
			request.put_ContentType("multipart/form-data");
			request.put_Boundary("----WebKitFormBoundary" + RandomStringUtils.randomAlphanumeric(16));

			// data o day
			formData.entrySet().forEach(e -> request.AddParam(e.getKey(), e.getValue()));
			//System.out.println(request.generateRequestText());

			//URI uri = new URI(url);
			int port = 80;
			this.response = http.SynchronousRequest(uri, port, this.ssl, request);
			if (null == this.response) {
				return null;
			}
			int statusCode = this.response.get_StatusCode();
			res.setStatusCode(statusCode);
			res.setBody(this.response.bodyStr());

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return res;
	}



}
