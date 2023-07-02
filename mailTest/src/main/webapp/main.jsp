<%@ page language="java" contentType="text/html; charset=UTF${board.name}-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mailTest</title>
</head>
<body>
	<h1>Mail Test입니다.</h1>
	<form action="/mailTest/mailOk" method="post">
		이메일: <input type="email" name="email" required><br /> <input
			type="submit" value="인증 메일 발송">&nbsp;&nbsp;
	</form>

	<c:if test="${not empty verifyCode}">
		${verifyCode}
	</c:if>
	
	
</body>

</html>