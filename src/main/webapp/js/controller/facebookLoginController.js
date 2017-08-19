myApp.controller( 'facebookLoginController',function($scope, $state,loginService)
{
	console.log("inside the facebookLoginController..........");
	
	$scope.facebookLoginController=function() {
		console.log("inside the lfacebookLoginController function....");
		
		var result=loginService.facebookLogin().then(function(data){
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

	}
});