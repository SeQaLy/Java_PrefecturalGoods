package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import service.UserRegisterService;

@WebServlet( "/UserRegister" )
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return;
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UserRegisterService registerService = new UserRegisterService();

    	String name = request.getParameter( "name" );
    	String id = request.getParameter( "id" );
    	String password = request.getParameter( "password" );
    	try {
    		registerService.registerUser( name, id, password );
    	} catch( SQLException e ) {
    		try {
	    		JSONObject result = new JSONObject();

	    		response.setCharacterEncoding( "UTF-8" );
	    		response.setContentType( "application/json" );
	    		result.put( "errorMessage", e.getMessage() );

	    		PrintWriter out = response.getWriter();
	    		out.print( result );
    		} catch( JSONException jex ) {
    			jex.printStackTrace();
    		}
    	}
	}

}
