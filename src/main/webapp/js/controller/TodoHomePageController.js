myApp.controller('showDivision',function ($scope,$state,$http,$uibModal,$window,$rootScope, $timeout,TodoHomePageService,fileReader1,profilePicService) {	
	
	$http({
        method : "GET",
        url : "http://localhost:8080/TodoApp/ToDoNoteList",
        headers: {'accessToken': localStorage.getItem("accessToken")}
    }).then(function(data){
    	console.log("status is :: :: :: ",data.data.status==-2)
		if(data.data.status==-2)
		{
			/*var token = {
					accessToken : localStorage.getItem("accessToken"),
					refreshToken : localStorage.getItem("refreshToken")
			}
			
			var refreshToken=TodoHomePageService.getRefreshToken(token).then(function(data){
				if(data.data.status==200)
				{
					localStorage.setItem("accessToken",data.data.accessToken);
					localStorage.setItem("refreshToken",data.data.refreshToken);
				}
				else if(data.data.status==-2)
				{
					$state.go("login");
				}
				else if(data.data.status==400)
				{
					$state.go("login");
				}
			});*/
			$scope.getRefreshToken();
		}
		else if(data.data.status==-1)
		{
			$state.go("login");
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
    	
    	toUpdate=$scope.records;
    	
    	$scope.user=data.data.user;
    	$scope.userNameInitial=$scope.user.userName[0];
    	$scope.changeBgColor={"background-color" : "red"} 
    	
    	$scope.userId=$scope.user.id;
    	if($scope.user.profileImage==null)
    	{
    		$scope.profileImage=$scope.user.facebookProfile;
    	}
    	else{
    		$scope.profileImage=$scope.user.profileImage;
    	}
    	
    	$scope.imageSrc = $scope.profileImage;
    	
    	$scope.userName=$scope.user.userName;
    	$scope.email=$scope.user.email;
    	$scope.mobileNo=$scope.usermobileNo;
    }, 
    function errorCallback(response) {
    });
	
    // This will hide the DIV by default.
	$scope.addImageDiv=false;
	$scope.addImageFunc=null;
	
    $scope.IsVisible = false;
    $scope.IsVisible1 = true ;
    $scope.createEditableNote=true;
    $scope.createReminderDiv=false;
    
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
   	$scope.reminderHtml=false;
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
    
    //----------profilepic----------------------

    // hide show division function----------
    $scope.ShowHide = function () {
        // If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
    };
    // ----------------------------end-------------------------
    
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
    	console.log();
    	if($scope.pinup==value)
    	{
    		$scope.pinup=false;
    		$scope.isArchive=value;
    		$scope.createNote();
    		toastr.success('Note unpinned and archive');
    	}
    	else{
        	$scope.isArchive=value;	
        	$scope.createNote();
        	toastr.success('Note archive');
    	}
    }
    
    $scope.createPinup=function(value)
    {
    	console.log("pinned value is :: ",value);
    	$scope.pinup=value;
    	$scope.pinImg=false;
        $scope.unpinImg=true;
    }

	// -------------------------------create note Function---------------------------------
	$scope.createNote=function()
	{
		$scope.IsVisible = false;
	    $scope.IsVisible1 = true ;
	    
	    if(angular.isUndefined($scope.title) && angular.isUndefined($scope.note))
	    {
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
				isReminder:$scope.createday,
				addImage:$scope.addImageFunc
		}
		
		var result=TodoHomePageService.createNote(createNoteObject).then(function(data){
			if(data.data.status==200)
			{
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
        var modalInstance = $uibModal.open({
        	templateUrl: "template/popupdiv.html",
        	controller: function($uibModalInstance) {
        		var $ctrl = this;
        		this.id = x.noteid;
        		this.title=x.title;
        		this.note=x.description;
        		this.noteCreatedDate=x.noteCreatedDate;
        		this.noteEditedDate=x.noteEditedDate;
        		this.color=x.color;
        		this.pin=x.pin;
        		this.isDelete=x.isDelete;
        		this.isArchive=x.isArchive;
        		this.reminder=x.isReminder;
        		this.addImage1=x.addImage;
        		
        		this.pageScrapedata1=x.pageScrapedata;
        		this.changedivColor=function(color)
        		{
        			this.color=color;
        		}
        		
        		this.updateNoteFunction=function(id){
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
        					isReminder:this.reminder,
        					addImage:this.addImage1,
        					pageScrapedata:this.pageScrapedata1
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
        // If DIV is visible it will be hidden and vice versa.
        $scope.IsVisible = !$scope.IsVisible;
        $scope.IsVisible1 = !$scope.IsVisible1;
	};
	
	 $scope.showPopupData= function(x) {
	    	$scope.updateDataNote=x;
		};
		
	// deletePermanent note function-----------
	$scope.deletePermanent=function(noteid)
	{
		var result=TodoHomePageService.deleteNote(noteid).then(function(data){
			if(data.data.status==200)
			{
				/*$scope.records=data.data.list.reverse();*/
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
	}
		
	// deletePermanent note function--------------------
	
	// delete note function-----------------------------
		$scope.deleteNote=function(x,value)
		{
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
			var result=TodoHomePageService.emptyTrash().then(function(data){
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
		}
	// ------------empty trash function ended----
	// ------------list view function---------------------------
	$scope.listview=function()
	{
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
		var updatePin = {
				noteid :x.noteid,
				title :x.title,
				description :x.description,
				noteCreatedDate:x.noteCreatedDate,
				noteEditedDate:x.noteEditedDate,
				color:x.color,
				pin:value,
				isDelete:$scope.isDelete,
				isArchive:$scope.isArchive,
				addImage:x.addImage
		}
		
		var result=TodoHomePageService.updateNote(updatePin).then(function(data){
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
	// -------------------------isPin()--------------------------
	// -------------------------isArchive()----------------------
	$scope.isarchive=function(x,value)
	{
		console.log("image is :: ",$scope.addImage);
		var updateArchive = {
				noteId :x.noteid,
				archive:value,
		}
		var result=TodoHomePageService.archive(updateArchive).then(function(data){
			if(data.data.status==200)
			{
				$scope.getAllNotes();
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
	//-----------------------------create isreminder()-----------------------------
	$scope.createReminder= function (day) {
		console.log(day);
		$scope.createday=day;
		if($scope.createday=='today')
		{
			var today = new Date();
			today.setHours(20, 0, 0);
		    $scope.createday = new Date(today);
		    $scope.createReminderDiv=true;
		}
		
		else if($scope.createday=='tomorrow')
		{
			 var tomorrow = new Date(currentDate);
			 tomorrow.setHours(20, 0, 0);
		     tomorrow.setDate(tomorrow.getDate() + 1);
		     $scope.createday = new Date(tomorrow);
		     $scope.createReminderDiv=true;
		}
		else if($scope.createday=='nextWeek')
		{
			var nextWeek = new Date(currentDate);
			nextWeek.setHours(20, 0, 0);
			nextWeek.setDate(nextWeek.getDate() + 7);
		    $scope.createday = new Date(nextWeek);
		    $scope.createReminderDiv=true;
		}
		else{
			
		}
	}
	//----------------------------isreminder()-----------------------------
	$scope.reminder= function (x,day) {
		var currentDate=new Date();
		$scope.day=day;
		var days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
		
		if($scope.day=='today')
		{
			var today = new Date();
			today.setHours(20, 0, 0);
		    $scope.day = new Date(today);
		}
		
		else if($scope.day=='tomorrow')
		{
			 var tomorrow = new Date(currentDate);
			 tomorrow.setHours(20, 0, 0);
		     tomorrow.setDate(tomorrow.getDate() + 1);
		     $scope.day = new Date(tomorrow);
		}
		else if($scope.day=='nextWeek')
		{
			var nextWeek = new Date(currentDate);
			nextWeek.setHours(20, 0, 0);
			nextWeek.setDate(nextWeek.getDate() + 7);
		    $scope.day = new Date(nextWeek);
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
		var fileuploader = angular.element("#fileInput");
		
	    fileuploader.on('click',function(){
	    })
	    
	    fileuploader.trigger('click');
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
		var modalInstance = $uibModal.open({
        	templateUrl: "template/profilepic.html",
        	controller: "profilePicController",
		});
	};
	//------------------closed profile pic-------------------------------------

	// -----------------------------refresh function---------------------------
	$scope.refresh= function () {
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
		var result=TodoHomePageService.getAllNotes().then(function(data){	
			if(data.data.status==200)
			{
				
				var count=0;
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
				$scope.getRefreshToken();
			}
			else if(data.data.status==-1)
			{
				$state.go("login");
			}
			else
			{
				$state.go("ToDoHomePage");
			}
		});
	}
	//------------------GetAll note function ended----------------------------
	
	
	$scope.getRefreshToken=function()
	{
		var token = {
				accessToken : localStorage.getItem("accessToken"),
				refreshToken : localStorage.getItem("refreshToken")
		}
		
		var refreshToken=TodoHomePageService.getRefreshToken(token).then(function(data){
			if(data.data.status==200)
			{
				localStorage.setItem("accessToken",data.data.accessToken);
				localStorage.setItem("refreshToken",data.data.refreshToken);
			}
			else if(data.data.status==-2)
			{
				$state.go("login");
			}
			else if(data.data.status==-1)
			{
				$state.go("login");
			}
		});
	}
	
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