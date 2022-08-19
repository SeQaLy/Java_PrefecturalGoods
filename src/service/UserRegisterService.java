package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import util.DBConnector;

public class UserRegisterService {
	public void registerUser( String name, String id, String password ) throws SQLException {
		Connection con = null;
		con = DBConnector.getConnection();
		UserDAO userDAO = new UserDAO();
		try {
			userDAO.registerUser( con, name, id, password );
		} catch( SQLException e ) {
			throw e;
		}
	}
}
