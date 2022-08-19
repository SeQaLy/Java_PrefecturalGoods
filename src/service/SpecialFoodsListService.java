package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.SpecialFoods;
import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodsListService {
	public ArrayList<SpecialFoods> provide( String prefecturalName ) {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();

		ArrayList<SpecialFoods> list = null;

		try {
			list = specialFoodsDAO.provide( con, prefecturalName );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}

		return list;
	}
	// 全件表示
	public ArrayList<SpecialFoods> provide() {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();

		ArrayList<SpecialFoods> list = null;
		try {
			list = specialFoodsDAO.provide( con );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	// 全件表示(表示制限)
	public ArrayList<SpecialFoods> provide( int from, int limit ) {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		ArrayList<SpecialFoods> list = null;

		try {
			list = specialFoodsDAO.provide( con, from, limit );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	// 検索結果を返す( 特産品名が部分一致 )
	public ArrayList<SpecialFoods> provide( int from, int limit, String searchFood ) throws SQLException {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		ArrayList<SpecialFoods> list = null;
		try {
			list = specialFoodsDAO.provide(con, from, limit, searchFood );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return list;
	}

	// 行数のカウントを返す
	public int countRow() {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		int rowCount = 0;
		try {
			rowCount = specialFoodsDAO.countRow( con );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return rowCount;
	}
	// 条件一致する行数のカウント
	public int countRow( String foodName ) {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		int rowCount = 0;
		try {
			rowCount = specialFoodsDAO.countRow( con, foodName );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return rowCount;
	}
}
