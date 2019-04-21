package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseRaw {

//	private static ParseRawHtml _instance;
//	private ParseRawHtml(){}

//	public static ParseRawHtml getInstance() {
//		if (_instance == null) {
//			synchronized (ParseRawHtml.class) {
//				if (_instance == null) {
//					_instance = new ParseRawHtml();
//				}
//			}
//		}
//		return _instance;
//	}

	public static String getFbDtsg(String html) {
		try {
			Pattern pattern = Pattern.compile("(name=\"fb_dtsg\"(.)value=\"([\\d\\w-_]+:[\\d\\w-_]+\"))");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {

				String result = matcher.group().split(" ")[1].split("\"")[1];
				return result;

			}
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
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
			System.out.println(ex.getStackTrace());
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
			System.out.println(ex.getStackTrace());
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
			System.out.println(ex.getStackTrace());
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
			System.out.println(ex.getStackTrace());
		}

		return listGroup;

	}

}
