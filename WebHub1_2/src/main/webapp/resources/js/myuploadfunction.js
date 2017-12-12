window.onbeforeunload = function(){
   // Do something
	deleteFiles();
}

var file = -1;

function editDocument(fileName){
	document.getElementById("edit2").innerHTML = "";
	document.getElementById("filename").innerHTML = "Loading...";
	
	$( '#editOptions' ).addClass( "hidden" );
	$( '#editarea' ).removeClass( "hidden" );
	document.getElementById("filename").innerHTML = "Loading...";
	
	$.ajax({
        url : "GetFile",
        type: "GET",
        dataType: "html",
        data:{"mname":fileName},
        success : function (data) {
            $("#edit2").html(data);
            document.getElementById("filename").innerHTML = fileName;
        }
    });
	$('#editarea').removeClass("hidden");
	
	var selectedContent = $('li[data-content="edit"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}


function updateChecklist(){
	var xhr1 = new XMLHttpRequest();
	xhr1.onload = function(){
		if(xhr1.status ===200){
			document.getElementById("filelist").innerHTML = xhr1.responseText;
			var list = document.getElementById("filelist").getElementsByClassName("file")
			for(var i = 0;i<list.length;i++){
				console.log(list[i].innerText.slice(0, -4) || list[i].textContent.slice(0, -4))
				$('#'+list[i].innerText.slice(0, -4)+'C').addClass( "hidden" );
			}
		}
	}
	xhr1.open('GET','UpdateChecklist',true);
	xhr1.send(null);
	
	
	var xhr2 = new XMLHttpRequest();
	xhr2.onload = function(){
		if(xhr2.status ===200){
			document.getElementById("editOptions").innerHTML = xhr2.responseText;
		}
	}
	xhr2.open('GET','UpdateEdit',true);
	xhr2.send(null);
}

$('#fileUploader1').on('change', uploadFile);
$('#fileUploader2').on('change', uploadFile);
$('#fileUploader3').on('change', uploadFile);
$('#fileUploader4').on('change', uploadFile);
$('#fileUploader5').on('change', uploadFile);



function CheckFile(num){
	if(num==1){
		file = 1
		var tags = document.getElementsByName('column1');
		var check = document.getElementById("fileUploader1").value.split(/(\\|\/)/g).pop();
		var name = "board_alight.csv"
		document.getElementById("fileUploader1").value = "";
	}
	if(num==2){
		file = 2
		var tags = document.getElementsByName('column2');
		var name = "trip_capacity.csv";
		var check = document.getElementById("fileUploader2").value.split(/(\\|\/)/g).pop();
		document.getElementById("fileUploader2").value = "";
	}
	if(num==3){
		file = 3
		var tags = document.getElementsByName('column3');
		var name = "rider_trip.csv";
		var check = document.getElementById("fileUploader3").value.split(/(\\|\/)/g).pop();
		document.getElementById("fileUploader3").value = "";
	}
	if(num==4){
		file = 4
		var tags = document.getElementsByName('column4');
		var name = "ridership.csv";
		var check = document.getElementById("fileUploader4").value.split(/(\\|\/)/g).pop();
		document.getElementById("fileUploader4").value = "";
	}
	if(num==5){
		file = 5
		var tags = document.getElementsByName('column5');
		var name = "rider_feed_info.csv";
		var check = document.getElementById("fileUploader5").value.split(/(\\|\/)/g).pop();
		document.getElementById("fileUploader5").value = "";
	}
	//console.log(name)
	//console.log(check)
	//console.log("Number of boxes is "+ tags.length);
	var columns = ""
	for(var i = 0;i<tags.length;i++){
		if(tags[i].checked){
			//console.log(tags[i].value)
			columns = columns + tags[i].value + ","
		}
	}
	console.log(columns)
	
	$.ajax({
		url : "CheckFile",
        type: "POST",
        cache: false,
        data:{"info":columns,"name":name,"check":check},
        dataType: 'html',
        success : function (data) {
            alert(data)
            var xhr2 = new XMLHttpRequest();
        	xhr2.onload = function(){
        		if(xhr2.status ===200){
        			document.getElementById("editOptions").innerHTML = xhr2.responseText;
        		}
        	}
        	xhr2.open('GET','UpdateEdit',true);
        	xhr2.send(null);
        	
        	var xhr1 = new XMLHttpRequest();
        	xhr1.onload = function(){
        		if(xhr1.status ===200){
        			document.getElementById("filelist").innerHTML = xhr1.responseText;
        			var list = document.getElementById("filelist").getElementsByClassName("file")
        			for(var i = 0;i<list.length;i++){
        				console.log(list[i].innerText.slice(0, -4) || list[i].textContent.slice(0, -4))
        				$('#'+list[i].innerText.slice(0, -4)+'C').addClass( "hidden" );
        			}
        		}
        	}
        	xhr1.open('GET','UpdateChecklist',true);
        	xhr1.send(null);
        	$( '#uploadBoard' ).addClass( "hidden" );
        	$( '#uploadTrip' ).addClass( "hidden" );
        	$( '#uploadRiderTrip' ).addClass( "hidden" );
        	$( '#uploadRiderShip' ).addClass( "hidden" );
        	$( '#uploadRideInfo' ).addClass( "hidden" );
        	
        }
	});
	
	
}



function uploadFile(event)
	{
	    event.stopPropagation(); 
	    event.preventDefault(); 
	    var files = event.target.files; 
	    var data = new FormData();
	    $.each(files, function(key, value)
	    {
	        data.append(key, value);
	    });
	    postFilesData(data); 
	 }
	
function postFilesData(data)
	{
	 $.ajax({
        url: 'LoadCSV',
        type: 'POST',
        data: data,
        cache: false,
        dataType: 'html',
        processData: false, 
        contentType: false, 
        success: function(data, textStatus, jqXHR)
        {
        	
        	$( '#uploadBoard' ).removeClass( "hidden" );
        	$( '#uploadTrip' ).removeClass( "hidden" );
        	$( '#uploadRiderTrip' ).removeClass( "hidden" );
        	$( '#uploadRiderShip' ).removeClass( "hidden" );
        	$( '#uploadRideInfo' ).removeClass( "hidden" );
        	
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            console.log('ERRORS: ' + textStatus);
        }
	    });
	}

$(function () {
	
    $('#fileupload').fileupload({
    	
        dataType: 'json',
        
        done: function (e, data) {
        	//$("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
            	
            	
                $("#uploaded-files").html(
                		$('<tr/>')
                		.append($('<td/>').text(file.fileName))
                		.append($('<td/>').text(file.fileSize))
                		.append($('<td/>').text(file.fileType))
                		
                		)//end $("#uploaded-files").append()
                		updateChecklist();
            }); 
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		
		dropZone: $('#dropzone')
    }).bind('fileuploadsubmit', function (e, data) {
        // The example input, doesn't have to be part of the upload form        
    });
   
});