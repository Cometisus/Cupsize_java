package cupsize.calculate;

import java.util.List;
import java.util.ArrayList;

public class Calculate {
	private ResultStrage rs;
	private CalculationBra cb;
	private int met;
	
	public Calculate(ResultStrage rs) {
		this.rs = rs;
		this.met = 0;
		this.cb = new CalculationBra();
	}
	
	public Calculate(ResultStrage rs, int met) {
		this.rs = rs;
		this.met = met;
		this.cb = new CalculationBra();
	}
	
	public ResultStrage start() {
		return start(0);
	}
	
	public ResultStrage start(int flag) {
		CalculationBra calcBra = new CalculationBra();
		startMethod0(calcBra);
		calcBustWeight();
		if ((flag & Flags.selectAverageHeight) > 0) {
			startGCcalc(StatisticsData.HeightMean);
			calcWeight(StatisticsData.HeightMean);
			calcIndex(StatisticsData.HeightMean);
			if (rs.getWeight() != -1) {
				calcBMI(StatisticsData.HeightMean / 100, rs.getWeight());
			} else {
				calcBMI(StatisticsData.HeightMean / 100, rs.getPredictWeight());
			}
		} else if (rs.getHeight() != -1) {
			startGCcalc();
			calcWeight();
			calcIndex();
			if (rs.getWeight() != -1) {
				calcBMI();
			} else {
				calcBMI(rs.getPredictWeight());
			}
		}
		return rs;
	}
	
	public ResultStrage startMethod0(CalculationBra calcBra) {
		rs.input( new DataStruct( DataType.UNDER_BUST, calculateUBust() ) );
		rs.input( new DataStruct( DataType.BUST_SIZE, calculateBustSize() ) );
		rs.inputString( DataType.CUP_SIZE, calculateCupSize() );
		rs.inputString( DataType.ADVICE, createAdvice() );
		calcBra.braSizeMain(rs);
		return rs;
	}
	
	private double calculateUBust() {
		return 0.600593474049778 * rs.getWaist() + 31.7546540135471;
	}
	
	/*
	private double calculateUBust2() {
		return 0.453195357872256 * rs.getBust() + 0.235525570746956 * rs.getWaist() + 17.8074045417923;
	}
	*/
	
	private double calculateBustSize() {
		return rs.getBust() - rs.getUnderBust();
	}
	
	private double calculateNextWaist() {
		return calculateNextDifference() / 0.600593474049778;
	}
	
	private double calculateNextBust() {
		return calculateNextDifference();
	}
	
	/* 次のカップサイズになるために必要なトップとアンダーの差 */
	private double calculateNextDifference() {
		if (rs.getBustSize() < 3.75) {
			return 3.75 - rs.getBustSize();
		}
		return 2.5 - ( ( rs.getBustSize() + 1.25 ) % 2.5 );
	}
	
	private String calculateCupSize() {
		return cb.calculateCupSize(rs.getBust() - rs.getUnderBust(), true);
	}
	
	private String calculateCupSizeNext() {
		if (rs.getBustSize() < 3.75) {
			return "AAAカップ";
		}
		return cb.calculateCupSize(rs.getBust() - rs.getUnderBust(), true, 1);
	}
	
	private String createAdvice() {
		StringBuilder sb = new StringBuilder();
		sb.append(calculateCupSizeNext());
		sb.append("になるためには、\nあとバストをプラス" + calculateNextBust() + "cm\n");
		sb.append("またはウェストをマイナス" + calculateNextWaist() + "cm\n");
		return sb.toString();
	}
	
	private void startGCcalc() {
		startGCcalc(rs.getHeight());
	}
	
	private void startGCcalc(double height) {
		rs.input( new DataStruct( DataType.GC_BUST, height * 0.54 ) );
		rs.input( new DataStruct( DataType.GC_UNDER_BUST, height * 0.44 ) );
		rs.input( new DataStruct( DataType.GC_WAIST, height * 0.38 ) );
		rs.input( new DataStruct( DataType.GC_HIP, height * 0.54 ) );
		rs.input( new DataStruct( DataType.GC_THIGH, height * 0.31 ) );
	}
	
