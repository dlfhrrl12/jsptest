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
	<h1>My JSP Project TEST!!</h1>
	<!-- 
		method get => 주소표시줄에 ? 쿼리시트링을 달고 이동
		method post => 별도의 저장공간에 담아서 이동 (보안, 많은 데이터를 이동시)
	 -->
	 <c:if test="${ses.id eq null }">
	 <form action="/memb/login" method="post">
	 	id: <input type="text" name="id">
	 	password: <input type="password" name="pwd">
	 	<button type="submit" class="btn btn-outline-secondary">login</button>
	<a href="/memb/join"> <button type="button" class="btn btn-outline-secondary">회원가입 페이지로 이동 </button></a> 
	 </form>
	 </c:if>
	 <div>
	 	<c:if test="${ses.id ne null }">
	 		${ses.id }님이 login 하셨습니다. <br>
	 		계정생성일 : ${ses.regdate } / 마지막접속 : ${ses.lastlogin } <br>
	 		<a href="/memb/modify"><button type="button" class="btn btn-outline-secondary">회원정보수정</button></a>
			<a href="/memb/list"><button type="button" class="btn btn-outline-secondary">회원리스트</button></a>
			<a href="/memb/logout"><button type="button" class="btn btn-outline-secondary">logout</button></a>
	 	</c:if>
	 </div>
	
	
	<a href="/brd/register"> <button type="button" class="btn btn-outline-secondary"> 글쓰기 페이지로 이동</button></a>
	<a href="/brd/list"> <button type="button" class="btn btn-outline-secondary">리스트 페이지로 이동 </button></a> 

<script type="text/javascript">
	const msg_login = `<c:out value="${msg_login}"></c:out>`;
	console.log(msg_login);
	if(msg_login === '-1'){
		alert("로그인 정보가 일치하지 않습니다.");
	}
	const msg_update = `<c:out value="${msg_update}"></c:out>`;
	console.log(msg_update);
	if(msg_update === "OK"){
		alert("회원정보수정이 완료되었습니다. 다시 로그인 해주세요.");
	}
	const msg_delete = `<c:out value="${msg_delete}"></c:out>`;
	console.log(msg_delete);
	if(msg_delete === "OK"){
		alert("회원 탈퇴가 정상적으로 완료되었습니다.");
	}
</script>

</body>
</html>