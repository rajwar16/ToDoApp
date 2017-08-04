myApp.directive('color', function() {
    return {
      restrict: 'AE', // only activate on element attribute
      templateUrl : "template/color.html",
    };
  });
