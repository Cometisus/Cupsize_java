package cupsize.calculate;

public class DataStruct {
	public DataType type;
	public double value;
	
	public DataStruct(DataType t, String s) {
		this.type = t;
		this.value = Double.parseDouble(s);
	}
	
	public DataStruct(DataType t, double d) {
		this.type = t;
		this.value = d;
	}
}