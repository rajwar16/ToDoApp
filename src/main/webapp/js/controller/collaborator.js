myApp.controller( 'collaborator',['$scope','$rootScope','$controller', '$http','$state','collaboratorService',function($scope, $rootScope,$controller,$state,fileReader,collaboratorService,noteObject)
	{
	  $controller('showDivision', {$scope: $scope}),
	  $controller('registerController', {$scope: $scope}),
	  
	  console.log("collaborator object...",noteObject);
	  console.log("collaborator ",$scope.noteObject);
	
	  $scope.collaboratorSave=function(sharedEmailId)
	  {
		  console.log("shared email id :: ", sharedEmailId);
		  console.log("collaborator ",$rootScope.noteObject);
		  var x=$rootScope.noteObject;
		  var collaboratorObject = {
					noteId : x.noteid,
					sharedEmailId:sharedEmailId
		  }
		  collaboratorService.collaborator(collaboratorObject).then(function(data){
			  if(data.data.status==200)
				{
				   console.log("collaborator created sucessfully :: ",data.data.noteSharedWithUser.profileImage);
				   $rootScope.sharedUserProfilePic=data.data.noteSharedWithUser.profileImage;
				}
		  });
	   } 
}]).service("collaboratorService", function($http,$q) {
	
    this.collaborator = function (collaboratorObject) {
    	console.log("collaborator...",collaboratorObject);
        return  $http({
            method : "PUT",
            url : "http://localhost:8080/TodoApp/collaborator",
            data : collaboratorObject
        });
    }
});