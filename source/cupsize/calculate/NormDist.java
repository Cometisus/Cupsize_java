package cupsize.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class NormDist {
	private BigDecimal[] xi;
	private BigDecimal[] wi;
	private final int n = 20;
	
	public NormDist() {
		init();
	}
	
	private void init() {
		xi = new BigDecimal[n];
		wi = new BigDecimal[n];
		
		xi[0] = new BigDecimal("-0.993128599185094924786122388471");
		xi[1] = new BigDecimal("-0.963971927277913791267666131197");
		xi[2] = new BigDecimal("-0.912234428251325905867752441203");
		xi[3] = new BigDecimal("-0.839116971822218823394529061702");
		xi[4] = new BigDecimal("-0.746331906460150792614305070356");
		xi[5] = new BigDecimal("-0.636053680726515025452836696226");
		xi[6] = new BigDecimal("-0.510867001950827098004364050955");
		xi[7] = new BigDecimal("-0.373706088715419560672548177025");
		xi[8] = new BigDecimal("-0.227785851141645078080496195369");
		xi[9] = new BigDecimal("-0.076526521133497333754640409399");
		int i;
		for (i = n / 2; i < n; i++) {
			xi[i] = xi[n - i - 1].multiply(BigDecimal.valueOf(-1));
		}
		for (i = 0; i < n; i++) {
			BigDecimal a = BigDecimal.valueOf(1).subtract(xi[i].multiply(xi[i]));
			BigDecimal b = BigDecimal.valueOf(n).multiply(legendre(n - 1, xi[i]));
			wi[i] = BigDecimal.valueOf(2).multiply(a).divide(b, 30, RoundingMode.HALF_UP).divide(b, 30, RoundingMode.HALF_UP);
		}
	}
	
	private BigDecimal legendre(int n, BigDecimal x) {
		if (n == 0) {
			return BigDecimal.valueOf(1);
		} else if (n == 1) {
			return x;
		}
		BigDecimal a1 = BigDecimal.valueOf(2 * n - 1).multiply(x).multiply(legendre(n - 1, x));
		BigDecimal a2 = BigDecimal.valueOf(n - 1).multiply(legendre(n - 2, x));
		BigDecimal a = a1.subtract(a2);
		return a.divide(BigDecimal.valueOf(n), 30, RoundingMode.HALF_UP);
	}
	
	private double stdNormDist(double x) {
		return 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-x * x / 2.0);
	}
	
	private double boole(double x1, double x2, int n, Function< Double, Double > f) {
		if (x1 == x2) return 0.0;
		if (n <= 0) return 0.0;
		double xe = (x1 > x2 ? x1 : x2);
		double xi = (x1 < x2 ? x1 : x2);
		double len = xe - xi;
		double d = len / n;
		
		double h = d / 4.0;
		double x = xi + d;
		double sum = 0.0;
		
		while (x <= xe - h) {
			sum += 32.0 * (f.apply(x) + f.apply(x + 2.0 * d)) + 12.0 * f.apply(x + d) + 14.0 * f.apply(x + 3.0 * d);
			x += 4.0 * d;
		}
		sum += (f.apply(xi) + f.apply(xe)) * 7.0;
		return sum * 2.0 * d / 45.0;
	}
	
	private double gaussLegendre(double x1, double x2, Function< Double, Double > f) {
		if (x1 == x2) return 0.0;
		if (n <= 0) return 0.0;
		double xe = (x1 > x2 ? x1 : x2);
		double xo = (x1 < x2 ? x1 : x2);
		
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			double t = (xe - xo) / 2.0 * xi[i].doubleValue() + (xe + xo) / 2.0;
			sum += wi[i].doubleValue() * f.apply(t);
		}
		return sum * (xe - xo) / 2.0;
	}
	
	public double upper(double x0) {
		double x = (x0 - 50) / 10;
		int sign = (x0 < 50 ? -1 : 1);
		Function< Double, Double > f = t -> stdNormDist(t);
		return (0.5 - gaussLegendre(0, x, f) * sign) * 100;
	}
	
	public double lower(double x0) {
		double x = (x0 - 50) / 10;
		int sign = (x0 < 50 ? -1 : 1);
		Function< Double, Double > f = t -> stdNormDist(t);
		return (0.5 + gaussLegendre(0, x, f) * sign) * 100;
	}
	
	public double integral(double x0, double x1, double std) {
		double x = (x1 - x0) / std;
		//int sign = (x < 0 ? -1 : 1);
		Function< Double, Double > f = t -> stdNormDist(t);
		return (gaussLegendre(0, x, f));
	}
}