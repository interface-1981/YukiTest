package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import DB.Action;

public class ReviewWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private String bid = null;
	private ResultSet result;
	private String partition = "***********************************************";
	private String Review = partition;

	/**
	 * Launch the application.
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ReviewWindow frame = new ReviewWindow("BID");
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 */
	public ReviewWindow(String BID) throws SQLException {
		setTitle("書評一覧");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);
		String[] Info = Action.Display(BID);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 51, 560, 244);
		contentPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		result = Action.Review(BID);
		while(result.next()){
			if (result.getString("Review").equals("") || result.getString("Review") == null){

			}else {
				Review = Review + "\n" + result.getString("Review") + "\n" + result.getString("ReturnDate") + "  " + result.getString("Name") + "\n" + partition;
			}
		}

		if ( Review.equals("***********************************************")){
			Review = "書評はありません。";
		}
		textPane.setText(Review);
		//JTextPane textPane = new JTextPane();
		//textPane.setText("本文①\r\n記入日　氏名\r\n\r\n本文②\r\n記入日　氏名\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・");
		scrollPane.setViewportView(textPane);

		JButton Button1 = new JButton("閉じる");
		Button1.setBounds(197, 305, 168, 32);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JLabel label = new JLabel(Info[1]);
		label.setBounds(50, 10, 460, 25);
		label.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(label);
	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("閉じる".equals(ae.getActionCommand())) {
				System.out.println("閉じるボタンが押されました");
				dispose();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
}