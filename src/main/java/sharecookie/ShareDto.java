package sharecookie;

import java.util.List;

public class ShareDto {

	private String uid;
	private String cookie;
	private List<String> listGroup;

	public ShareDto(String uid, String cookie, List<String> listGroup) {
		this.uid = uid;
		this.cookie = cookie;
		this.listGroup = listGroup;
	}

	public ShareDto(String uid, String cookie) {
		this.uid = uid;
		this.cookie = cookie;

	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}


	public List<String> getListGroup() {
		return listGroup;
	}

	public void setListGroup(List<String> listGroup) {
		this.listGroup = listGroup;
	}


}
