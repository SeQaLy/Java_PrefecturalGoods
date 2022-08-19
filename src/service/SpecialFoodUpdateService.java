package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.SpecialFoods;
import dao.SpecialFoodsDAO;
import util.DBConnector;

public class SpecialFoodUpdateService {
	public void update( SpecialFoods specialFoods ) throws SQLException, Exception {
		Connection con = null;
		con = DBConnector.getConnection();

		// memo DAO -> Service -> Servletでエラーを受け取る
		// DAO : throw new SQLException( 任意の文字列 )
		SpecialFoodsDAO specialFoodsDAO = new SpecialFoodsDAO();
		// DB内のタイムスタンプ
		String currentTimestamp = specialFoodsDAO.getVersion( con, specialFoods.getSpecialFoodNumber() );
		try {
			if ( !specialFoodsDAO.isDuplicate( con, specialFoods ) ) {
				if ( currentTimestamp.equals( specialFoods.getTimestamp() ) ) {
					specialFoodsDAO.update( con, specialFoods );
				} else {
					throw new Exception( "他の人が編集済みです。内容を確認して、もう一度編集してください。" );
				}
			} else {
				throw new Exception( "既に登録されている内容です。" );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		} catch( Exception e ) {
			throw new Exception ( "既に削除されています。" );
		}
	}
}
