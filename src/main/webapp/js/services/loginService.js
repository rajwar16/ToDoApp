myApp.service('loginService', function($http) {
	console.log("loginservice...");
    this.userLogin = function (registerObject) {
        return  $http({
            method : "POST",
            url : "http://localhost:8080/TodoApp/UserLogin",
            data : registerObject
        });
    }
    this.facebookLogin = function () {
    	console.log("inside the lfacebookLoginservice function....");
        return  $http({
            method : "GET",
            url : "http://localhost:8080/TodoApp/facebookLogin",
        });
    }
    
});