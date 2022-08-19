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
import service.SpecialFoodSortService;
import service.SpecialFoodsListService;


@WebServlet( "/SpecialFoodSort" )
public class SpecialFoodSortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SpecialFoodSortService foodSortService = new SpecialFoodSortService();
		SpecialFoodsListService foodsListService = new SpecialFoodsListService();

		HttpSession session = request.getSession();

		ArrayList<SpecialFoods> foodList = null;
		String sortColumn = null;
		String pageNumber = request.getParameter( "pageNumber" );
		String searchTerm = null;
		String sortMethod = null;
		boolean isAsc = true;
    	int startAt = 0;
    	int maxViewSize = 10; // 最大表示数;
    	int maxPage = 0; // 最大ページ
    	int pageStart = 1; // 現在ページ-pageRange
    	int pageEnd = 0; // 現在ページ+pageRange
    	int pageRange = 2;	// 現在のページから前後に表示するページ番号数  3 -> 1,2,4,5
    	int currentPage = 1; // 現在ページ番号
    	int allRowCount = 0;

    	if ( session.getAttribute( "sortMethod" ) == null || request.getParameter( "sortMethod" ) != null ) {
    		sortMethod = request.getParameter( "sortMethod" );
    	} else {
    		sortMethod = (String)session.getAttribute( "sortMethod" );
    	}

    	// 昇順降順の判別
//    	isAsc = request.getParameter( "sortMethod" ).equals( "asc" );
    	isAsc = sortMethod.equals( "asc" );


    	// ソート列の引継ぎ
    	if ( session.getAttribute( "sortColumn" ) == null || request.getParameter( "sortColumn" ) != null ) {
    		sortColumn = request.getParameter( "sortColumn" );
    	} else {
    		sortColumn = (String)session.getAttribute( "sortColumn" );
    	}

    	// 検索文字の引継ぎ
    	if ( session.getAttribute( "searchTerm" ) == null || request.getParameter( "searchTerm" ) != null ) {
    		searchTerm = request.getParameter( "searchTerm" );
    	} else {
    		searchTerm = (String)session.getAttribute( "searchTerm" );
    	}

    	// 行数のカウント
    	if ( searchTerm == null ) {
    		allRowCount = foodsListService.countRow();
    	} else {
    		allRowCount = foodsListService.countRow( searchTerm );
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
    		if ( searchTerm == null ) {
    			foodList = foodSortService.sort( startAt, maxViewSize, sortColumn, isAsc );
    		} else {
    			foodList = foodSortService.sort( startAt, maxViewSize, sortColumn, searchTerm, isAsc );
    		}
    	} catch( SQLException e ) {
    		e.printStackTrace();
    	}
    	request.setAttribute( "isSearch", true );
    	session.setAttribute( "sortMethod", sortMethod );
    	session.setAttribute( "sortColumn", sortColumn );
    	session.setAttribute( "foodList", foodList );
    	request.getRequestDispatcher( "datalist.jsp" ).forward( request, response );
	}

}
