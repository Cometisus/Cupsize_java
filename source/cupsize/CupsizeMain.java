package cupsize;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import cupsize.database.SortElement;
import cupsize.database.SortDirection;
import cupsize.calculate.*;

public class CupsizeMain extends JFrame implements ActionListener {
	private WidgetManager wm;
	private TableManager tm;
	private SearchWidgets sw;
	private DefaultTableModel characterTableModel;
	private CardLayout MainCLayout;
	private CardLayout ResCLayout;
	private CardLayout TableCLayout;
	private JPanel MainCardPanel;
	private JPanel ResCardPanel;
	private JPanel TableCardPanel;
	private JTextField bustInput;
	private JTextField waistInput;
	private JTextField heightInput;
	private JTextField hipInput;
	private JTextField weightInput;
	private JTextField thighInput;
	private JTextField underBustOutput;
	private JTextField bustSizeOutput;
	private JTextField cupSizeOutput;
	private JTextArea bustSizeAdvice;
	private JTextArea fitBraOutput;
	private JTextField availableBraOutput;
	private JTextField GCBustOutput;
	private JTextField GCUBustOutput;
	private JTextField GCWaistOutput;
	private JTextField GCHipOutput;
	private JTextField GCThighOutput;
	private JTextField BustVolumeOutput;
	private JTextField BustWeightOutput;
	private JTextField BustIndexOutput;
	private JTextField BustSizeIndexOutput;
	private JTextField ProportionIndexOutput;
	private JTextField PIOutput;
	private JTextField PIStatusOutput;
	private JTextField BustIndexStatusOutput;
	private JTextField BMIOutput;
	private JTextField BMIStatusOutput;
	private JTextField NewBMIOutput;
	private JTextField NewBMIStatusOutput;
	private JTextField SuitableWeightOutput;
	private JTextField CosmeticWeightOutput;
	private JTextField PredictWeightOutput;
	private JTextField StdErrorOutput;
	private JTextArea bustSizeProbabilityOutput;
	private JTextArea TScoreOutput;
	private JTextArea charTScoreOutput;
	private JCheckBox AverageHeightCheck;
	private JTable characterTable;
	private JRadioButton[] sortDirectionRadio;
	private JComboBox<String> selectElementCombo;
	private JPanel GCPanel;
	
	CupsizeMain (String title) {
		setTitle(title);
		setBounds(100, 100, 960, 640);
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initValues();
		MainCardPanel = setCard();
		inputTextInit();
		
		Container contentPane = getContentPane();
		contentPane.add(MainCardPanel, BorderLayout.CENTER);
	}
	
	private JPanel setCard() {
		JPanel p = new JPanel();
		MainCLayout = new CardLayout();
		p.setLayout(MainCLayout);
		
		p.add(setMainWidgets(), "Main");
		p.add(setDatabaseForms(), "Database");
		
		return p;
	}
	
	private JPanel setMainWidgets() {
		JPanel p = new JPanel();
		
		p.add(setInputForms());
		
		ResCardPanel = setIOWidgets();
		p.add(ResCardPanel);
		
		return p;
	}
	
	private JPanel setIOWidgets() {
		JPanel p = new JPanel();
		ResCLayout = new CardLayout();
		p.setLayout(ResCLayout);
		
		JPanel pInput = new JPanel();
		pInput.add(setMainButtons());
		
		p.add(pInput, "Input");
		p.add(setResultWidgets(), "Result");
		
		return p;
	}
	
	private JPanel setResultWidgets() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JTabbedPane tabbedpane = new JTabbedPane();
		tabbedpane.addTab("Basic", setOutputFormsMain());
		tabbedpane.addTab("Statistics", setStatisticsForms());
		tabbedpane.addTab("Others", setOthersForms());
		
		p.add(tabbedpane);
		p.add(setResultButtons());
		
