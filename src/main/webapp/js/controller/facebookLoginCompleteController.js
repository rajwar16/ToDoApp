myApp.controller( 'facebookLoginCompleteController',function($scope, $state,$location,facebookLoginService)
{
	console.log("inside the facebookLoginCompleteController..........");
	
	var tokenKey=$location.search().TempTokenKey;
	
	
	console.log("TokenObject :: ",tokenKey);
		
		var result=facebookLoginService.facebookLoginToken(tokenKey).then(function(data){
			if(data.data.status==200)
			{
				console.log("facebook login sucessfully status 200..");
				localStorage.setItem("accessToken",data.data.accessToken);
				$state.go("ToDoHomePage");
			}
			
			else
			{
				console.log("invalid credintial..");
				$state.go("login");
			}
});
}).service('facebookLoginService', function($http) {
	console.log("facebookLoginService...");
    this.facebookLoginToken = function (tokenKey) {
        return  $http({
            method : "POST",
            url : "FacebookTokenKey",
            data : tokenKey
        });
    };
});