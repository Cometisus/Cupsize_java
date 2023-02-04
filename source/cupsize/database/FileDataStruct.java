package cupsize.database;

import java.util.EnumSet;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

public class FileDataStruct {
	public String name;
	public String title;
	public String appendix;
	public String Media;
	public double height;
	public double bust;
	public double waist;
	public double hip;
	public double weight;
	public double thigh;
	public int index;
	
	private EnumSet<Status> flags;
	
	public FileDataStruct(String[] data) {
		this.flags = EnumSet.noneOf(Status.class);
		this.index = -1;
		this.height = -1;
		this.bust = -1;
		this.waist = -1;
		this.hip = -1;
		this.weight = -1;
		this.thigh = -1;
		Deque<String> q = arrayToQueue(data);
		
		for (Status s : Status.values()) {
			inputData(q, s);
		}
	}
	
	public FileDataStruct(List<String> data) {
		this.flags = EnumSet.noneOf(Status.class);
		this.index = -1;
		Deque<String> q = arrayToQueue(data);
		
		for (Status s : Status.values()) {
			inputData(q, s);
		}
	}
	
	private Deque<String> arrayToQueue(String[] data) {
		Deque<String> queue = new ArrayDeque<>();
		for (String s : data) {
			queue.add(s);
		}
		return queue;
	}
	
	private Deque<String> arrayToQueue(List<String> data) {
		Deque<String> queue = new ArrayDeque<>();
		for (String s : data) {
			queue.add(s);
		}
		return queue;
	}
	
	private ParseResult getData(String s) {
		try {
			return new ParseResult(Double.parseDouble(s), true);
		} catch (NumberFormatException e) {
			return new ParseResult(-1, false);
		} catch (NullPointerException e) {
			return new ParseResult(-1, false);
		}
		//return new ParseResult(-1, false);
	}
	
	private void inputData(Deque<String> q, Status s) {
		ParseResult pr;
		if (q.peek() != null) {
			switch (s) {
				case Name:
					if (q.peek() != null) {
						this.name = q.poll();
						flags.add(s);
					} else {
						this.name = "";
					}
					break;
				case Title:
					if (q.peek() != null) {
						this.title = q.poll();
						flags.add(s);
					} else {
						this.title = "";
					}
					break;
				case Appendix:
					if (q.peek() != null) {
						this.appendix = q.poll();
						flags.add(s);
					} else {
						this.appendix = "";
					}
					break;
				case Media:
					if (q.peek() != null) {
						this.Media = q.poll();
						flags.add(s);
					} else {
						this.Media = "";
					}
					break;
				case Height:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.height = pr.result;
						flags.add(s);
					} else {
						this.height = -1;
					}
					break;
				case Bust:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.bust = pr.result;
						flags.add(s);
					} else {
						this.bust = -1;
					}
					break;
				case Waist:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.waist = pr.result;
						flags.add(s);
					} else {
						this.waist = -1;
					}
					break;
				case Hip:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.hip = pr.result;
						flags.add(s);
					} else {
						this.hip = -1;
					}
					break;
				case Weight:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.weight = pr.result;
						flags.add(s);
					} else {
						this.weight = -1;
					}
					break;
				case Thigh:
					pr = getData(q.poll());
					if (pr.isNum) {
						this.thigh = pr.result;
						flags.add(s);
					} else {
						this.thigh = -1;
					}
					break;
			}
		}
	}
	
	public String getString(Status s) {
		switch (s) {
			case Name:
				return this.name;
			case Title:
				return this.title;
			case Appendix:
				return this.appendix;
			default:
				ParseResult r = getDouble(s);
				if (r.isNum != true) return String.valueOf(r.result);
				return null;
		}
	}
	
	public ParseResult getDouble(Status s) {
		switch (s) {
			case Height:
				return new ParseResult(this.height, true);
			case Bust:
				return new ParseResult(this.bust, true);
			case Waist:
				return new ParseResult(this.waist, true);
			case Hip:
				return new ParseResult(this.hip, true);
			case Weight:
				return new ParseResult(this.weight, true);
			case Thigh:
				return new ParseResult(this.thigh, true);
			default:
				return new ParseResult(-1, false);
		}
	}
}

class ParseResult {
	double result;
	boolean isNum;
	
	ParseResult(double result, boolean isNum) {
		this.result = result;
		this.isNum = isNum;
	}
}

enum Status {
	Name,
	Title,
	Appendix,
	Media,
	Height,
	Bust,
	Waist,
	Hip,
	Weight,
	Thigh;
}