<%-- <html>
<body>
<jsp:forward page="/LoginPage"></jsp:forward>
</body>
</html> --%>

<!--  <head>
 </head>
 <body ng-app="routerApp">
 <div class="container">
    THIS IS WHERE WE WILL INJECT OUR CONTENT ===============
    <div ui-view></div>
</div>
</body> -->
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/login.css">
<!-- <link rel="stylesheet" href="css/navigationBarCss/reset.css"> CSS reset
<link rel="stylesheet" href="css/navigationBarCss/style.css"> Resource style -->
<link rel="stylesheet" href="css/navigationBarCss/navigationbar.css">
<link rel="stylesheet" href="css/pageContent.css">
<link rel="stylesheet" href="css/todoNotesList.css">
<link rel="stylesheet" href="css/colorchange.css">
<link rel="stylesheet" href="css/dragAndDrop.css">



<!-- <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css"> -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/packery/2.1.1/packery.pkgd.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/draggabilly/2.1.1/draggabilly.pkgd.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0/angular-animate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui/0.4.0/angular-ui.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript" src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-drag-and-drop-lists/2.1.0/angular-drag-and-drop-lists.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-sortable/0.17.1/sortable.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.0/angular-sanitize.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap-tpls.min.js"></script>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>


<!-- <script type="text/javascript" src="bower_components/angular/angular.js"></script> -->
<!-- <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script> -->
<!-- <script type="text/javascript" src="bower_components/angular/angular.min.js"></script> -->
<!-- <script type="text/javascript" src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script> -->
<!-- <script src="js/pageJs/navigationBarJs/modernizr.js"></script> -->



<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript" src="js/pageJs/login.js"></script>
<!-- <script type="text/javascript" src="js/pageJs/navigationBarJs/navigationbar.js"></script> -->
<script type="text/javascript" src="js/controller/logincontroller.js"></script>
<script type="text/javascript" src="js/services/loginService.js"></script>
<script type="text/javascript" src="js/controller/registerController.js"></script>
<script type="text/javascript" src="js/services/registerService.js"></script>
<script type="text/javascript" src="js/controller/trashController.js"></script>
<script type="text/javascript" src="js/controller/archiveController.js"></script>


<script type="text/javascript" src="js/controller/directive.js"></script>
<script type="text/javascript" src="js/directives/navigationbar.js"></script>

<script type="text/javascript" src="js/directives/color.js"></script>
<script type="text/javascript" src="js/controller/TodoHomePageController.js"></script>
<script type="text/javascript" src="js/services/TodoHomePageService.js"></script>
<script type="text/javascript" src="js/directives/dragDropPackery.js"></script>
<script type="text/javascript" src="js/controller/profilePicController.js"></script>
<script type="text/javascript" src="js/services/profilePicService.js"></script>
<script type="text/javascript" src="js/controller/facebookLoginCompleteController.js"></script>
<script type="text/javascript" src="js/controller/collaborator.js"></script>
<script type="text/javascript" src="js/controller/reminderController.js"></script>
<script type="text/javascript" src="js/controller/forgotPassword.js"></script>



<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
</script>

<title>Google Keep</title>
<link rel="shortcut icon" href="Images/googlekeep.png" type="image/png" />
</head>
<body data-ng-app="todo" style="background-color: #e8e8e8">
	<div ui-view></div>
	<!-- <ui-view></ui-view> -->
</body>
</html>