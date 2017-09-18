myApp.controller( 'reminderController',['$scope', '$controller', function($scope, $controller)
	{
	  $controller('showDivision', {$scope: $scope}),
	  console.log("reminderController reminder...");
	  
		$scope.pinupHtml=false;
	   	$scope.viewHtml=false;
	   	$scope.trashHtml=false;
	   	$scope.archiveHtml=false;
	   	$scope.reminderHtml=true;
	   	$scope.createEditableNote=false;
	   	
	   	$scope.navigationBar="rgb(96, 125, 139)";
	    $scope.navigationBorder="rgb(96, 125, 139)";
	    $scope.google=false;
	    $scope.navbarName="Reminders";
	    $scope.navcolor="white";
	    $scope.menuImg="Images/menu.svg";
	    $scope.listImg="Images/list2.svg";
	    $scope.gridImg="Images/navigationBarImages/grid2.svg";
	    $scope.refreshImg="Images/navigationBarImages/refresh2.svg";
	    $scope.notificationImg="Images/navigationBarImages/notification2.svg";
	   	
}]);