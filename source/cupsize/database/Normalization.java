package cupsize.database;

import java.util.ArrayList;
import java.util.List;

public class Normalization {
	private static final String kanaTbl[][] = {
       { "ｶﾞ", "ガ" }, { "ｷﾞ", "ギ" }, { "ｸﾞ", "グ" }, { "ｹﾞ", "ゲ" }, { "ｺﾞ", "ゴ" }, 
       { "ｻﾞ", "ザ" }, { "ｼﾞ", "ジ" }, { "ｽﾞ", "ズ" }, { "ｾﾞ", "ゼ" }, { "ｿﾞ", "ゾ" },
       { "ﾀﾞ", "ダ" }, { "ﾁﾞ", "ヂ" }, { "ﾂﾞ", "ヅ" }, { "ﾃﾞ", "デ" }, { "ﾄﾞ", "ド" },
       { "ﾊﾞ", "バ" }, { "ﾋﾞ", "ビ" }, { "ﾌﾞ", "ブ" }, { "ﾍﾞ", "ベ" }, { "ﾎﾞ", "ボ" }, 
       { "ﾊﾟ", "パ" }, { "ﾋﾟ", "ピ" }, { "ﾌﾟ", "プ" }, { "ﾍﾟ", "ペ" }, { "ﾎﾟ", "ポ" }, 
       { "ｳﾞ", "ヴ" },
       { "ｱ", "ア" }, { "ｲ", "イ" }, { "ｳ", "ウ" }, { "ｴ", "エ" }, { "ｵ", "オ" }, 
       { "ｶ", "カ" }, { "ｷ", "キ" }, { "ｸ", "ク" }, { "ｹ", "ケ" }, { "ｺ", "コ" }, 
       { "ｻ", "サ" }, { "ｼ", "シ" }, { "ｽ", "ス" }, { "ｾ", "セ" }, { "ｿ", "ソ" }, 
       { "ﾀ", "タ" }, { "ﾁ", "チ" }, { "ﾂ", "ツ" }, { "ﾃ", "テ" }, { "ﾄ", "ト" }, 
       { "ﾅ", "ナ" }, { "ﾆ", "ニ" }, { "ﾇ", "ヌ" }, { "ﾈ", "ネ" }, { "ﾉ", "ノ" }, 
       { "ﾊ", "ハ" }, { "ﾋ", "ヒ" }, { "ﾌ", "フ" }, { "ﾍ", "ヘ" }, { "ﾎ", "ホ" }, 
       { "ﾏ", "マ" }, { "ﾐ", "ミ" }, { "ﾑ", "ム" }, { "ﾒ", "メ" }, { "ﾓ", "モ" }, 
       { "ﾔ", "ヤ" }, { "ﾕ", "ユ" }, { "ﾖ", "ヨ" }, 
       { "ﾗ", "ラ" }, { "ﾘ", "リ" }, { "ﾙ", "ル" }, { "ﾚ", "レ" }, { "ﾛ", "ロ" }, 
       { "ﾜ", "ワ" }, { "ｦ", "ヲ" }, { "ﾝ", "ン" }, 
       { "ｧ", "ァ" }, { "ｨ", "ィ" }, { "ｩ", "ゥ" }, { "ｪ", "ェ" }, { "ｫ", "ォ" }, 
       { "ｬ", "ャ" }, { "ｭ", "ュ" }, { "ｮ", "ョ" }, { "ｯ", "ッ" }, 
       { "｡", "。" }, { "｢", "「" }, { "｣", "」" }, { "､", "、" }, { "･", "・" }, 
       { "ｰ", "ー" }, { "", "" }
   };
	
	public String convChar(String str) {
		String string = convAlpha(convKana(str));
		return string.toLowerCase();
	}
	
	private static String convKana(String p) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0, j = 0; i < p.length(); i++) {
			Character c = Character.valueOf(p.charAt(i));        
			if (c.compareTo(Character.valueOf((char)0xff61)) >= 0
				&& c.compareTo(Character.valueOf((char)0xff9f)) <= 0) {
				for (j = 0; j < kanaTbl.length; j++) {
					if (p.substring(i).startsWith(kanaTbl[j][0])) {
						sb.append(kanaTbl[j][1]);
						i += kanaTbl[j][0].length() - 1;
						break;
					}
				}
				if (j >= kanaTbl.length) {
					sb.append(c);
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private static String convAlpha(String p) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < p.length(); i++) {
			Character c = Character.valueOf(p.charAt(i));
			if (c.compareTo(Character.valueOf((char)0xff01)) >= 0
				&& c.compareTo(Character.valueOf((char)0xff5e)) <= 0) {
				Character x = Character.valueOf((char) (c.charValue() - (Character.valueOf((char)0xfee0)).charValue()));
				sb.append(x);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}