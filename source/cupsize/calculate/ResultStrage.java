package cupsize.calculate;

import java.util.HashMap;

public class ResultStrage implements Cloneable {
	private double height;
	private double weight;
	private double bust;
	private double uBust;
	private double waist;
	private double hip;
	private double thigh;
	private double GCbust;
	private double GCuBust;
	private double GCwaist;
	private double GChip;
	private double GCthigh;
	private double bustSize;
	private String cupSize;
	private String braFitInfo;
	private String braAvailableInfo;
	private String braAdvice;
	private String bmiStatus;
	private String newBmiStatus;
	private String PIStatus;
	private String bustIndexStatus;
	private double bmi;
	private double newBmi;
	private double cosmeticWeight;
	private double suitableWeight;
	private double bustWeight;
	private double bustVolume;
	private double predictWeight;
	private double pi;
	private double proportionIndex;
	private double bustSizeIndex;
	private double bustIndex;
	
	private HashMap<DataType, Boolean> valueStatus;
	
	public ResultStrage() {
		this.valueStatus = new HashMap<>();
		for (DataType t : DataType.values()) {
			this.valueStatus.put(t, false);
		}
	}
	
	public void input(DataStruct... data) {
		for (DataStruct d : data) {
			this.valueStatus.replace(d.type, true);
			switch (d.type) {
				case HEIGHT:
					this.height = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case BUST:
					this.bust = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case WAIST:
					this.waist = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case HIP:
					this.hip = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case THIGH:
					this.thigh = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case WEIGHT:
					this.weight = d.value;
					if (d.value < 0) this.valueStatus.replace(d.type, false);
					break;
				case UNDER_BUST:
					this.uBust = d.value;
					break;
				case BUST_SIZE:
					this.bustSize = d.value;
					break;
				case GC_BUST:
					this.GCbust = d.value;
					break;
				case GC_UNDER_BUST:
					this.GCuBust = d.value;
					break;
				case GC_WAIST:
					this.GCwaist = d.value;
					break;
				case GC_HIP:
					this.GChip = d.value;
					break;
				case GC_THIGH:
					this.GCthigh = d.value;
					break;
				case BUST_VOLUME:
					this.bustVolume = d.value;
					break;
				case BUST_WEIGHT:
					this.bustWeight = d.value;
					break;
				case PREDICT_WEIGHT:
					this.predictWeight = d.value;
					break;
				case BMI:
					this.bmi = d.value;
					break;
				case NEW_BMI:
					this.newBmi = d.value;
					break;
				case SUITABLE_WEIGHT:
					this.suitableWeight = d.value;
					break;
				case COSMETIC_WEIGHT:
					this.cosmeticWeight = d.value;
					break;
				case PI:
					this.pi = d.value;
					break;
				case PROPORTION_INDEX:
					this.proportionIndex = d.value;
					break;
				case BUST_INDEX:
					this.bustIndex = d.value;
					break;
				case BUST_SIZE_INDEX:
					this.bustSizeIndex = d.value;
					break;
			}
		}
	}
		
	public void inputString(DataType t, String d) {
		this.valueStatus.replace(t, true);
		switch (t) {
			case CUP_SIZE:
				this.cupSize = d;
				break;
			case FIT_SIZE:
				this.braFitInfo = d;
				break;
			case AVAILABLE_SIZE:
				this.braAvailableInfo = d;
				break;
			case ADVICE:
				this.braAdvice = d;
				break;
			case BMI_STATUS:
				this.bmiStatus = d;
				break;
			case NEW_BMI_STATUS:
				this.newBmiStatus = d;
				break;
			case PI_STATUS:
				this.PIStatus = d;
				break;
			case BUST_INDEX_STATUS:
				this.bustIndexStatus = d;
				break;
		}
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public double getBust() {
		return this.bust;
	}
	
	public double getUnderBust() {
		return this.uBust;
	}
	
	public double getWaist() {
		return this.waist;
	}
	
	public double getHip() {
		return this.hip;
	}
	
	public double getThigh() {
		return this.thigh;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public double getGCBust() {
		return this.GCbust;
	}
	
	public double getGCUnderBust() {
		return this.GCuBust;
	}
	
	public double getGCWaist() {
		return this.GCwaist;
	}
	
	public double getGCHip() {
		return this.GChip;
	}
	
	public double getGCThigh() {
		return this.GCthigh;
	}
	
	public double getBustSize() {
		return this.bustSize;
	}
	
	public String getCupSize() {
		return this.cupSize;
	}
	
	public String getFitSize() {
		return this.braFitInfo;
	}
	
	public String getAvailable() {
		return this.braAvailableInfo;
	}
	
	public String getAdvice() {
		return this.braAdvice;
	}
	
	public double getBustWeight() {
		return this.bustWeight;
	}
	
	public double getBustVolume() {
		return this.bustVolume;
	}
	
	public double getPredictWeight() {
		return this.predictWeight;
	}
	
	public String getBMIStatus() {
		return this.bmiStatus;
	}
	
	public String getNewBMIStatus() {
		return this.newBmiStatus;
	}
	
	public String getPIStatus() {
		return this.PIStatus;
	}
	
	public String getBustIndexStatus() {
		return this.bustIndexStatus;
	}
	
	public double getBMI() {
		return this.bmi;
	}
	
	public double getNewBMI() {
		return this.newBmi;
	}
	
	public double getSuitableWeight() {
		return this.suitableWeight;
	}
	
	public double getCosmeticWeight() {
		return this.cosmeticWeight;
	}
	
	public double getPI() {
		return this.pi;
	}
	
	public double getProportionIndex() {
		return this.proportionIndex;
	}
	
	public double getBustSizeIndex() {
		return this.bustSizeIndex;
	}
	
	public double getBustIndex() {
		return this.bustIndex;
	}
	
	public boolean getValueStatus(DataType t) {
		return this.valueStatus.get(t);
	}
	
	@Override
	public ResultStrage clone() {
		ResultStrage rs = new ResultStrage();
		rs.height = this.height;
		rs.weight = this.weight;
		rs.bust = this.bust;
		rs.waist = this.waist;
		rs.hip = this.hip;
		rs.thigh = this.thigh;
		return rs;
	}
}
