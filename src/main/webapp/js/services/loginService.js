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
            url : "facebookLogin",
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                'Access-Control-Allow-Headers': 'Content-Type, X-Requested-With',
                'X-Random-Shit':'123123123'
            }
        });
    }
    
});