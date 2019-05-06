package sharecookie;

public class ProxyDto {

	private int port;
	private String host;
	private String user;
	private String pass;

	public ProxyDto(int port, String host, String user, String pass) {
		this.port = port;
		this.host = host;
		this.user = user;
		this.pass = pass;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
