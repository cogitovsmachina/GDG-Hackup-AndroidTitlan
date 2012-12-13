var fs = require("fs");
var express = require("express");
var app = express();
var backend = require('./backend');


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
        var image = req.files.image;
        var report = {
                image: {
                  path: image.path
                },
                location: [
                  req.body.latitude * 1.0,
                  req.body.longitude * 1.0
                ],
                time: req.body.time,
                type: req.body.type
        };
        backend.saveReport(report, function(err, report){
          if(err){
	    console.log(err);
	    res.status(500);
	    res.json(req.params);
          } else {
            console.log(report);
            res.status(200);
            res.json({id: report.id.toString()});
          }
        })
});

app.listen(8080);
console.log('Listening on port: 8080');
