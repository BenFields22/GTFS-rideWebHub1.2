var answer;
var fileEdit;
var def = '<p>Please Select the option that best describes your data:<br>\
	<input onclick= updateAns(this) type="radio" name="dataType" value="1"> Complete unvalidated GTFS-ride feed<br>\
	<input onclick= updateAns(this) type="radio" name="dataType" value="2"> Complete validated GTFS-ride feed<br>\
	<input onclick= updateAns(this) type="radio" name="dataType" value="3"> Partial GTFS-ride feed<br>\
	<input onclick= updateAns(this) type="radio" name="dataType" value="4"> GTFS feed<br>\
	<input  type="submit" value= "Submit" onclick="return checkAnswer()"> \
</p>';

$(document).ready(function(){
	if (window.File && window.FileReader && window.FileList && window.Blob) {
		  // Great success! All the File APIs are supported.
		//alert('Supported');
		} else {
		  alert('The File APIs are not fully supported in this browser.');
		}
	
});

function GenerateReport()
{
	var newForm = window.open('DataUploadOption2.html');
	
	newForm.onload = function() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function(){
		if(xhr.status ===200){
			newForm.document.getElementById("reportContent").innerHTML = xhr.responseText;
		}
	}
	
	xhr.open('POST', 'GenerateReport',true);
	xhr.send(null);
	}
}


function checkAnswer(passForm)
{
	//str = "you chose "+ answer;
	//alert(str);
	if(answer=='1')
		{
		//unvalidated gtfs-ride file
			$('#dataPanel').html('<p>Please validate your feed before you continue with the data upload process.' +
				'The validation tool can be found at <a target=# href="https://github.com/ODOT-PTS/transitfeed-ride">https://github.com/ODOT-PTS/transitfeed-ride</a></p>');
			$('#dataPanel').append('<input type=button value="Back" onclick="refresh()">');
		}
	else if(answer=='2')
		{
			//complete validated gtfs-ride feed
			window.open('DataUploadOption1.html','_self');
		
		}
	else if(answer=='3')
		{
		//partial gtfs ride feed
		window.open('DataUploadOption1.html','_self');
		}
	else if(answer=='4')
		{
		//only gtfs data
		window.open('DataUploadOption1.html','_self');
		}
	
	var selectedContent = $('li[data-content="Data Entry"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
	return false;
}

function clearEdit(){
	$('#editData').html("");
	var selectedContent = $('li[data-content="Data Entry"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
}

function refresh()
{
	$('#dataPanel').html(def);
	var selectedContent = $('li[data-content="Data Entry"]');
	var slectedContentHeight = selectedContent.innerHeight();
	$('.cd-tabs-content').animate({
		'height': slectedContentHeight
	}, 200);
	return false;
}

function updateAns(formButtom)
{
	answer = formButtom.value;
}