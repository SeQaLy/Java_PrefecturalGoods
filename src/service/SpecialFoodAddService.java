package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.SpecialFoods;
import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodAddService {
	public void insertData( SpecialFoods specialFoods ) throws SQLException, Exception {
		Connection con = null;
		con = DBConnector.getConnection();

		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		try {
			if ( !specialFoodsDAO.isDuplicate( con, specialFoods ) ) {
				specialFoodsDAO.insertData( specialFoods, con );
			} else {
				throw new Exception( "既に登録されている内容です。" );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
			throw new SQLException( e.getMessage() );
		} catch( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
}
