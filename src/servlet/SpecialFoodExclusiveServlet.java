package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import service.SpecialFoodExclusiveService;

@WebServlet( "/SpecialFoodExclusive" )
public class SpecialFoodExclusiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String foodNumber = request.getParameter( "foodNumber" );
		String timestamp = "";

		SpecialFoodExclusiveService foodExclusiveService = new SpecialFoodExclusiveService();
		try {
			timestamp = foodExclusiveService.getTimestamp( foodNumber );

			response.setCharacterEncoding( "UTF-8" );
			response.setContentType( "application/json" );

			JSONObject result = new JSONObject();
			result.put( "timestamp", timestamp );
			PrintWriter out = response.getWriter();
			out.print( result );
		} catch( JSONException jsonEx ) {
			jsonEx.printStackTrace();
		}
	}

}
