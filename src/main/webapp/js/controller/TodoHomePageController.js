myApp.controller('showDivision',function ($scope,$state,$http,$uibModal,$window,$rootScope, $timeout,TodoHomePageService,fileReader1,profilePicService) {	
	
	$http({
        method : "GET",
        url : "http://localhost:8080/TodoApp/ToDoNoteList",
        headers: {'accessToken': localStorage.getItem("accessToken")}
    }).then(function(data){
    	
    	console.log("accessToken:::"+localStorage.getItem("accessToken"));
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
		
    	var count=0; // for count pin
		var countView=0;  // for count pin
		$scope.records=data.data.list.reverse();
		
		for(var i=0;i<=$scope.records.length-1;i++)
		{
			if($scope.records[i].pin=='true')
			{
				count=count+1;
			}
			
			if($scope.records[i].pin=='false' && $scope.records[i].isArchive=='false' && $scope.records[i].isDelete=='false')
			{
				countView=countView+1;
			}
		}
		console.log("count view is  :: ",countView);
		if(count==0)
		{
			$scope.pinShow=false;
		}
		else{
			$scope.pinShow=true;
		}
		
		if(countView==0)
		{
			$scope.othershow=false;
		}
		else{
			$scope.othershow=true;
		}
		
		if(count==0 && countView!=0)
		{
			$scope.othershow=false;
		}
    	
    	console.log("initial note data :: "+$scope.records);
    	toUpdate=$scope.records;
    	$scope.userNameInitial=$scope.records[0].user.userName[0];
    	console.log("first character :: "+$scope.userNameInitial);
    	/*$scope.changeBgColor={"background-color" : "red"} */
    	
    	$scope.userId=$scope.records[0].user.id;
    	$scope.profileImage=$scope.records[0].user.profileImage;
    	$scope.imageSrc = $scope.profileImage;
        console.log("profile image source  imageSrc:: ",$scope.imageSrc);
    	
    	$scope.userName=$scope.records[0].user.userName;
    	$scope.email=$scope.records[0].user.email;
    	$scope.mobileNo=$scope.records[0].user.mobileNo;
    }, 
    function errorCallback(response) {
    	console.log("accessToken:::"+localStorage.getItem("accessToken"));
      
    });
	
	console.log("show division controller...");
    // This will hide the DIV by default.
	$scope.addImageDiv=false;
	$scope.addImageFunc=null;
	
    $scope.IsVisible = false;
    $scope.IsVisible1 = true ;
    $scope.createEditableNote=true;
    
    // list view and gridview image hide and show
    $scope.gridviewImg=false;
    $scope.listviewImg=true;
    
    $scope.menuImg="Images/navigationBarImages/menu.svg";
    $scope.listImg="Images/list.svg";
    $scope.gridImg="Images/navigationBarImages/grid.svg";
    $scope.refreshImg="Images/navigationBarImages/refresh.svg";
    $scope.notificationImg="Images/navigationBarImages/notification.svg";
    
    $scope.space3col="";
    $scope.colLg2="11.5%";
    $scope.colLg4="30%";
    $scope.colLg4view="28%";
    $scope.view_change="col-lg-4 col-sm-9 col-md-4 col-xs-12";
    /*
	 * $scope.space3col=""; $scope.view_change="col-lg-4 col-sm-9 col-md-4
	 * col-xs-12";
	 */
    
    // navigation bar
    $scope.navigationBar="rgb(255, 187, 0)";
    $scope.navigationBorder="rgb(255, 187, 0)";
    $scope.google=true;
    $scope.navbarName="keep";
    
	$scope.pinupHtml=true;
   	$scope.viewHtml=true;
   	$scope.trashHtml=false;
   	$scope.archiveHtml=false;
    
    // is pin
    $scope.pinup=false;
    $scope.pinImg=true;
    $scope.unpinImg=false;
    $scope.createPinup=false;
    $scope.pinShow=false;
    $scope.othershow=false;
    
    // isdeleted
    $scope.isDelete=false;
    
    // is archive
    $scope.isArchive=false;
    
    $scope.color="#FFF";
    
    //----------- profilepic----------------------

    // hide show division function----------
    $scope.ShowHide = function () {
    	console.log("hide and show function...");
        // If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
    };
    // end
    
    // --------------------------toaster-------------------------------------------
    $scope.setOptions = function() {
        toastr.options.positionClass = "toast-bottom-left";
        toastr.options.closeButton = true;
        toastr.options.showMethod = 'slideDown';
        toastr.options.hideMethod = 'slideUp';
      };
      $scope.setOptions();
      $scope.success = function() {
    	  console.log("toster function :: ");
          toastr.success('This is a success toaster');
        };
    // -------------------------end toaster-----------------------------------------
    //create note----------------------------------------------------------------------------    
    $scope.createArchive=function(value)
    {
    	console.log("archive value is :: ",value);
    	toastr.success('Note archive');
    	$scope.isArchive=value;
    }
    
    $scope.createPinup=function(value)
    {
    	console.log("pinned value is :: ",value);
    	$scope.pinup=value;
    	$scope.pinImg=false;
        $scope.unpinImg=true;
    }

	// create note Function...........
	$scope.createNote=function()
	{
		$scope.IsVisible = false;
	    $scope.IsVisible1 = true ;
	    
	    console.log("title is :: ", $scope.title," ","description is :: ",$scope.note);
	    if(angular.isUndefined($scope.title) && angular.isUndefined($scope.note))
	    {
	    	console.log("inside empty note title is :: ",$scope.title," ","description is :: ",$scope.note);
	    	$state.go("ToDoHomePage");
	    }
	    else{
		var createNoteObject = {
				title : $scope.title,
				description : $scope.note,
				color:$scope.createColor,
				pin:$scope.pinup,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
				addImage:$scope.addImageFunc
		}
		console.log("division data :: "+createNoteObject.isdelete);
		
		var result=TodoHomePageService.createNote(createNoteObject).then(function(data){
			console.log("todo notes create successfully...",data);		
			console.log("todo notes list :: ",data.data.list);
			console.log("list of headers :: "+data.headers('accessToken'));
			console.log("my status :: "+data.data.status);	
			console.log("my messages :: "+data.data.message);
			
			if(data.data.status==200)
			{
				console.log("get all notes.....................");
				$scope.getAllNotes();
				/*var count=0;
				var countView=0;
				$scope.records=data.data.list.reverse();
				
				for(var i=0;i<=$scope.records.length-1;i++)
				{
					if($scope.records[i].pin=='true')
					{
						count=count+1;
					}
					
					if($scope.records[i].pin=='false' && $scope.records[i].isArchive=='false' && $scope.records[i].isDelete=='false')
					{
						countView=countView+1;
						console.log("count view is  :: ",countView);
					}
				}
				console.log("count view is  :: ",countView);
				if(count==0)
				{
					$scope.pinShow=false;
				}
				else{
					$scope.pinShow=true;
				}
				
				if(countView==0)
				{
					$scope.othershow=false;
				}
				else{
					$scope.othershow=true;
				}
				
				if(count==0 && countView!=0)
				{
					$scope.othershow=false;
				}
				*/
				$scope.title=null;
				$scope.note=null;
				$scope.addImageFunc=null;
				$scope.addImageDiv=false;
				$scope.pinup=false;
				$scope.pinImg=true;
				$scope.unpinImg=false;
				$scope.createColor="#FFF";
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			
			else
			{
				console.log("invalid credintial..");
				$state.go("login");
			}
		});
	    }
	};
	// end of createNote function
    
    // popup function
    $scope.open = function (x) {
    	console.log("open popup data....."+x +"jjoo"+x.title+"  decsc :: "+x.description);
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
        		this.pin=x.pin;
        		this.isDelete=x.isDelete;
        		this.isArchive=x.isArchive;
        		this.addImage1=x.addImage
        		console.log("updated title :: "+this.title);
        		console.log("updated note :: "+this.note);
        		console.log("updated note :: "+this.id);
        		
        		/*
        		$scope.$on("fileProgress1", function(e, progress) {
        		      $scope.progress = progress.loaded / progress.total;
        		  });*/
        		
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
        					pin:this.pin,
        					isDelete:this.isDelete,
        					isArchive:this.isArchive,
        					addImage:this.addImage1
        			}
        			
        			var result=TodoHomePageService.updateNote(updateObj).then(function(data){
        				if(data.data.status==200)
        				{
        					$scope.getAllNotes();
        				}
        				
        				else if(data.data.status==-2)
        				{
        					$state.go("login");
        				}
        				
        				else
        				{
        					$state.go("ToDoHomePage");
        				}
        			});
        		};
        	},
        	controllerAs: "$ctrl",
        });
      };
    
  // hide show division function----------
    $scope.ShowHide1= function() {
    	console.log("onblur hide and show 1 function...");
        // If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
	};
	
	 $scope.showPopupData= function(x) {
	    	console.log("showPopupData title..."+x.title);
	    	console.log("showPopupData description..."+x.description);
	    	$scope.updateDataNote=x;
		};
		
	// deletePermanent note function-----------
	$scope.deletePermanent=function(noteid)
	{
		console.log("note id is :: "+noteid);
		var result=TodoHomePageService.deleteNote(noteid).then(function(data){
			if(data.data.status==200)
			{
				/*$scope.records=data.data.list.reverse();*/
				$scope.getAllNotes();
				console.log("200 success delete Notes ::  ",$scope.records);
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			else
			{
				console.log("notedeleted");
				$state.go("ToDoHomePage");
			}
		});
	}
		
	// deletePermanent note function--------------------
	
	// delete note function-----------------------------
		$scope.deleteNote=function(x,value)
		{
			console.log("note is :: "+x);
			$scope.isDelete=value;
			
			var updateObj = {
					noteid :x.noteid,
					title :x.title,
					description :x.description,
					noteCreatedDate:x.noteCreatedDate,
					noteEditedDate:x.noteEditedDate,
					color:x.color,
					pin:false,
					isDelete:$scope.isDelete,
					isArchive:$scope.isArchive,
					addImage:x.addImage
			}
			
			var result=TodoHomePageService.updateNote(updateObj).then(function(data){
				if(data.data.status==200)
				{
					$scope.getAllNotes();
				}
				
				else if(data.data.status==-2)
				{
					$state.go("login");
				}
				else
				{
					$state.go("ToDoHomePage");
				}
			});
			
			if(value=='true')
			{
				toastr.success('Note trashed');
			}
			else
			{
				toastr.success('Note restored');
			}
		}	
		
	// end of delete note function........
	
	// empty trash function------------
		$scope.emptyTrash=function()
		{
			console.log("empty trash....");
			var result=TodoHomePageService.emptyTrash().then(function(data){
				if(data.data.status==200)
				{
					$scope.records=data.data.list.reverse();
					
					console.log("200 success delete Notes ::  ",$scope.records);
				}
				else if(data.data.status==-2)
				{
					$state.go("login");
				}
				else
				{
					console.log("notedeleted");
					$state.go("ToDoHomePage");
				}
			});
		}
	// ------------empty trash function ended----
	// ------------list view function---------------------------
	$scope.listview=function()
	{
		console.log("list view");
		$scope.gridviewImg=false;
	    $scope.listviewImg=true;
	    $scope.space3col="";
	    $scope.colLg2="13.666667%";
	    $scope.colLg4="30%";
	    $scope.colLg4view="28%";
	    $scope.view_change="col-lg-4 col-sm-9 col-md-4 col-xs-12";
	};
	// ----------grid view function---------------------------
	$scope.gridview=function()
	{
		console.log("grid view");
		$scope.gridviewImg=true;
	    $scope.listviewImg=false;
	    
	    $scope.space3col="col-lg-2";
	    $scope.colLg2="4.333333%";
	    $scope.colLg4="";
	    $scope.colLg4view="";
	    $scope.view_change = "col-sm-8 col-lg-8 col-xs-12 col-md-5";
	};
	// -------------------isPin()-------------------------
	$scope.isPin=function(x,value)
	{
		$scope.pinup=value;
		var updatePin = {
				noteid :x.noteid,
				title :x.title,
				description :x.description,
				noteCreatedDate:x.noteCreatedDate,
				noteEditedDate:x.noteEditedDate,
				color:x.color,
				pin:$scope.pinup,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
				addImage:x.addImage
		}
		/*var updatePin = {
				noteid :x.noteid,
				pin:$scope.pinup,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
		}*/
		
		var result=TodoHomePageService.updateNote(updatePin).then(function(data){
			if(data.data.status==200)
			{
				$scope.getAllNotes();
				$scope.pinup=false;
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			
			else
			{
				$state.go("ToDoHomePage");
			}
		});
	   
	};
	// -------------------isPin()--------------------------
	
	// -------------------isArchive()----------------------
	$scope.isarchive=function(x,value)
	{
		$scope.isArchive=value;
		var updatePin = {
				noteid :x.noteid,
				title :x.title,
				description :x.description,
				noteCreatedDate:x.noteCreatedDate,
				noteEditedDate:x.noteEditedDate,
				color:x.color,
				pin:false,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
				addImage:$scope.addImage
		}
		
		var result=TodoHomePageService.updateNote(updatePin).then(function(data){
			if(data.data.status==200)
			{
				$scope.getAllNotes();
				$scope.pinup=false;
			}
			else
			{
				$state.go("ToDoHomePage");
			}
		});
		
		if(value=='true')
		{
			toastr.success('Note archived');
		}
		else
		{
			toastr.success('Note unarchived');
		}
	   
	};
	//-----------------------------end isArchive()--------------------------
	//-----------------------------isreminder()-----------------------------
	
	$scope.reminder= function (x,day) {
		console.log("new date.....",new Date());
		var currentDate=new Date();
		$scope.day=day;
		var days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
		console.log("week day :: ",days[currentDate.getDay()]);
		
		if($scope.day=='today')
		{
			var today = new Date();
			today.setHours(20, 0, 0);
		    $scope.day = new Date(today);
		    console.log("calculated day :: ",$scope.day);
		}
		
		else if($scope.day=='tomorrow')
		{
			 var tomorrow = new Date(currentDate);
			 tomorrow.setHours(20, 0, 0);
		     tomorrow.setDate(tomorrow.getDate() + 1);
		     $scope.day = new Date(tomorrow);
		     console.log("calculated day :: ",$scope.day);
		}
		else if($scope.day=='nextWeek')
		{
			var nextWeek = new Date(currentDate);
			nextWeek.setHours(20, 0, 0);
			nextWeek.setDate(nextWeek.getDate() + 7);
		    $scope.day = new Date(nextWeek);
		    console.log("calculated day :: ",$scope.day);
		}
		
		else{
			
		}
		
		var reminder={
				noteId :x.noteid,
				reminder:$scope.day
		}
		
		var result=TodoHomePageService.reminder(reminder).then(function(data){
			if(data.data.status==200)
			{
				$scope.getAllNotes();
				$scope.pinup=false;
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			else
			{
				$state.go("ToDoHomePage");
			}
		});
		
		$scope.reminderDiv=true;
	};
	// ---------------------------------end isreminder()------------------
	
	//-----------------------------------collaborator--------------------
	 $scope.collaborator = function (x) {
		console.log("colaborator function called",x);
		$rootScope.noteObject=x; 
		var modalInstance = $uibModal.open({
        	templateUrl: "template/collaborator.html",
        	controller: "collaborator",
        	data: {
    			noteObject:x
            }
		});
		modalInstance.result.catch(function(error) {
		      console.log("error", error);
		});
		this.cancel = function() {
		      $uibModalInstance.dismiss('cancel');
		};
	}
	//-------------------------------------add Images------------------------------
	 
	 $scope.$on("fileProgress1", function(e, progress) {
	      $scope.progress = progress.loaded / progress.total;
	  });
	 
	$scope.formdata = new FormData();
	
	$scope.addImage=function(addImageFunc){
		 $scope.addImageDiv=true;
	}
	
	//--------view image-------------------------
	$scope.triggerUpload=function()
	{
		console.log("upload pic on view....");
		var fileuploader = angular.element("#fileInput");
		
	    fileuploader.on('click',function(){
	    })
	    
	    fileuploader.trigger('click');
	    console.log("incomimg img",$scope.addImageFunc);
	}

	
	/*$scope.getTheFiles = function ($files) {
		angular.forEach($files, function (value, key) {
            formdata.append(key, value);
        });
    };*/
	
	//-------------------------------end of add Image--------------------------------------
	
	//-------------------------------open profile pic--------------------------------------
	$scope.openProfilepic=function()
	{
		console.log("open Profile pic :: ");
		var modalInstance = $uibModal.open({
        	templateUrl: "template/profilepic.html",
        	controller: "profilePicController",
		});
		modalInstance.result.catch(function(error) {
		      console.log("error", error);
		});
		this.cancel = function() {
		      $uibModalInstance.dismiss('cancel');
		};
		
	};
	//------------------closed profile pic------------------------------------------

	// -----------------------------refresh function---------------------------
	$scope.refresh= function () {
		console.log("refresh......");
		  window.location.reload(); 
	};
	
	// -----------------------------end refresh function---------------------------
	
	// -----------------------------logout function---------------------------
	$scope.logout= function () {
		
		var result=TodoHomePageService.logout().then(function(data){
			if(data.data.status==200)
			{
				localStorage.setItem("accessToken","");
				localStorage.setItem("refreshToken","");
				$state.go("login");
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
		});
	};
	//-----------------------------end logout function---------------------------
	
	//------------------------------- GetAll notes function-----------------------
	$scope.getAllNotes=function()
	{
		console.log("all recordss value....",$scope.records);
		var result=TodoHomePageService.getAllNotes().then(function(data){	
			if(data.data.status==200)
			{
				
				var count=0;
				var countView=0;
				$scope.records=data.data.list.reverse();
				
				console.log("all recordss value....",$scope.records);
				for(var i=0;i<=$scope.records.length-1;i++)
				{
					if($scope.records[i].pin=='true')
					{
						count=count+1;
					}
					
					if($scope.records[i].pin=='false' && $scope.records[i].isArchive=='false' && $scope.records[i].isDelete=='false')
					{
						countView=countView+1;
					}
				}
				if(count==0)
				{
					$scope.pinShow=false;
				}
				else{
					$scope.pinShow=true;
				}
				
				if(countView==0)
				{
					$scope.othershow=false;
				}
				else{
					$scope.othershow=true;
				}
				
				if(count==0 && countView!=0)
				{
					$scope.othershow=false;
				}
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			else
			{
				$state.go("ToDoHomePage");
			}
		});
		
		/*toUpdate=$scope.records;
    	$scope.userNameInitial=$scope.records[0].user.userName[0];
    	console.log("first character :: "+$scope.userNameInitial);
    	$scope.changeBgColor={"background-color" : "red"} 
    	
    	$scope.userId=$scope.records[0].user.id;
    	$scope.profileImage=$scope.records[0].user.profileImage;
    	$scope.imageSrc = $scope.profileImage;
        console.log("profile image source  imageSrc:: ",$scope.imageSrc);
    	
    	$scope.userName=$scope.records[0].user.userName;
    	$scope.email=$scope.records[0].user.email;
    	$scope.mobileNo=$scope.records[0].user.mobileNo;*/
	}
	//------------------GetAll note function ended----------------------------
	
	
	// ----------select color for div view function---------------------------
	$scope.changedivColor=function(x,color)
	{	
		if(x==null)
		{
			$scope.createColor=color;
			return;
		}
		
		if(x.pin=='true')
		{
			$scope.pinup=true;
		}
		if(x.isArchive=='true')
		{
			$scope.isArchive=true;
		}
		
		$scope.color=color;
		/* $scope.divColor=$scope.color; */
		
		var updateColor = {
				noteid :x.noteid,
				title :x.title,
				description :x.description,
				noteCreatedDate:x.noteCreatedDate,
				noteEditedDate:x.noteEditedDate,
				color:$scope.color,
				pin:$scope.pinup,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
				addImage :x.addImage
		}
		
		var result=TodoHomePageService.updateNote(updateColor).then(function(data){
			if(data.data.status==200)
			{
				$scope.getAllNotes();
				/*$scope.records=data.data.list.reverse();
				console.log("200 success delete Notes ::  ",$scope.records);*/
				$scope.pinup=false;
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			
			else
			{
				$state.go("ToDoHomePage");
			}
		});
	}
});

myApp.directive('dndList', function () {
	   return function($scope, $element, attrs) {
	       // variables used for dnd
		   var toUpdate;
		   $scope.$watch(attrs.dndList, function (records) {
	           toUpdate = records;
	       }, true);
	      
	       var startIndex = 0;
	       $($element[0]).sortable({
	           start: function (event, ui) {
	               // on start we define where the item is dragged from
	        	   console.log("ui item :: ",ui);
	               startIndex = ($(ui.item).index());
	               console.log("startIndex index :: ",startIndex);
	             
	           },
	           stop: function (event, ui) {
	               // on stop we determine the new index of the
	               // item and store it there
	               var newIndex = ($(ui.item).index());
	               console.log("newIndex : : ",newIndex);
	               
	               var toMove = toUpdate[startIndex];
	               console.log("tomove : : ",toMove);
	               
	               toUpdate.splice(startIndex, 1);
	               toUpdate.splice(2, 0, toMove);
	               // we move items in the array, if we want
	               // to trigger an update in angular use $apply()
	               // since we're outside angulars lifecycle
	               $scope.$apply($scope.records);

	               console.log($scope.records);
	           },
	           update: function(event, ui) {
	        	    // grabs the new positions now that we've finished sorting
	        	    var new_position = ui.item.index();
	        	}
	       })
	   }
	})
/*
 * $scope.isList = false; var cView = readCookie('view'); if( 'true' == cView ) {
 * $scope.isList = true; } else { $scope.isList = false; } this.changeView =
 * function(){ writeCookie('view', $scope.isList); }
 */