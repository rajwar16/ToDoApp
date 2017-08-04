<!-- index.html -->

    <!-- define angular app -->
    <html ng-app="scotchApp">
    <head>
      <!-- SCROLLS -->
      <!-- load bootstrap and fontawesome via CDN -->
      <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
      <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />

      <!-- SPELLS -->
      <!-- load angular via CDN -->
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
          <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
      <script src="javaScript/pajeLoader.js"></script>
    </head>

    <!-- define angular controller -->
    <body ng-controller="mainController">
<!-- HEADER AND NAVBAR -->
        <header>
            <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/">Angular Routing Example</a>
                </div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- index.html -->

    <!-- define angular app -->
    <html ng-app="scotchApp">
    <head>
      <!-- SCROLLS -->
      <!-- load bootstrap and fontawesome via CDN -->
      <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
      <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />

      <!-- SPELLS -->
      <!-- load angular via CDN -->
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
          <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
      <script src="javaScript/pajeLoader.js"></script>
    </head>

    <!-- define angular controller -->
    <body ng-controller="mainController">
<!-- HEADER AND NAVBAR -->
        <header>
            <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/">Angular Routing Example</a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                    <li><a href="#about"><i class="fa fa-shield"></i> About</a></li>
                    <li><a href="#contact"><i class="fa fa-comment"></i> Contact</a></li>
                </ul>
            </div>
            </nav>
        </header>
    ...

    <!-- MAIN CONTENT AND INJECTED VIEWS -->
    <div id="main">
        {{ message }}

        <!-- angular templating -->
        <!-- this is where content will be injected -->
    </div>

 
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                    <li><a href="#about"><i class="fa fa-shield"></i> About</a></li>
                    <li><a href="#contact"><i class="fa fa-comment"></i> Contact</a></li>
                </ul>
            </div>
            </nav>
        </header>
    ...

    <!-- MAIN CONTENT AND INJECTED VIEWS -->
    <div id="main">
        {{ message }}

        <!-- angular templating -->
        <!-- this is where content will be injected -->
    </div>

  --%>
  
  
  
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script> 
$(document).ready(function(){
    $("#button1").click(function(){
        $("div").animate({
            left: '250px',
            opacity: '0.5',
            height: '150px',
            width: '150px'
        });
    });
     $("#button2").click(function(){
        $("div").animate({
        	left: '0px',
            background:'#98bf21',
            height:'100px',
            width:'100px',
            position:'absolute'
        });
    });
});
</script> 
</head>
<body>
<button id="button1">lft</button>
<button id="button2">rght</button>

<p>By default, all HTML elements have a static position, and cannot be moved. To manipulate the position, remember to first set the CSS position property of the element to relative, fixed, or absolute!</p>

<div style="background:#98bf21;height:100px;width:100px;position:absolute;"></div>

</body>
</html>

<!--  ghfggggggggggh gui897898m ggggggu ggggggggggg  -->

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.9/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript">
        var app = angular.module('MyApp', [])
        app.controller('MyController', function ($scope) {
            //This will hide the DIV y default.
            $scope.somePlaceholder="Show Hide DIV";
            $scope.IsVisible = false;
            $scope.ShowHide = function () {
               	$scope.somePlaceholder="";
                $scope.IsVisible = $scope.IsVisible ? false : true;
            }
            $scope.HideShow = function () {
               	$scope.somePlaceholder="Show Hide DIV";
                $scope.IsVisible = $scope.IsVisible ? false : true;
            }
        });
        
    </script>
    <div ng-app="MyApp" ng-controller="MyController">
        <div ng-show = "IsVisible">Show Hide DIV</div>
        <input id="hideplaceholder" type="text" placeholder="{{somePlaceholder}}" ng-click="ShowHide()" ng-blur="HideShow()" />
        <br/>
        <br/>
    </div>
</body>
</html>





  