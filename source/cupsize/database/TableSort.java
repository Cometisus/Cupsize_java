package cupsize.database;

import java.util.ArrayList;
import java.util.List;

public class TableSort {
	private List<TableDataStruct> oldList;
	boolean direction;
	
	public List<TableDataStruct> tableSort(List<TableDataStruct> list, SortElement s, boolean direction) {
		List<TableDataStruct> newList = new ArrayList<>();
		this.direction = direction;
		
		if (s != SortElement.NONE) {
			oldList = list;
			newList = sort(s);
		} else {
			newList.addAll(list);
		}
		
		return newList;
	}
	
	private List<TableDataStruct> sort(SortElement s) {
		List<TableDataStruct> list = new ArrayList<>();
		list.addAll(oldList);
		msort(list, 0, this.oldList.size() - 1, s);
		return list;
	}
	
	private void msort(List<TableDataStruct> list, int left, int right, SortElement s) {
		int mid, i, j, k, n;
		
		if (left >= right) return;
		
		mid = (left + right) / 2;
		msort(list, left, mid, s);
		msort(list, mid + 1, right, s);
		
		List<TableDataStruct> tmpList = new ArrayList<>();
		for (i = 0; i <= mid; i++) tmpList.add(list.get(i));
		for (i = mid + 1, j = right; i <= right; i++, j--) tmpList.add(list.get(j));
		
		i = left;
		j = right;
		for (k = left; k <= right; k++) {
			if (!cmp(tmpList, i, j, s)) {
				list.set(k, tmpList.get(i++));
			} else {
				list.set(k, tmpList.get(j--));
			}
		}
	}
	
	private boolean cmp(List<TableDataStruct> tmpList, int i, int j, SortElement s) {
		switch (s) {
			case NAME:
				return _cmp(tmpList.get(i).name, tmpList.get(j).name);
			case TITLE:
				return _cmp(tmpList.get(i).title, tmpList.get(j).title);
			case APPENDIX:
				return _cmp(tmpList.get(i).appendix, tmpList.get(j).appendix);
			case MEDIA:
				return _cmp(tmpList.get(i).media, tmpList.get(j).media);
			case HEIGHT:
				return _cmp(tmpList.get(i).height, tmpList.get(j).height);
			case BUST:
				return _cmp(tmpList.get(i).bust, tmpList.get(j).bust);
			case WAIST:
				return _cmp(tmpList.get(i).waist, tmpList.get(j).waist);
			case HIP:
				return _cmp(tmpList.get(i).hip, tmpList.get(j).hip);
			case WEIGHT:
				return _cmp(tmpList.get(i).weight, tmpList.get(j).weight);
			case THIGH:
				return _cmp(tmpList.get(i).thigh, tmpList.get(j).thigh);
			case BUST_SIZE:
				return _cmp(tmpList.get(i).bustSize, tmpList.get(j).bustSize);
			case CUP_SIZE:
				return _cmp(tmpList.get(i).cupSize, tmpList.get(j).cupSize);
			case BUST_INDEX:
				return _cmp(tmpList.get(i).bustIndex, tmpList.get(j).bustIndex);
			case BUST_SIZE_INDEX:
				return _cmp(tmpList.get(i).bustSizeIndex, tmpList.get(j).bustSizeIndex);
			case PROPORTION_INDEX:
				return _cmp(tmpList.get(i).proportionIndex, tmpList.get(j).proportionIndex);
			case PI:
				return _cmp(tmpList.get(i).pi, tmpList.get(j).pi);
			case BUST_WEIGHT:
				return _cmp(tmpList.get(i).bustWeight, tmpList.get(j).bustWeight);
		}
		return false;
	}
	
	private boolean _cmp(double p1, double p2) {
		if (this.direction) {
			return p1 > p2;
		} else {
			return p1 < p2;
		}
	}
	
	private boolean _cmp(String p1, String p2) {
		if (this.direction) {
			return p1.compareTo(p2) > 0;
		} else {
			return p1.compareTo(p2) < 0;
		}
	}
}