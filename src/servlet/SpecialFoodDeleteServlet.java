package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import service.SpecialFoodDeleteService;

@WebServlet("/SpecialFoodDelete")
public class SpecialFoodDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SpecialFoodDeleteService foodDeleteService = new SpecialFoodDeleteService();
		int deleteRows = 0;

		ArrayList<String> errorMessages = new ArrayList<String>();

		// JSON形式( 文字列 )で渡ってくる
		String deleteIndexStr = request.getParameter( "deleteIndex" );
		// [と]を削除
		deleteIndexStr = deleteIndexStr.substring( 1, deleteIndexStr.length() - 1 );
		// "を削除
		deleteIndexStr = deleteIndexStr.replace( "\"", "" );
		// ,で区切り、数値だけを取り出す
		String[] deleteIndexes = deleteIndexStr.split( "," );
		ArrayList<Integer> deleteIndex = new ArrayList<Integer>();
		// 削除するIDをリストに追加する
		for ( String index : deleteIndexes ) {
			deleteIndex.add( Integer.parseInt( index ) );
		}

		try {
			deleteRows = foodDeleteService.delete( deleteIndex );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		if ( deleteRows <= 0 ) {
			errorMessages.add( "既に削除されています。" );
			try {
				response.setCharacterEncoding( "UTF-8" );
				response.setContentType( "application/json" );

				JSONObject result = new JSONObject();
				result.put( "errorMessages", errorMessages );

				PrintWriter out = response.getWriter();
				out.print( result );
			} catch( JSONException jsonEx ) {
				jsonEx.printStackTrace();
			}
		}
	}

}
