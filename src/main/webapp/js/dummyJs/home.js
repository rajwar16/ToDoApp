var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "about.jsp",
    })
    .when("/london", {
        templateUrl : "london.jsp",
        controller : "londonCtrl"
    })
    .when("/paris", {
        templateUrl : "paris.jsp",
        controller : "parisCtrl"
    });
});
app.controller("londonCtrl", function ($scope) {
    $scope.msg = "I love London";
});
app.controller("parisCtrl", function ($scope) {
    $scope.msg = "I love Paris";
});
