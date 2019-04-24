package ssh;

public class Ssh {

	private String host;
	private String username;
	private String password;
	private int port;

	public Ssh(String host, String user, String pass) {
		this.host = host;
		this.username = user;
		this.password = pass;
		this.port = 22;
	}

	public Ssh(String host, int port, String user, String pass) {
		this.host = host;
		this.username = user;
		this.password = pass;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
