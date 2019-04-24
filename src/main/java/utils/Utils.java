package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Utils {
	private static final Lock locker = new ReentrantLock();

	public static int getFreeSocketPort() throws IOException {
		int socksPort;
		try {
			locker.lock();
			ServerSocket socket = new ServerSocket(0);
			socksPort = socket.getLocalPort();
			socket.close();
		} catch (Exception ex) {
			System.out.println(ex);
			socksPort = 0;
		} finally {
			locker.unlock();
		}
		return 0 != socksPort ? socksPort : getFreeSocketPort();
	}
}
