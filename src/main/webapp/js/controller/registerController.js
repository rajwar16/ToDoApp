myApp.controller( 'registerController',function($scope, $state, $http,$uibModal,registerService)
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
				var modalInstance = $uibModal.open({
		        	templateUrl: "template/EmailVarification.html",
		        	controller: function($uibModalInstance) 
		        	{
		        		var $ctrl = this;
		        		console.log("open popup controller data.....");
		        	},
		        	controllerAs: "$ctrl",
				});
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