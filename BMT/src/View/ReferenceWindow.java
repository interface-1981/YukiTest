package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.Action;

public class ReferenceWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ReferenceWindow frame = new ReferenceWindow("BID");
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public ReferenceWindow(String BID) throws SQLException {
		setTitle("図書情報参照画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);

		//データベースへの接続
		/*try {
			con = DriverManager.getConnection(url, user, pw);
		}catch (SQLException exception){
				exception.printStackTrace();
			}*/
		Action.Connect();

		//BIDを前の画面から持ってきて情報を取得するよう修正必要
		//String sql1 = "SELECT * FROM BOOK_LIST WHERE BID = \"" + BID + "\"";
		//PreparedStatement ps1 = Action.con.prepareStatement(sql1);
		//ResultSet result1 = ps1.executeQuery();
		//result1.next();
		//String label[] = {result1.getString("Title"), result1.getString("Author"), result1.getString("Variety"), result1.getString("Company"), result1.getString("ReleaseDate"), result1.getString("Version")};

		String label[] = Action.Display(BID);
		JLabel label1 = new JLabel(label[1]);
		System.out.println(label[2]);
		label1.setBackground(Color.WHITE);
		label1.setBounds(54, 10, 425, 29);
		label1.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
		contentPane.add(label1);

		JLabel label2 = new JLabel("著者名：" + label[2]);
		label2.setBounds(33, 49, 161, 29);
		contentPane.add(label2);

		JLabel label3 = new JLabel("ジャンル：" + label[3]);
		label3.setBounds(195, 49, 161, 29);
		contentPane.add(label3);

		JLabel label4 = new JLabel("発行社：" + label[4]);
		label4.setBounds(385, 49, 161, 29);
		contentPane.add(label4);

		JLabel label5 = new JLabel("発行日：" + label[6]);
		label5.setBounds(33, 88, 161, 29);
		contentPane.add(label5);

		JLabel label6 = new JLabel("版数：第" + label[5] + "版");
		label6.setBounds(195, 88, 161, 29);
		contentPane.add(label6);

		String[] header = {"借用者名", "貸出日", "貸出期間", "返却予定日", "返却日"};
		model = new DefaultTableModel(header, 0);
		table = new JTable(model){
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 127, 514, 185);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);

		JButton Button1 = new JButton("書評を確認する");
		Button1.setBounds(390, 88, 131, 29);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JButton Button2 = new JButton("キャンセル");
		Button2.setBounds(89, 336, 150, 43);
		contentPane.add(Button2);
		Button2.addActionListener(this);

		JButton Button3 = new JButton("貸出/貸出予約する");
		Button3.setBounds(318, 336, 150, 43);
		contentPane.add(Button3);
		Button3.addActionListener(this);

		
		ResultSet result = Action.LendingRecord(BID);
		int i = 0;
		while(result.next()){
			String DueDate = Action.CalcReturnDate(result.getString("RID"), result.getString("LendingPeriod"));
			String[] tableDate = {result.getString("Name"), result.getString("LoanDate"), result.getString("LendingPeriod")+ "週間", DueDate};
			model.insertRow(i,  tableDate);
			i++;
		}
		//try{
			//con = DriverManager.getConnection(url, user, pw);
			//System.out.println("MySQLに接続しました。(testDB)");
			//String sql2 = "SELECT * FROM RECORD";
			//PreparedStatement ps2 = Action.con.prepareStatement(sql2);
			//ResultSet result2 = ps2.executeQuery();
			//int i = 0;
			//while(result2.next()){
				//String sql3 = "SELECT LoanDate + INTERVAL " + result2.getString("LendingPeriod") + " WEEK as DueDate FROM RECORD WHERE RID = " + result2.getString("RID");
				//System.out.println(sql3);
				//PreparedStatement ps3 = Action.con.prepareStatement(sql3);
				//ResultSet result3 = ps3.executeQuery();
				//result3.next();
				//String[] tableDate2 = {result2.getString("Name"), result2.getString("LoanDate"), result2.getString("LendingPeriod")+ "週間", result3.getString("DueDate")};
				//model.insertRow(i,  tableDate2);
				//i++;
			//}
		//}catch (SQLException exception){
		//	exception.printStackTrace();
		//}
	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("書評を確認する".equals(ae.getActionCommand())) {
				System.out.println("書評を確認するボタンが押されました");
				new ReviewWindow();
			}else if("キャンセル".equals(ae.getActionCommand())){
				System.out.println("キャンセルボタンが押されました");
				dispose();
				new LendingManage();
			}else if("貸出/貸出予約する".equals(ae.getActionCommand())){
				System.out.println("貸出/貸出予約するボタンが押されました");
				new LendingProccess();
				dispose();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
}