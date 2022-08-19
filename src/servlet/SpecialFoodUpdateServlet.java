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

import dao.SpecialFoods;
import service.PrefecturalShowService;
import service.SpecialFoodUpdateService;

@WebServlet( "/SpecialFoodUpdate" )
public class SpecialFoodUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SpecialFoodUpdateService foodUpdateService = new SpecialFoodUpdateService();

		int foodNumber = Integer.parseInt( request.getParameter( "foodNumber" ) );
		String foodName = request.getParameter( "foodName" );
		String prefecturalId = "";
		String timestamp = request.getParameter( "originTimestamp" );
		String updateUser = request.getParameter( "updateUser" );

		ArrayList<String> errorMessages = new ArrayList<String>();
		PrefecturalShowService prefecturalShowService = new PrefecturalShowService();
		try {
			prefecturalId = prefecturalShowService.getIdByName( request.getParameter( "prefecturalName" ) );
			SpecialFoods specialFoods = new SpecialFoods( foodNumber, foodName, prefecturalId );
			specialFoods.setTimestamp( timestamp );
			specialFoods.setUpdateUser( updateUser );
			foodUpdateService.update( specialFoods );
		} catch( SQLException e ) {
			e.printStackTrace();
		} catch( Exception e ) {
			errorMessages.add( e.getMessage() );
			try {
				JSONObject result = new JSONObject();
				response.setCharacterEncoding( "UTF-8" );
				response.setContentType( "application/json" );

				result.put( "errorMessages", errorMessages );
				PrintWriter out = response.getWriter();
				out.print( result );
			} catch( JSONException jsonEx ) {
				jsonEx.printStackTrace();
			}
		}
	}

}
