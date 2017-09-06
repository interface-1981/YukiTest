package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DB.Action;

public class UpdateWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	static Connection con = null;
	private static final String url = "jdbc:mysql://localhost:3306/bmt?autReconnect=true&useSSL=false";
	private static final String user = "root";
	private static final String pw = "root";
	public static String Title = null;
	public static String Author = null;
	public static String Variety = null;
	public static String Company = null;
	public static String Version = null;
	public static String ReleaseDate = null;
	public static String bid = null;

	/**
	 * Launch the application.
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		UpdateWindow frame = new UpdateWindow("BID", "Title", "Author", "Variety", "Company", "Version", "ReleaseDate");
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public UpdateWindow(String BID, String Title, String Author, String Variety, String Company, String Version, String ReleaseDate) {
		setTitle("図書情報更新画面");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);
		bid = BID;


		JLabel label1 = new JLabel("図書情報を更新後、修正ボタンをクリックしてください。");
		label1.setBounds(140, 22, 400, 29);
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

		JButton Button2 = new JButton("更新");
		Button2.setBounds(347, 296, 130, 42);
		contentPane.add(Button2);
		Button2.addActionListener(this);

		textField1.setText(Title);
		textField2.setText(Author);
		textField3.setText(Variety);
		textField4.setText(Company);
		textField5.setText(Version);
		textField6.setText(ReleaseDate);

	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("キャンセル".equals(ae.getActionCommand())) {
				System.out.println("キャンセルボタンが押されました");
				//super.setVisible(false);
				new ManageWindow();
				dispose();
			}else if("更新".equals(ae.getActionCommand())){
				System.out.println("更新ボタンが押されました");
				Title = textField1.getText();
				Author = textField2.getText();
				Variety = textField3.getText();
				Company = textField4.getText();
				Version = textField5.getText();
				ReleaseDate = textField6.getText();
				System.out.println(Title + Author + Variety + Company + Version + ReleaseDate );
				Action.Update(bid, Title, Author, Variety, Company, Version, ReleaseDate);
				new ManageWindow();
				dispose();}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
}
