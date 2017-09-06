package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LendingProccess extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LendingProccess frame = new LendingProccess();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public LendingProccess() {
		setTitle("貸出/貸出予約処理画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		JLabel label = new JLabel("選択された図書名");
		label.setBounds(58, 22, 301, 29);
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"借用期間を選択してください", "1週間", "2週間", "3週間", "4週間"}));
		comboBox.setBounds(115, 129, 214, 29);
		contentPane.add(comboBox);
	}
	
	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("貸出/貸出予約".equals(ae.getActionCommand())) {
				System.out.println("貸出/貸出予約ボタンが押されました");
			}else if("キャンセル".equals(ae.getActionCommand())){
				System.out.println("キャンセルボタンが押されました");
				dispose();
				new ReferenceWindow();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
	
}