package Validate;

import java.sql.Connection;

import javax.swing.JOptionPane;

public class ErrorDesision {

	private static final int max = 30;
	static Connection con = null;


	private static final String E1000 = "入力フィールドに文字列が入力されていません。入力フィールドに文字列を入力してください。";
	private static final String E1010 = "入力フィールドに入力された文字数が {" + max + "文字}を超えています。";
	private static final String E1020 = "入力フィールドに半角文字が含まれています。";
	private static final String E1021 = "入力フィールドに全角文字が含まれています。";
	private static final String E1030 = "更新対象のデータが見つかりません";
	private static final String E1040 = "表示対象のデータが存在しません。";
	private static final String E1050 = "対象図書が選択されていません。";
	private static final String E1060 = "貸出期間が選択されていません。";

	public static int getMax() {
		return max;
	}

	public static String getE1000(){
		return E1000;
	}

	public static String getE1010(){
		return E1010;
	}

	public static String getE1020(){
		return E1020;
	}

	public static String getE1030(){
		return E1030;
	}

	public static String getE1040(){
		return E1040;
	}


	//更新対象選択チェック
	public void chkSelected(String BID){
		if ( BID == "null" || BID.equals(null)){
			JOptionPane.showMessageDialog(null, E1030);

		}else {
			System.out.println("aaa");
		}
	}

	/*
	//空白チェック
	public static int chkBlank(String str){
		if ( str == null || str.length() == 0){
			return 1000;
		}else {
			return 0;
		}
	}

	//入力文字数チェック
	public static int chkCharCount(String str, int max){
		if ( str.length() > max){
			return 1010;
		}else{
			return 0;
		}
	}

	//入力文字内容チェック
	public static int chkCharContents(String str){
		if ( str.getBytes().length < (str.length() * 3 )){
			return 1020;
		}else {
			return 0;
		}
	}

	//更新データ存在チェック
	public static int chkUpdateData(String BID){
		String sql = "SELECT COUNT(*) AS cnt FRM book_list WHERE BID = \"" + BID + "\"";

		/*
		System.out.println("引数は"+ Main.pid );
		String sql = "SELECT COUNT(*) AS cnt FROM tbl_hoge WHERE PID = \"" + Main.pid + "\"";
		try {
			con = DriverManager.getConnection(Main.getUrl(),Main.getUser(),Main.getPw());
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			result.next();
			//String cnt = result.getString("cnt");
			if ( result.getInt("cnt") == 1 ){
				return 0;
			}else {
				return 1030;
			}
		}catch (SQLException exception){
			exception.printStackTrace();
		}finally {
			if ( con != null ){
				try {
					con.close();
					System.out.println("MySQLをクローズしました。(Validate)");
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(Main.b3, "MySQLのクローズに失敗しました。");
				}
			}
		}
		System.out.println("更新データをチェックします。");

		return 0;
	}

	//表示データ存在チェック
	public static int chkDisplayData(){
		String sql = "SELECT COUNT(*)"
	}
		String sql = "SELECT COUNT(*) AS cnt FROM tbl_hoge;";
		try {
			con = DriverManager.getConnection(Main.getUrl(),Main.getUser(),Main.getPw());
			System.out.println("MySQLに接続しました。(Validate)");
			PreparedStatement ps = con.prepareStatement(sql);
			System.out.println("SQL文を実行しました。");
			ResultSet result = ps.executeQuery();
			result.next();
			String cnt = result.getString("cnt");
			System.out.println( "レコード数は" + cnt + "件です。" );
			if ( result.getInt("cnt") != 0){
				System.out.println("return code:0");
				return 0;
			}else {
				System.out.println("return code:1040");
				return 1040;
			}
		}catch (SQLException exception){
			exception.printStackTrace();
		}finally {
			if ( con != null ){
				try {
					con.close();
					System.out.println("MySQLをクローズしました。(Validate)");
				} catch (SQLException exception){
					JOptionPane.showMessageDialog(Main.b4, "MySQLのクローズに失敗しました。");
				}
			}
		}
		return 0;
	}*/

}
