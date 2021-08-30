<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <%@include file="style.css" %>   이거는 jsp 코드 갖고 올 때 includ 지시어 --%>
    
<link rel="stylesheet" type="text/css" href="style.css">

<style type="text/css">
	body{
		align:center;
		text-align: center;
	}
</style>
<script type="text/javascript" src="./js/jquery.js"></script>
<script type="text/javascript" src="script.js"></script><!-- 외부파일 주소 알려주기 -->


writeForm.jsp<br>

<b>글쓰기</b> <br>
<form method="post" name="writeform" action="writeProc.bd">
	<table border="1" width="450" align="center">
		<tr>
			<td colspan="2" align="right">
				<a href="list.jsp">글목록</a>
			</td>
		</tr>
		
		<tr>
			<td width="100">이름</td>
			<td width="350">
				<input type="text" name="writer" value="홍길동">
			</td>
		</tr>
		
		<tr>
			<td width="100">제목</td>
			<td width="350">
				<input type="text" name="subject" value="제목">
			</td>
		</tr>
		
		<tr>
			<td width="100">Email</td>
			<td width="350">
				<input type="text" name="email" value="abc@d.net">
			</td>
		</tr>
		
		<tr>
			<td width="100">내용</td>
			<td width="350">
				<textarea name="content" rows="15" cols="50">내용입니다.</textarea>				
			</td>
		</tr>
		
		<tr>
			<td width="100">비밀번호</td>
			<td width="350">
				<input type="password" name="passwd" value="1234">
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="글쓰기" onClick="return writeSave()">
				<input type="reset" value="다시 작성">
				<input type="button" value="목록보기" onClick="location.href='list.bd'">
			</td>
		</tr>
		
	</table>
</form>