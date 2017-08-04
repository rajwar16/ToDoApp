<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
</head>
<body>
<form:form method="POST" commandName="userRegistration">

		<table style="margin-left: 38%; margin-top:">
			<tr>
				<td style="text-align: center;"><label for="name">
				<h3>User Registration</h3></label></td>
			</tr>

			<tr>
				<td><label for="name">Name</label></td>
			</tr>

			<tr>
				<td><form:input path="firstName" placeholder="firstName"/></td>
				<td><form:input path="lastName" placeholder="lastName"/></td>
			</tr>

			<tr>
				<td><label for="username">Choose your username</label></td>
			</tr>

			<tr>
				<td colspan="2">
				<form:input path="userName" placeholder="lastName" id="username" style="width: 350px"/></td>
				<td align="left" id="status"><div><img src="/img/p1.jpg" height=20px width=20px></div></td>
			</tr>

			<tr>
				<td><label for="password">Create a password</label></td>
			</tr>

			<tr>
				<td colspan="2"><form:input path="password" placeholder="lastName" id="password" style="width: 350px"/></td>
			</tr>

			<tr>
				<td><label for="confirmPassword">Confirm your password</label></td>
			</tr>

			<tr>
				<td colspan="2">
				<input type="password" name="confirmPassword" placeholder="lastName" id="confirmPassword" style="width: 350px"/>
				</td>
			</tr>

			<tr>
				<td><label for="date">BirthDay</label></td>
			</tr>

			<tr>
				<td colspan="2"><form:input path="birthDate" placeholder="lastName" id="date" style="width: 350px"/></td>
			</tr>

			<tr>
				<td colspan="2"><label for="date">Gender</label></td>
			</tr>

			<tr>
				<td><form:radiobutton path="gender" id="male" value="male"/>Male</td>
				<td><form:radiobutton path="gender" id="female" value="female"/>Female</td>
			</tr>

			<tr>
				<td><label for="mobileno">Mobile phone</label></td>
			</tr>

			<tr>
				<td colspan="2"><input type="number" name="mobilePhone"
					id="mobileno" style="width: 350px"></td>
			</tr>

			<tr>
				<td><label for="email">Email address</label></td>
			</tr>

			<tr>
				<td colspan="2"><form:input path="emailId" placeholder="lastName" id="email" style="width: 350px"/></td>
				<td align="left" id="status">ifyuoiuoui.......</td>
			</tr>

			<tr>
				<td><input type="reset" name="reset" id="reset" value="Reset"></td>
				<td><button name="register" id="register"
						formaction="UserRegister" style="width: 175px">Register</button></td>
			</tr>
			<tr>
				<td colspan="2">Already have u an account plzz
					<button id="login" style="background: none !important; color: inherit; border: none; padding: 0 !important; font: inherit; border-bottom: 1px solid #444; cursor: pointer;">login</button>
				</td>
			</tr>
		</table>

</form:form>
</body>
</html>