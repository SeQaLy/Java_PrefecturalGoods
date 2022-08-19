package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.SpecialFoods;
import service.SpecialFoodsListService;

/**
 * 都道府県名を受け取り、各都道府県の特産品をレスポンスとして返す
 */

@WebServlet( urlPatterns={"/SpecialGoods"} )
public class SpecialGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SpecialGoodsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String prefecturalName = request.getParameter( "prefecturalName" );

		SpecialFoodsListService specialFoodsListService = new SpecialFoodsListService();
		ArrayList<SpecialFoods> specialFoodsList = specialFoodsListService.provide( prefecturalName );

		try {
		JSONObject result = new JSONObject();

		response.setCharacterEncoding( "UTF-8" );
		response.setContentType( "application/json" );
		result.put( "list", specialFoodsList );

		PrintWriter out = response.getWriter();
		out.print( result );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}



	}

}
