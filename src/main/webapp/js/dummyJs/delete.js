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
	
	
	$scope.getAllNoteList=function (){
		console.log("inside the getAll Notes :: ");
		var result1=TodoHomePageService.getAllNoteList().then(function(data){
    	console.log("initial note data :: "+data.data);
    	$scope.records=data.data;
    });
	}