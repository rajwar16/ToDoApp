<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-route.js"></script>
      <!-- SCROLLS -->
      <!-- load bootstrap and fontawesome via CDN -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />

      <!-- SPELLS -->
      <!-- load angular via CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
<!-- My js File -->
<script src="javaScript/home.js"></script>  
</head>

<body ng-app="myApp">
   <header>
            <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/">Angular Routing Example</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#/!"><i class="fa fa-home"></i> Main</a></li>
                    <li><a href="#!london"><i class="fa fa-shield"></i>City 1</a></li>
                    <li><a href="#!paris"><i class="fa fa-comment"></i>City 2</a></li>
                </ul>
            </div>
            </nav>
   </header>
<div ng-view></div>
</body>
</html>