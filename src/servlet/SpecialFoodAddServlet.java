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
import service.SpecialFoodAddService;


@WebServlet( "/SpecialFoodAdd" )
public class SpecialFoodAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prefecturalName = request.getParameter( "prefecturalName" );
		String foodName = request.getParameter( "foodName" );
		String updateUser = request.getParameter( "updateUser" );

		ArrayList<String> errorMessages = new ArrayList<String>();
		PrefecturalShowService prefecturalShowService = new PrefecturalShowService();
		SpecialFoodAddService foodAddService = new SpecialFoodAddService();
		try {
			SpecialFoods specialFoods = new SpecialFoods(
					foodName,
					prefecturalShowService.getIdByName( prefecturalName )
					);
			specialFoods.setUpdateUser( updateUser );
			foodAddService.insertData( specialFoods );
		} catch( SQLException e ) {
			e.printStackTrace();
		} catch( Exception e ) {
			errorMessages.add( e.getMessage() );
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
