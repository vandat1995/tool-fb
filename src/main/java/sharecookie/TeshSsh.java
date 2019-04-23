package sharecookie;


import com.chilkatsoft.*;
import ssh.SocksHttpRequest;
import utils.Utils;

import java.net.ServerSocket;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TeshSsh {


	private static final String URL = "https://api.ipify.org";
	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}


	public static void main(String[] args) throws Exception {

		int socketPort = Utils.getFreeSocketPort();


//		System.exit(0);

		CkGlobal chilkatGlob = new CkGlobal();
		boolean success = chilkatGlob.UnlockBundle("Anything for 30-day trial.");
		if (success != true) {
			System.out.println(chilkatGlob.lastErrorText());
			return;
		}

		String host = "49.247.207.242";
		String user = "uworks";
		String pass = "nathanae@nate";

		CkSshTunnel tunnel = new CkSshTunnel();

		tunnel.put_ConnectTimeoutMs(3000);
		success = tunnel.Connect(host, 22);
		if (true != success) {
			System.out.println("timeout");
			System.out.println(tunnel.lastErrorText());
			return;
		}

		success = tunnel.AuthenticatePw(user, pass);
		if (true != success) {
			System.out.println("authen");
			System.out.println(tunnel.lastErrorText());
			return;
		}

		tunnel.put_DynamicPortForwarding(true);
		success = tunnel.BeginAccepting(socketPort);
		if (true != success) {
			System.out.println("forward fail");
			System.out.println(tunnel.lastErrorText());
			return;
		}

		System.out.println(tunnel.getCurrentState());
		System.out.println(tunnel.IsSshConnected());
		//System.out.println(tunnel.);


		String url = "https://graph.facebook.com/";
//		CkHttp http = new CkHttp();
//		http.put_SocksHostname("localhost");
//		http.put_SocksPort(1234);
//		http.put_SocksVersion(5);
//
//		String html = http.quickGetStr(URL);
//
//		System.out.println(http.get_LastStatus());
//
//		if (http.get_LastMethodSuccess() != true) {
//			System.out.println(http.lastErrorText());
//			return;
//		}
//		System.out.println(html);

		System.out.println("Socket Port: " + socketPort);
		SocksHttpRequest rq = new SocksHttpRequest(socketPort);
		Map<String, String> rs = rq.get(URL);
		System.out.println("ket qua: ");
		System.out.println(rs);

		success = tunnel.StopAccepting(true);
		success = tunnel.CloseTunnel(true);









	}
}
