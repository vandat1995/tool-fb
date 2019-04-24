package sharecookie;


import ssh.Connection;
import ssh.SocksHttpRequest;
import ssh.SocksHttpResponse;
import ssh.Ssh;

public class TeshSsh {


	private static final String URL = "https://api.ipify.org";



	public static void main(String[] args) throws Exception {

		Ssh ssh = new Ssh("211.230.156.199", "guest1", "guest1");
		Connection conn = new Connection(ssh);
		int socksPort = conn.openDynamicPortForwarding(3000);
		SocksHttpRequest request = new SocksHttpRequest(socksPort);
		SocksHttpResponse res = request.get(URL);

		if (null != res) {
			System.out.println(res.getStatusCode());
			System.out.println(res.getBody());
		}

		conn.close();





	}
}
