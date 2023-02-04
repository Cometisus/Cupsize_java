package cupsize;

public class StringUtil {
	public static String generateRepetitionString(String s, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
}