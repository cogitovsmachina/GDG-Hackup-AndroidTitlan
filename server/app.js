var fs = require("fs");
var express = require("express");
var app = express();

app.use(express.bodyParser({ keepExtensions: true, uploadDir: 'uploads/' }));
app.engine('jade', require('jade').__express);
app.set('view engine', 'jade')

var saveReport = function(report){
	// Saves the report
	fs.appendFile('log.txt', JSON.stringify(report), function(err){
		console.log(err);
	});
	return 0;
};

app.get('/', function(req, res){
  res.render('index', {})
})

// POST Datos
app.post('/newreport', function(req, res){
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
