package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import DB.Action;

public class ManageWindow extends JFrame implements ActionListener{

//	static Connection con = null;
//	private static final String url = "jdbc:mysql://localhost:3306/bmt?autReconnect=true&useSSL=false";
//	private static final String user = "root";
//	private static final String pw = "root";
	public static String bid = null;
	public static JTable table;
	public static int row = 0;
	public static int col = 0;
	public static String SelectedBID = null;
	private static DefaultTableModel model;
	private static int cnt = 0;
	private JComboBox comboBox;
	private JTextField text1;

	public static void main(String[] args) throws SQLException{
		ManageWindow frame = new ManageWindow();
		frame.setVisible(true);
	}


	public ManageWindow() throws SQLException {
		super("図書の管理画面");

		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);

		super.setSize(600, 600);
		super.setContentPane( contentPane );
		super.setVisible(true);

		//データベースへの接続
		/*try {
			con = DriverManager.getConnection(url, user, pw);
		}catch (SQLException exception){
				exception.printStackTrace();
			}*/
		Action.Connect();


	//ボタン配置
	JButton Button1 = new JButton("登録");
	Button1.setBounds(40, 20, 140, 40);
	contentPane.add(Button1);
	Button1.addActionListener(this);

	JButton Button2 = new JButton("更新");
	Button2.setBounds(225,  20,  140,  40);
	contentPane.add(Button2);
	Button2.addActionListener(this);

	JButton Button3 = new JButton("delete");
	Button3.setBounds(405, 20, 140, 40);
	contentPane.add(Button3);
	Button3.addActionListener(this);

	JButton Button4 = new JButton("検索");
	Button4.setBounds(490, 70, 75, 35);
	contentPane.add(Button4);
	Button4.addActionListener(this);

	JButton Button5 = new JButton("return");
	Button5.setBounds(22, 520, 108, 31);
	contentPane.add(Button5);
	Button5.addActionListener(this);

	//コンボボックス配置
	cnt = Action.CountVariety();
	String[] comboData = new String[cnt+1];
	ArrayList<String> Varieties = Action.GetVariety();
	comboData[0] = "ジャンルを選択";
	int x = 1;
	int y = 0;
	while (cnt > y){
		comboData[x] = Varieties.get(y);
		System.out.println("cnt:"+cnt+"y:"+y+"x:"+x);
		System.out.println(comboData[y]);
		x++;
		y++;
	}
	comboBox = new JComboBox(comboData);
	comboBox.setBounds(12, 70, 180, 35);
	contentPane.add(comboBox);
	comboBox.setBackground(Color.WHITE);

	//テキストフィールド配置
	text1 = new JTextField();
	text1.setBounds( 200, 70, 280, 35);
	contentPane.add(text1);
	text1.setText("キーワードを入力");

	//テーブル配置
	String[] header = {"BID", "タイトル", "著者名", "ジャンル"};
	model = new DefaultTableModel(header, 0);
	table = new JTable(model){
		public boolean isCellEditable(int row, int col){
			return false;
		}
	};



	//セルの幅を設定
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
	columnModel.getColumn(0).setPreferredWidth(40);
	columnModel.getColumn(1).setPreferredWidth(320);
	columnModel.getColumn(2).setPreferredWidth(110);
	columnModel.getColumn(3).setPreferredWidth(110);


	JScrollPane scroll = new JScrollPane(table);
	scroll.setBounds(10, 120, 565, 390);
	contentPane.add(scroll);

	String sql = "SELECT * FROM BOOK_LIST";
	PreparedStatement ps = Action.con.prepareStatement(sql);
	ResultSet result = ps.executeQuery();
	int i = 0;
	while(result.next()){
		String[] tableDate = {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety")};
		model.insertRow(i,  tableDate);
		i++;
	}


	//マウスリスナの追加
	table.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			row = table.getSelectedRow();
			col = table.getSelectedColumn();
			bid = table.getValueAt(row, 0).toString();
			System.out.println("選択行のBIDは" + bid + "です。");
			System.out.println("行" + row + "::列" + col);
		}
	});

}


	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("登録".equals(ae.getActionCommand())) {
				System.out.println("登録ボタンが押されました");
				dispose();
				new RegistorWindow();
			}else if("更新".equals(ae.getActionCommand())){
				System.out.println("更新ボタンが押されました");
				String[] Info = Action.Display();
				new UpdateWindow(Info[0],Info[1], Info[2], Info[3], Info[4], Info[5], Info[6]);
				dispose();
			}else if("削除".equals(ae.getActionCommand())){
				System.out.println("削除ボタンが押されました");
				String[] Info = Action.Display();
				new DeleateVerify(Info[0], Info[1]);
				dispose();
			}else if("検索".equals(ae.getActionCommand())){
				System.out.println("検索ボタンが押されました");
				model.setRowCount(0);
				int i = 0;
				String Keyword = text1.getText();
				if (Keyword.equals("キーワードを入力")){
					Keyword = "%";
				}
				String Variety = (String)comboBox.getSelectedItem();
				if (Variety.equals("ジャンルを選択")){
					Variety = "%";
				}
				String State = "%";
				ResultSet result = Action.Retrieval(Keyword, Variety, State);
				while (	result.next()){
					String[] tableData
					= {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety")};
				model.insertRow(i,tableData);
				i++;
				}
			}else if("戻る".equals(ae.getActionCommand())){
				System.out.println("戻るボタンが押されました");
				setVisible(false);
				new Top();

			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}


}
