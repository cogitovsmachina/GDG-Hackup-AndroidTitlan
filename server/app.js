var express = require("express");
var app = express();

var saveReport = function(report){
	// Saves the report
	return 0;
};

// POST Datos
app.get('/newreport', function(req, res){
	try {
		var report = {
			image: req.files.image,
			location: req.body.location,
			time: req.body.time,
			type: req.body.type
		};
		var reportid = saveReport(report);
		res.status(200);
		res.json({id: reportid});

	} catch (err) {
		console.log(err);
		res.status(500);
		res.json(req.params);
	}
});

app.listen(8080);
console.log('Listening on port: 8080');
