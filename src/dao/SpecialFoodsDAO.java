package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import util.TimeUtil;

public class SpecialFoodsDAO {
	PreparedStatement stmt = null;
	ResultSet result = null;
	ArrayList<SpecialFoods> list = null;

	// 全件表示用
	public ArrayList<SpecialFoods> provide( Connection con ) throws SQLException {
		SpecialFoods bean = null;
		list = new ArrayList<SpecialFoods>();

		try {
			// SQL
			String sql = "select * from tokusanhin"
					+ " inner join todofuken on tokusanhin.todofuken_id = todofuken.todofuken"
					+ " order by tokusanhin_sno";
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			while ( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" ),
						result.getString( "todofuken" )
						);

				list.add( bean );
			}
		} catch( SQLException e ) {
			throw new SQLException( e.getMessage() );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 県名別特産品一覧を返す
	 * 特産品(子画面)で特産を表示するため使用
	 * @param con
	 * @param prefecturalName
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SpecialFoods> provide( Connection con, String prefecturalName ) throws SQLException {
		SpecialFoods bean = null;
		list = new ArrayList<SpecialFoods>();
		try {
			// SQL
			String sql = "select * from tokusanhin where todofuken_id = (select todofuken_id from todofuken where todofuken = ?)";
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, prefecturalName );


			result = stmt.executeQuery();
			while ( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" )
						);
				list.add( bean );
			}
		} catch( SQLException e ) {
			throw new SQLException( e.getMessage() );
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 行数を制限して全件取得
	 */
	public ArrayList<SpecialFoods> provide( Connection con, int from, int limit ) throws SQLException {
		SpecialFoods bean = null;
		list = new ArrayList<SpecialFoods>();

		try {
			String sql = "select * from tokusanhin t inner join todofuken td on t.todofuken_id = td.todofuken_id order by tokusanhin_sno limit ? offset ?";
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, limit );
			stmt.setInt( 2, from );

			result = stmt.executeQuery();
			while ( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" ),
						result.getString( "todofuken" ),
						result.getString( "reg_time" ) // TODO 必要性の検証
						);
				list.add( bean );
			}
		} catch ( SQLException e ) {
			throw new SQLException( e.getMessage() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 検索結果を返す
	 */
	public ArrayList<SpecialFoods> provide( Connection con, int from, int limit, String searchTerm ) throws SQLException {
		SpecialFoods bean = null;
		String sql = "select * from tokusanhin t inner join todofuken td on t.todofuken_id = td.todofuken_id where t.hinmei like '%"+ searchTerm + "%' order by tokusanhin_sno limit ? offset ?";
		list = new ArrayList<SpecialFoods>();
		try {
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, limit);
			stmt.setInt( 2, from );
			result = stmt.executeQuery();
			while( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" ),
						result.getString( "todofuken" )
						);
				list.add( bean );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			result.close();
			stmt.close();
		}
		return list;
	}

	/**
	 * ソートした結果を返す
	 */
	public ArrayList<SpecialFoods> provide( Connection con, int from, int limit, String sortColumn, boolean isAsc ) throws SQLException {
		SpecialFoods bean = null;
		list = new ArrayList<SpecialFoods>();
		String sql = "select * from tokusanhin t inner join todofuken td on t.todofuken_id = td.todofuken_id order by t." + sortColumn + " " + ( isAsc ? "asc" : "desc" ) + " offset ? limit ?";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, from );
			stmt.setInt( 2, limit );
			result = stmt.executeQuery();
			while ( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" ),
						result.getString( "todofuken" )
						);
				list.add( bean );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
			throw e;
		} finally {
			result.close();
			stmt.close();
		}
		return list;
	}
	/**
	 * 検索した結果をソートして返す
	 */
	public ArrayList<SpecialFoods> provide( Connection con, int from, int limit, String sortColumn,String searchTerm, boolean isAsc ) throws SQLException {
		SpecialFoods bean = null;
		list = new ArrayList<SpecialFoods>();
		String sql = "select * from tokusanhin t inner join todofuken td on t.todofuken_id = td.todofuken_id where t.hinmei like '%" + searchTerm + "%' order by t." + sortColumn + " " + ( isAsc ? "asc" : "desc" ) + " offset ? limit ?";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, from );
			stmt.setInt( 2, limit );
			result = stmt.executeQuery();
			while ( result.next() ) {
				bean = new SpecialFoods(
						result.getInt( "tokusanhin_sno" ),
						result.getString( "hinmei" ),
						result.getString( "todofuken_id" ),
						result.getString( "todofuken" )
						);
				list.add( bean );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
			throw e;
		} finally {
			result.close();
			stmt.close();
		}
		return list;
	}

	/**
	 * DB内の行数の数を返す
	 */
	public int countRow( Connection con ) throws SQLException {
		int rowCount = 0;
		try {
			String sql = "select count( tokusanhin_sno ) as cnt from tokusanhin";
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			result.next();
			rowCount = result.getInt( "cnt" );
		} catch ( SQLException e ) {
			throw new SQLException( e.getMessage() );
		}
		return rowCount;
	}

	/**
	 * 条件一致する、DB内の行数の数を返す
	 */
	public int countRow( Connection con, String foodName ) throws SQLException {
		int rowCount = 0;
		try {
			String sql = "select count( tokusanhin_sno ) as cnt from tokusanhin where hinmei like '%" + foodName + "%'";
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			result.next();
			rowCount = result.getInt( "cnt" );
		} catch ( SQLException e ) {
			throw new SQLException( e.getMessage() );
		} finally {
			result.close();
			stmt.close();
		}
		return rowCount;
	}

	/**
	 * DBに追加
	 */
	public void insertData( SpecialFoods specialFoods, Connection con ) throws SQLException {
		try {
			con.setAutoCommit( false );
			// ステートメントが書き換えられるため分離
			int newestNumber = getNewestNumber( con );
			String sql = "insert into tokusanhin( tokusanhin_sno, hinmei, todofuken_id, login_id ) values( ?, ?, ?, ? )";
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, newestNumber );
			stmt.setString( 2, specialFoods.getSpecialFoodName() );
			stmt.setString( 3, specialFoods.getPrefecturalID() );
			stmt.setString( 4 , specialFoods.getUpdateUser() );

			stmt.executeUpdate();
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	/**
	 * 最後尾の特産品番号を取得
	 */
	public int getNewestNumber( Connection con ) throws SQLException {
		int newestNumber = 0;
		// tokusanhin_snoを降順に並び替える
		String sql = "select tokusanhin_sno from tokusanhin order by tokusanhin_sno desc";
		try {
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			// 1件分だけ回す
			result.next();
			newestNumber = result.getInt( "tokusanhin_sno" );
		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			result.close();
		}
		return newestNumber + 1;
	}

	/**
	 * 削除処理 tokusanhin_sno を条件にして削除
	 */
	public int delete( Connection con, ArrayList<Integer>foodNumber ) throws SQLException {
		String sql = "delete from tokusanhin where ";
		int deleteRows = 0;
		// 最後の要素まで or でつなげる
		for( int i = 0; i < foodNumber.size(); i++ ) {
			if ( !( i == foodNumber.size() - 1 ) ) {
				sql += String.format( "tokusanhin_sno = %d or ",foodNumber.get( i ) );
			} else {
				sql += String.format( "tokusanhin_sno = %d" , foodNumber.get( i ) );
			}
		}
		try {
			stmt = con.prepareStatement( sql );
			deleteRows = stmt.executeUpdate();
		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return deleteRows;
	}

	/**
	 * 更新処理
	 */
	public void update( Connection con, SpecialFoods specialFoods ) throws SQLException {
		String sql = "update tokusanhin set hinmei = ?, todofuken_id = ?, reg_time = ?, login_id = ? where tokusanhin_sno = ?";
		try {
			con.setAutoCommit( false );
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, specialFoods.getSpecialFoodName() );
			stmt.setString( 2, specialFoods.getPrefecturalID() );
			stmt.setTimestamp( 3, Timestamp.valueOf( TimeUtil.getCurrentTimestamp() ) );
			stmt.setString( 4 , specialFoods.getUpdateUser() );
			stmt.setInt( 5, specialFoods.getSpecialFoodNumber() );

			stmt.executeUpdate();
			con.commit();
		} catch( SQLException e ) {
			e.printStackTrace();
			throw new SQLException( e.getMessage() );
		} finally {
			stmt.close();
		}
	}

	/**
	 * 排他処理
	 */

	/**
	 * 最新のバージョンを取得
	 */
	public String getVersion( Connection con, int specialFoodNumber ) throws SQLException {
		String sql = "select reg_time from tokusanhin where tokusanhin_sno = ?";
		String version = null;
		try {
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, specialFoodNumber );
			result = stmt.executeQuery();
			if ( result.next() ) {
				version = result.getString( "reg_time" );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
			throw new SQLException( e.getMessage() );
		} finally {
			result.close();
			stmt.close();
		}
		return version;
	}

	/**
	 * 重複チェック
	 */
	public boolean isDuplicate( Connection con, SpecialFoods specialFoods ) throws SQLException {
		boolean isDuplicated = false;
		// 特産品名と都道府県が同じなら重複判定
		String sql = "select * from tokusanhin where hinmei = ? and todofuken_id = ?";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, specialFoods.getSpecialFoodName() );
			stmt.setString( 2, specialFoods.getPrefecturalID() );
			result = stmt.executeQuery();
			// 結果が1件でもあるなら
			if ( result.next() ) {
				isDuplicated = true;
			}

		} catch( SQLException e ) {
			e.printStackTrace();
		}  finally {
			result.close();
			stmt.close();
		}
		return isDuplicated;
	}

	/**
	 * 都道府県名を全取得
	 */
	public ArrayList<String> getAllPrefectural( Connection con ) throws SQLException {
		String sql = "select todofuken from todofuken order by todofuken_id";
		ArrayList<String> prefecturalNameList = new ArrayList<String>();
		try {
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			while ( result.next() ) {
				prefecturalNameList.add( result.getString( "todofuken" ) );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
			throw e;
		}
		return prefecturalNameList;
	}

	/**
	 * 都道府県名からIDを返す
	 */
	public String getIdByName( Connection con, String prefecturalName ) throws SQLException {
		String sql = "select todofuken_id from todofuken where todofuken = ?";
		String prefecturalId = "";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, prefecturalName );
			result = stmt.executeQuery();
			result.next();
			prefecturalId = result.getString( "todofuken_id" );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return prefecturalId;
	}

}