		return p;
	}
	
	private JPanel setDatabaseForms() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		TableCardPanel = new JPanel();
		TableCLayout = new CardLayout();
		TableCardPanel.setLayout(TableCLayout);
		TableCardPanel.add(setDatabaseTable(), "Table");
		TableCardPanel.add(setDatabaseSearch(), "Search");
		p.add(TableCardPanel);
		
		p.add(setDatabaseButtons());
		return p;
	}
	
	private JPanel setDatabaseSearch() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		sw = new SearchWidgets(this);
		
		JPanel pbtn = new JPanel();
		pbtn.setLayout(new FlowLayout());
		pbtn.add(wm.setButton("??????", "DatabaseTable", this));
		pbtn.add(wm.setButton("??????", "DatabaseSearchStart", this));
		
		p.add(sw.p);
		p.add(pbtn);
		return p;
	}
	
	private JPanel setSortForms() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel pradio = new JPanel();
		pradio.setLayout(new FlowLayout());
		sortDirectionRadio = new JRadioButton[2];
		sortDirectionRadio[0] = new JRadioButton("??????");
		sortDirectionRadio[1] = new JRadioButton("??????", true);
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(sortDirectionRadio[0]);
		bgroup.add(sortDirectionRadio[1]);
		pradio.add(sortDirectionRadio[0]);
		pradio.add(sortDirectionRadio[1]);
		p.add(pradio);
		
		JPanel pcombo = new JPanel();
		pcombo.setLayout(new FlowLayout());
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (SortElement se : SortElement.values()) {
			if (se.getElement().equals("")) continue;
			model.addElement(se.getElement()); 
		}
		model.setSelectedItem(SortElement.NONE.getElement());
		selectElementCombo = new JComboBox<>(model);
		selectElementCombo.setEditable(false);
		pcombo.add(new JLabel("?????????????????????"));
		pcombo.add(selectElementCombo);
		p.add(pcombo);
		
		JPanel pbtn = new JPanel();
		pbtn.add(wm.setButton("?????????", "DatabaseSort", this));
		p.add(pbtn);
		return p;
	}
	
	private JPanel setOthersForms() {
		JPanel p = new JPanel();
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(900, 340));
		sp.setViewportView(setOtherOutputForms());
		p.add(sp);
		return p;
	}
	
	private JPanel setStatisticsForms() {
		JPanel p = new JPanel();
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(900, 340));
		sp.setViewportView(setStatisticsMain());
		p.add(sp);
		return p;
	}
	
	private JPanel setStatisticsMain() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(setStatisticsData1());
		p.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel p1 = new JPanel();
		TScoreOutput = new JTextArea(3, 30);
		TScoreOutput.setLineWrap(true);
		TScoreOutput.setEditable(false);
		p1.add(TScoreOutput);
		p.add(p1);
		p.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel p2 = new JPanel();
		charTScoreOutput = new JTextArea(3, 30);
		charTScoreOutput.setLineWrap(true);
		charTScoreOutput.setEditable(false);
		p2.add(charTScoreOutput);
		p.add(p2);
		return p;
	}
	
	private JPanel setStatisticsData1() {
		wm.wigdgetGBLInit("rl");
		StdErrorOutput = wm.addInputTextGBL("???????????? : ", 30);
		StdErrorOutput.setEditable(false);
		StdErrorOutput.setText(String.valueOf(getData.getStdError()));
		bustSizeProbabilityOutput = wm.addTextAreaGBL("?????? : ", 15, 30);
		bustSizeProbabilityOutput.setEditable(false);
		return wm.setWidgetGBL();
	}
	
	private JPanel setInputForms() {
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		p1.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel form = _setInputForms();
		layout.setConstraints(form, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		AverageHeightCheck = new JCheckBox("????????????????????????????????????");
		AverageHeightCheck.addActionListener(this);
		AverageHeightCheck.setActionCommand("AveHeightChk");
		layout.setConstraints(AverageHeightCheck, gbc);
		
		p1.add(form);
		p1.add(AverageHeightCheck);
		
		p.add(p1);
		return p;
	}
	
	private JPanel _setInputForms() {
		wm.wigdgetGBLInit("rl");
		heightInput = wm.addInputTextGBL("?????? (cm) : ", 30);
		bustInput = wm.addInputTextGBL("????????? (cm) : ", 30, Color.red);
		waistInput = wm.addInputTextGBL("???????????? (cm) : ", 30, Color.red);
		hipInput = wm.addInputTextGBL("????????? (cm) : ", 30);
		weightInput = wm.addInputTextGBL("?????? (kg) : ", 30);
		thighInput = wm.addInputTextGBL("????????? (cm) : ", 30);
		return wm.setWidgetGBL();
	}
	
	private JPanel setOutputFormsMain() {
		JPanel p = new JPanel();
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(900, 340));
		sp.setViewportView(_setOutputFormsMain());
		p.add(sp);
		return p;
	}
	
	private JPanel _setOutputFormsMain() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(setOutputFormsMain1());
		p.add(Box.createRigidArea(new Dimension(10,10)));
		p.add(setOutputFormsMain2());
		p.add(Box.createRigidArea(new Dimension(10,10)));
		GCPanel = setOutputFormsMain3();
		p.add(GCPanel);
		return p;
	}
	
	private JPanel setOutputFormsMain1() {
		wm.wigdgetGBLInit("rl");
		underBustOutput = wm.addInputTextGBL("????????????????????? (cm) : ", 30);
		underBustOutput.setEditable(false);
		bustSizeOutput = wm.addInputTextGBL("?????????????????? (cm) : ", 30);
		bustSizeOutput.setEditable(false);
		cupSizeOutput = wm.addInputTextGBL("?????????????????? : ", 30);
		cupSizeOutput.setEditable(false);
		bustSizeAdvice = wm.addTextAreaGBL("??????????????? : ", 5, 30);
		bustSizeAdvice.setEditable(false);
		return wm.setWidgetGBL();
	}
	
	private JPanel setOutputFormsMain2() {
		wm.wigdgetGBLInit("rl");
		fitBraOutput = wm.addTextAreaGBL("??????????????????????????? : ", 5, 30);
		fitBraOutput.setEditable(false);
		availableBraOutput = wm.addInputTextGBL("?????????????????????????????? : ", 30);
		availableBraOutput.setEditable(false);
		return wm.setWidgetGBL();
	}
	
	private JPanel setOutputFormsMain3() {
		wm.wigdgetGBLInit("rlrlrl");
		GCBustOutput = wm.addInputTextGBL("????????? (cm) : ", 20);
		GCBustOutput.setEditable(false);
		GCUBustOutput = wm.addInputTextGBL("????????????????????? (cm) : ", 20);
		GCUBustOutput.setEditable(false);
		GCWaistOutput = wm.addInputTextGBL("???????????? (cm) : ", 20);
		GCWaistOutput.setEditable(false);
		GCHipOutput = wm.addInputTextGBL("????????? (cm) : ", 20);
		GCHipOutput.setEditable(false);
		GCThighOutput = wm.addInputTextGBL("????????? (cm) : ", 20);
		GCThighOutput.setEditable(false);
		return wm.setWidgetGBL("????????????????????????");
	}
	
	private JPanel setOtherOutputForms() {
		JPanel p = new JPanel();
		p.add(setOtherOutputFormsWeight());
		p.add(Box.createRigidArea(new Dimension(10,10)));
		p.add(setOtherOutputFormsBust());
		p.add(Box.createRigidArea(new Dimension(10,10)));
		p.add(setOtherOutputFormsOthers());
		return p;
	}
	
	private JPanel setOtherOutputFormsBust() {
		wm.wigdgetGBLInit("rl");
		BustIndexOutput = wm.addInputTextGBL("??????????????? : ", 20);
		BustIndexOutput.setEditable(false);
		BustIndexStatusOutput = wm.addInputTextGBL("?????? : ", 20);
		BustIndexStatusOutput.setEditable(false);
		BustSizeIndexOutput = wm.addInputTextGBL("???????????????????????? : ", 20);
		BustSizeIndexOutput.setEditable(false);
		ProportionIndexOutput = wm.addInputTextGBL("??????????????????????????? : ", 20);
		ProportionIndexOutput.setEditable(false);
		PIOutput = wm.addInputTextGBL("PI : ", 20);
		PIOutput.setEditable(false);
		PIStatusOutput = wm.addInputTextGBL("?????? : ", 20);
		PIStatusOutput.setEditable(false);
		return wm.setWidgetGBL("???????????????????????????");
	}
	
	private JPanel setOtherOutputFormsWeight() {
		wm.wigdgetGBLInit("rl");
		BMIOutput = wm.addInputTextGBL("BMI : ", 20);
		BMIOutput.setEditable(false);
		BMIStatusOutput = wm.addInputTextGBL("?????? : ", 20);
		BMIStatusOutput.setEditable(false);
		NewBMIOutput = wm.addInputTextGBL("?????????BMI : ", 20);
		NewBMIOutput.setEditable(false);
		NewBMIStatusOutput = wm.addInputTextGBL("?????? : ", 20);
		NewBMIStatusOutput.setEditable(false);
		SuitableWeightOutput = wm.addInputTextGBL("???????????? : ", 20);
		SuitableWeightOutput.setEditable(false);
		CosmeticWeightOutput = wm.addInputTextGBL("???????????? : ", 20);
		CosmeticWeightOutput.setEditable(false);
		return wm.setWidgetGBL("BMI");
	}
	
	private JPanel setOtherOutputFormsOthers() {
		wm.wigdgetGBLInit("rl");
		BustVolumeOutput = wm.addInputTextGBL("????????????????????? (cm3) : ", 20);
		BustVolumeOutput.setEditable(false);
		BustWeightOutput = wm.addInputTextGBL("????????????????????? (g) : ", 20);
		BustWeightOutput.setEditable(false);
		PredictWeightOutput = wm.addInputTextGBL("???????????? (kg) : ", 20);
		PredictWeightOutput.setEditable(false);
		return wm.setWidgetGBL();
	}
	
	private JPanel setDatabaseTable() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		characterTableModel = tm.makeDefaultTableModel();
		characterTable = new JTable(characterTableModel)  {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(2000, 240);
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		characterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		characterTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane sp = new JScrollPane(characterTable);
		sp.setPreferredSize(new Dimension(850, 300));
		p.add(sp);
		p.add(wm.setButton("??????", "DatabaseSearchForm", this));
		p.add(setSortForms());
		return p;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
			case "Quit":
				System.exit(0);
				break;
			case "Calculate":
				ErrorType err = isCorrectFormat();
				if (err == ErrorType.OK) {
					ResCLayout.show(ResCardPanel, "Result");
					showResult();
				} else {
					wm.errorDialog(this, err.getMessage());
				}
				break;
			case "Clear":
				ResCLayout.show(ResCardPanel, "Input");
				clearInputText();
				break;
			case "Reset":
				ResCLayout.show(ResCardPanel, "Input");
				inputTextInit();
				break;
			case "AveHeightChk":
				heightInput.setEnabled(!AverageHeightCheck.isSelected());
				break;
			case "Database":
				MainCLayout.show(MainCardPanel, "Database");
				break;
			case "DatabaseSort":
				sortDatabase();
				break;
			case "DatabaseSearchStart":
				characterTableModel = tm.search(sw);
				characterTable.setModel(characterTableModel);
			case "DatabaseTable":
				TableCLayout.show(TableCardPanel, "Table");
				break;
			case "DatabaseSearchForm":
				TableCLayout.show(TableCardPanel, "Search");
				break;
			case "Select":
				SelectData();
				break;
			case "Main":
				MainCLayout.show(MainCardPanel, "Main");
				break;
		}
	}
	
	private void sortDatabase() {
		SortDirection sd;
		if (sortDirectionRadio[1].isSelected()) {
			sd = SortDirection.DESC;
		} else {
			sd = SortDirection.ASC;
		}
		String selectElement = (String)selectElementCombo.getSelectedItem();
		SortElement element = null;
		for (SortElement se : SortElement.values()) {
			if (se.getElement().equals(selectElement)) {
				element = se;
				break;
			}
		}
		if (element == null) return;
		characterTableModel = tm.sortElements(element, sd);
		characterTable.setModel(characterTableModel);
	}
	
	private void SelectData() {
		int index = characterTable.getSelectedRow();
		if (index >= 0) {
			bustInput.setText(tm.getData(index, DataType.BUST));
			waistInput.setText(tm.getData(index, DataType.WAIST));
			heightInput.setText(tm.getData(index, DataType.HEIGHT));
			hipInput.setText(tm.getData(index, DataType.HIP));
			weightInput.setText(tm.getData(index, DataType.WEIGHT));
			thighInput.setText(tm.getData(index, DataType.THIGH));
			MainCLayout.show(MainCardPanel, "Main");
			ErrorType err = isCorrectFormat();
			if (err == ErrorType.OK) {
				ResCLayout.show(ResCardPanel, "Result");
				showResult();
			}
		}
	}
	
	private ErrorType isCorrectFormat() {
		if (!isOK(isInput(bustInput))) {
			return ErrorType.NO_INPUT_BUST;
		}
		if (!isOK(isInput(waistInput))) {
			return ErrorType.NO_INPUT_WAIST;
		}
		if (!isOK(isCorrect(heightInput))) {
			return ErrorType.FORMAT_WRONG;
		}
		
		return ErrorType.OK;
	}
	
	private void showResult() {
		ResultStrage rs = startCalc();
		setText(underBustOutput, rs.getUnderBust());
		setText(bustSizeOutput, rs.getBustSize());
		setText(cupSizeOutput, rs.getCupSize());
		setText(bustSizeAdvice, rs.getAdvice());
		setText(fitBraOutput, rs.getFitSize());
		setText(availableBraOutput, rs.getAvailable());
		if (rs.getHeight() != -1) {
			GCPanel.setVisible(true);
			setTextGC(GCOutputSelecter.BUST, rs);
			setTextGC(GCOutputSelecter.UNDER, rs);
			setTextGC(GCOutputSelecter.WAIST, rs);
			setTextGC(GCOutputSelecter.HIP, rs);
			setTextGC(GCOutputSelecter.THIGH, rs);
			setText(PredictWeightOutput, rs.getPredictWeight());
			setText(BMIOutput, rs.getBMI());
			setText(BMIStatusOutput, rs.getBMIStatus());
			setText(NewBMIOutput, rs.getNewBMI());
			setText(NewBMIStatusOutput, rs.getNewBMIStatus());
			setText(SuitableWeightOutput, rs.getSuitableWeight());
			setText(CosmeticWeightOutput, rs.getCosmeticWeight());
			setText(PIOutput, rs.getPI());
			setText(PIStatusOutput, rs.getPIStatus());
			setText(ProportionIndexOutput, rs.getProportionIndex());
			setText(BustSizeIndexOutput, rs.getBustSizeIndex());
			setText(BustIndexOutput, rs.getBustIndex());
			setText(BustIndexStatusOutput, rs.getBustIndexStatus());
		} else {
			GCPanel.setVisible(false);
		}
		setText(BustWeightOutput, rs.getBustWeight());
		setText(BustVolumeOutput, rs.getBustVolume());
		CalculateStatistics cals = new CalculateStatistics();
		StatisticsResult sr = cals.calcStatistics(rs, tm.getBustSizeData());
		bustSizeProbabilityOutput.setText(sr.probability);
		TScoreOutput.setText(sr.TScore);
		charTScoreOutput.setText(String.format("%s\n%s", sr.charTScore, tm.rankStr(rs.getBustSize())));
	}
	
	private ResultStrage startCalc() {
		ResultStrage rs = new ResultStrage();
		rs.input(
			new DataStruct(DataType.BUST, bustInput.getText()),
			new DataStruct(DataType.WAIST, waistInput.getText())
		);
		inputTextFieldData(rs, DataType.HEIGHT, heightInput);
		inputTextFieldData(rs, DataType.HIP, hipInput);
		inputTextFieldData(rs, DataType.WEIGHT, weightInput);
		inputTextFieldData(rs, DataType.THIGH, thighInput);
		Calculate calc = new Calculate(rs);
		int flags = 0;
		if (AverageHeightCheck.isSelected()) {
			flags |= Flags.selectAverageHeight;
		}
		return calc.start(flags);
	}
	
	private void inputTextFieldData(ResultStrage rs, DataType dt, JTextField text) {
		if (!text.getText().equals("")) {
			rs.input(new DataStruct(dt, text.getText()));
		} else {
			rs.input(new DataStruct(dt, -1));
		}
	}
	
	private JPanel setMainButtons() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(wm.setButton("Quit", this));
		p.add(wm.setButton("Clear", this));
		p.add(wm.setButton("Reset", this));
		p.add(wm.setButton("Database", this));
		p.add(wm.setButton("Calculate", this));
		return p;
	}
	
	private JPanel setResultButtons() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(wm.setButton("Quit", this));
		p.add(wm.setButton("Clear", this));
		p.add(wm.setButton("Reset", this));
		p.add(wm.setButton("Database", this));
		p.add(wm.setButton("Calculate", this));
		return p;
	}
	
	private JPanel setDatabaseButtons() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(wm.setButton("Quit", this));
		p.add(wm.setButton("Main", this));
		p.add(wm.setButton("Select", this));
		return p;
	}
	
	private void initValues() {
		wm = new WidgetManager();
		tm = new TableManager();
	}
	
	private void inputTextInit() {
		bustInput.setText("90");
		waistInput.setText("58");
		heightInput.setText("155");
	}
	
	private void clearInputText() {
		bustInput.setText("");
		waistInput.setText("");
		heightInput.setText("");
	}
	
	private void setTextGC(GCOutputSelecter sel, ResultStrage rs) {
		StringBuilder sb = new StringBuilder();
		double tmp, tmp2;
		switch (sel) {
			case BUST:
				tmp = rs.getGCBust();
				sb.append(String.valueOf(tmp));
				sb.append(String.format(" (%+f)", rs.getBust() - tmp));
				setText(GCBustOutput, sb.toString());
			case UNDER:
				tmp = rs.getGCUnderBust();
				sb.append(String.valueOf(tmp));
				sb.append(String.format(" (%+f)", rs.getUnderBust() - tmp));
				setText(GCUBustOutput, sb.toString());
			case WAIST:
				tmp = rs.getGCWaist();
				sb.append(String.valueOf(tmp));
				sb.append(String.format(" (%+f)", rs.getWaist() - tmp));
				setText(GCWaistOutput, sb.toString());
			case HIP:
				tmp = rs.getGCHip();
				sb.append(String.valueOf(tmp));
				tmp2 = rs.getHip();
				if (tmp2 != -1) {
					sb.append(String.format(" (%+f)", tmp2 - tmp));
				}
				setText(GCHipOutput, sb.toString());
			case THIGH:
				tmp = rs.getGCThigh();
				sb.append(String.valueOf(tmp));
				tmp2 = rs.getThigh();
				if (tmp2 != -1) {
					sb.append(String.format(" (%+f)", tmp2 - tmp));
				}
				setText(GCThighOutput, sb.toString());
		}
	}
	
	private void setText(JTextField text, String str) {
		text.setText(str);
	}
	
	private void setText(JTextArea text, double num) {
		text.setText(String.valueOf(num));
	}
	
	private void setText(JTextArea text, String str) {
		text.setText(str);
	}
	
	private void setText(JTextField text, double num) {
		text.setText(String.valueOf(num));
	}
	
	private ErrorType isInput(JTextField InputText) {
		if (InputText.getText().equals("")) {
			return ErrorType.NO_INPUT;
		}
		return isCorrect(InputText);
	}
	
	private ErrorType isCorrect(JTextField InputText) {
		String str = InputText.getText();
		if (str.equals("")) return ErrorType.OK;
		ErrorType err = isNum(str);
		if (!isOK(err)) {
			return err;
		}
		return ErrorType.OK;
	}
	
	private ErrorType isNum(String str) {
		ErrorType ret = ErrorType.FORMAT_WRONG;
		try {
			Double.parseDouble(str);
			ret = ErrorType.OK;
		} catch (NumberFormatException e) {
			/* Do Nothing */
		}
		return ret;
	}
	
	private boolean isOK(ErrorType err) {
		return err == ErrorType.OK;
	}
	
	enum GCOutputSelecter {
		BUST,
		UNDER,
		WAIST,
		HIP,
		THIGH;
	}
}

enum ErrorType {
	OK(null),
	NO_INPUT("?????????????????????"),
	NO_INPUT_BUST("???????????????????????????????????????????????????"),
	NO_INPUT_WAIST("??????????????????????????????????????????????????????"),
	FORMAT_WRONG("?????????????????????"),
	UNKNOWN_ERROR("????????????????????????");
	
	private String Message;
	
	private ErrorType(String Message) {
		this.Message = Message;
	}
	
	public String getMessage() {
		return this.Message;
	}
	
}