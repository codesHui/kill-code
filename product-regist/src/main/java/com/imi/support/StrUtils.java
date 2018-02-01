package com.imi.support;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;


/**
 * 字符串的帮助类，提供静态方法，不可以实例化。
 * 
 * @author 
 * 
 */
public class StrUtils {
	/**
	 * 禁止实例化
	 */
	private StrUtils() {
	}

	/**
	 * 处理url
	 * 
	 * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
	 * 
	 * @param url
	 * @return
	 */
	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if (url.equals("") || url.startsWith("http://")
				|| url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url.trim();
		}
	}

	/**
	 * 分割并且去除空格
	 * 
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @param sep2
	 *            第二个分隔符
	 * @return 如果str为空，则返回null。
	 */
	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		if (!StringUtils.isEmpty(sep2)) {
			str = StringUtils.replace(str, sep2, sep);
		}
		String[] arr = StringUtils.split(str, sep);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isEmpty(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		return s.substring(0, len) + append;
		/*// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isEmpty(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}*/
	}


	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * 
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(search)) {
			return false;
		}
		String reg = StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	public static boolean containsKeyString(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r")
				|| str.contains("\n") || str.contains("\t")
				|| str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}

	// 将""和'转义
	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
	/**
	 * 
	 * 功能描述：判断是否都是由数字组成
	 *
	 * @author  qinbingzhong
	 * <p>创建日期 ：Jun 25, 2013 3:43:59 PM</p>
	 *
	 * @param str
	 * @return
	 *
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public static boolean isNum(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		if (pattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}
	public static boolean isFloat(String str){
		
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		String[] s = str.split("\\.");
		if (s.length<=1||!isNum(s[0])||!isNum(s[1])) {
			return false;
		}
		return true;
	}
	
	public static boolean isEmail(String str){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		if (pattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 功能描述：转换时间格式（将数68s转换成01:08）
	 *
	 * @author  bingzhong.qin
	 * <p>创建日期 ：2014-1-14 下午5:19:08</p>
	 *
	 * @param vodieTime
	 * @return
	 *
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
    public static String prasVideoTime(String videoTime){
    	if (StringUtils.isEmpty(videoTime)||!isNum(videoTime.split("\\.")[0])) {
			return "00:00";
		}
    	videoTime = videoTime.split("\\.")[0];
    	int time = Integer.parseInt(videoTime);
    	//小于60s
    	if (time<60) {
    	  if (time<10) {
    		  return "00:0"+String.valueOf(time);
		  }
		  return "00:"+String.valueOf(time);
		} 
    	int m = time/60;
    	int s = time%60;
    	String rm;
    	String rs;
    	if (m<10) {
    		rm ="0" + String.valueOf(m)+":";
		}else {
			rm = String.valueOf(m)+":";
		}
    	if (s<10) {
    		rs ="0" + String.valueOf(s)+"";
		}else {
			rs = String.valueOf(s)+"";
		}
    	return rm + rs;
    }
	public static void main(String args[]) {
		System.out.println(StrUtils.isFloat("80"));
	}
	
	
	
}