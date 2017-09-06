package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Top extends JFrame implements ActionListener{

	private JPanel contentPane;
	public static Top frame;
	public static void main(String[] args){

		frame = new Top();
		frame.setVisible(true);
	}

	public Top() {

		//JFrameのコンストラクタ
		super("トップ画面");

		//画面コンポーネントの初期化と配置
		JPanel basePanel = new JPanel();


		basePanel.setLayout(null);

		//「図書管理プログラム」ラベル
		JLabel label1 = new JLabel( "図書管理プログラム" );
		basePanel.add( label1 );
		label1.setBounds( 170, 10, 200, 30 );

		//「図書の管理」ボタン
		JButton Menu1 = new JButton("図書の管理");
		Menu1.setBounds(100, 50, 250, 50);
		Menu1.addActionListener(this);
		basePanel.add( Menu1 );

		//「図書の貸出/返却」ボタン
		JButton Menu2 = new JButton("図書の貸出/返却");
		Menu2.setBounds(100, 150, 250, 50);
		Menu2.addActionListener(this);
		basePanel.add( Menu2 );

		super.setContentPane( basePanel );
		super.setSize( 450, 300 );
		super.setVisible(true);

	}

	public void actionPerformed(ActionEvent ae ) {

		try {

			//図書の管理画面への遷移
			if("図書の管理".equals(ae.getActionCommand())) {
				dispose();
				new ManageWindow();
			}else if("図書の貸出/返却".equals(ae.getActionCommand())){
				dispose();
				new LendingManage();
			}
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}
	}

}
