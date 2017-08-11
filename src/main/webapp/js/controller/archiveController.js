myApp.controller( 'archiveController',['$scope', '$controller', function($scope, $controller)
	{
	  $controller('showDivision', {$scope: $scope}),
	  	console.log("archiveController archive...");
	  
		$scope.pinupHtml=false;
	   	$scope.viewHtml=false;
	   	$scope.trashHtml=false;
	   	$scope.archiveHtml=true;
	   	$scope.createEditableNote=false;
	   	
}]);