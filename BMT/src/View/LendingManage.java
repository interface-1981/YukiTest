package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import DB.Action;

public class LendingManage extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JComboBox comboBox;

	//確認用メインプロセス
		public static void main(String[] args) throws SQLException {
		LendingManage frame = new LendingManage();
		frame.setVisible(true);
	}


	public LendingManage() throws SQLException {
		super("図書貸出管理画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);

		//データベースへの接続
		Action.Connect();

		JButton Button1 = new JButton("参照");
		Button1.setBounds(40, 10, 140, 40);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JButton Button2 = new JButton("貸出/貸出予約");
		Button2.setBounds(200, 10, 140, 40);
		contentPane.add(Button2);
		Button2.addActionListener(this);

		JButton Button3 = new JButton("返却");
		Button3.setBounds(360, 10, 140, 40);
		contentPane.add(Button3);
		Button3.addActionListener(this);

		JButton Button4 = new JButton("検索");
		Button4.setBounds(480, 65, 87, 56);
		contentPane.add(Button4);
		Button4.addActionListener(this);

		JButton Button5 = new JButton("戻る");
		Button5.setBounds(22, 520, 108, 31);
		contentPane.add(Button5);
		Button5.addActionListener(this);


		//テーブル配置
		String[] header = {"BID", "タイトル", "著者名", "ジャンル", "貸出状態"};
		DefaultTableModel model = new DefaultTableModel(header, 0);
		table = new JTable(model){
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};

		//セルの幅を設定
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(300);
		columnModel.getColumn(2).setPreferredWidth(110);
		columnModel.getColumn(3).setPreferredWidth(110);
		columnModel.getColumn(4).setPreferredWidth(60);



		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(10, 130, 565,370);
		contentPane.add(scroll);

		comboBox = new JComboBox();
		comboBox.setBounds(20, 61, 121, 26);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"技術資料", "自己啓蒙", "ビジネス"}));
		contentPane.add(comboBox);
		comboBox.setBackground(Color.WHITE);

		JCheckBox ChexBox1 = new JCheckBox("貸出中");
		ChexBox1.setBounds(150, 58, 80, 32);
		contentPane.add(ChexBox1);

		JCheckBox ChexBox2 = new JCheckBox("貸出可能");
		ChexBox2.setBounds(240, 61, 85, 26);
		contentPane.add(ChexBox2);

		JCheckBox ChexBox3 = new JCheckBox("貸出予約");
		ChexBox3.setBounds(300, 61, 87, 26);
		contentPane.add(ChexBox3);

		JCheckBox ChexBox4 = new JCheckBox("選択しない");
		ChexBox4.setBounds(360, 61, 87, 26);
		contentPane.add(ChexBox4);

		ButtonGroup group = new ButtonGroup();
		group.add(ChexBox1);
		group.add(ChexBox2);
		group.add(ChexBox3);


		textField = new JTextField();
		textField.setText("キーワードを入力してください");
		textField.setBounds(39, 98, 387, 26);
		contentPane.add(textField);
		textField.setColumns(10);



		String sql = "SELECT * FROM BOOK_LIST";
		PreparedStatement ps = Action.con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		int i = 0;
		while(result.next()){
			String[] tableDate = {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety"), result.getString("State")};
			model.insertRow(i,  tableDate);
			i++;
		}

	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("参照".equals(ae.getActionCommand())) {
				System.out.println("参照ボタンが押されました");
				new ReferenceWindow();
				dispose();
			}else if("貸出/貸出予約".equals(ae.getActionCommand())){
				System.out.println("貸出/貸出予約ボタンが押されました");
				new LendingProccess();
			}else if("返却".equals(ae.getActionCommand())){
				System.out.println("返却ボタンが押されました");
				new ReturnProccess();
			}else if("戻る".equals(ae.getActionCommand())){
				System.out.println("戻るボタンが押されました");
				dispose();
				new Top();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}

}
