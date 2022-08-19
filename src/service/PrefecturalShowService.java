package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.SpecialFoodsDAO;
import util.DBConnector;

public class PrefecturalShowService {
	/**
	 * 都道府県名を全て返す
	 * @return 日本語の都道府県名
	 * @throws SQLException
	 */
	public ArrayList<String> getAllPrefectural() throws SQLException {

		Connection con = null;
		con = DBConnector.getConnection();
		ArrayList<String> list = null;

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();

		try {
			list = specialFoodsDAO.getAllPrefectural( con );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	public String getIdByName( String prefecturalName ) throws SQLException {
		Connection con = null;
		con = DBConnector.getConnection();
		String id = "";
		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();

		try {
			id = specialFoodsDAO.getIdByName( con, prefecturalName );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return id;
	}
}
