package util;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;

public class Test {
	public static void main( String[] args ) {

		Connection con = DBConnector.getConnection();
		UserDAO userDAO = new UserDAO();


		for ( int i = 0; i < 50; i++ ) {
			try {
				userDAO.registerUser(con, "user" + i, "user_" + i, "password" );
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
