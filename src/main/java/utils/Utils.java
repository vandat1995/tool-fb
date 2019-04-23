package utils;

import java.io.IOException;
import java.net.ServerSocket;

public class Utils {

	public static int getFreeSocketPort() throws IOException {
		int socksPort;
		try {
			ServerSocket socket = new ServerSocket(0);
			socksPort = socket.getLocalPort();
			socket.close();
		} catch (Exception ex) {
			System.out.println(ex);
			socksPort = 0;
		}
		return 0 != socksPort ? socksPort : getFreeSocketPort();
	}
}
