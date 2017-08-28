myApp.directive('contenteditable1', [function() {
    return {
        require: '?ngModel',
        scope: {
        },
        link: function(scope, element, attrs, ctrl) {
            // view -> model (when div gets blur update the view value of the model)
            element.bind('blur', function() {
                scope.$apply(function() {
                    ctrl.$setViewValue(element.html());
                });
            });

            // model -> view
            ctrl.$render = function() {
                element.html(ctrl.$viewValue);
            };

            // load init value from DOM
            ctrl.$render();

            // remove the attached events to element when destroying the scope
            scope.$on('$destroy', function() {
                element.unbind('blur');
                element.unbind('paste');
                element.unbind('focus');
            });
        }
    };
}]);


myApp.factory("fileReader1", function($q, $log) {
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
		      scope.$broadcast("fileProgress1", {
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

/*myApp.directive("ngFileSelect1", function(fileReader, $timeout) {
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
*/


/*
app.directive("fileinput", [function () {

	   return {
	       scope: {
	           fileinput: "=",
	           filepreview: "="
	       },
	       link: function (scope, element, attributes) {
	           element.bind("change", function (changeEvent) {
	               scope.fileinput = changeEvent.target.files[0];
	               var reader = new FileReader();
	               reader.onload = function (loadEvent) {
	                   scope.$apply(function () {
	                       scope.filepreview = loadEvent.target.result;
	                   });
	               }
	               reader.readAsDataURL(scope.fileinput);
	           });
	       }
	   }
	}]);
*/

myApp.directive('image', function(fileReader, $timeout,$q) {
    'use strict'

    var URL = window.URL || window.webkitURL;

    var getResizeArea = function () {
        var resizeAreaId = 'fileupload-resize-area';

        var resizeArea = document.getElementById(resizeAreaId);

        if (!resizeArea) {
            resizeArea = document.createElement('canvas');
            resizeArea.id = resizeAreaId;
            resizeArea.style.visibility = 'hidden';
            document.body.appendChild(resizeArea);
        }

        return resizeArea;
    }

    var resizeImage = function (origImage, options) {
        var maxHeight = options.resizeMaxHeight || 300;
        var maxWidth = options.resizeMaxWidth || 250;
        var quality = options.resizeQuality || 0.7;
        var type = options.resizeType || 'image/jpg';

        var canvas = getResizeArea();

        var height = origImage.height;
        var width = origImage.width;

        // calculate the width and height, constraining the proportions
        if (width > height) {
            if (width > maxWidth) {
                height = Math.round(height *= maxWidth / width);
                width = maxWidth;
            }
        } else {
            if (height > maxHeight) {
                width = Math.round(width *= maxHeight / height);
                height = maxHeight;
            }
        }

        canvas.width = width;
        canvas.height = height;

        //draw image on canvas
        var ctx = canvas.getContext("2d");
        ctx.drawImage(origImage, 0, 0, width, height);

        // get the data from canvas as 70% jpg (or specified type).
        return canvas.toDataURL(type, quality);
    };

    var createImage = function(url, callback) {
        var image = new Image();
        image.onload = function() {
            callback(image);
        };
        image.src = url;
    };

    var fileToDataURL = function (file) {
        var deferred = $q.defer();
        var reader = new FileReader();
        reader.onload = function (e) {
            deferred.resolve(e.target.result);
        };
        reader.readAsDataURL(file);
        return deferred.promise;
    };


    return {
        restrict: 'A',
        scope: {
            image: '=',
            resizeMaxHeight: '@?',
            resizeMaxWidth: '@?',
            resizeQuality: '@?',
            resizeType: '@?',
        },
        link: function postLink(scope, element, attrs, ctrl) {

            var doResizing = function(imageResult, callback) {
                createImage(imageResult.url, function(image) {
                    var dataURL = resizeImage(image, scope);
                    imageResult.resized = {
                        dataURL: dataURL,
                        type: dataURL.match(/:(.+\/.+);/)[1],
                    };
                    callback(imageResult);
                });
            };

            var applyScope = function(imageResult) {
                scope.$apply(function() {
                    //console.log(imageResult);
                    if(attrs.multiple)
                        scope.image.push(imageResult);
                    else
                        scope.image = imageResult; 
                });
            };


            element.bind('change', function (evt) {
                //when multiple always return an array of images
                if(attrs.multiple)
                    scope.image = [];

                var files = evt.target.files;
                for(var i = 0; i < files.length; i++) {
                    //create a result object for each file in files
                    var imageResult = {
                        file: files[i],
                        url: URL.createObjectURL(files[i])
                    };

                    fileToDataURL(files[i]).then(function (dataURL) {
                        imageResult.dataURL = dataURL;
                    });

                    if(scope.resizeMaxHeight || scope.resizeMaxWidth) { //resize image
                        doResizing(imageResult, function(imageResult) {
                            applyScope(imageResult);
                        });
                    }
                    else { //no resizing
                        applyScope(imageResult);
                    }
                }
            });
        }
    };
});
