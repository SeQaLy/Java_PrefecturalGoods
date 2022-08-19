package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.User;
import service.UserLoginService;

@WebServlet( "/UserLogin" )
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost( request, response );
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = request.getParameter( "id" );
    	String password = request.getParameter( "password" );
    	System.out.println( password );
    	User user = null;
    	boolean isLogin = false;

    	HttpSession session = request.getSession();
    	UserLoginService userLoginService = new UserLoginService();

		try {
			JSONObject result = new JSONObject();
			try {
				isLogin = userLoginService.login( id, password );
				user = userLoginService.provide( id );

			} catch ( Exception e ) {
				result.put( "errorMessage" , e.getMessage() );
			}
			response.setCharacterEncoding( "UTF-8" );
			response.setContentType( "application/json" );
			result.put( "isLogin", isLogin );
			result.put( "user" , user );

			PrintWriter out = response.getWriter();
			out.print( result );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		session.setAttribute( "isLogin", isLogin );
		session.setAttribute( "user", user );
	}

}
