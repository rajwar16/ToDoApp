myApp.controller( 'forgotPassword',function($scope, $state, $http,$uibModal,$location,forgotPasswordService)
{
	console.log("inside the forgotPassword controller");
	
	$scope.showEmailError=false;
	
	$scope.gettingEmailId=function(emailid){
		forgotPasswordService.gettingEmailId(emailid).then(function(data){
			
			if(data.data.status==200)
			{
				console.log("200 success...")
				$state.go("enterTheCode");
				/*$state.go("checkYourEmailid");*/
			}
			if(data.data.status==404)
			{
				$scope.showEmailError=true;
			}
		});
	}
	
	$scope.verifyCode=function(code)
	{
		console.log("code :: ",code)
		forgotPasswordService.validateCode(code).then(function(data){
			if(data.data.status==200)
			{
				$state.go("enterTheCode");
			}
			if(data.data.status==404)
			{
				$scope.showEmailError=true;
			}
		});
	}
	
	$scope.changePassword=function(newpassword)
	{
		var tokenKey=$location.search().token;
		console.log("tokenkey :: ",tokenKey);
		console.log("newpassword :: ",newpassword)
		
		var createNoteObject = {
			newpassword : newpassword,
				tokenKey : tokenKey,
				
		}
		
		forgotPasswordService.changePassword(createNoteObject).then(function(data){
			if(data.data.status==200)
			{
				$state.go("login");
			}
			if(data.data.status==404)
			{
				$scope.showEmailError=true;
			}
		});
	}
}).service('forgotPasswordService',function($http){
	console.log("forgotPasswordService...");
	this.gettingEmailId=function(emailid)
	{
		return $http({
			method:"POST",
			url:"forgotPassword",
			data:emailid
		});
	}
	
	this.validateCode=function(code)
	{
		return $http({
			method:"POST",
			url:"validateCode",
			data:code
		});
	}
	
	this.changePassword=function(createNoteObject)
	{
		return $http({
			method:"POST",
			url:"changePassword",
			data:createNoteObject
		});
	}
	
});