package cupsize.calculate;

import cupsize.StringUtil;
import java.util.List;
import java.util.ArrayList;

public class CalculationBra {
	private double braUnder;			// ブラのアンダーバストのサイズ
	private double braTop;				// ブラのトップバストのサイズ
	private double uBust;
	private double bust;
	private ResultStrage rs;
	
	public void braSizeMain(ResultStrage rs) {
		StringBuilder sb = new StringBuilder();
		this.rs = rs;
		
		double uBust = rs.getUnderBust();
		sb.append("ゆったりしたのが好みならば……");
		sb.append( braSizeName( BraSizeSelectrer.UPPER ) );
		sb.append("\nきつくてもバストを大きくみせたいならば……");
		sb.append( braSizeName( BraSizeSelectrer.LOWER ) );
		sb.append("\n最もフィットするのは……");
		sb.append(braSizeSub());
		rs.inputString( DataType.FIT_SIZE, sb.toString() );
	}
	
	private String braSizeSub() {
		List<BraSizeCalcStruct> BSCSlist = new ArrayList<>();
		BSCSlist.add(createBSCS(BraSizeSelectrer.UPPER));
		BSCSlist.add(createBSCS(BraSizeSelectrer.UPPER,  1));
		BSCSlist.add(createBSCS(BraSizeSelectrer.UPPER, -1));
		BSCSlist.add(createBSCS(BraSizeSelectrer.LOWER));
		BSCSlist.add(createBSCS(BraSizeSelectrer.LOWER,  1));
		BSCSlist.add(createBSCS(BraSizeSelectrer.LOWER, -1));
		sortBSCSlist(BSCSlist);
		StringBuilder sb = new StringBuilder();
		int loopMax = 5;
		for (int i = 0; i < loopMax; i++) {
			sb.append(BSCSlist.get(i).braName);
			if (i != loopMax - 1) sb.append(", ");
		}
		rs.inputString( DataType.AVAILABLE_SIZE, sb.toString() );
		return BSCSlist.get(0).braName;
	}
	
	private void sortBSCSlist(List<BraSizeCalcStruct> BSCSlist) {
		for (int i = 0; i < BSCSlist.size(); i++) {
			for (int j = i; j < BSCSlist.size(); j++) {
				if (BSCSlist.get(i).fitness > BSCSlist.get(j).fitness) {
					swapsortBSCSlist(BSCSlist, i, j);
				}
			}
		}
	}
	
	private void swapsortBSCSlist(List<BraSizeCalcStruct> BSCSlist, int i, int j) {
		BraSizeCalcStruct tmp = BSCSlist.get(i);
		BSCSlist.set(i, BSCSlist.get(j));
		BSCSlist.set(j, tmp);
	}
	
	private BraSizeCalcStruct createBSCS(BraSizeSelectrer bss) {
		return createBSCS(bss, 0);
	}
	
	private BraSizeCalcStruct createBSCS(BraSizeSelectrer bss, int cupSelect) {
		double fitness = calcFitness(bss, cupSelect);
		String braName = braSizeName(bss, cupSelect);
		return new BraSizeCalcStruct(fitness, braName);
	}
	
	private double calcFitness(BraSizeSelectrer bss, int cupSelect) {
		double braUBustHalfDiff = calcBraUnderSizeHalfDiff(bss);
		double braUBustHalf = calcBraUnderSizeHalf(bss);
		double braUB = calcBraUnderSize(bss);
		double braBustDiff = calcBraBustSizeDiff(bss) + cupSelect * 2.5;
		double braBust = braBustDiff + braUB;
		double uBust = rs.getUnderBust();
		double bust = rs.getBust();
		double fitness;
		
		if (bust > braUBustHalf + braBustDiff) {
			if (braUBustHalf > uBust) {
				fitness = bust  - braBustDiff - uBust;
			} else {
				fitness = bust - braBust;
			}
		} else {
			if (braUBustHalf > uBust) {
				fitness = braBust - bust;
			} else {
				fitness =  uBust + braBustDiff - bust;
			}
		}
		
		return fitness;
	}
	
	/* ブラのアンダーのサイズは5刻み */
	private double calcBraUnderSize(BraSizeSelectrer bss) {
		switch (bss) {
			case UPPER:
				return rs.getUnderBust() + ( 5.0 - ( rs.getUnderBust() % 5 ) );
			case LOWER:
				return rs.getUnderBust() - ( rs.getUnderBust() % 5 );
		}
		return -1;
	}
	
	private double calcBraUnderSizeHalf(BraSizeSelectrer bss) {
		switch (bss) {
			case UPPER:
				return rs.getUnderBust() + ( 5.0 - ( rs.getUnderBust() % 5 ) ) / 2;
			case LOWER:
				return rs.getUnderBust() - ( rs.getUnderBust() % 5 ) / 2;
		}
		return -1;
	}
	
	private double calcBraUnderSizeHalfDiff(BraSizeSelectrer bss) {
		switch (bss) {
			case UPPER:
				return ( 5.0 - ( rs.getUnderBust() % 5 ) ) / 2;
			case LOWER:
				return ( rs.getUnderBust() % 5 ) / 2;
		}
		return -1;
	}
	
	private double calcBraBustSizeDiff(BraSizeSelectrer bss) {
		return (int)( Math.max( 0.0, rs.getBust() - calcBraUnderSizeHalf(bss) + 1.25 ) / 2.5 ) * 2.5;
	}
	
	public String calculateCupSize(double bustsize, boolean outputType, int plus) {
		return calculateCupSize(bustsize + 2.5 * plus, outputType);
	}
	
	public String calculateCupSize(double bustsize, boolean outputType) {
		StringBuilder sb = new StringBuilder();
		if (bustsize < 8.75) {
			if (bustsize < 3.75) {
				if (outputType) {
					return "AAAカップ未満";
				}
				return "AAA未満";
			} else {
				int count = 3 - (int)( ( bustsize - 3.75 ) / 2.5 );
				sb.append( StringUtil.generateRepetitionString( "A", count ) );
			}
		} else if (bustsize >= 70.75) {
			if (outputType) {
				return "Zカップ以上";
			}
			return "Z以上";
		} else {
			int ascii = (int)( (bustsize - 8.75) / 2.5 ) + 65;
			sb.append(Character.toString(ascii));
		}
		if (outputType) {
			return sb.append("カップ").toString();
		}
		return sb.toString();
	}
	
	private String braSizeName(BraSizeSelectrer bss, int cupSelect) {
		StringBuilder sb = new StringBuilder();
		double braUnder = calcBraUnderSize(bss);
		double braUnderHalf = calcBraUnderSizeHalf(bss);
		sb.append(calculateCupSize(rs.getBust() + cupSelect * 2.5 - braUnderHalf, false));
		sb.append(String.format("(%d)", (int)braUnder));
		return sb.toString();
	}
	
	private String braSizeName(BraSizeSelectrer bss) {
		return braSizeName(bss, 0);
	}
	
	protected enum BraSizeSelectrer {
		UPPER,								// アンダー大きめ
		LOWER,								// アンダー小さめ
	}

	protected class BraSizeCalcStruct {
		public double fitness;
		public String braName;
		
		BraSizeCalcStruct(double fitness, String braName) {
			this.fitness = fitness;
			this.braName = braName;
		}
	}
}