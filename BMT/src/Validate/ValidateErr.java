package Validate;

import javax.swing.JOptionPane;

public class ValidateErr {

	private static int max = 0;


	private static final String noBlankChk = "入力フィールドに文字列が入力されていません。入力フィールドに文字列を入力してください。";
	private static final String noCharCntChk = "入力フィールドに入力された文字数が {" + max + "文字}を超えています。";
	private static final String noDouble_byteCharChk = "入力フィールドに半角文字が含まれています。";
	private static final String noSingle_byteCharChk = "入力フィールドに全角文字が含まれています。";
	private static final String noUpdateDataTargetChk = "更新対象のデータが見つかりません";
	private static final String noDisplayTargetChk = "表示対象のデータが存在しません。";
	private static final String noSelectedBookChk = "対象図書が選択されていません。";
	private static final String noSelectedLendingPeriodChk = "貸出期間が選択されていません。";


	//空白チェック
	public static boolean BlankChk(String text){
		if ( text == null || text.length() == 0 ){
			JOptionPane.showMessageDialog(null, noBlankChk);
			return false;
		}else {
			return true;
		}
	}

	//更新対象選択有無チェック
	public static Boolean SelectedBookChk(String BID){
		//System.out.println("check start　"+BID);
		if ( BID == null || BID.length() == 0){
			JOptionPane.showMessageDialog(null, noSelectedBookChk);
			//System.out.println("false");
			return false;
		}else {
			//System.out.println("true");
			return true;
		}
	}

	//文字数チェック
	public static Boolean CharCntChk(String text, int max){
		ValidateErr.max = max;
		if (text.length() > max ){
			JOptionPane.showMessageDialog(null, noCharCntChk);
			return false;
		}else {
			return true;
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
