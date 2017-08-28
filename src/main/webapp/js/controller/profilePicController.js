myApp.controller( 'profilePicController',['$scope','$controller','$state', '$http','uploadService',function($scope, $controller,$state,fileReader,uploadService)
	{
	  console.log("profilePicController ...");
	  $controller('showDivision', {$scope: $scope}),
	  $controller('registerController', {$scope: $scope}),
	  
	  $scope.imageSrc="Images/image.jpg";
	  
	  $scope.$on("fileProgress", function(e, progress) {
	      $scope.progress = progress.loaded / progress.total;
	  });
	  $scope.uploadPic=function(imageSrc)
	  {
		  $scope.imageSrc=imageSrc;
		  var updateprofilePic = {
					id : $scope.userId,
					profileImage:$scope.imageSrc
				}
		  console.log("updateprofilePic object :: ",updateprofilePic);
		  
		  uploadService.updateprofile(updateprofilePic).then(function(data){
			  console.log("data profile pic :: ",data);
			  if(data.data.status==200)
				{
				   console.log("updateprofilePic updated sucessfully :: ",data.data.userRegistration);
				   $scope.imageSrc=data.data.userRegistration.profileImage;
				   $scope.profileImage=data.data.userRegistration.profileImage;
				   $state.go("ToDoHomePage");
				}
		  });
		  
	   } 
}]).service("uploadService", function($http,$q) {
	
    this.updateprofile = function (updateprofilePic) {
    	console.log("uploadService...",updateprofilePic);
        return  $http({
            method : "PUT",
            url : "http://localhost:8080/TodoApp/userUpdate",
            data : updateprofilePic
        });
    }
}).factory("fileReader", function($q, $log) {
	  var onLoad = function(reader, deferred, scope) {
		    return function() {
		      scope.$apply(function() {
		        deferred.resolve(reader.result);
		      });
		    };
		  };

		  var onError = function(reader, deferred, scope) {
		    return function() {
		      scope.$apply(function() {
		        deferred.reject(reader.result);
		      });
		    };
		  };

		  var onProgress = function(reader, scope) {
		    return function(event) {
		      scope.$broadcast("fileProgress", {
		        total: event.total,
		        loaded: event.loaded
		      });
		    };
		  };

		  var getReader = function(deferred, scope) {
		    var reader = new FileReader();
		    reader.onload = onLoad(reader, deferred, scope);
		    reader.onerror = onError(reader, deferred, scope);
		    reader.onprogress = onProgress(reader, scope);
		    return reader;
		  };

		  var readAsDataURL = function(file, scope) {
		    var deferred = $q.defer();

		    var reader = getReader(deferred, scope);
		    reader.readAsDataURL(file);

		    return deferred.promise;
		  };

		  return {
		    readAsDataUrl: readAsDataURL
		};
}).directive("ngFileSelect", function(fileReader, $timeout) {
			return {
				scope: {
				ngModel: '='
			},	
			link: function($scope, el) {
					function getFile(file) {
						fileReader.readAsDataUrl(file, $scope).then(function(result) {
							$timeout(function() {
								$scope.ngModel = result;
							});
						});
					}
					el.bind("change", function(e) {
						var file = (e.srcElement || e.target).files[0];
						getFile(file);
					});
			}
      };
});


