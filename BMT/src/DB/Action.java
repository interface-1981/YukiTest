package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import View.ManageWindow;

public class Action {

	public static Connection con = null;
	private static final String url = "jdbc:mysql://localhost:3306/bmt?autReconnect=true&useSSL=false";
	private static final String user = "root";
	private static final String pw = "root";
	public static String [] Info = null;


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
		String sql = "INSERT INTO BOOK_LIST (Title, Author, Variety, Company, Version, ReleaseDate) VALUES ("
				+ "\"" + Title + "\"" + "," + "\"" + Author + "\"" + "," + "\"" + Variety + "\"" + "," + "\"" + Company
				+ "\"" + "," + Version + "," + "\"" +ReleaseDate + "\")" ;
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//貸出履歴の登録
	public static void Registor(String BID, String LendingPeriod, String Name, String LoanDate) throws SQLException{
		String sql = "INSERT INTO RECORD (BID, LendingPeriod, Name, LoanDate) VALUES ("
				+ "\"" + BID + "\"" + LendingPeriod + "\"" + Name + "\"" + LoanDate + "\")";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//返却時の処理
	public static void Return(String RID, String BID, String Review) throws SQLException{
		String sql = "UPDATE RECORD SET ReturnDate  RID = CURDATE() WHERE = " + RID;
		String sql1 = "UPDATE BOOK_LIST SET State = \"未貸出\" WHERE RID = " + BID;
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.executeUpdate();
		String sql2 = "INSERT INTO IMPRESSION";
	}

	//貸出時の処理
	public static void Rental(String BID, String LendingPeriod, String Name) throws SQLException{
		//貸出状態を変更
		String sql = "UPDATE BOOK_LIST SET State = \"貸出中\" WHERE BID = " + BID;
		//貸出期間の登録
		String sql1 = "INSERT INTO RECORD (BID, LendingPeriod, Name, LoanDate, DueDate) VALUES ( \"" + BID + "\", \""
		+ LendingPeriod + "\", \"" + Name + "\", CURDATE() ,CURDATE() + INTERVAL " + LendingPeriod + " WEEK)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.executeUpdate();
		System.out.println(sql);
		System.out.println(sql1);
	}


	/*
	//ジャンルのみ又はキーワードのみ又は貸出状態のみの検索
	public static void Retrieval(String[] Keyword){
		//ジャンルのみ又は貸出状態のみの場合
		if (Keyword[0].equals("State") || Keyword[0].equals("Variety")){
			String sql = "SELECT * FROM BOOK_LIST WHERE " + Keyword[0] + " = \"" + Keyword[1] + "\"";
			System.out.println(sql);
		}
		//キーワードのみの検索の場合
		else if (Keyword[0].equals("Keyword")){
			//Keyword が 半角英数字のみの場合
			if (Keyword[1] == "a" ){
				String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword[1] + "%\" OR Author LIKE \"%" + Keyword[1]
						+ "%\" OR Variety LIKE \"%" + Keyword[1] + "%\" OR State LIKE \"%" + Keyword[1] + "%\" OR ReleaseDate "
						+ "LIKE \"%" + Keyword[1] + "%\" OR Company LIKE  \"%" + Keyword[1] + "%\" OR Version LIKE  \"%" + Keyword[1] + "%\"";
				System.out.println(sql);
			}
			//Keyword に全角文字が含まれる場合
			else{
				String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword[1] + "%\" OR Author LIKE \"%" + Keyword[1]
						+ "%\" OR Variety LIKE \"%" + Keyword[1] + "%\" OR State LIKE \"%" + Keyword[1] + "%\" OR Company"
						+ " LIKE  \"%" + Keyword[1] + "%\" OR Version LIKE \"%" + Keyword[1] + "%\"";
				System.out.println(sql);
			}
		}
	}


	public static void Retrieval(String[] Keyword1, String[] Keyword2){
		if (Keyword1[0].equals("Keyword") || Keyword2[0].equals("Keyword")){
			if (Keyword1[0].equals("Keyword")){
				//半角英数字のみの場合
				if (Keyword1[1] == "a"){
					String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword1[1] + "%\" OR Author LIKE \"%" + Keyword1[1]
							+ "%\" OR Variety LIKE \"%" + Keyword1[1] + "%\" OR State LIKE \"%" + Keyword1[1] + "%\" OR ReleaseDate "
							+ "LIKE \"%" + Keyword1[1] + "%\" OR Company LIKE  \"%" + Keyword1[1] + "%\" OR Version LIKE  \"%" + Keyword1[1] + "%\"";
					System.out.println(sql);
				}
				//全角文字が含まれている場合
				else {
					String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword1[1] + "%\" OR Author LIKE \"%" + Keyword1[1]
							+ "%\" OR Variety LIKE \"%" + Keyword1[1] + "%\" OR State LIKE \"%" + Keyword1[1] + "%\" OR Company"
							+ " LIKE  \"%" + Keyword1[1] + "%\" OR Version LIKE \"%" + Keyword1[1] + "%\"";
					System.out.println(sql);
				}
			}else if (Keyword2[1].equals("Keyword")){
				//半角英数字のみの場合
				if (Keyword2[1] == "a"){
					String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword2[1] + "%\" OR Author LIKE \"%" + Keyword2[1]
							+ "%\" OR Variety LIKE \"%" + Keyword2[1] + "%\" OR State LIKE \"%" + Keyword2[1] + "%\" OR ReleaseDate "
							+ "LIKE \"%" + Keyword2[1] + "%\" OR Company LIKE  \"%" + Keyword2[1] + "%\" OR Version LIKE  \"%" + Keyword2[1] + "%\"";
					System.out.println(sql);
				}
				//全角文字が含まれている場合
				else {
					String sql = "SELECT * FROM BOOK_LIST WHERE Title LIKE \"%" + Keyword2[1] + "%\" OR Author LIKE \"%" + Keyword2[1]
							+ "%\" OR Variety LIKE \"%" + Keyword2[1] + "%\" OR State LIKE \"%" + Keyword2[1] + "%\" OR Company"
							+ " LIKE  \"%" + Keyword2[1] + "%\" OR Version LIKE \"%" + Keyword2[1] + "%\"";
					System.out.println(sql);
				}
			}
		}
	}*/

	//検索処理
	public static ResultSet Retrieval(String Keyword, String Variety, String State) throws SQLException{
		//Keywordが半角英数字のみの場合
		if (Keyword == "a"){
			String sql = "SELECT * FROM BOOK_LIST WHERE (Title LIKE \"%" + Keyword + "%\" OR Author LIKE \"%" + Keyword
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
			String sql = "SELECT * FROM BOOK_LIST WHERE (Title LIKE \"%" + Keyword + "%\" OR Author LIKE \"%" + Keyword
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
	public static String[] Display() throws SQLException{
		String bid = ManageWindow.bid;
		String sql = "SELECT * FROM BOOK_LIST WHERE BID = \"" + bid + "\"";
		//System.out.println("実行するSQL文は"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		Info = new String[] {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety"), result.getString("Company"), result.getString("Version"), result.getString("ReleaseDate"), result.getString("State")};
		return Info;
	}
	public static String[] Display(String BID) throws SQLException{
		//String bid = ManageWindow.bid;
		String sql = "SELECT * FROM BOOK_LIST WHERE BID = \"" + BID + "\"";
		//System.out.println("実行するSQL文は"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		Info = new String[] {result.getString("BID"), result.getString("Title"), result.getString("Author"), result.getString("Variety"), result.getString("Company"), result.getString("Version"), result.getString("ReleaseDate"), result.getString("State")};
		return Info;
	}


	//データの削除
	public static void Delete(String bid) throws SQLException{
		String sql = "DELETE FROM BOOK_LIST WHERE BID = " + bid;
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
		System.out.println(sql);
	}

	//図書データの更新
	public static void Update(String BID, String Title, String Author, String Variety, String Company, String Version,
			String ReleaseDate) throws SQLException{
		String sql = "UPDATE BOOK_LIST SET Title = \"" + Title + "\", Author = \"" + Author + "\", Variety = \""
			+ Variety + "\", Company = \"" + Company + "\", Version = \"" + Version + "\", ReleaseDate = \"" + ReleaseDate + "\""
			+ " WHERE BID = \"" + BID + "\";";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}

	//登録済みジャンルの取得
	public static ArrayList GetVariety() throws SQLException{
		String sql = "SELECT Variety FROM BOOK_LIST GROUP BY Variety";
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
		int count = 0;
		String sql = "SELECT Variety FROM BOOK_LIST GROUP BY Variety";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		result.last();
		count = result.getRow();

		return count;
	}

	//貸出履歴の取得
	public static ResultSet LendingRecord(String BID) throws SQLException{
		String sql = "SELECT * FROM RECORD WHERE BID = \"" + BID + "\"";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

	//返却予定日の計算
	public static String CalcReturnDate(String RID, String LendingPeriod) throws SQLException{
		String sql = "SELECT LoanDate + INTERVAL "+ LendingPeriod + " WEEK as DueDate FROM RECORD WHERE RID = \"" + RID + "\"";
		PreparedStatement ps = con.prepareStatement(sql);
		//System.out.println(sql);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getString("DueDate");
	}

	//BID検索（record、impressionテーブル）
	public static ResultSet Review(String BID) throws SQLException{
		String sql = "SELECT * FROM RECORD INNER JOIN IMPRESSION ON RECORD.RID = IMPRESSION.RID WHERE RECORD.BID = \"" + BID + "\"";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

	//BID検索（record、impressionテーブル）
	public static ResultSet Review2(String RID) throws SQLException{
		String sql = "SELECT * FROM RECORD INNER JOIN BOOK_LIST ON RECORD.BID = BOOK_LIST.BID WHERE RID = \"" + RID + "\"";
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}

}
