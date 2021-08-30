package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BoardDao {

	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String userId="jspid";
	String userPw="jsppw";	
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	
	private static BoardDao instance;
	
	private BoardDao() {
		System.out.println("BoardDao() 생성자");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, userPw);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//BoardDao
	
	
	
	public static BoardDao getInstance() {
		if(instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}//getInstance
	
	
	
	public int getAritcleCount() {

		int count = 0;
		
		String sql="select count(*) as count from board";
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");								
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			}catch(SQLException e) {
				
			}
		}//finally;
		return count;
	}//getAritcleCount
	
	
	public ArrayList<BoardBean> getArticles(int start, int end){
		
		ArrayList<BoardBean> lists = new ArrayList<BoardBean>();
		
		String sql="select num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,re_level,content,ip ";
		sql += "from (select rownum as rank, num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,re_level,content,ip ";
		sql += "from (select num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,re_level,content,ip ";
		sql += "from board ";
		sql += "order by ref desc, re_step asc)) ";
		sql += "where rank between ? and ? ";
		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String writer = rs.getString("writer");
				String email = rs.getString("email");
				String subject = rs.getString("subject");
				String passwd = rs.getString("passwd");
				Timestamp reg_date = rs.getTimestamp("reg_date");
				int readcount = rs.getInt("readcount");
				int ref = rs.getInt("ref");
				int re_step = rs.getInt("re_step");
				int re_level = rs.getInt("re_level");
				String content = rs.getString("content");
				String ip = rs.getString("ip");
				
				BoardBean pb = new BoardBean(num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,
								re_level,content,ip);
				
				lists.add(pb);
			}
			
		} catch (SQLException e) {
			
	} finally {
		try {
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
		}catch(SQLException e) {
			
		}
	}//finally;
	return lists;
	
}//getArticles
	
	
	
	public int insertArticle(BoardBean bb) throws SQLException{ //지금은 원글쓰기하는 중. ref=num이랑 같은 거 쓸 거
		
		int cnt = -1;
		
		String sql="insert into board(num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,re_level,content,ip) "+
		"values(board_seq.nextval,?,?,?,?,?,0,board_seq.currval,?,?,?,?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, bb.getWriter());
		ps.setString(2, bb.getEmail());
		ps.setString(3, bb.getSubject());
		ps.setString(4, bb.getPasswd());
		ps.setTimestamp(5, bb.getReg_date());
		ps.setInt(6, 0); //re_step
		ps.setInt(7, 0); //re_level
		ps.setString(8, bb.getContent());
		ps.setString(9, bb.getIp());
		
		cnt = ps.executeUpdate();	
		return cnt;
		
	}//insertArticle. try/catch 없이 finally 못 씀.
	
	
	public BoardBean getArticle(int num) {
		
		
		BoardBean article = null;
		
		String sqlUpdate="update board set readcount=readcount+1 where num=?"; 
		//조회수 증가시키기 클릭한 게시물(num) 받아서. 증가 먼저 하고, 셀렉트
				
		String sql="select * from board where num=?";
		try {
			ps = conn.prepareStatement(sqlUpdate);
			ps.setInt(1, num);
			ps.executeUpdate();
			
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				
				int num2 = rs.getInt("num");
				String writer = rs.getString("writer");
				String email = rs.getString("email");
				String subject = rs.getString("subject");
				String passwd = rs.getString("passwd");
				Timestamp reg_date = rs.getTimestamp("reg_date");
				int readcount = rs.getInt("readcount");
				int ref = rs.getInt("ref");
				int re_step = rs.getInt("re_step");
				int re_level = rs.getInt("re_level");
				String content = rs.getString("content");
				String ip = rs.getString("ip");
				
				article = new BoardBean(num2,writer,email,subject,passwd,reg_date,
								readcount,ref,re_step,re_level,content,ip);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(ps!=null) {
					ps.close();
				}
				}catch(SQLException e) {
				
			}
		}//finally;
		
		return article;
	}//getArticle
	
	
	public BoardBean updateFormArticle(int num) {
		BoardBean article = null;
						
		String sql="select * from board where num=?";
		try {
				
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				
				int num2 = rs.getInt("num");
				String writer = rs.getString("writer");
				String email = rs.getString("email");
				String subject = rs.getString("subject");
				String passwd = rs.getString("passwd");
				Timestamp reg_date = rs.getTimestamp("reg_date");
				int readcount = rs.getInt("readcount");
				int ref = rs.getInt("ref");
				int re_step = rs.getInt("re_step");
				int re_level = rs.getInt("re_level");
				String content = rs.getString("content");
				String ip = rs.getString("ip");
				
				article = new BoardBean(num2,writer,email,subject,passwd,reg_date,
								readcount,ref,re_step,re_level,content,ip);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(ps!=null) {
					ps.close();
				}
			}catch(SQLException e) {
				
			}
		}//finally;
		
		return article;
	}//updateFormArticle
	
		
	public int updateArticle(BoardBean article){ //5+1(날짜)
		
		int cnt = -1;
		
		String sqlSelect = "select passwd from board where num=?";
		String sqlUpdate="update board set writer=?,subject=?,email=?,content=?,reg_date=? where num=?";
		
		try {
			ps = conn.prepareStatement(sqlSelect);
			ps.setInt(1, article.getNum());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				String dbpasswd = rs.getString("passwd");
				if(dbpasswd.equals(article.getPasswd())){
					
					ps = conn.prepareStatement(sqlUpdate);
					ps.setString(1, article.getWriter());
					ps.setString(2, article.getSubject());
					ps.setString(3, article.getEmail());
					ps.setString(4, article.getContent());
					ps.setTimestamp(5, article.getReg_date());
					ps.setInt(6, article.getNum());
					
					cnt = ps.executeUpdate();
					
				}//패스워드 일치 if
				else {
					cnt = -1;
				}//패스워드 일치 안 함 else
				
			}//패스워드 있음
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(ps!=null) {
					ps.close();
				}	
			}catch(SQLException e) {
				
			}
		}//finally;
				
		return cnt ;
	}//updateArticle
		
	
	
	public int deleteArticle(String num, String passwd){
		
		int cnt = -1;
		String sqlSelect="select passwd from board where num=?";
		String sqlDelete="delete from board where num=?";
		
		try {
			ps = conn.prepareStatement(sqlSelect);
			ps.setInt(1, Integer.parseInt(num));
			
			rs = ps.executeQuery();
			if(rs.next()) {
				String dbpasswd = rs.getString("passwd");
				if(dbpasswd.equals(passwd)) {
					
					ps = conn.prepareStatement(sqlDelete);
					ps.setInt(1, Integer.parseInt(num));
					
					cnt = ps.executeUpdate();
					
					
				}//passwd 같은 if
				else {
					cnt = -1;
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(ps!=null) {
					ps.close();
				}
			}catch(SQLException e) {
				
			}
		}//finally;
		return cnt;
	}//deleteArticle
	
	public void replyArticle(BoardBean article) {
		//ref,re_step,re_level:부모 거
		//나머지:내것
		
		//같은 그룹이면서 부모 re_step보다 큰 거를 하나씩 증가시키겠다
		String sqlUpdate = "update board set re_step = re_step+1 where ref=? and re_step > ?"; 
		  
		String sqlInsert = "insert into board(num,writer,email,subject,passwd,reg_date,ref,re_step,re_level,content,ip)"+
					"values(board_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			ps = conn.prepareStatement(sqlUpdate);
			ps.setInt(1, article.getRef()); //부모 거
			ps.setInt(2, article.getRe_step()); //부모 거
								
			ps.executeUpdate();
			
			
			ps = conn.prepareStatement(sqlInsert);
			ps.setString(1, article.getWriter());
			ps.setString(2, article.getEmail());
			ps.setString(3, article.getSubject());
			ps.setString(4, article.getPasswd());
			ps.setTimestamp(5, article.getReg_date());
			ps.setInt(6, article.getRef());
			ps.setInt(7, article.getRe_step()+1); //부모거+1
			ps.setInt(8, article.getRe_level()+1); //부모거+1
			ps.setString(9, article.getContent());
			ps.setString(10, article.getIp());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if(ps!=null)
					ps.close();
			}catch(SQLException e) {
				
			}
		}//finally;
	
	}//replyArticle
	
	
	
	
	
	
	
}//
