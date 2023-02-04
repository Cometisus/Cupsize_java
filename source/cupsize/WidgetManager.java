package cupsize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class WidgetManager {
	private List<ObjectTypeStrage> widgetList;
	private GridBagLayout GBLayout;
	private GridBagConstraints gbc;
	private int xSize;
	private int insetsX;
	private int insetsY;
	private String pos;
	
	WidgetManager() {
		widgetList = new ArrayList<>();
	}
	
	public void wigdgetGBLInit() {
		wigdgetGBLInit(2, "ii");
	}
	
	public void wigdgetGBLInit(String pos) {
		wigdgetGBLInit(pos.length(), pos, 0, 0);
	}
	
	public void wigdgetGBLInit(int xSize) {
		wigdgetGBLInit(xSize, StringUtil.generateRepetitionString("i", xSize), 0, 0);
	}
	
	public void wigdgetGBLInit(String pos, int insets) {
		wigdgetGBLInit(pos.length(), pos, insets, insets);
	}
	
	public void wigdgetGBLInit(String pos, int insetsX, int insetsY) {
		wigdgetGBLInit(pos.length(), pos, insetsX, insetsY);
	}
	
	public void wigdgetGBLInit(int xSize, int insets) {
		wigdgetGBLInit(xSize, StringUtil.generateRepetitionString("i", xSize), insets, insets);
	}
	
	public void wigdgetGBLInit(int xSize, int insetsX, int insetsY) {
		wigdgetGBLInit(xSize, StringUtil.generateRepetitionString("i", xSize), insetsX, insetsY);
	}
	
	public void wigdgetGBLInit(int xSize, String pos) {
		wigdgetGBLInit(xSize, pos, 0, 0);
	}
	
	public void wigdgetGBLInit(int xSize, String pos, int insets) {
		wigdgetGBLInit(xSize, pos, insets, insets);
	}
	
	public void wigdgetGBLInit(int xSize, String pos, int insetsX, int insetsY) {
		GBLayout = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		this.xSize = xSize;
		this.insetsX = insetsX;
		this.insetsY = insetsY;
		widgetList.clear();
		String buf;
		if (pos == null) {
			buf = StringUtil.generateRepetitionString("i", xSize);
		} else {
			buf = pos.toLowerCase();
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < buf.length(); i++) {
			if (i >= xSize) break;
			switch (buf.charAt(i)) {
				case 'c':
					sb.append("c");
					break;
				case 'l':
					sb.append("l");
					break;
				case 'r':
					sb.append("r");
					break;
				default:
					sb.append("i");
			}
		}
		if (buf.length() < xSize) {
			sb.append(StringUtil.generateRepetitionString("i", xSize - buf.length()));
		}
		this.pos = sb.toString();
	}
	
	private int StrToPos(char c) {
		switch (c) {
			case 'l':
				return GridBagConstraints.WEST;
			case 'r':
				return GridBagConstraints.EAST;
			default:
				return GridBagConstraints.CENTER;
		}
	}
	
	public JTextField addInputTextGBL(String title) {
		return addInputTextGBL(title, "", 20, Color.BLACK);
	}
	public JTextField addInputTextGBL(String title, Color fg) {
		return addInputTextGBL(title, "", 20, fg);
	}
	public JTextField addInputTextGBL(String title, int columns) {
		return addInputTextGBL(title, "", columns, Color.BLACK);
	}
	public JTextField addInputTextGBL(String title, int columns, Color fg) {
		return addInputTextGBL(title, "", columns, fg);
	}
	public JTextField addInputTextGBL(String title, String text) {
		return addInputTextGBL(title, text, 20, Color.BLACK);
	}
	public JTextField addInputTextGBL(String title, String text, Color fg) {
		return addInputTextGBL(title, text, 20, fg);
	}
	public JTextField addInputTextGBL(String title, String text, int columns) {
		return addInputTextGBL(title, text, columns, Color.BLACK);
	}
	public JTextField addInputTextGBL(String title, String text, int columns, Color fg) {
		JLabel label = new JLabel(title);
		label.setForeground(fg);
		widgetList.add(new ObjectTypeStrage(label));
		JTextField inputText = new JTextField(text, columns);
		widgetList.add(new ObjectTypeStrage(inputText));
		return inputText;
	}
	
	public JTextArea addTextAreaGBL(String title, boolean wrap) {
		return addTextAreaGBL(title, "", 5, 20, wrap, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, boolean wrap, Color fg) {
		return addTextAreaGBL(title, "", 5, 20, wrap, fg);
	}
	public JTextArea addTextAreaGBL(String title, String text, boolean wrap) {
		return addTextAreaGBL(title, text, 5, 20, wrap, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, String text, boolean wrap, Color fg) {
		return addTextAreaGBL(title, text, 5, 20, wrap, fg);
	}
	public JTextArea addTextAreaGBL(String title, int rows, int columns, boolean wrap) {
		return addTextAreaGBL(title, "", rows, columns, wrap, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, int rows, int columns, boolean wrap, Color fg) {
		return addTextAreaGBL(title, rows, columns, wrap, fg);
	}
	public JTextArea addTextAreaGBL(String title, String text, int rows, int columns,boolean wrap) {
		return addTextAreaGBL(title, text, rows, columns, wrap, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title) {
		return addTextAreaGBL(title, "", 5, 20, true, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, Color fg) {
		return addTextAreaGBL(title, "", 5, 20, true, fg);
	}
	public JTextArea addTextAreaGBL(String title, String text) {
		return addTextAreaGBL(title, text, 5, 20, true, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, String text, Color fg) {
		return addTextAreaGBL(title, text, 5, 20, true, fg);
	}
	public JTextArea addTextAreaGBL(String title, int rows, int columns) {
		return addTextAreaGBL(title, "", rows, columns, true, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, int rows, int columns, Color fg) {
		return addTextAreaGBL(title, rows, columns, true, fg);
	}
	public JTextArea addTextAreaGBL(String title, String text, int rows, int columns) {
		return addTextAreaGBL(title, text, rows, columns, true, Color.BLACK);
	}
	public JTextArea addTextAreaGBL(String title, String text, int rows, int columns, boolean wrap, Color fg) {
		JLabel label = new JLabel(title);
		label.setForeground(fg);
		widgetList.add(new ObjectTypeStrage(label));
		JTextArea inputText = new JTextArea(text, rows, columns);
		inputText.setLineWrap(wrap);
		widgetList.add(new ObjectTypeStrage(inputText));
		return inputText;
	}
	
	public JPanel setWidgetGBL(String title) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel(title));
		p.add(setWidgetGBL());
		return p;
	}
	
	public JPanel setWidgetGBL() {
		JPanel p = new JPanel();
		p.setLayout(GBLayout);
		int[] posNum = new int[xSize];
		for (int i = 0; i < xSize; i++) {
			posNum[i] = StrToPos(pos.charAt(i));
		}
		for (int i = 0; i < widgetList.size(); i++) {
			gbc.gridx = i % xSize;
			gbc.gridy = i / xSize;
			gbc.anchor = posNum[i % xSize];
			gbc.insets = new Insets(insetsY, insetsX, insetsY, insetsX);
			ObjectType ot = widgetList.get(i).getObjectType();
			switch (ot) {
				case Label:
					JLabel label = (JLabel)widgetList.get(i).getObject();
					p.add(label);
					GBLayout.setConstraints(label, gbc);
					break;
				case InputText:
					JTextField text = (JTextField)widgetList.get(i).getObject();
					p.add(text);
					GBLayout.setConstraints(text, gbc);
					break;
				case TextArea:
					JTextArea textArea = (JTextArea)widgetList.get(i).getObject();
					p.add(textArea);
					GBLayout.setConstraints(textArea, gbc);
					break;
			}
		}
		
		return p;
	}
	
	public JButton setButton(String str, ActionListener l) {
		return setButton(str, str, l);
	}
	
	public JButton setButton(String str, String cmd, ActionListener l) {
		JButton btn = new JButton(str);
		btn.addActionListener(l);
		btn.setActionCommand(cmd);
		return btn;
	}
	
	public void errorDialog(Component c, String message) {
		JOptionPane.showMessageDialog(c, message, "Error",
			JOptionPane.ERROR_MESSAGE);
	}
	
	public void errorDialog(Component c, String message, String title) {
		JOptionPane.showMessageDialog(c, message, title,
			JOptionPane.ERROR_MESSAGE);
	}
}

class ObjectTypeStrage {
	private Object obj;
	private ObjectType objtype;
	
	ObjectTypeStrage(Object obj) {
		this.obj = obj;
		if (obj instanceof JLabel) {
			objtype = ObjectType.Label;
		} else if (obj instanceof JTextField) {
			objtype = ObjectType.InputText;
		} else if (obj instanceof JTextArea) {
			objtype = ObjectType.TextArea;
		}
	}
	
	public Object getObject() {
		return obj;
	}
	
	public ObjectType getObjectType() {
		return objtype;
	}
}

enum ObjectType {
	Label,
	InputText,
	TextArea
};