	private void calcBustWeight() {
		double diameter = calcBustDiameter();
		if (rs.getBustSize() < 0) diameter = 0;
		double volume = calcBustVolume(diameter);
		double bustWeight = volume * StatisticsData.FatRelativeDensity;
		
		rs.input( new DataStruct( DataType.BUST_VOLUME, volume ) );
		rs.input( new DataStruct( DataType.BUST_WEIGHT, bustWeight ) );
	}
	
	private double calcBustDiameter(double bustSize, double underBust) {
		final double initSize = 8.3;
		final double diffCup = 0.98;
		double cupDouble = bustSize / 2.5 - 3;
		return initSize + diffCup * ((underBust - 65) / 5 - 1) + diffCup * (cupDouble - 1);
	}
	
	private double calcBustDiameter() {
		return calcBustDiameter(rs.getBustSize(), rs.getUnderBust());
	}
	
	private double calcBustVolume(double diameter) {
		return 2.0 / 3.0 * Math.PI * Math.pow(diameter / 2, 3);
	}
	
	private void calcWeight() {
		calcWeight(rs.getHeight());
	}
	
	private void calcWeight(double height) {
		double weightBody = 0.460216146808236 * height + 0.948522568812633 * rs.getWaist() - 82.5478099122805;
		
		double weightBust = rs.getBustWeight();
		double diameterMeanBust = calcBustDiameter(StatisticsData.BustSizeMean, StatisticsData.UnderBustMean);
		double weightMeanBust = calcBustVolume(diameterMeanBust);
		
		double weight = weightBody + (weightBust - weightMeanBust) / 500;
		
		rs.input( new DataStruct( DataType.PREDICT_WEIGHT, weight ) );
	}
	
	private void calcBMI() {
		calcBMI(rs.getHeight() / 100, rs.getWeight());
	}
	
	private void calcBMI(double weight) {
		calcBMI(rs.getHeight() / 100, weight);
	}
	
	private void calcBMI(double height, double weight) {
		double bmi = weight / height / height;
		String status = BMIStatus(bmi);
		double newBmi = weight / Math.pow(height, 2.5) * 1.3;
		String newStatus = BMIStatus(newBmi);
		double suitableWeight = height * height * 22;
		double cosmeticWeight = height * height * 20;
		rs.input( new DataStruct( DataType.BMI, bmi ) );
		rs.input( new DataStruct( DataType.NEW_BMI, newBmi ) );
		rs.inputString( DataType.BMI_STATUS, status );
		rs.inputString( DataType.NEW_BMI_STATUS, newStatus );
		rs.input( new DataStruct( DataType.SUITABLE_WEIGHT, suitableWeight ) );
		rs.input( new DataStruct( DataType.COSMETIC_WEIGHT, cosmeticWeight ) );
	}
	
	private String BMIStatus(double bmi) {
		if (bmi < 18.5) {
			return "低体重";
		} else if (bmi < 25) {
			return "普通体重";
		} else if (bmi < 30) {
			return "肥満 (1度)";
		} else if (bmi < 35) {
			return "肥満 (2度)";
		} else if (bmi < 40) {
			return "肥満 (3度)";
		}
		return "肥満 (4度)";
	}
	
	private void calcIndex(double height, double hip, double thigh) {
		double bustIndex = rs.getBust() / height;
		double bustSizeIndex = rs.getBustSize() / height;
		double proportionIndex = rs.getBust() * height * hip / (rs.getUnderBust() * rs.getWaist() * rs.getWaist());
		double pi = (rs.getWaist() + 2 * thigh - hip - rs.getBustSize()) / height;
		rs.input( new DataStruct( DataType.PI, pi ) );
		rs.input( new DataStruct( DataType.PROPORTION_INDEX, proportionIndex ) );
		rs.input( new DataStruct( DataType.BUST_INDEX, bustIndex ) );
		rs.input( new DataStruct( DataType.BUST_SIZE_INDEX, bustSizeIndex ) );
	}
	
	private void calcIndex() {
		calcIndex(rs.getHeight());
	}
	
	private void calcIndex(double height) {
		double hip = rs.getHip();
		double thigh = rs.getThigh();
		if (hip < 0) {
			hip = height * 0.54;
		}
		if (thigh < 0) {
			thigh = height * 0.31;
		}
		calcIndex(height, hip, thigh);
	}
}