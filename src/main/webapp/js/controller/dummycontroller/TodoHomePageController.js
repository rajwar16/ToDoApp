myApp.controller('showDivision',function ($scope,$http,$uibModal,$window,TodoHomePageService) {
	
	$http({
        method : "GET",
        url : "http://localhost:8080/TodoApp/ToDoNoteList",
        headers: {'accessToken': localStorage.getItem("accessToken")}
    }).then(function(data){
    	console.log("initial note data :: "+data.data);
    	$scope.records=data.data.reverse();
    });
	
	console.log("show division controller...");
    //This will hide the DIV by default.
	$scope.IsVisible = false;
    $scope.IsVisible1 = true;
    
    
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
        		this.title=x.title;
        		this.note=x.description;
        		this.id = x.id;
        		console.log("updated title :: "+this.title);
        		console.log("updated note :: "+this.note);
        		console.log("updated note :: "+this.id);
        		
        		this.updateNoteFunction=function(id){
        			console.log("inside updateNote functiom :: "+id.title1);
        			
        			var updateObj = {
        					title : this.title1,
        					description :  this.note1,
        					noteid :id
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
	
	
	$scope.listview=function()
	{
		console.log("list view");
		
		$scope.gridviewImg=false;
	    $scope.listviewImg=true;
	    $scope.space3col="";
	    $scope.view_change="col-lg-4 col-sm-9 col-md-4 col-xs-12";
	};
	
	$scope.gridview=function()
	{
		console.log("grid view");
		$scope.gridviewImg=true;
	    $scope.listviewImg=false;
	    
	    $scope.space3col="col-lg-3";
	    $scope.view_change = "col-sm-8 col-lg-8 col-xs-12 col-md-5";
	   
	};
	
	$scope.refresh= function () {
		console.log("refresh......");
		  window.location.reload(); 
		};
		$scope.IsVisible= function () {
			console.log("IsVisible......");
			  window.location.reload(); 
		};
	
});


