package cupsize;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cupsize.database.*;
import cupsize.calculate.*;

class TableManager {
	private FileIO fio;
	private DefaultTableModel characterTableModel;
	private List<TableDataStruct> originalCharacterList;
	private List<TableDataStruct> selectedCharacterList;
	private List<TableDataStruct> currentCharacterList;
	private DefaultTableModel tableModel;
	private Vector<String> columnName;
	private SortElement current_se;
	private boolean current_d;
	private boolean normalizationFlag;
	private List<Double> bustSizeData;
	
	TableManager() {
		fio = new FileIO();
		this.normalizationFlag = false;
	}
	
	DefaultTableModel makeDefaultTableModel() {
		List<FileDataStruct> characterList = fio.getIndexList();
		bustSizeData = new ArrayList<>();
		columnName = new Vector<>();
		columnName.add("名前");
		columnName.add("登場作品名");
		columnName.add("ブランド名");
		columnName.add("身長");
		columnName.add("バスト");
		columnName.add("ウエスト");
		columnName.add("バストサイズ");
		columnName.add("カップ");
		columnName.add("バスト指数");
		columnName.add("バストサイズ指数");
		columnName.add("プロポーション指数");
		columnName.add("PI");
		columnName.add("乳房の片側重量");
		columnName.add("ヒップ");
		columnName.add("体重");
		columnName.add("太もも");
		originalCharacterList = new ArrayList<>();
		selectedCharacterList = new ArrayList<>();
		currentCharacterList = new ArrayList<>();
		Vector<Vector<String>> data = new Vector<>();
		
		for (int i = 0; i < characterList.size(); i++) {
			Vector<String> d = new Vector<>();
			TableDataStruct tds = new TableDataStruct(characterList.get(i).index);
			ResultStrage rs = calc(i, characterList);
			
			d.add(characterList.get(i).name);
			tds.name = characterList.get(i).name;
			d.add(characterList.get(i).title);
			tds.title = characterList.get(i).title;
			d.add(characterList.get(i).appendix);
			tds.appendix = characterList.get(i).appendix;
			addData(d, rs, DataType.HEIGHT, characterList.get(i).height);
			tds.height = characterList.get(i).height;
			addData(d, rs, DataType.BUST, characterList.get(i).bust);
			tds.bust = characterList.get(i).bust;
			addData(d, rs, DataType.WAIST, characterList.get(i).waist);
			tds.waist = characterList.get(i).waist;
			addData(d, rs, DataType.BUST_SIZE, rs.getBustSize());
			tds.bustSize = rs.getBustSize();
			bustSizeData.add(tds.bustSize);
			addData(d, rs, DataType.CUP_SIZE, rs.getCupSize());
			tds.cupSize = rs.getCupSize();
			addData(d, rs, DataType.BUST_INDEX, rs.getBustIndex());
			tds.bustIndex = rs.getBustIndex();
			addData(d, rs, DataType.BUST_SIZE_INDEX, rs.getBustSizeIndex());
			tds.bustSizeIndex = rs.getBustSizeIndex();
			addData(d, rs, DataType.PROPORTION_INDEX, rs.getProportionIndex());
			tds.proportionIndex = rs.getProportionIndex();
			addData(d, rs, DataType.PI, rs.getPI());
			tds.pi = rs.getPI();
			addData(d, rs, DataType.BUST_WEIGHT, rs.getBustWeight());
			tds.bustWeight = rs.getBustWeight();
			
			addData(d, rs, DataType.HIP, characterList.get(i).hip);
			tds.hip = characterList.get(i).hip;
			addData(d, rs, DataType.WEIGHT, characterList.get(i).weight);
			tds.weight = characterList.get(i).weight;
			addData(d, rs, DataType.THIGH, characterList.get(i).thigh);
			tds.thigh = characterList.get(i).thigh;
			data.add(d);
			currentCharacterList.add(tds);
		}
		
		tableModel = new DefaultTableModel(data, columnName);
		originalCharacterList.addAll(currentCharacterList);
		selectedCharacterList.addAll(originalCharacterList);
		this.current_d = false;
		this.current_se = SortElement.NONE;
		
		return tableModel;
	}
	
