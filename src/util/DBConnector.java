package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	public static Connection getConnection() {
		try {
			Class.forName( "org.postgresql.Driver" );
			String url = "jdbc:postgresql://localhost:5432/koshu2-3";
			Connection conn = DriverManager.getConnection( url, "postgres", "postgres" );
			return conn;
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		} catch ( SQLException e ) {
			e.printStackTrace();
			return null;
		}
	}
	// コミット処理
	public static void commit( Connection connection ) {
		if ( connection == null ) {
			return;
		}

		try {
			connection.commit();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}

	// ロールバック
	public static void rollback( Connection connection ) {
		if ( connection == null ) {
			return;
		}

		try {
			connection.rollback();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}

	// クローズ
	public static void close( Connection connection ) {
		if ( connection == null ) {
			return;
		}

		try {
			connection.close();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}
}
