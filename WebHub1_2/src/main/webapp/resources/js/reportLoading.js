var selection = window.opener.document.getElementById("report").value
	//$( "select#report option:checked").val();
var date = window.opener.document.getElementById("datePickerS").value
	//$("#datePickerS").datepicker().val();
var date2 = window.opener.document.getElementById("datePickerE").value
	//$("#datePickerE").datepicker().val();
var time = window.opener.document.getElementById("timePickerS").value
	//window.opener.document.$("#timePickerS").val();
var time2 = window.opener.document.getElementById("timePickerE").value
	//window.opener.document.$("#timePickerE").val();
var agg = window.opener.document.getElementById("reportAggregation").value
	
var agency = window.opener.document.getElementById("reportAgency").value
	
console.log(agency);
console.log(agg);
console.log(date);
console.log(selection);

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
	 agg = "System Level"
	}
if(agg == 2)
{
 agg = "Route Level"
}
if(agg == 3)
{
 agg = "Stop Level"
}

if(agg == 4)
{
 agg = "Trip Level"
}
var bodycontent;

if(selection == 1)
{
	$.ajax({
	       url:'GetReportData',
	       type:'post',
	       cache:false,
	       data:{"select":selection,"aggreg":agg, "agcy":agency,"StartDate":date,"EndDate":date2,"StartTime":time,"EndTime":time2},
	       async:false,
	       success:function(data){
	          //alert(data);
	          bodyContent = data;
	          
	       },
	       error:function(){
	         alert('error when getting report content');
	       }
	    }
	    
	);
	$('#aggregateReportContent').html(bodyContent);
}

if(selection == 2)
{
	$.ajax({
	       url:'GetReportDataPerformance',
	       type:'post',
	       cache:false,
	       data:{"select":selection,"aggreg":agg, "agcy":agency,"StartDate":date,"EndDate":date2,"StartTime":time,"EndTime":time2},
	       async:false,
	       success:function(data){
	          //alert(data);
	          bodyContent = data;
	          
	       },
	       error:function(){
	         alert('error when getting report content');
	       }
	    }
	    
	);
	$('#performanceReportContent').html(bodyContent);
}

if(selection == 3)
{
	$.ajax({
	       url:'GetReportDataDensity',
	       type:'post',
	       cache:false,
	       data:{"select":selection,"aggreg":agg, "agcy":agency,"StartDate":date,"EndDate":date2,"StartTime":time,"EndTime":time2},
	       async:false,
	       success:function(data){
	          //alert(data);
	          bodyContent = data;
	          
	       },
	       error:function(){
	         alert('error when getting report content');
	       }
	    }
	    
	);
	$('#densityReportContent').html(bodyContent);
}

if(selection == 4)
{
	$.ajax({
	       url:'GetReportDataNTDPerformance',
	       type:'post',
	       cache:false,
	       data:{"select":selection,"aggreg":agg, "agcy":agency,"StartDate":date,"EndDate":date2,"StartTime":time,"EndTime":time2},
	       async:false,
	       success:function(data){
	          alert(data);
	          bodyContent = data;
	          
	       },
	       error:function(){
	         alert('error when getting report content');
	       }
	    }
	    
	);
	$('#NTDperformanceReportContent').html(bodyContent);
}


$(document).ready(function() {
	
	
	if(selection == 1)
	{
		$('#example').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	} );
		$('#reportInfo').html("<strong>Agency:</strong>  "+ agency+"<br> <strong>Aggregation:</strong> "+ agg+" <br> <strong>StartDate:</strong> "+ date+ "      <strong>StartTime:</strong> "+ time +" <br> <strong>EndDate:</strong> "+ date2 +"      <strong>EndTime:</strong> "+ time2 +"<br><br>" );
		$('#aggregateReportContent').html(bodyContent);
	}
	if(selection == 2)
	{
		$('#example').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	} );
		$('#reportInfo').html("<strong>Agency:</strong>  "+ agency+"<br> <strong>Aggregation:</strong> "+ agg+" <br> <strong>StartDate:</strong> "+ date+ "      <strong>StartTime:</strong> "+ time +" <br> <strong>EndDate:</strong> "+ date2 +"      <strong>EndTime:</strong> "+ time2 +"<br><br>" );
		$('#performanceReportContent').html(bodyContent);
	}
	if(selection == 3)
	{
		$('#reportInfo').html("<strong>Agency:</strong>  "+ agency+"<br> <strong>StartDate:</strong> "+ date+ "      <strong>StartTime:</strong> "+ time +" <br> <strong>EndDate:</strong> "+ date2 +"      <strong>EndTime:</strong> "+ time2 +"<br><br>" );
		$('#densityReportContent').html(bodyContent);
	}
	if(selection == 4)
	{
		$('#example').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	} );
		$('#reportInfo').html("<strong>Agency:</strong>  "+ agency+"<br> <strong>Aggregation:</strong> "+ agg+" <br> <strong>StartDate:</strong> "+ date+ "      <strong>StartTime:</strong> "+ time +" <br> <strong>EndDate:</strong> "+ date2 +"      <strong>EndTime:</strong> "+ time2 +"<br><br>" );
		$('#NTDperformanceReportContent').html(bodyContent);
	}
	
	var x = document.getElementsByTagName("td");
	x.align = "center";
	
} );


