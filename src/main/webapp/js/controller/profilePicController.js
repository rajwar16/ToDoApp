myApp.controller( 'profilePicController',['$scope', '$controller', function($scope, $controller,fileReader,TodoHomePageService,profilePicService)
	{
		console.log("profilePicController ...");
	  $controller('showDivision', {$scope: $scope}),
	  $controller('registerController', {$scope: $scope}),
	  
	  $scope.imageSrc = "";
	 
	  $scope.$on("fileProgress", function(e, progress) {
	      $scope.progress = progress.loaded / progress.total;
	  });
	  
	  $scope.uploadPic=function(imageSrc)
	  {
		  var updateprofilePic = {
					userName : $scope.userName,
					email : $scope.email,
					password:$scope.password,
					mobileNo:$scope.mobileNo,
				}
		  console.log("updateprofilePic object :: ",updateprofilePic);
		  profilePicService.updateprofile(updateprofilePic).then(function(data){
			  console.log("data profile pic :: ",data);
			 /* if(data.data.status==200)
				{
				   console.log("updateprofilePic updated sucessfully :: ",updateprofilePic);
				}*/
		  });
		  
	   } 
}]);

myApp.directive("ngFileSelect", function(fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result) {
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

myApp.factory("fileReader", function($q, $log) {
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
});