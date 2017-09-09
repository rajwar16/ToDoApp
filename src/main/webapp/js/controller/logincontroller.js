myApp.controller( 'loginController',function($scope, $state,loginService)
{
	console.log("inside the loginController");
	$scope.invalidCredintial=false;
	$scope.loginController=function() {
		console.log("inside the login controller function...gbbbbbbbbbbbbb");
		console.log($scope.userName);
		console.log($scope.password);
		var loginObject = {
				userName : $scope.userName,
				password : $scope.password
		}
		
		var result=loginService.userLogin(loginObject).then(function(data){
			console.log("Response Body :: ",data.data);		
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			if(data.data.status==200)
			{
				console.log("login sucessfully status 200..");
				console.log("accessToken",data.data.accessToken,"    \nrefreshToken",data.data.refreshToken);
				localStorage.setItem("accessToken",data.data.accessToken);
				localStorage.setItem("refreshToken",data.data.refreshToken);
				$state.go("ToDoHomePage");
			}
			
			else if(data.data.status==404)
			{
				console.log("login unsucessfully status 404..");
				$scope.invalidCredintial=true;
				$state.go("login");
			}
			else
			{
				console.log("invalid credintial..");
				$state.go("login");
			}
		});

	}
	
	$scope.facebookLoginController=function() {
		console.log("inside the facebookLoginController function....");
		
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
	
	
	/*
	$scope.isSignIn = function () 
	{
		var httpObje = loginService.isLogin();
		httpObje.then(function(data)
				{
			$scope.user = data;
			console.log("inside the controller");
			if(data.status==200)
			{
						


			}else
			{
				$state.go('login');
			}

				})
		}*/
});