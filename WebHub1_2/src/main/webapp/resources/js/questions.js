document.getElementById("datePickerS").defaultValue = "2017-01-02";
document.getElementById("datePickerE").defaultValue = "2017-01-03";
document.getElementById("timePickerS").defaultValue = "12:00";
document.getElementById("timePickerE").defaultValue = "14:00";

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
function GenerateReportHTML()
{
	window.open("../../AggregateRidership.html");
}

function GenerateReport()
{
	
	var selection = $( "select#report option:checked" ).val();
	//var $j = jQuery.noConflict();
	//$j("#datepicker").datepicker();
	var date = $("#datePickerS").datepicker().val();
	var date2 = $("#datePickerE").datepicker().val();
	var time = $("#timePickerS").val();
	var time2 = $("#timePickerE").val();
	var agg = $("select#reportAggregation option:checked" ).val();
	var agency = $( "select#reportAgency option:checked" ).val();
	
	if(agency == 1)
	{
		agency = "RVTD"
	}
	if(agency == 2)
	{
		agency = "Union County"
	}
	if(agency == 3)
	{
		agency = "LTD"
	}
	
	if(agg == 1)
		{
		 agg = "system"
		window.open("../../AggregateRidershipSystem.html");
		}
	if(agg == 2)
	{
	 agg = "route";
	 window.open("../../AggregateRidershipRoute.html");
	}
	if(agg == 3)
	{
	 agg = "stop";
	 window.open("../../AggregateRidershipStop.html");
	}
	
	if(agg == 4)
	{
	 agg = "trip";
	 window.open("../../AggregateRidershipTrip.html");
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