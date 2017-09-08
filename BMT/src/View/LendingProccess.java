package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DB.Action;

public class LendingProccess extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private String bid = null;
	private int x = 0;
	private String rid = null;
	private JComboBox comboBox;
	private String LoanPeriod = null;

	/**
	 * Launch the application.
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		LendingProccess frame = new LendingProccess(0, "00001");
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public LendingProccess(int info, String BID) throws SQLException {
		setTitle("貸出/貸出予約処理画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		x = info;
		bid = BID;

		String[] result = Action.Display(bid);

		JLabel label = new JLabel("タイトル：" + result[1]);
		label.setBounds(10, 30, 430, 29);
		label.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(label);

		JLabel label_1 = new JLabel("借用者名：");
		label_1.setBounds(32, 80, 71, 29);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("貸出期間：");
		label_2.setBounds(32, 129, 71, 29);
		contentPane.add(label_2);

		textField = new JTextField();
		textField.setBounds(115, 80, 214, 29);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton Button1 = new JButton("キャンセル");
		Button1.setBounds(69, 191, 119, 35);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JButton Button2 = new JButton("貸出/貸出予約");
		Button2.setBounds(243, 191, 119, 35);
		contentPane.add(Button2);
		Button2.addActionListener(this);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"借用期間を選択してください", "1週間", "2週間", "3週間", "4週間"}));
		comboBox.setBounds(115, 129, 214, 29);
		contentPane.add(comboBox);
	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("貸出/貸出予約".equals(ae.getActionCommand())) {
				System.out.println("貸出/貸出予約ボタンが押されました");
				String Period = (String)comboBox.getSelectedItem();
				if (Period.equals("借用期間を選択してください")){
					LoanPeriod = "0";
				}else if (Period.equals("1週間")){
					LoanPeriod = "1";
				}else if (Period.equals("2週間")){
					LoanPeriod = "2";
				}else if (Period.equals("3週間")){
					LoanPeriod = "3";
				}else if (Period.equals("4週間")){
					LoanPeriod = "4";
				}
				String Name = textField.getText();
				Action.Rental(bid, LoanPeriod, Name);
				dispose();
				if (x == 0){
					new LendingManage();
				}else if (x == 1){
					new ReferenceWindow(bid);
				}
			}else if("キャンセル".equals(ae.getActionCommand())){
				System.out.println("キャンセルボタンが押されました");
				dispose();
				if (x == 0){
					new LendingManage();
				}else if (x == 1){
					new ReferenceWindow(bid);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}

}