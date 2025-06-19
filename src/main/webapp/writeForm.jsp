<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>
		<form action = "http://localhost:8080/guestbook/gbc" method = "get">
			<table border="1" width="540px">
				<tr>
					<label>이름</label>
					<input type="text" name="content" value = ""></td>
					<td>비밀번호</td><td><input type="password" name="password" value = ""></td>
				</tr>
				<tr>
					<td colspan="4"><textarea cols="72" rows="5"></textarea></td>
				</tr>
				<tr>
					<td colspan="4"><button type="submit">확인</button></td>
				</tr>
			</table>
		</form>
		<br>
		
	</body>

</html>