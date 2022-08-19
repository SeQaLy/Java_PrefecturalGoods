package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.SpecialFoods;
import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodSortService {
	public ArrayList<SpecialFoods> sort( int from, int limit, String sortColumn, boolean isAsc ) throws SQLException {
		Connection con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		ArrayList<SpecialFoods> list = null;
		try {
			list = specialFoodsDAO.provide( con, from, limit, sortColumn, isAsc );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 検索した結果をソート
	 */
	public ArrayList<SpecialFoods> sort( int from, int limit, String sortColumn,String searchTerm, boolean isAsc ) {
		Connection con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		ArrayList<SpecialFoods> list = null;
		try {
			list = specialFoodsDAO.provide( con, from, limit, sortColumn, searchTerm, isAsc );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}
}
