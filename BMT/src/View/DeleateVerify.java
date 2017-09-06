package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DB.Action;

public class DeleateVerify extends JFrame implements ActionListener{

	private JPanel contentPane;
	public static String bid = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DeleateVerify frame = new DeleateVerify("Title", "BID");
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public DeleateVerify(String BID, String Title) {
		setTitle("図書情報削除確認画面");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 250);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);
		bid = BID;

		JLabel label1 = new JLabel(Title);
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setBounds(10, 20, 390, 30);
		contentPane.add(label1);

		JLabel label2 = new JLabel("をデータベースから削除してもよろしいですか？");
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setBounds(40, 50, 300, 30);
		contentPane.add(label2);

		JButton btnNewButton = new JButton("いいえ");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(40, 100, 140, 40);
		contentPane.add(btnNewButton);

		JButton button = new JButton("はい");
		button.addActionListener(this);
		button.setBounds(210, 100, 140, 40);
		contentPane.add(button);
	}

	//各ボタン押下時の動作
	public void actionPerformed( ActionEvent ae ){
		try {
			if("いいえ".equals(ae.getActionCommand())) {
				dispose();
				System.out.println("いいえボタンが押されました");
				new ManageWindow();
			}else if("はい".equals(ae.getActionCommand())){
				Action.Delete(bid);
				System.out.println("はいボタンが押されました");
				JOptionPane.showMessageDialog(null,  "対象図書を削除しました。");
				dispose();
				new ManageWindow();
			}
		} catch (Exception ex) {
			//TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}
}

