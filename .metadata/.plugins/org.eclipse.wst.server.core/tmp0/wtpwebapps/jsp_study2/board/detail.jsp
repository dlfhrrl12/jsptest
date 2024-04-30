<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>

<div class="container-sm">
<h1>Board JSP Detail Page</h1>

<img alt="" src="/_fileUpload/_th_${bvo.imageFile }">
<table class="table table-dark table-hover">
	<tr>
		<th>bno</th>
		<td>${bvo.bno }</td>
	</tr>
	<tr>
		<th>title</th>
		<td>${bvo.title }</td>
	</tr>
	<tr>
		<th>writer</th>
		<td>${bvo.writer }</td>
	</tr>
	<tr>
		<th>regdate</th>
		<td>${bvo.regdate }</td>
	</tr>
	<tr>
		<th>moddate</th>
		<td>${bvo.moddate }</td>
	</tr>
	<tr>
		<th>content</th>
		<td>${bvo.content }</td>
	</tr>
</table>
</div>
	<c:if test="${bvo.writer eq ses.id }">
	 <a href="/brd/modify?bno=${bvo.bno }"><button type="button" class="btn btn-outline-secondary">modify</button></a> 
	 <a href="/brd/remove?bno=${bvo.bno }"><button type="button" class="btn btn-outline-secondary">remove</button></a> 
	</c:if> 
	 <a href="/brd/list"> <button type="button" class="btn btn-outline-secondary">list</button> </a>
	 <a href="../index.jsp"> <button type="button" class="btn btn-outline-secondary">index</button> </a>
	 
	 <!-- comment line -->
	 <hr>
	 <div>
	 comment line <br>
	 <input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly"> <br>
	 <input type="text" id="cmtText" placeholder="Add Comment...">
	 <button type="button" id="cmtAddBtn">Comment post</button>
	 </div>
	 <br>
	 <hr>
	 <!-- 댓글 출력 -->
	 <div id="commentLine">
	 	<div>
	 		<div>cno, bno, writer, regdate</div>
	 		<div>
	 			<button>수정</button> <button>삭제</button> <br>
	 			<input type="text" value="content">
	 		</div>
	 	</div>
	 </div>
	 
	 <script type="text/javascript">
	 const bnoVal = `<c:out value="${bvo.bno}"/>`;
	 const id = `<c:out value="${ses.id}"/>`;
	 console.log(bnoVal);
	 console.log(id);
	 </script>
	 
	 <script type="text/javascript" src="/resources/board_detail.js"></script>
	 
	 <script type="text/javascript">
	 printCommentList(bnoVal);
	 </script>
</body>
</html>