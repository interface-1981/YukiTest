package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Action {

	public static Connection con = null;
	private static final String url = "jdbc:mysql://localhost:3306/bmt?autReconnect=true&useSSL=false";
	private static final String user = "root";
	private static final String pw = "root";
	public static String [] Info = null;
	private static String sql = null;
	private static String sql1 = null;
	private static String sql2 = null;
	private static String sql3 = null;


	//DBへの接続
	public static void Connect(){
		try {
			con = DriverManager.getConnection(url, user, pw);
			System.out.println("接続しました");
		}catch (SQLException exception){
			System.out.println("sippai");
			exception.printStackTrace();
		}
	}

	//DBからの切断
	public static void DisConnect(){
		if ( con != null){
			try {
				con.close();
				System.out.println("MySQLをクローズしました。(testDB)");
			} catch (SQLException exception){
				JOptionPane.showMessageDialog(null, "MySQLのクローズに失敗しました。");
			}
		}
	}

	//図書データの登録
	public static void Registor(String Title, String Author, String Variety, String Company, String Version, String ReleaseDate) throws SQLException{
		sql = "INSERT INTO BOOK_LIST (Title, Author, Variety, Company, Version, ReleaseDate) VALUES ("
				+ "\"" + Title + "\"" + "," + "\"" + Author + "\"" + "," + "\"" + Variety + "\"" + "," + "\"" + Company
				+ "\"" + "," + Version + "," + "\"" +ReleaseDate + "\")" ;
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//貸出履歴の登録
	public static void Registor(String BID, String LendingPeriod, String Name, String LoanDate) throws SQLException{
		sql = "INSERT INTO RECORD (BID, LendingPeriod, Name, LoanDate) VALUES ("
				+ "\"" + BID + "\"" + LendingPeriod + "\"" + Name + "\"" + LoanDate + "\")";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//返却時の処理
	public static void Return(String RID, String BID, String Review) throws SQLException{
		sql = null;
		sql1 = null;
		sql2 = null;
		sql3 = null;
		System.out.println("返却処理を実施します");
		sql = "UPDATE RECORD SET ReturnDate = CURDATE() WHERE RID = " + RID;
		System.out.println("返却日の登録：" + sql);
		String cnt = CountReserve(BID);
		System.out.println("貸出予約数は"+cnt);
		if (cnt.equals("0")){
			sql1 = "UPDATE BOOK_LIST SET State = \"未貸出\" WHERE BID = " + BID;
		}else if (cnt.equals("1")){
			sql1 = "UPDATE BOOK_LIST SET State = \"貸出中\" WHERE BID = " + BID;
			String rid = getRID(BID);
			sql2 = "UPDATE RECORD SET LoanDate = CURDATE() WHERE RID = " + rid;
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.executeUpdate();
			System.out.println("自動貸し出し日の登録：" + sql2);
		}else {
			sql1 = "UPDATE BOOK_LIST SET State = \"貸出中(貸出予約あり)\" WHERE BID = \"" + BID + "\"";
			String rid = getRID(BID);
			sql2 = "UPDATE RECORD SET LoanDate = CURDATE() WHERE RID = \"" + rid + "\"";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.executeUpdate();
			System.out.println("自動貸し出し日の登録：" + sql2);
		}
		System.out.println("貸出状態の変更：" + sql1);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.executeUpdate();
		ps1.close();
		sql3 = "INSERT INTO IMPRESSION (RID, BID, Review) VALUES (\"" + RID + "\", \"" + BID + "\", \"" + Review + "\" )";
		System.out.println("impressionテーブルへの登録" + sql3);
		PreparedStatement ps3 = con.prepareStatement(sql3);
		ps3.executeUpdate();
		ps3.close();
		System.out.println("返却処理を実施しました");
	}

	//貸出時の処理
	public static void Rental(String BID, String LendingPeriod, String Name, int status) throws SQLException{
		sql = null;
		sql1 = null;
		//貸出状態を変更(BOOK_LISTへの更新処理)
			//未貸出の図書について
		if (status == 0){
			sql = "UPDATE BOOK_LIST SET State = \"貸出中\" WHERE BID = " + BID;
		}
			//未貸出(貸出予約あり)の図書について
		else if (status == 1){
			String cnt = CountReserve(BID);
			if (cnt.equals("1")){
				sql = "UPDATE BOOK_LIST SET State = \"貸出中\" WHERE BID = " + BID;
			}else{
				sql = "UPDATE BOOK_LIST SET State = \"貸出中（貸出予約あり）\" WHERE BID = " + BID;
			}
		}
			//貸出中の図書について
		else if (status == 2){
			sql = "UPDATE BOOK_LIST SET State = \"貸出中（貸出予約あり）\" WHERE BID = " + BID;
		}
			//貸出中(貸出予約あり)の図書について
		else if (status == 3){
			sql = "UPDATE BOOK_LIST SET State = \"貸出中（貸出予約あり）\" WHERE BID = " + BID;
		}
		//貸出期間の登録(RECORDへの追加処理)
		sql1 = "INSERT INTO RECORD (BID, LendingPeriod, Name, LoanDate, DueDate) VALUES ( \"" + BID + "\", \""
		+ LendingPeriod + "\", \"" + Name + "\", CURDATE() ,CURDATE() + INTERVAL " + LendingPeriod + " WEEK)";

		System.out.println(sql);
		System.out.println(sql1);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.executeUpdate();
		ps1.close();
		System.out.println(sql);
		System.out.println(sql1);
	}

	//貸出予約数チェック
	public static String CountReserve(String BID) throws SQLException {
		String sql = "SELECT COUNT(*) AS CNTRESERVE FROM RECORD WHERE BID = \"" + BID + "\" AND LoanDate IS NULL";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		System.out.println("貸出予約数を取得しました");
		System.out.println(result.getString("cntreserve"));
		return result.getString("cntreserve");
	}

	//対象図書の貸出予約をしているRIDを取得
	public static String getRID(String BID) throws SQLException {
		String sql = "SELECT * FROM RECORD WHERE BID = \"" + BID + "\" AND LoanDate IS NULL";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		String RID = result.getString("RID");
		System.out.println("RIDを取得しました");
		result.close();
		ps.close();
		return RID;
	}

	//検索処理
	public static ResultSet Retrieval(String Keyword, String Variety, String State) throws SQLException{
		sql = null;
		//Keywordが半角英数字のみの場合
		System.out.println(Keyword);
		if (Keyword.equals("2009")){
			sql = "SELECT * FROM BOOK_LIST WHERE (Title LIKE \"%" + Keyword + "%\" OR Author LIKE \"%" + Keyword
					+ "%\" OR Variety LIKE \"%" + Keyword + "%\" OR State LIKE \"%" + Keyword + "%\" OR ReleaseDate "
					+ "LIKE \"%" + Keyword + "%\" OR Company LIKE  \"%" + Keyword + "%\" OR Version LIKE  \"%" + Keyword
					+ "%\") AND Variety LIKE \"" + Variety + "\" AND State LIKE \"" + State + "\"";
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			return result;
		}
		//全角文字が含まれている場合
		else {
			sql = "SELECT * FROM BOOK_LIST WHERE (Title LIKE \"%" + Keyword + "%\" OR Author LIKE \"%" + Keyword
					+ "%\" OR Variety LIKE \"%" + Keyword + "%\" OR State LIKE \"%" + Keyword + "%\" OR Company"
					+ " LIKE  \"%" + Keyword + "%\" OR Version LIKE \"%" + Keyword + "%\") "
					+ "AND Variety LIKE \"" + Variety + "\" AND State LIKE \"" + State + "\"";
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			return result;
		}

	}

	//BID検索表示
	public static String[] Display(String BID) throws SQLException{
		sql = null;
		//String bid = ManageWindow.bid;
		sql = "SELECT * FROM BOOK_LIST WHERE BID = \"" + BID + "\"";
		//System.out.println("実行するSQL文は"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		Info = new String[] {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety"), result.getString("Company"), result.getString("Version"), result.getString("ReleaseDate"), result.getString("State")};
		return Info;
	}


	//データの削除
	public static void Delete(String bid) throws SQLException{
		sql = null;
		sql = "DELETE FROM BOOK_LIST WHERE BID = " + bid;
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		System.out.println(sql);
	}

	//図書データの更新
	public static void Update(String BID, String Title, String Author, String Variety, String Company, String Version,
			String ReleaseDate) throws SQLException{
		sql = null;
		sql = "UPDATE BOOK_LIST SET Title = \"" + Title + "\", Author = \"" + Author + "\", Variety = \""
			+ Variety + "\", Company = \"" + Company + "\", Version = \"" + Version + "\", ReleaseDate = \"" + ReleaseDate + "\""
			+ " WHERE BID = \"" + BID + "\";";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//登録済みジャンルの取得
	public static ArrayList GetVariety() throws SQLException{
		sql = null;
		sql = "SELECT Variety FROM BOOK_LIST GROUP BY Variety";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		int i = 0;
		ArrayList<String> Varieties= new ArrayList<String>();
		while(result.next()){
			Varieties.add(result.getString("Variety"));
			i++;
		}
		return Varieties;
	}

	//登録済みジャンル数の取得
	public static int CountVariety() throws SQLException{
		sql = null;
		int count = 0;
		sql = "SELECT Variety FROM BOOK_LIST GROUP BY Variety";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.last();
		count = result.getRow();

		return count;
	}

	//貸出履歴の取得
	public static ResultSet LendingRecord(String BID) throws SQLException{
		sql = null;
		sql = "SELECT * FROM RECORD WHERE BID = \"" + BID + "\"";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

	//返却予定日の計算
	public static String CalcReturnDate(String RID, String LendingPeriod) throws SQLException{
		sql = null;
		sql = "SELECT LoanDate + INTERVAL "+ LendingPeriod + " WEEK as DueDate FROM RECORD WHERE RID = \"" + RID + "\"";
		PreparedStatement ps = con.prepareStatement(sql);
		//System.out.println(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getString("DueDate");
	}

	//BID検索（record、impressionテーブル）
	public static ResultSet Review(String BID) throws SQLException{
		sql = null;
		sql = "SELECT * FROM RECORD INNER JOIN IMPRESSION ON RECORD.RID = IMPRESSION.RID WHERE RECORD.BID = \"" + BID + "\"";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

	//BID検索（record、impressionテーブル）
	public static ResultSet Review2(String RID) throws SQLException{
		sql = null;
		sql = "SELECT * FROM RECORD INNER JOIN BOOK_LIST ON RECORD.BID = BOOK_LIST.BID WHERE RID = \"" + RID + "\"";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

}
