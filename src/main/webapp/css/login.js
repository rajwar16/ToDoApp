	var app = angular.module('MyApp', [])
	app.controller('MyController', function($scope) {
		//This will hide the DIV y default.
		$scope.somePlaceholder = "Email or UserName:";
		$scope.IsVisible = false;
		$scope.ShowHide = function() {
			$scope.somePlaceholder = "";
			$scope.IsVisible = $scope.IsVisible ? false : true;
		}
		$scope.HideShow = function() {
			$scope.somePlaceholder = "Email or UserName:";
			$scope.IsVisible = $scope.IsVisible ? false : true;
		}
});