package ssh;

import com.chilkatsoft.*;
import utils.Utils;

import java.io.IOException;
import java.net.ConnectException;

public class Connection {

	protected Ssh ssh;
	protected CkGlobal chilkatGlob;
	protected CkSshTunnel tunnel;
	private boolean success;
	private int timeout;

	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public Connection(Ssh ssh) throws ExceptionInInitializerError {
		this.ssh = ssh;
		this.chilkatGlob = new CkGlobal();
		this.success = this.chilkatGlob.UnlockBundle("Anything for 30-day trial.");
		if (!this.success) {
			//System.out.println(chilkatGlob.lastErrorText());
			throw new ExceptionInInitializerError("chilkatGlob cannot unlockBundle.");
		}
		this.tunnel = new CkSshTunnel();
	}

	public int openDynamicPortForwarding() throws IOException {
		this.success = tunnel.Connect(ssh.getHost(), ssh.getPort());
		if (!this.success) {
			throw new ConnectException("Connect timeout.");
		}
		this.success = tunnel.AuthenticatePw(ssh.getUsername(), ssh.getPassword());
		if (!this.success) {
			throw new ConnectException("Authentication failed.");
		}

		tunnel.put_DynamicPortForwarding(true);
		int portForward = Utils.getFreeSocketPort();
		this.success = tunnel.BeginAccepting(portForward);
		if (!this.success) {
			throw new IOException("Forward port " + portForward + " failed.");
		}
		return portForward;
	}

	public int openDynamicPortForwarding(int timeout) throws IOException {
		this.timeout = timeout;
		this.tunnel.put_ConnectTimeoutMs(this.timeout);
		this.success = tunnel.Connect(ssh.getHost(), ssh.getPort());
		if (!this.success) {
			throw new ConnectException("Connect timeout.");
		}
		this.success = tunnel.AuthenticatePw(ssh.getUsername(), ssh.getPassword());
		if (!this.success) {
			throw new ConnectException("Authentication failed.");
		}

		tunnel.put_DynamicPortForwarding(true);
		int portForward = Utils.getFreeSocketPort();
		this.success = tunnel.BeginAccepting(portForward);
		if (!this.success) {
			throw new IOException("Forward port " + portForward + " failed.");
		}
		return portForward;
	}

	public void close() throws IOException {
		this.success = tunnel.StopAccepting(true);
		this.success = tunnel.CloseTunnel(true);
		if (!this.success) {
			throw new IOException("Close tunnel failed. Cause: " + tunnel.lastErrorText());
		}
	}

}
