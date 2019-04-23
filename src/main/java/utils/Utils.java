package utils;

import java.io.IOException;
import java.net.ServerSocket;

public class Utils {

	public static int getFreeSocketPort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		try {
			return socket.getLocalPort();
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			socket.close();
		}
		return 0;
	}
}
