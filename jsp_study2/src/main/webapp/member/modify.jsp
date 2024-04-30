<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Member JSP Modify Page</h1>
	<form action="/memb/update" method="post">
		<table border="1">
		<tr>
			<th>ID</th>
			<td> <input type="text" name="id" value="${ses.id }" readonly="readonly"> </td>
		</tr>
		<tr>
			<th>PASSWORD</th>
			<td> <input type="password" name="pwd" value="${ses.pwd }"> </td>
		</tr>
		<tr>
			<th>EMAIL</th>
			<td> <input type="text" name="email" value="${ses.email }"> </td>
		</tr>
		<tr>
			<th>AGE</th>
			<td> <input type="text" name="age" value="${ses.age }"> </td>
		</tr>
		<tr>
			<th>TEL</th>
			<td> <input type="text" name="phone" value="${ses.phone }"> </td>
		</tr>
		<tr>
			<th>REGDATE</th>
			<td> <input type="text" name="regdate" value="${ses.regdate }" disabled="disabled"> </td>
		</tr>
		<tr>
			<th>LASTLOGIN</th>
			<td> <input type="text" name="lastlogin" value="${ses.lastlogin }" disabled="disabled"> </td>
		</tr>
		</table>
		<button type="submit">modify</button>
	</form>
		<a href="/memb/delete"> <button type="button">delete</button> </a>
		<a href="../index.jsp"> <button type="button">index</button> </a>
		
		<script type="text/javascript">
		const msg_update = `<c:out value="${msg_update}"></c:out>`;
		console.log(msg_update);
		if(msg_update === "Fail"){
			alert("회원정보수정이 실패했습니다.");
		}
		</script>
</body>
</html>