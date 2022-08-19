package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SpecialFoods;
import service.SpecialFoodSearchService;
import service.SpecialFoodsListService;

@WebServlet( "/SpecialFoodSearch" )
public class SpecialFoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute( "sortMethod" );
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerm = null;
		ArrayList<SpecialFoods> foodList = new ArrayList<SpecialFoods>();
		HttpSession session = request.getSession();
		SpecialFoodsListService foodListService = new SpecialFoodsListService();
		SpecialFoodSearchService foodSearchService = new SpecialFoodSearchService();
		String pageNumber = request.getParameter( "pageNumber" );
    	int startAt = 0;
    	int maxViewSize = 10; // 最大表示数;
    	int maxPage = 0; // 最大ページ
    	int pageStart = 1; // 現在ページ-pageRange
    	int pageEnd = 0; // 現在ページ+pageRange
    	int pageRange = 2;	// 現在のページから前後に表示するページ番号数  3 -> 1,2,4,5
    	int currentPage = 1; // 現在ページ番号
    	int allRowCount = 0;

    	// 検索文字の引継ぎ
    	if ( session.getAttribute( "searchTerm" ) == null || request.getParameter( "searchTerm" ) != null ) {
    		searchTerm = request.getParameter( "searchTerm" );
    	} else {
    		searchTerm = (String)session.getAttribute( "searchTerm" );
    	}

    	// 行数のカウント
    	if ( searchTerm == null ) {
    		allRowCount = foodListService.countRow();
    	} else {
    		allRowCount = foodListService.countRow( searchTerm );
    	}

    	// ページの最大数を計算
    	if ( allRowCount % maxViewSize == 0 && allRowCount / maxViewSize != 0 ) {
    		maxPage = allRowCount / maxViewSize;
    	} else {
    		maxPage = ( allRowCount / maxViewSize ) + 1;
    	}

    	// DBのオフセットを決める
		if ( pageNumber == null ) {
			startAt = 0;
			pageNumber = "1";
		} else {
			startAt = ( Integer.parseInt( pageNumber ) - 1 ) * maxViewSize;
			currentPage = Integer.parseInt( pageNumber );
		}

		pageStart = Math.max( Integer.parseInt( pageNumber ) - pageRange, 1 );
    	pageEnd = Math.min( Integer.parseInt( pageNumber ) + pageRange, maxPage );

    	if ( currentPage < 3 ) {
    		pageEnd = pageRange * 2 + 1;
    	}

    	if ( currentPage + 2 > maxPage ) {
    		pageEnd = maxPage;
    		pageStart = maxPage - pageRange * 2;
    	}
    	// ページ始まりが1を下回る場合、1を設定
    	pageStart = pageStart < 0 ? 1 : pageStart;

		request.setAttribute( "pageStart" , pageStart );
		request.setAttribute( "pageEnd" , pageEnd );

		request.setAttribute( "maxPage", maxPage );
		request.setAttribute( "currentPage", currentPage );


		try {

			foodList = foodSearchService.provide( startAt, maxViewSize, searchTerm );
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		request.setAttribute( "isSearch", true );

		session.setAttribute( "foodList", foodList );
		session.setAttribute( "searchTerm" , searchTerm );
		request.getRequestDispatcher( "datalist.jsp" ).forward( request, response );
	}

}
