<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/login.css">
<script src="js/login.js"></script>

<script type="text/javascri pt"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.9/angular.min.js"></script>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- <script type="text/javascript">
	var app = angular.module('MyApp', [])
	app.controller('MyController', function($scope) {
		//This will hide the DIV y default.
		$scope.somePlaceholder = "Email or UserName:";
		$scope.IsVisible = false;
		$scope.ShowHide = function() {
			$scope.somePlaceholder = "";
			$scope.IsVisible = $scope.IsVisible ? false : true;
		}
		
		$scope.HideShow = function() {
			$scope.somePlaceholder = "Email or UserName:";
			$scope.IsVisible = $scope.IsVisible ? false : true;
		}
	});
</script> -->

</head>
<body>
	<div ng-app="MyApp" ng-controller="MyController">
		<form:form method="POST" commandName="userLogin">
			<p style="text-align: center; color: red; margin-top: 10%">${authentication}</p>
			<table style="margin-left: 38%; margin-top:">
				<tr>
					<td colspan="2" style="text-align: center;"><h3>Sign in</h3></td>
				</tr>
				
				<tr>
					<td colspan="2" id="showEmail" ng-show="IsVisible">Email or UserName:</td>
				</tr>
				
				<tr>
					<td colspan="2"><form:input id="hideplaceholder" path="emailId" placeholder="{{somePlaceholder}}" ng-click="ShowHide()" ng-blur="HideShow()" style="width: 300px"/></td>
				</tr>
				
				<tr>
					<td colspan="2">password :</td>
				</tr>
				
				<tr>
					<td colspan="2"><form:password path="password" style="width: 300px"/></td>
				</tr>
				
				<tr>
					<td><button name="login" id="login" formaction="UserLogin" style="width: 175px">Login</button></td>
				</tr>
				
				<tr>
					<td colspan="2">Don't have an acount
						<button formaction="registerPage" value="Register" id="register" style="background: none !important; color: inherit; border: none; padding: 0 !important; font: inherit; border-bottom: 1px solid #444; cursor: pointer;">Register</button>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>
