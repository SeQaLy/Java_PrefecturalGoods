package service;

import java.sql.Connection;

import dao.UserDAO;
import util.DBConnector;

public class UserChangePermissionService {
	public void changePermission( String userId, int newPermission ) {
		Connection con = null;
		con = DBConnector.getConnection();

		UserDAO userDAO = new UserDAO();

		userDAO.changePermission( con, userId, newPermission );
	}
}
