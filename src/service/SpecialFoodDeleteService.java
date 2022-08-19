package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodDeleteService {
	public int delete( ArrayList<Integer> foodNumber ) throws SQLException {
		Connection con = null;
		int deleteRows = 0;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodDAO = new SpecialFoodsDAO();

		try {
			deleteRows = specialFoodDAO.delete( con, foodNumber );
		} catch( SQLException e ) {
			e.printStackTrace();
		}

		return deleteRows;
	}
}
