package board;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudyBoardWriteProc implements BoardCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String writer = request.getParameter("writer");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String passwd = request.getParameter("passwd");
		Timestamp reg_date = new Timestamp(System.currentTimeMillis());
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		
		BoardBean bb = new BoardBean(0,writer,email,subject,passwd,reg_date,0,0,0,0,content,ip);
		BoardDao bdao = BoardDao.getInstance();
		try {
			bdao.insertArticle(bb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
