package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DB.Action;
import Validate.ValidateErr;

public class RegistorWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	public static String Title = null;
	public static String Author = null;
	public static String Variety = null;
	public static String Company = null;
	public static String Version = null;
	public static String ReleaseDate = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		RegistorWindow frame = new RegistorWindow();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public RegistorWindow() {
		setTitle("図書情報登録画面");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 399);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);

		Action.Connect();

		JLabel label1 = new JLabel("新規登録図書の詳細情報を入力してください。");
		label1.setBounds(166, 22, 290, 29);
		contentPane.add(label1);

		JLabel label2 = new JLabel("図書名：");
		label2.setBounds(44, 64, 60, 29);
		contentPane.add(label2);
		label2.setHorizontalAlignment(JLabel.RIGHT);

		textField1 = new JTextField();
		textField1.setBounds(116, 69, 417, 19);
		contentPane.add(textField1);
		textField1.setColumns(10);


		JLabel label3 = new JLabel("著者名：");
		label3.setBounds(44, 101, 60, 29);
		contentPane.add(label3);
		label3.setHorizontalAlignment(JLabel.RIGHT);

		textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setBounds(116, 106, 417, 19);
		contentPane.add(textField2);


		JLabel label4 = new JLabel("ジャンル：");
		label4.setBounds(34, 140, 70, 29);
		contentPane.add(label4);
		label4.setHorizontalAlignment(JLabel.RIGHT);

		textField3 = new JTextField();
		textField3.setColumns(10);
		textField3.setBounds(116, 145, 417, 19);
		contentPane.add(textField3);


		JLabel label7 = new JLabel("発行社：");
		label7.setBounds(44, 179, 60, 29);
		contentPane.add(label7);
		label7.setHorizontalAlignment(JLabel.RIGHT);

		textField4 = new JTextField();
		textField4.setColumns(10);
		textField4.setBounds(116, 184, 417, 19);
		contentPane.add(textField4);


		JLabel label6 = new JLabel("版数：");
		label6.setBounds(44, 218, 60, 29);
		contentPane.add(label6);
		label6.setHorizontalAlignment(JLabel.RIGHT);

		textField5 = new JTextField();
		textField5.setColumns(10);
		textField5.setBounds(116, 223, 417, 19);
		contentPane.add(textField5);


		JLabel label5 = new JLabel("発行日：");
		label5.setBounds(44, 257, 60, 29);
		contentPane.add(label5);
		label5.setHorizontalAlignment(JLabel.RIGHT);

		textField6 = new JTextField();
		textField6.setColumns(10);
		textField6.setBounds(116, 262, 417, 19);
		contentPane.add(textField6);



		JButton Button1 = new JButton("キャンセル");
		Button1.setBounds(126, 296, 130, 42);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JButton Button2 = new JButton("登録");
		Button2.setBounds(347, 296, 130, 42);
		contentPane.add(Button2);
		Button2.addActionListener(this);

	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("キャンセル".equals(ae.getActionCommand())) {
				System.out.println("キャンセルボタンが押されました");
				//super.setVisible(false);
				new ManageWindow();
				dispose();
			}else if("登録".equals(ae.getActionCommand())){
				System.out.println("登録ボタンが押されました");
				Title = textField1.getText();
				Author = textField2.getText();
				Variety = textField3.getText();
				Company = textField4.getText();
				Version = textField5.getText();
				ReleaseDate = textField6.getText();
				if (ValidateErr.BlankChk(Title) && ValidateErr.BlankChk(Author) && ValidateErr.BlankChk(Variety)
						&& ValidateErr.BlankChk(Company) && ValidateErr.BlankChk(Version) && ValidateErr.BlankChk(ReleaseDate)){
					if (ValidateErr.CharCntChk(Title, 30) && ValidateErr.CharCntChk(Author, 20) && ValidateErr.CharCntChk(Variety, 20)
							&& ValidateErr.CharCntChk(Company, 20) && ValidateErr.CharCntChk(Version, 2) ){
						System.out.println(Title + Author + Variety + Company + Version + ReleaseDate );
						Action.Registor(Title, Author, Variety, Company, Version, ReleaseDate);
						new ManageWindow();
						dispose();
					}
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
}
