myApp.service('profilePicService', function($http) {
	console.log("profilePicService...");
    this.updateprofile = function (updateprofilePic) {
        return  $http({
            method : "POST",
            url : "http://localhost:8080/TodoApp/userUpdate",
            data : updateprofilePic
        });
    }
});