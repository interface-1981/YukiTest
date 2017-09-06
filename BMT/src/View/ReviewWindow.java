package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ReviewWindow extends JFrame implements ActionListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ReviewWindow frame = new ReviewWindow();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public ReviewWindow() {
		setTitle("書評一覧");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 51, 560, 244);
		contentPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		textPane.setText("本文①\r\n記入日　氏名\r\n\r\n本文②\r\n記入日　氏名\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・\r\n　・");
		scrollPane.setViewportView(textPane);

		JButton Button1 = new JButton("閉じる");
		Button1.setBounds(197, 305, 168, 32);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JLabel label = new JLabel("選択図書名を表示");
		label.setBounds(216, 10, 161, 25);
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