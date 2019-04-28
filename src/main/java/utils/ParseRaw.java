package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseRaw {

	public static String getFbDtsg(String html) {
		try {
			Pattern pattern = Pattern.compile("name=\"fb_dtsg\"(.)value=\"([\\d\\w-_]+:[\\d\\w-_]+)\"");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				//String result = matcher.group().split(" ")[1].split("\"")[1];
				String result = matcher.group(2);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getJazoest(String html) {
		try {
			Pattern pattern = Pattern.compile("name=\"jazoest\" value=\"([0-9]+)\"");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getEav(String html) {
		try {
			Pattern pattern = Pattern.compile("eav=([\\w\\d_-])+\"");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getCsid(String html) {
		try {
			Pattern pattern = Pattern.compile("csid=(.{36})?");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getShareUrl(String html) {
		try {
			Pattern pattern = Pattern.compile("action=\"(.+)\".id=\"composer_form\"");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}


	public static String getUidFromCookie(String cookie) {
		try {
			Pattern pattern = Pattern.compile("c_user=(\\d+)");
			Matcher matcher = pattern.matcher(cookie);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getPostIdOfVideo(String html) {
		try {
			Pattern pattern = Pattern.compile("target_id=(\\d+)");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static String getPageIdOfVideo(String html) {
		try {
			Pattern pattern = Pattern.compile("profile\\.php\\?fan&amp;id=(\\d+)");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				String result = matcher.group(1);
				return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static List<String> getListIdGroup(String html) {
		List<String> listGroup = new ArrayList<>();
		try {
			Pattern pattern = Pattern.compile("groups\\/(\\d+)\\?");
			Matcher matcher = pattern.matcher(html);

			while (matcher.find()) {
				listGroup.add(matcher.group(1));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listGroup;
	}

}
