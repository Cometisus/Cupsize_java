package cupsize;

class debug {
	static private final boolean enable = true;
	
	static void debugPrint(String str) {
		if (enable) {
			System.out.println(str);
		}
	}
	static void debugPrint(double d) {
		if (enable) {
			System.out.println(d);
		}
	}
	
	static boolean debagEnabled() {
		return enable;
	}
}