package cupsize.calculate;

import java.util.ArrayList;
import java.util.List;

public class CalculateStatistics {
	ResultStrage rs;
	CalculationBra cb;
	NormDist nd;
	StatisticsResult sr;
	double charAverage;
	double charStd;
	
	public StatisticsResult calcStatistics(ResultStrage rs, List<Double> BustSizeData) {
		this.rs = rs;
		calcCharctorData(BustSizeData);
		nd = new NormDist();
		cb = new CalculationBra();
		sr = new StatisticsResult();
		probability();
		TScore();
		
		return sr;
	}
	
	private void calcCharctorData(List<Double> BustSizeData) {
		double sum = 0;
		for (double bustSize : BustSizeData) {
			sum += bustSize;
		}
		this.charAverage = sum / BustSizeData.size();
		sum = 0;
		for (double bustSize : BustSizeData) {
			sum += (bustSize - this.charAverage) * (bustSize - this.charAverage);
		}
		double charVar = sum / (BustSizeData.size() - 1);
		this.charStd = Math.sqrt(charVar);
	}
	
	private void TScore() {
		StringBuilder sb = new StringBuilder();
		double TScore = calcTScore(rs.getBustSize(), StatisticsData.BustSizeMean, StatisticsData.BustSizeStd);
		
		sb.append(String.format("バストサイズ偏差値（現実）：%f\n", TScore));
		sb.append(String.format("上位：%f%%", nd.upper(TScore)));
		sr.TScore = sb.toString();
		
		sb = new StringBuilder();
		double charTScore = calcTScore(rs.getBustSize(), charAverage, charStd);
		
		sb.append(String.format("バストサイズ偏差値（2次元）：%f\n", charTScore));
		sb.append(String.format("上位：%f%%", nd.upper(charTScore)));
		sr.charTScore = sb.toString();
	}
	
	private void probability() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = -3; i <= 3; i++) {
			String str = calcProbability(i);
			sb.append(str);
			if (i != 3 && !str.equals("")) sb.append("\n");
		}
		
		sr.probability = sb.toString();
	}
	
	private String calcProbability(int n) {
		StringBuilder sb = new StringBuilder();
		double bustSize = rs.getBustSize();
		double upper = bustSize + 2.5 - ( ( rs.getBustSize() + 1.25 ) % 2.5 ) + n * 2.5;
		double lower = bustSize - ( rs.getBustSize() + 1.25 ) % 2.5 + n * 2.5;
		String fmt = "バストサイズ：%.2f - %.2f（%s）\n";
		String cupSize = cb.calculateCupSize(lower + 1.25, true);
		double probability;
		
		if (lower > rs.getBust() - rs.getWaist()) return "";
		if (upper < 0) return "";
		
		if (n == 0) {
			double probabilityUpper = nd.integral(bustSize, upper, StatisticsData.StdError);
			double probabilityLower = nd.integral(bustSize, lower, StatisticsData.StdError);
			probability = probabilityUpper+ probabilityLower;
		} else if (n < 0) {
			double probabilityUpper = nd.integral(bustSize, upper, StatisticsData.StdError);
			double probabilityLower = nd.integral(bustSize, lower, StatisticsData.StdError);
			probability = probabilityLower - probabilityUpper;
		} else {
			double probabilityUpper = nd.integral(bustSize, upper, StatisticsData.StdError);
			double probabilityLower = nd.integral(bustSize, lower, StatisticsData.StdError);
			probability = probabilityUpper - probabilityLower;
		}
		sb.append( String.format(fmt, lower, upper, cupSize) );
		sb.append(String.format("\t%f%%", probability * 100));
		return sb.toString();
	}
	
	private double calcTScore(double x, double average, double std) {
		return 10 * (x - average) / std + 50;
	}
}