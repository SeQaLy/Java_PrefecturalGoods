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

import service.PrefecturalShowService;

@WebServlet(name = "PrefecturalNameList", urlPatterns = { "/PrefecturalNameList" })
public class PrefecturalNameListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> prefecturalNameList = null;
		PrefecturalShowService prefecturalShowService = new PrefecturalShowService();
		try {

			prefecturalNameList = prefecturalShowService.getAllPrefectural();
			
			JSONObject result = new JSONObject();
			response.setCharacterEncoding( "UTF-8" );
			response.setContentType( "application/json" );
			result.put( "prefecturalList", prefecturalNameList );

			PrintWriter out = response.getWriter();
			out.print( result );
		} catch( JSONException jsonex ) {
			jsonex.printStackTrace();
		} catch( SQLException e ) {
			e.printStackTrace();
		}
	}

}
