<%@page import="board.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.BoardDao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="color.jsp" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
        
<link rel="stylesheet" type="text/css" href="style.css">


<style type="text/css">
	body{
		align:center;   
		text-align: center;
	}
</style>
    
    
    
list.jsp<br>


	<b>전체 레코드 갯수(${count })</b>
<table border="1" width="700" align="center">
	<tr>
		<td align="right" bgcolor="<%=value_c%>">
			<a href="writeForm.jsp">글쓰기</a>
		</td>
	</tr>
</table>

	<c:if test="${empty lists }">

		<table border="1" width="700" align="center">
			<tr>
				<td align="right">
					게시판에 저장(insert)된 글이 없습니다.
				</td>
			</tr>
		</table>
	</c:if>

	<table border="1" width="700" align="center">
		<tr bgcolor="<%=value_c%>">
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회</td>
			<td>IP</td>
		</tr>
		<c:forEach var="one" items="${lists }">
				<tr>
					<td>
						<c:set var="i" value="${i+1}"/>
						${number+1-i}
					
					</td> <!-- 14로 시작해서 하나씩 감소 -->
					<td> <!-- 제목칸이다 여기 -->
						<c:set var="width" value="${20*one.re_level}"/>
					
						<c:forEach items="${one.re_level>0}">
							<img src="images/level.gif" width="${width }" height="15">
							<img src="images/re.gif">
							
						</c:forEach>
						
						<c:forEach items="${one.re_level=0 }">
							<img src="images/level.gif" width="${width }" height="15">
						</c:forEach>
						
						
						<a href="content.jsp?num=${one.num }&pageNum=${pageNum}">${one.subject}</a>
						
						<c:if test="${one.readcount>=10 }">
								<img src="images/hot.gif" height="15">
						</c:if>
					</td>
					<td>${one.writer}</td>
					<td>
						<fmt:formatDate value="${one.reg_date}" type="date" dateStyle="full"/>
					</td>
					<td>${one.readcount }</td>
					<td>${one.ip }</td>
				</tr>
		</c:forEach>
				
	</table>

	<c:if test="${count>0 }">
		
		<c:if test="${startPage>10 }">
			<a href="list.bd?pageNum=${startPage-10}">[이전]</a>
		</c:if>
		
		<c:forEach var="i" begin="${startPage }" end="${endPage }">
				<a href="list.bd?pageNum=${i }">[${i}]</a>
		</c:forEach>
				
		<c:if test="${pageCount>endPage}">
			<a href="list.bd?pageNum=${startPage+10}">[다음]</a>
		</c:if>
	</c:if>


<!-- 글 수정하는데 비번 일치 안 하면 일치 안 한다고 alert 띄우기
삭제도 비번 일치 안 하면 비번 일치 안 함 alert 띄우고
일치하면 삭제하고 보고 있던 페이지로 돌아옴
보고 있던 페이지의 레코드가 하나면 이전 페이지로? -->