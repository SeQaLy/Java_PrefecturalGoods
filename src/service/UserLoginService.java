package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.User;
import dao.UserDAO;
import util.DBConnector;

public class UserLoginService {
	public boolean login( String id, String password ) throws Exception {
		Connection con = null;
		con = DBConnector.getConnection();

		boolean isLogin = false;
		UserDAO userDAO = new UserDAO();

		try {
			isLogin = userDAO.login( con, id, password );
			if ( isLogin ) {
				userDAO.loginTimeUpdate( con, id );
			} else {
				throw new Exception( "IDまたは、パスワードが違います。" );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return isLogin;
	}

	public User provide( String id ) {
		Connection con = null;
		con = DBConnector.getConnection();

		User user = null;

		UserDAO userDAO = new UserDAO();
		user = userDAO.provide( con, id );

		return user;

	}
}
