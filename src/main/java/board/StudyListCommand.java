package board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudyListCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int pageSize = 10;
		String pageNum = request.getParameter("pageNum"); //이거를 설정해야 설정한 페이지로 넘어감
		if(pageNum==null) {
			pageNum="1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)/pageSize*pageSize+1;
		int endRow = currentPage*pageSize;
		int count = 0;
		int number = 0;
		
		BoardDao bdao = BoardDao.getInstance();
		count = bdao.getAritcleCount();
		number = count-(currentPage-1)*pageSize;
		
		ArrayList<BoardBean> lists = bdao.getArticles(startRow, endRow);
		
		int pageBlock = 10;
		int pageCount = count/pageSize + (count % pageSize == 0? 0:1);
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		if(endPage>pageCount) {
			endPage=pageCount;
		}
		
		request.setAttribute("count", count);
		request.setAttribute("lists", lists);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		
	}

}
