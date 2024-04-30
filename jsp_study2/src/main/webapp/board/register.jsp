<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<h1>Board JSP Register Page</h1>
<!-- enctype="multipart/form-data" : 이미지 파일을 가져갈 때 사용하는 타입 -->
<!-- 파일 : 종류/확장자 image/jpg image/png -->
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
		title : <input type="text" name="title"> <br>
		writer : <input type="text" name="writer" value="${ses.id }" readonly="readonly" > <br>
		content : <br> <textarea rows="10" cols="30" name="content"></textarea> <br>
		첨부파일 : <input type="file" name="imageFile" accept="image/png, image/jpg, image/gif, image/jpeg"> <br>
		<button type="submit" class="btn btn-outline-secondary">전송</button>
	</form>
</body>
</html>