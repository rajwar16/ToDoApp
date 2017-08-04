myApp.directive('navigationbar', function() {
    return {
      restrict: 'AE', // only activate on element attribute
      templateUrl : "template/navigationbar.html",
    };
  });