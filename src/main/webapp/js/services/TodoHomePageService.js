myApp.service('TodoHomePageService', function($http) {
	console.log("TodoHomePageService......");
	console.log("local storage accesstoken :: ",localStorage.getItem("accessToken"));
	
    this.createNote = function (createNoteObject) {
    	console.log("create note object :: ",createNoteObject);
        return  $http({
            method : "POST",
            url : "CreateToDoNote",
            data : createNoteObject,
            headers: {'accessToken': localStorage.getItem("accessToken")}
        });
    }
    
    this.getAllNoteList = function() {
    	return $http({
    		method : "GET",
            url : "ToDoNoteList",
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.updateNote = function(updateObj) {
    	console.log("service update object  :: ",updateObj);
    	return $http({
    		method : "PUT",
            url : "ToDoNoteUpdate",
            data : updateObj,
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.deleteNote = function(noteid) {
    	return $http({
    		method : "POST",
            url : "TodoNoteDelete",
            data : noteid,
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.getRefreshToken = function(token) {
    	console.log("service refreshToken object object  :: ",token);
    	return $http({
    		method : "POST",
            url : "refreshToken",
            data : token,
    });
    }
    
    this.getAllNotes = function() {
    	console.log("service getAllNotes object object  :: ",token);
    	return $http({
    		method : "GET",
            url : "ToDoNoteList",
            headers: {'accessToken': localStorage.getItem("accessToken")}
    });
    }
    
    
    this.reminder = function(reminder) {
    	console.log("service reminder object  :: ",reminder);
    	return $http({
    		method : "PUT",
            url : "ToDoNoteReminder",
            data : reminder,
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    
    
});