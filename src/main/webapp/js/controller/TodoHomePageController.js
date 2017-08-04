myApp.controller('showDivision',function ($scope,$state,$http,$uibModal,$window,TodoHomePageService) {
	
	$http({
        method : "GET",
        url : "http://localhost:8080/TodoApp/ToDoNoteList",
        headers: {'accessToken': localStorage.getItem("accessToken")}
    }).then(function(data){
		if(data.data.status==-2)
		{
			console.log("accessToken expired status ::  ", data.data.status);
			
			var token = {
					accessToken : localStorage.getItem("accessToken"),
					refreshToken : localStorage.getItem("refreshToken")
			}
			console.log("token object access token :: "+token.accessToken);
			
			var refreshToken=TodoHomePageService.getRefreshToken(token).then(function(data){
				console.log("getting refresh and accessToken::  200 status  ::  ",data.data.status);
				if(data.data.status==200)
				{
					console.log("getting refresh and accessToken::  200 status  ::  ",data.data.status);
					localStorage.setItem("accessToken",data.data.accessToken);
					localStorage.setItem("refreshToken",data.data.refreshToken);
				}
				else if(data.data.status==-2)
				{
					console.log("refreshToken is expired you have to login  status ::  ", data.data.status);
					$state.go("login");
				}
				else if(data.data.status==400)
				{
					console.log("accessToken is missing you have to login  status ::  ", data.data.status);
					$state.go("login");
				}
			});
		}
		
    	$scope.records=data.data.list.reverse();
    	console.log("initial note data :: "+$scope.records);
    	$scope.userName=$scope.records[0].user.userName[0];
    	console.log("first character :: "+$scope.userName);
    	/*$scope.changeBgColor={"background-color" : "red"}*/
    });
	
	console.log("show division controller...");
    //This will hide the DIV by default.
    $scope.IsVisible = false;
    $scope.IsVisible1 = true ;
    
    //list view and gridview image hide and show
    $scope.gridviewImg=false;
    $scope.listviewImg=true;
    $scope.space3col="";
    $scope.view_change="col-lg-4 col-sm-9 col-md-4 col-xs-12";

    //hide show division function----------
    $scope.ShowHide = function () {
    	console.log("hide and show function...");
        //If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
    };
    //end
    
    
    //hoverhideshow function
    
   /* $scope.hoverHideShow = function (x) {
    	console.log("hide and show edit div function..."+x);
    	$scope.hoverHideShow1=!$scope.hoverHideShow1;
    };*/
    
    //end of hideovershow function
    
    //popup function
    $scope.open = function (x) {
    	console.log("open popup data....."+x.title+"  decsc :: "+x.description);
        var modalInstance = $uibModal.open({
        	templateUrl: "template/popupdiv.html",
        	controller: function($uibModalInstance) {
        		var $ctrl = this;
        		console.log("open popup controller data....."+x.title+"  decsc :: "+x.description+"id :: "+x.id);
        		this.id = x.noteid;
        		this.title=x.title;
        		this.note=x.description;
        		this.noteCreatedDate=x.noteCreatedDate;
        		this.noteEditedDate=x.noteEditedDate;
        		this.color=x.color;
        		console.log("updated title :: "+this.title);
        		console.log("updated note :: "+this.note);
        		console.log("updated note :: "+this.id);
        		
        		this.changedivColor=function(color)
        		{
        			this.color=color;
        			console.log("popup color function :: "+color)
        		}
        		
        		this.updateNoteFunction=function(id){
        			console.log("inside updateNote functiom :: "+id);
        			$uibModalInstance.dismiss('Done');
        			var updateObj = {
        					noteid :id,
        					title : this.title,
        					description :  this.note,
        					noteCreatedDate:this.noteCreatedDate,
        					noteEditedDate:this.noteEditedDate,
        					color:this.color,
        			}
        			
        			console.log("update function title :: "+updateObj.title);
        			console.log("update function description :: "+updateObj.description);
        			console.log("update function id :: "+id);
        			
        			var result=TodoHomePageService.updateNote(updateObj).then(function(data){
        				console.log("todo notes updated successfully...",data);		
        				console.log("todo notes list :: ",data.data.list);
        				console.log("list of headers :: "+data.headers('accessToken'));
        				console.log("my status :: "+data.data.status);	
        				console.log("my messages :: "+data.data.message);
        				
        				if(data.data.status==200)
        				{
        					$scope.records=data.data.list.reverse();
        					console.log("200 success delete Notes ::  ",$scope.records);
        				}
        				
        				else
        				{
        					console.log("noteUpdated");
        					$state.go("ToDoHomePage");
        				}
        			});
        		};
        	},
        	controllerAs: "$ctrl",
        });
      };
    
    
    
    
  //hide show division function----------
    $scope.ShowHide1= function() {
    	console.log("onblur hide and show 1 function...");
        //If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
	};
	
	 $scope.showPopupData= function(x) {
	    	console.log("showPopupData title..."+x.title);
	    	console.log("showPopupData description..."+x.description);
	    	$scope.updateDataNote=x;
		};
		
		//update note function-----------
		$scope.updateNote= function(x) {
	    	console.log("updateNote title..."+x);
	    	console.log("popup title :: "+$scope.popupTitle);
	    	console.log("popup title :: "+$scope.popupNote);
		};
	
	//delete note function-----------
	$scope.deleteNote=function(noteid)
	{
		console.log("note id is :: "+noteid);
		var result=TodoHomePageService.deleteNote(noteid).then(function(data){
			console.log("todo notes create successfully...",data);		
			console.log("todo notes list :: ",data.data.list);
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			if(data.data.status==200)
			{
				$scope.records=data.data.list.reverse();
				console.log("200 success delete Notes ::  ",$scope.records);
			}
			
			else
			{
				console.log("notedeleted");
				$state.go("ToDoHomePage");
			}
		});
		
	}
	//end of delete note function........
	
	
	//GetAll notes function-----------
	$scope.getAllNotes=function()
	{
		
		var result=TodoHomePageService.getAllNotes().then(function(data){
			console.log("todo notes create successfully...",data);		
			console.log("todo notes list :: ",data.data.list);
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			if(data.data.status==200)
			{
				$scope.records=data.data.list.reverse();
				console.log("200 success delete Notes ::  ",$scope.records);
			}
			
			else
			{
				console.log("notedeleted");
				$state.go("ToDoHomePage");
			}
		});
		
	}
	//GetAll note function ended........
	
	
	//create note Function...........
	$scope.createNote=function()
	{
		$scope.IsVisible = false;
	    $scope.IsVisible1 = true ;
	    
		console.log("createNote function inputtext..."+ $scope.title);
		console.log("createNote function inputtext..."+ $scope.note);
		
		var createNoteObject = {
				title : $scope.title,
				description : $scope.note
		}
		console.log("division data :: "+createNoteObject.title);
		
		var result=TodoHomePageService.createNote(createNoteObject).then(function(data){
			console.log("todo notes create successfully...",data);		
			console.log("todo notes list :: ",data.data.list);
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			
			if(data.data.status==200)
			{
				$scope.records=data.data.list.reverse();
				console.log("200 success getting all Notes ::  ",$scope.records);
				$scope.title=null;
				$scope.note=null;
			}
			
			else
			{
				console.log("invalid credintial..");
				$state.go("login");
			}
		});
	};
	//end of createNote function
	
	
	//----------list view function---------------------------
	$scope.listview=function()
	{
		console.log("list view");
		
		$scope.gridviewImg=false;
	    $scope.listviewImg=true;
	    $scope.space3col="";
	    $scope.view_change="col-lg-4 col-sm-9 col-md-4 col-xs-12";
	};
	
	
	//----------grid view function---------------------------
	$scope.gridview=function()
	{
		console.log("grid view");
		$scope.gridviewImg=true;
	    $scope.listviewImg=false;
	    
	    $scope.space3col="col-lg-3";
	    $scope.view_change = "col-sm-8 col-lg-8 col-xs-12 col-md-5";
	   
	};
	
	
	
	//----------refresh function---------------------------
	$scope.refresh= function () {
		console.log("refresh......");
		  window.location.reload(); 
	};
	
	
	//----------select color for div view function---------------------------
	$scope.changedivColor=function(x,color)
	{
		console.log("color change note :: ",x);
		console.log("color change note color :: ",color);
		
		var updateColor = {
				noteid :x.noteid,
				title :x.title,
				description :x.description,
				noteCreatedDate:x.noteCreatedDate,
				noteEditedDate:x.noteEditedDate,
				color:color
		}
		
		var result=TodoHomePageService.updateNote(updateColor).then(function(data){
			console.log("todo notes updated successfully...",data);		
			console.log("todo notes list :: ",data.data.list);
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			if(data.data.status==200)
			{
				$scope.records=data.data.list.reverse();
				console.log("200 success delete Notes ::  ",$scope.records);
			}
			
			else
			{
				console.log("noteUpdated");
				$state.go("ToDoHomePage");
			}
		});
	}
});


myApp.directive('dndList', function () {
	   return function($scope, element, attrs) {
	       // variables used for dnd
	       var toUpdate;
	       var startIndex = -1;
	       // watch the model, so we always know what element
	       // is at a specific position
	       //scope.$watch(attrs.dndList, function (value, new1) {
	       //    toUpdate = value, new1;
	       //}, true);
	       // use jquery to make the element sortable (dnd). This is called
	       // when the element is rendered
	      
	       $(element[0]).sortable({
	           items: 'span',
	           start: function (event, ui) {
	               // on start we define where the item is dragged from
	               startIndex = ($(ui.item).index());
	             
	           },
	           stop: function (event, ui) {
	               // on stop we determine the new index of the
	               // item and store it there
	            
	               
	   
	               var newIndex = ($(ui.item).index());
	             
	               var toMove = toUpdate[startIndex];
	               toUpdate.splice(startIndex, 1);
	     
	               toUpdate.splice(newIndex, 0, toMove);
	               // we move items in the array, if we want
	               // to trigger an update in angular use $apply()
	               // since we're outside angulars lifecycle
	               scope.$apply($scope.records);

	               console.log($scope.records);
	           },

	       })
	   }
	   /*dkljhtlyjuyt*rtytry*/


	})

