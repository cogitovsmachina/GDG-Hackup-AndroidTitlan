
var mongoose = require('mongoose');
var mongo = require('mongo');

var dbUrl = "mongodb://gdg_reporte:gdg_reporte0@ds045507.mongolab.com:45507/gdg_reporte"

var db = mongoose.connect(dbUrl, function(err) {
    if (err) {
        console.log("error in mongo connection");
        throw err;
    }
    console.log("connected to mongo");
});

var Schema = mongoose.Schema;

var Reporte = new Schema({
  time: Number,
  type: String,
  image: {
    path: String,
    size: Number
  },
  location: [Number]
});
Reporte.index({ location: '2d' })

var ReporteModel = db.model('Reporte', Reporte);

var saveReport = function(report, cb){
  var reporte = new ReporteModel(report)
  reporte.save(cb);
};

var getReportById = function(id, cb){
  ReporteModel.findById(id, cb);
};

exports.Reporte = ReporteModel;
exports.db = db;
exports.saveReport = saveReport;
exports.getReportById = getReportById;




