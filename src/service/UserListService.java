package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.User;
import dao.UserDAO;
import util.DBConnector;

public class UserListService {
	public ArrayList<User> provide( int from, int limit ) {
		Connection con = null;
		con = DBConnector.getConnection();

		ArrayList<User> list = null;
		UserDAO userDAO = new UserDAO();

		try {
			list = userDAO.provide( con, from, limit );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	public int countRow() {
		Connection con = null;
		con = DBConnector.getConnection();
		int rowCount = 0;
		UserDAO userDAO = new UserDAO();
		try {
			rowCount = userDAO.countRow( con );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return rowCount;
	}
}
