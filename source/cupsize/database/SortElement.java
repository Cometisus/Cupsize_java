package cupsize.database;

public enum SortElement {
	NAME("名前"),
	TITLE("登場作品名"),
	APPENDIX("ブランド名"),
	MEDIA(""),
	HEIGHT("身長"),
	BUST("バスト"),
	WAIST("ウエスト"),
	HIP("ヒップ"),
	WEIGHT("体重"),
	THIGH("太もも"),
	BUST_SIZE("バストサイズ"),
	CUP_SIZE("カップ"),
	BUST_INDEX("バスト指数"),
	BUST_SIZE_INDEX("バストサイズ指数"),
	PROPORTION_INDEX("プロポーション指数"),
	PI("PI"),
	BUST_WEIGHT("乳房の片側重量"),
	NONE("登録順");
	
	private String element;
	
	private SortElement(String element) {
		this.element = element;
	}
	
	public String getElement() {
		return this.element;
	}
}