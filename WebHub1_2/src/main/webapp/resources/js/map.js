/**
 * 
 */

var mymap = L.map('mainmapid').setView([43.8041, -120.5542], 7);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	// Initialise the FeatureGroup to store editable layers
	var drawnItems = new L.FeatureGroup();
	mymap.addLayer(drawnItems);

	// Initialise the draw control and pass it the FeatureGroup of editable layers
	var drawControl = new L.Control.Draw({
	  edit: {
	    featureGroup: drawnItems
	  }
	});

	mymap.addControl(drawControl);

	mymap.on(L.Draw.Event.CREATED, function (e) {
	  var type = e.layerType
	  var layer = e.layer;

	  // Do whatever else you need to. (save to db, add to map etc)

	  drawnItems.addLayer(layer);
	});
	
	