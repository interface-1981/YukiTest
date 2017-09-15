package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import DB.Action;

public class ReturnProccess extends JFrame implements ActionListener {


	private JPanel contentPane;
	private JTextPane textPane;
	private String bid = null;

	public static void main(String[] args) {
		ReturnProccess frame = new ReturnProccess("bid");
		frame.setVisible(true);
	}


	public ReturnProccess(String BID) {
		super("図書返却処理画面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setVisible(true);
		bid = BID;

		textPane = new JTextPane();
		textPane.setBounds(25, 65, 384, 161);
		contentPane.add(textPane);

		JLabel label1 = new JLabel("書評を記入し、返却ボタンを押下してください。");
		label1.setBounds(25, 10, 360, 30);
		contentPane.add(label1);

		JLabel label2 = new JLabel("（書評の記入は任意です）");
		label2.setBounds(241, 36, 168, 30);
		contentPane.add(label2);

		JButton Button1 = new JButton("キャンセル");
		Button1.setBounds(72, 251, 120, 39);
		contentPane.add(Button1);
		Button1.addActionListener(this);

		JButton Button2 = new JButton("返却");
		Button2.setBounds(234, 251, 120, 39);
		contentPane.add(Button2);
		Button2.addActionListener(this);
	}

	//各ボタン押下時の動作
	public void actionPerformed(ActionEvent ae ) {

		try {
			if("キャンセル".equals(ae.getActionCommand())) {
				System.out.println("キャンセルボタンが押されました");
				System.out.println(bid);
				new LendingManage();
				dispose();
			}else if("返却".equals(ae.getActionCommand())){
				System.out.println("返却ボタンが押されました");
				System.out.println(bid);
				String RID = Action.getRID2(bid);
				System.out.println(RID + bid + textPane.getText());
				Action.Return(RID, bid, textPane.getText());
				new LendingManage();
				dispose();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}

}
