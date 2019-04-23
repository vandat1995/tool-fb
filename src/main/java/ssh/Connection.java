package ssh;

import com.chilkatsoft.*;

public abstract class Connection {

	protected Ssh ssh;

	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public Connection(Ssh ssh) {
		this.ssh = ssh;
	}


	public void open() {

	}





}
