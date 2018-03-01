/**
 * 
 */

function deleteFiles(){
	var xhr1 = new XMLHttpRequest();
	xhr1.onload = function(){
		if(xhr1.status ===200){
			document.getElementById("filelist").innerHTML = xhr1.responseText;
		}
	}
	xhr1.open('POST','ClearFiles',true);
	xhr1.send(null);
}

function loadIntoDB(){
	var person = prompt("Please enter the password", "Password");
	if (person != "ride") {
        alert("Sorry that is the wrong password");
    }
	else
		{
			//alert("that is correct");
			var xhr1 = new XMLHttpRequest();
			xhr1.onload = function(){
				if(xhr1.status ===200){
					document.getElementById("uploadClick").innerHTML = xhr1.responseText;
				}
			}
			xhr1.open('POST','loadFeedIntoDB',true);
			xhr1.send(null);
		}
	
}

function checkExit(){
	let ans = confirm("Are you sure you would you like to return to Home? \nAll progress will be discarded.");
	if(ans){
		deleteFiles();
		window.open('index.html','_self');
	}
	else{
		return;
	}
}

function showSection(sec){
	if(sec==1){
		$( '#create_table' ).addClass( "hidden" );
		$( '#createRideFeedInfo' ).removeClass( "hidden" );
	}
	if(sec==2){
		$( '#create_table' ).addClass( "hidden" );
		$( '#createRidership' ).removeClass( "hidden" );
	}
	if(sec==3){
		$( '#create_table' ).addClass( "hidden" );
		$( '#createRider_Trip' ).removeClass( "hidden" );
	}
	if(sec==4){
		$( '#create_table' ).addClass( "hidden" );
		$( '#trip_capacity' ).removeClass( "hidden" );
	}
	if(sec==5){
		$( '#create_table' ).addClass( "hidden" );
		$( '#board_alight' ).removeClass( "hidden" );
	}
	
	var selectedContent = $('li[data-content="create"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}

function hideSection(sec){
	if(sec==1){
		$( '#createRideFeedInfo' ).addClass( "hidden" );
		$( '#create_table' ).removeClass( "hidden" );
	}
	if(sec==2){
		$( '#createRidership' ).addClass( "hidden" );
		$( '#create_table' ).removeClass( "hidden" );
	}
	if(sec==3){
		$( '#createRider_Trip' ).addClass( "hidden" );
		$( '#create_table' ).removeClass( "hidden" );
	}
	if(sec==4){
		$( '#trip_capacity' ).addClass( "hidden" );
		$( '#create_table' ).removeClass( "hidden" );
	}
	if(sec==5){
		$( '#board_alight' ).addClass( "hidden" );
		$( '#create_table' ).removeClass( "hidden" );
	}
	
	
	var selectedContent = $('li[data-content="create"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}

$("#saveButton").click(function(){
	var text = $('#areaText').val();
	var fname = $('#filename').text();
	
    $.ajax({
       url:'SaveFile',
       data:{"name":fname,"data":text},
       type:'post',
       cache:false,
       success:function(data){
          alert(data +fname);
          text = null
          fname = null
       },
       error:function(){
         alert('error');
       }
    }
    
);
    
});

$('#exportButton').click(function() {
	$.ajax({
        url : "ExportZip",
        type: "GET",
        dataType: "html",
        success : function (data) {
            $("#uploadClick").html(data);
        }
    });
});


function hideEdit(){
	$( '#editarea' ).addClass( "hidden" );
	$( '#editOptions' ).removeClass( "hidden" );
	$('#edit2').empty();
	var selectedContent = $('li[data-content="edit"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}
function showEdit(string){
	$( '#editOptions' ).addClass( "hidden" );
	$( '#editarea' ).removeClass( "hidden" );
	var selectedContent = $('li[data-content="edit"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}