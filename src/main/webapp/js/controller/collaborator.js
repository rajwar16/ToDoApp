myApp.controller( 'collaborator',['$scope','$rootScope','$controller', '$http','$state','collaboratorService','$uibModal','$uibModalInstance',function($scope, $rootScope,$controller,$state,fileReader,collaboratorService,$uibModal,$uibModalInstance,noteObject)
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
				   console.log("collaborator shared user :: ",data.data.SharedUser);
				   $uibModalInstance.dismiss('Done');
				}
		  });
	   };
	   $scope.cancel = function () {
			  $uibModalInstance.dismiss('cancel');
	   };
}]).service("collaboratorService", function($http,$q) {
    this.collaborator = function (collaboratorObject) {
    	console.log("collaborator...",collaboratorObject);
        return  $http({
            method : "PUT",
            url : "http://localhost:8080/TodoApp/collaborator",
            data : collaboratorObject,
            headers: {'accessToken': localStorage.getItem("accessToken")}
        });
    }
});