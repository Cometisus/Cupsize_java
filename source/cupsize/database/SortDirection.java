package cupsize.database;

public enum SortDirection {
	ASC(true),
	DESC(false);
	
	private boolean direction;
	
	private SortDirection(boolean direction) {
		this.direction = direction;
	}
	
	public boolean getBool() {
		return this.direction;
	}
}