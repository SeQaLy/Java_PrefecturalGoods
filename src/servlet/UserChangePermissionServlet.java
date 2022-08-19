package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserChangePermissionService;


@WebServlet( "/UserChangePermission" )
public class UserChangePermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = request.getParameter( "userId" );
		int newPermission = Integer.parseInt( request.getParameter( "newPermission" ) );

		UserChangePermissionService changePermissionService = new UserChangePermissionService();
		changePermissionService.changePermission( userId, newPermission );
	}

}
