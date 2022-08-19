package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodExclusiveService {
	public String getTimestamp( String foodNumber ) {
		Connection con = DBConnector.getConnection();
		String timestamp = "";
		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		try {
			timestamp = specialFoodsDAO.getVersion( con, Integer.parseInt( foodNumber  ) );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return timestamp;
	}
}
