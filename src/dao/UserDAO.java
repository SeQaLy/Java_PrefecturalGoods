package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import util.TimeUtil;
import util.Useful;

public class UserDAO {
	PreparedStatement stmt = null;
	ResultSet result = null;
	ArrayList<User> list = null;

	// 全件取得
	public ArrayList<User> provide( Connection con ) throws SQLException {
		User user = null;
		list = new ArrayList<User>();
		String sql = "select * from login";
		try {
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			while( result.next() ) {
				user = new User(
						result.getString( "login_id" ),
						result.getString( "password" ),
						result.getString( "name" ),
						result.getInt( "type" ),
						result.getString( "login_time" )
						);
				list.add( user );
			}
		} catch( SQLException e ) {
			throw new SQLException( e.getMessage() );
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			result.close();
			stmt.close();
		}
		return list;
	}

	// 全権表示(表示制限)
		public ArrayList<User> provide( Connection con, int from, int limit ) throws SQLException {
			User user = null;
			list = new ArrayList<User>();
			String sql = "select * from login order by login_id offset ? limit ?";
			try {
				stmt = con.prepareStatement( sql );
				stmt.setInt( 1, from );
				stmt.setInt( 2, limit );
				result = stmt.executeQuery();
				while( result.next() ) {
					user = new User(
							result.getString( "login_id" ),
							result.getString( "name" ),
							result.getInt( "type" ),
							result.getString( "login_time" ),
							Useful.typeToAuth( result.getInt( "type" ) )
							);
					list.add( user );
				}
			} catch( SQLException e ) {
				throw new SQLException( e.getMessage() );
			} catch( Exception e ) {
				e.printStackTrace();
			} finally {
				result.close();
				stmt.close();
			}
			return list;
		}

	// ログインしたユーザー１件分のデータを取得
	public User provide( Connection con, String id ) {
		String sql = "select * from login where login_id = ?";
		User user = null;
		try {
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, id );
			result = stmt.executeQuery();
			result.next();
			// 1件分のUserを作成
			user = new User(
					result.getString( "login_id" ),
					result.getString( "password" ),
					result.getString( "name" ),
					result.getInt( "type" ),
					result.getString( "login_time" )
					);
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return user;
	}

	// 新規登録
	public void registerUser( Connection con, String name, String id, String password ) throws SQLException {
		String sql = "insert into login values( ?, ?, ?, ? , ? )";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setString( 1, id ); // USER ID
			stmt.setString( 2 , password ); // PASSWORD
			stmt.setString( 3, name ); // NAME
			stmt.setInt( 4, 3 ); // TYPE * 権限
			stmt.setTimestamp( 5, Timestamp.valueOf( TimeUtil.getCurrentTimestamp() ) );
			stmt.executeUpdate();
		} catch( SQLException e ) {
			e.printStackTrace();
			throw new SQLException( "このIDは既に使用されています。" );
		}
	}

	// ログイン時刻更新
	public void loginTimeUpdate( Connection con, String id ) {
		String sql = "update login set login_time = ? where login_id = ?";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setTimestamp( 1, Timestamp.valueOf( TimeUtil.getCurrentTimestamp() ) );
			stmt.setString( 2, id );
			stmt.executeUpdate();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}


	// IDとパスワードを調べる
	public boolean login( Connection con, String id, String password ) throws SQLException {
		String sql = "select * from login where login_id = ? and password = ?";
		boolean isLogin = false;
		try {
			stmt = con.prepareStatement( sql );
			stmt.setString( 1 , id );
			stmt.setString( 2 , password );
			result = stmt.executeQuery();

			if ( result.next() ) {
				isLogin = true;
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return isLogin;
	}

	// ユーザー権限変更
	public void changePermission( Connection con, String userId, int newPermission ) {
		String sql = "update login set type = ? where login_id = ?";
		try {
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, newPermission );
			stmt.setString( 2 , userId );
			stmt.executeUpdate();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * DB内の行数の数を返す
	 */
	public int countRow( Connection con ) throws SQLException {
		int rowCount = 0;
		try {
			String sql = "select count( login_id ) as cnt from login";
			stmt = con.prepareStatement( sql );
			result = stmt.executeQuery();
			result.next();
			rowCount = result.getInt( "cnt" );
		} catch ( SQLException e ) {
			throw new SQLException( e.getMessage() );
		}
		return rowCount;
	}
}
