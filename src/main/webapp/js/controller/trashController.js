myApp.controller( 'trashController',['$scope', '$controller', function($scope, $controller)
{
		
	  $controller('showDivision', {$scope: $scope}),
	  console.log("trashController archive...");
	  
		$scope.pinupHtml=false;
	   	$scope.viewHtml=false;
	   	$scope.trashHtml=true;
	   	$scope.archiveHtml=false;
	   	$scope.createEditableNote=false;
	   	
	   	
	   	$scope.navigationBar="rgb(99, 99, 99)";
	    $scope.navigationBorder="rgb(99, 99, 99)";
	    $scope.google=false;
	    $scope.navbarName="Trash";
	    $scope.navcolor="white";
	    $scope.menuImg="Images/menu.svg";
	    $scope.listImg="Images/list2.svg";
	    $scope.gridImg="Images/navigationBarImages/grid2.svg";
	    $scope.refreshImg="Images/navigationBarImages/refresh2.svg";
	    $scope.notificationImg="Images/navigationBarImages/notification2.svg";
	   	
	   	
	   	
	   /* var tmpList = [];
	    
	    for (var i = 1; i <= 6; i++){
	      tmpList.push({
	        text: 'Item ' + i,
	        value: i
	      });
	    }
	  
	    $scope.list = tmpList;
	    
	    
	    $scope.sortingLog = [];*/
	    
	   /* $scope.sortableOptions = {
	      handle: '> .todoNoteDiv',*/
	      /*update: function(e, ui) {
	        var logEntry = tmpList.map(function(i){
	          return i.value;
	        }).join(', ');
	        $scope.sortingLog.push('Update: ' + logEntry);
	      },
	      stop: function(e, ui) {
	        // this callback has the changed model
	        var logEntry = tmpList.map(function(i){
	          return i.value;
	        }).join(', ');
	        $scope.sortingLog.push('Stop: ' + logEntry);
	      }*/
	   /* };*/

	    
	   	
}]);
