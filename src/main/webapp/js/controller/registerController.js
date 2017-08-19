myApp.controller( 'registerController',function($scope, $state, $http,registerService)
{
	console.log("inside the register controller");
	
	$scope.registerController=function() {
		console.log("inside the register controller function ");
		
		var registerObject = {
				userName : $scope.userName,
				email : $scope.email,
				password:$scope.password,
				mobileNo:$scope.mobileNo
		}
		console.log(registerObject);
		
		var result=registerService.userRegister(registerObject).then(function(data){
			
			console.log(data);			
			console.log(data.data.status);	
			console.log(data.data.message);
			
			if(data.data.status==200)
			{
				console.log("login sucessfully status 200..");
				$state.go("login");
			}
			else
			{
				console.log("not registered...");
				$state.go("register");
			}
		});
	}
});