	public List<Double> getBustSizeData() {
		return this.bustSizeData;
	}
	
	public String getData(int i, DataType t) {
		int j = currentCharacterList.get(i).index;
		switch (t) {
			case HEIGHT:
				if (originalCharacterList.get(j).height < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).height);
			case BUST:
				if (originalCharacterList.get(j).bust < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).bust);
			case WAIST:
				if (originalCharacterList.get(j).waist < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).waist);
			case HIP:
				if (originalCharacterList.get(j).hip < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).hip);
			case WEIGHT:
				if (originalCharacterList.get(j).weight < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).weight);
			case THIGH:
				if (originalCharacterList.get(j).thigh < 0) {
					return "";
				}
				return String.valueOf(originalCharacterList.get(j).thigh);
		}
		return "";
	}
	
	private void addData(Vector<String> d, ResultStrage rs, DataType t, String v) {
		if (rs.getValueStatus(t)) {
			d.add(v);
		} else {
			d.add("");
		}
	}
	
	private void addData(Vector<String> d, ResultStrage rs, DataType t, double v) {
		if (rs.getValueStatus(t)) {
			d.add(String.valueOf(v));
		} else {
			d.add("");
		}
	}
	
	private void addData(Vector<String> d, String v) {
		if (v != null) {
			d.add(v);
		} else {
			d.add("");
		}
	}
	
	private void addData(Vector<String> d, double v) {
		addData(d, v, false);
	}
	
	private void addData(Vector<String> d, double v, boolean b) {
		if (v >= 0 || b) {
			d.add(String.valueOf(v));
		} else {
			d.add("");
		}
	}
	
	private ResultStrage calc(int i, List<FileDataStruct> characterList) {
		ResultStrage rs = new ResultStrage();
		rs.input(
			new DataStruct(DataType.HEIGHT, characterList.get(i).height),
			new DataStruct(DataType.BUST, characterList.get(i).bust),
			new DataStruct(DataType.WAIST, characterList.get(i).waist),
			new DataStruct(DataType.HIP, characterList.get(i).hip),
			new DataStruct(DataType.WEIGHT, characterList.get(i).weight),
			new DataStruct(DataType.THIGH, characterList.get(i).thigh)
		);
		Calculate calc = new Calculate(rs);
		int flags = 0;
		if (characterList.get(i).height == -1) {
			flags |= Flags.selectAverageHeight;
		}
		return calc.start(flags);
	}
	
	public DefaultTableModel sortElements(SortElement s, SortDirection direction) {
		return sortElements(s, direction.getBool());
	}
	
	public DefaultTableModel sortElements(SortElement s, boolean direction) {
		TableSort ts = new TableSort();
		currentCharacterList = ts.tableSort(selectedCharacterList, s, direction);
		Vector<Vector<String>> data = new Vector<>();
		
		for (int i = 0; i < currentCharacterList.size(); i++) {
			Vector<String> d = new Vector<>();
			
			d.add(currentCharacterList.get(i).name);
			d.add(currentCharacterList.get(i).title);
			d.add(currentCharacterList.get(i).appendix);
			addData(d, currentCharacterList.get(i).height);
			addData(d, currentCharacterList.get(i).bust);
			addData(d, currentCharacterList.get(i).waist);
			addData(d, currentCharacterList.get(i).bustSize, true);
			addData(d, currentCharacterList.get(i).cupSize);
			addData(d, currentCharacterList.get(i).bustIndex, true);
			addData(d, currentCharacterList.get(i).bustSizeIndex, true);
			addData(d, currentCharacterList.get(i).proportionIndex, true);
			addData(d, currentCharacterList.get(i).pi, true);
			addData(d, currentCharacterList.get(i).bustWeight, true);
			
			addData(d, currentCharacterList.get(i).hip);
			addData(d, currentCharacterList.get(i).weight);
			addData(d, currentCharacterList.get(i).thigh);
			data.add(d);
		}
		
		tableModel = new DefaultTableModel(data, columnName);
		this.current_d = direction;
		this.current_se = s;
		
		return tableModel;
	}
	
	private String convChar(String str) {
		if (normalizationFlag) {
			Normalization norm = new Normalization();
			return norm.convChar(str);
		}
		return str;
	}
	
	public DefaultTableModel search(SearchWidgets sw) {
		String str1, str2;
		this.normalizationFlag = sw.NormalizationCheck.isSelected();
		selectedCharacterList.clear();
		selectedCharacterList.addAll(originalCharacterList);
		String str = sw.NameText.getText();
		if (!str.equals("")) {
			List<TableDataStruct> tmp = new ArrayList<>();
			for (TableDataStruct tds : selectedCharacterList) {
				str1 = convChar(tds.name);
				str2 = convChar(str);
				if (str1.indexOf(str2) != -1) tmp.add(tds);
			}
			selectedCharacterList.clear();
			selectedCharacterList.addAll(tmp);
			tmp.clear();
		}
		
		str = sw.TitleText.getText();
		if (!str.equals("")) {
			List<TableDataStruct> tmp = new ArrayList<>();
			for (TableDataStruct tds : selectedCharacterList) {
				str1 = convChar(tds.title);
				str2 = convChar(str);
				if (str1.indexOf(str2) != -1) tmp.add(tds);
			}
			selectedCharacterList.clear();
			selectedCharacterList.addAll(tmp);
			tmp.clear();
		}
		
		str = sw.AppendixText.getText();
		if (!str.equals("")) {
			List<TableDataStruct> tmp = new ArrayList<>();
			for (TableDataStruct tds : selectedCharacterList) {
				str1 = convChar(tds.appendix);
				str2 = convChar(str);
				if (str1.indexOf(str2) != -1) tmp.add(tds);
			}
			selectedCharacterList.clear();
			selectedCharacterList.addAll(tmp);
			tmp.clear();
		}
		
		str1 = (String)sw.CupMinText.getSelectedItem();
		str2 = (String)sw.CupMaxText.getSelectedItem();
		if (!str1.equals("未選択")) {
			List<TableDataStruct> tmp = new ArrayList<>();
			for (TableDataStruct tds : selectedCharacterList) {
				if (tds.cupSize.compareTo(str1) >= 0) tmp.add(tds);
			}
			selectedCharacterList.clear();
			selectedCharacterList.addAll(tmp);
			tmp.clear();
		}
		if (!str2.equals("未選択")) {
			List<TableDataStruct> tmp = new ArrayList<>();
			for (TableDataStruct tds : selectedCharacterList) {
				if (tds.cupSize.compareTo(str2) <= 0) tmp.add(tds);
			}
			selectedCharacterList.clear();
			selectedCharacterList.addAll(tmp);
			tmp.clear();
		}
		
		str1 = sw.MinBustSizeText.getText();
		str2 = sw.MaxBustSizeText.getText();
		double minval, maxval;
		if (!str1.equals("")) {
			try {
				minval = Double.parseDouble(str1);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustSize >= minval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
			}
		}
		if (!str2.equals("")) {
			try {
				maxval = Double.parseDouble(str2);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustSize <= maxval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
				str2 = "";
			}
		}
		
		str1 = sw.MinBustSizeIndexText.getText();
		str2 = sw.MinBustSizeIndexText.getText();
		if (!str1.equals("")) {
			try {
				minval = Double.parseDouble(str1);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustSizeIndex >= minval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
			}
		}
		if (!str2.equals("")) {
			try {
				maxval = Double.parseDouble(str2);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustSizeIndex <= maxval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
				str2 = "";
			}
		}
		
		str1 = sw.MinBustIndexText.getText();
		str2 = sw.MinBustIndexText.getText();
		if (!str1.equals("")) {
			try {
				minval = Double.parseDouble(str1);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustIndex >= minval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
			}
		}
		if (!str2.equals("")) {
			try {
				maxval = Double.parseDouble(str2);
				List<TableDataStruct> tmp = new ArrayList<>();
				for (TableDataStruct tds : selectedCharacterList) {
					if (tds.bustIndex <= maxval) tmp.add(tds);
				}
				selectedCharacterList.clear();
				selectedCharacterList.addAll(tmp);
				tmp.clear();
			} catch (NumberFormatException e) {
				str2 = "";
			}
		}
		
		return sortElements(this.current_se, this.current_d);
	}
}