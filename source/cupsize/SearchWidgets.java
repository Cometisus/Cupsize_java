package cupsize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import cupsize.calculate.CalculationBra;

class SearchWidgets {
	JPanel p;
	JTextField NameText;
	JTextField TitleText;
	JTextField AppendixText;
	JTextField MinBustSizeText;
	JTextField MaxBustSizeText;
	JTextField MinBustSizeIndexText;
	JTextField MaxBustSizeIndexText;
	JTextField MinBustIndexText;
	JTextField MaxBustIndexText;
	JComboBox<String> CupMinText;
	JComboBox<String> CupMaxText;
	JCheckBox NormalizationCheck;
	
	private List<String> cup;
	private CupsizeMain cm;
	
	SearchWidgets(CupsizeMain cm) {
		this.cm = cm;
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		makeArray();
		p.add(setNameSearch());
		p.add(setTitleSearch());
		p.add(setAppendixSearch());
		p.add(setBustSizeSearch());
		p.add(setCupSearch());
		p.add(setBustIndexSearch());
		p.add(setBustSizeIndexSearch());
		p.add(setCheckbox());
	}
	
	private void makeArray() {
		int i = 1;
		CalculationBra cb = new CalculationBra();
		cup = new ArrayList<>();
		cup.add("未選択");
		while (true) {
			double x = 2.5 * i;
			String str = cb.calculateCupSize(x, true);
			if (cup.indexOf(str) == -1) {
				cup.add(str);
			} else {
				break;
			}
			i++;
		}
	}
	
	private JCheckBox setCheckbox() {
		NormalizationCheck = new JCheckBox("表記揺れ", false);
		return NormalizationCheck;
	}
	
	private JPanel setNameSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("名前");
		NameText = new JTextField(30);
		
		p.add(label);
		p.add(NameText);
		
		return p;
	}
	
	private JPanel setTitleSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("登場作品名");
		TitleText = new JTextField(30);
		
		p.add(label);
		p.add(TitleText);
		
		return p;
	}
	
	private JPanel setAppendixSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("ブランド名");
		AppendixText = new JTextField(30);
		
		p.add(label);
		p.add(AppendixText);
		
		return p;
	}
	
	private JPanel setBustSizeSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("バストサイズ");
		MinBustSizeText = new JTextField(30);
		JLabel label2 = new JLabel("〜");
		MaxBustSizeText = new JTextField(30);
		
		p.add(label);
		p.add(MinBustSizeText);
		p.add(label2);
		p.add(MaxBustSizeText);
		
		return p;
	}
	
	private JPanel setBustSizeIndexSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("バストサイズ指数");
		MinBustSizeIndexText = new JTextField(30);
		JLabel label2 = new JLabel("〜");
		MaxBustSizeIndexText = new JTextField(30);
		
		p.add(label);
		p.add(MinBustSizeIndexText);
		p.add(label2);
		p.add(MaxBustSizeIndexText);
		
		return p;
	}
	
	private JPanel setBustIndexSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("バスト指数");
		MinBustIndexText = new JTextField(30);
		JLabel label2 = new JLabel("〜");
		MaxBustIndexText = new JTextField(30);
		
		p.add(label);
		p.add(MinBustIndexText);
		p.add(label2);
		p.add(MaxBustIndexText);
		
		return p;
	}
	
	private JPanel setCupSearch() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("カップ");
		DefaultComboBoxModel<String> minModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> maxModel = new DefaultComboBoxModel<>();
		for (String str : cup) {
			minModel.addElement(str);
			maxModel.addElement(str);
		}
		CupMinText = new JComboBox<>(minModel);
		CupMinText.setEditable(false);
		JLabel label2 = new JLabel("〜");
		CupMaxText = new JComboBox<>(maxModel);
		CupMaxText.setEditable(false);
		
		p.add(label);
		p.add(CupMinText);
		p.add(label2);
		p.add(CupMaxText);
		
		return p;
	}
}