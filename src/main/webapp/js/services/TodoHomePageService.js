myApp.service('TodoHomePageService', function($http) {
	console.log("TodoHomePageService......");
	console.log("local storage accesstoken :: ",localStorage.getItem("accessToken"));
	
    this.createNote = function (createNoteObject) {
        return  $http({
            method : "POST",
            url : "http://localhost:8080/TodoApp/CreateToDoNote",
            data : createNoteObject,
            headers: {'accessToken': localStorage.getItem("accessToken")}
        });
    }
    
    this.getAllNoteList = function() {
    	return $http({
    		method : "GET",
            url : "http://localhost:8080/TodoApp/ToDoNoteList",
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.updateNote = function(updateObj) {
    	console.log("service update object  :: ",updateObj);
    	return $http({
    		method : "PUT",
            url : "http://localhost:8080/TodoApp/ToDoNoteUpdate",
            data : updateObj,
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.deleteNote = function(noteid) {
    	return $http({
    		method : "POST",
            url : "http://localhost:8080/TodoApp/TodoNoteDelete",
            data : noteid,
            headers: {'accessToken': localStorage.getItem("accessToken")
    	}
    });
    }
    
    this.getRefreshToken = function(token) {
    	console.log("service refreshToken object object  :: ",token);
    	return $http({
    		method : "POST",
            url : "http://localhost:8080/TodoApp/refreshToken",
            data : token,
    });
    }
    
    this.getAllNotes = function() {
    	console.log("service getAllNotes object object  :: ",token);
    	return $http({
    		method : "GET",
            url : "http://localhost:8080/TodoApp/ToDoNoteList",
            headers: {'accessToken': localStorage.getItem("accessToken")}
    });
    }
    
});