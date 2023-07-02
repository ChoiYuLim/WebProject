<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mailTest</title>
</head>
<body>
	<h1>Mail Test입니다.</h1>
	<form method="post">
		이메일: <input type="email" name="email" required value="${param.email}">
		<button type="submit" formaction="/mailTest/mailOk" name="sendMail">인증
			메일 발송</button>
		<br />인증번호: <input type="text" name="verifyCode">
		<button type="button" onclick="checkCode()">코드 확인</button>
		&nbsp;&nbsp;
	</form>

	<script>
		function checkCode() {
			var verifyCode = "${verifyCode}";
			var inputCode = document.querySelector('input[name="verifyCode"]').value;
			if (verifyCode === inputCode) {
				alert("인증 성공");
			} else {
				alert("인증번호가 일치하지 않습니다.");
			}
		}
	</script>

</body>
</html>
