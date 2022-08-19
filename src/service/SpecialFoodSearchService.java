package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.SpecialFoods;
import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodSearchService {
	public ArrayList<SpecialFoods> provide( int from, int limit, String searchTerm ) throws SQLException {
		Connection con = null;
		con = DBConnector.getConnection();
		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		ArrayList<SpecialFoods> list = null;
		try {
			list = specialFoodsDAO.provide( con, from, limit, searchTerm );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}
}
