package Validate;

import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class ValidateErr {



	private static final String noBlankChk = "入力フィールドに文字列が入力されていません。入力フィールドに文字列を入力してください。";
	private static final String noCharCntChk1 = "入力フィールドに入力された文字数が {";
	private static final String noCharCntChk2 = "文字}を超えています。";
	private static final String noSingle_byteCharChk = "入力フィールドに半角文字が含まれています。";
	private static final String noDouble_byteCharChk = "入力フィールドに全角文字が含まれています。";
	private static final String noUpdateDataTargetChk = "更新対象のデータが見つかりません";
	private static final String noDisplayTargetChk = "表示対象のデータが存在しません。";
	private static final String noSelectedBookChk = "対象図書が選択されていません。";
	private static final String noSelectedLendingPeriodChk = "貸出期間が選択されていません。";
	private static final String noCharContentChk = "入力内容に誤りがあります。";
	private static final String noDateChk = "入力した日付に誤りがあります。";
	private static final String noValueChk = "入力した値にアルファベットが含まれています。";
	private static final String noPeriodChk = "貸出期間が選択されていません。";

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
		if (text.length() > max ){
			JOptionPane.showMessageDialog(null, noCharCntChk1 + max + noCharCntChk2);
			return false;
		}else {
			return true;
		}
	}

	//日付チェック
	public static Boolean DateChk(String strDate){
		strDate = strDate.replace('-', '/');
		DateFormat format = DateFormat.getDateInstance();
		//日付/時刻解析を厳密に行うかどうかを設定する。
		format.setLenient(false);
		try {
			format.parse(strDate);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, noDateChk);
			return false;
		}
	}

	//半角文字存在チェック
	public static Boolean Single_byteCharChk(String str) {
		if ( str.length() == (str.length() * 1) ){
			return true;
		}else {
			JOptionPane.showMessageDialog(null, noSingle_byteCharChk);
			return false;
		}
	}

	//全角文字存在チェック
	public static Boolean Double_byteCharChk(String str){
		int i = str.length();
		int x = 0;
		String[] strArray = str.split("");
		Boolean hantei = true;
		while ( x < i) {
			if ( strArray[x].getBytes().length == 2 || strArray[x].getBytes().length == 3){
				hantei = false;
			}else if ( strArray[x].getBytes().length == 1){
			}
			x++;
		}
		if ( hantei == false){
			JOptionPane.showMessageDialog(null, noDouble_byteCharChk);
			return false;
		}else {
			return true;
		}
	}

	//半角数値チェック
	public static Boolean ValueChk(String str){
		System.out.println(str);
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(str);
		if( m.find() ){
			return true;
		}else {
			JOptionPane.showMessageDialog(null, noValueChk);
			return false;
		}
	}

	//貸出期間選択チェック
	public static Boolean PeriodChk(String LoanPeriod){
		if (LoanPeriod.equals("0")){
			JOptionPane.showMessageDialog(null, noPeriodChk);
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
