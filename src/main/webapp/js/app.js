var myApp = angular.module('todo', [ 'ui.router','ngSanitize','ui.bootstrap']);

myApp.config(function($stateProvider, $urlRouterProvider) {
	console.log("login router ");
	
	$stateProvider.state("login", {
		url : "/login",
		templateUrl : "template/userLogin.html",
		controller : "loginController"
	}).state("register", {
		url : "/register",
		templateUrl : "template/UserRegistration.html",
		controller : "registerController"
	}).state("ToDoHomePage", {
		url : "/ToDoHomePage",
		templateUrl : "template/TodoHomePage.html",
		controller : "showDivision"
	}).state("getrefreshToken", {
		url : "/getrefreshToken",
		/*controller : "TodoHomePageController"*/
	}).state("getAllNotes", {
		controller : "getAllNotes"
	});
	$urlRouterProvider.otherwise('/login');

});