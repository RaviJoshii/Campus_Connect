const bodyParser=require('body-parser');
const multer  = require('multer');
const db=require('./config');

module.exports=function(app){

	const uploadnotes = multer({dest:"uploads/notes"});
	const uploadnotice= multer({dest:"uploads/notice"});
	const uploadsyllabus= multer({dest:"uploads/syllabus"});
	app.post('/home/teacher/unotes',uploadnotes.any(),function(req,res,next){

	// req.file is the `avatar` file
	  // req.body will hold the text fields, if there were any
	  console.log(req.file);
	  res.send("notes uplaod successfully");
});
	app.post('/home/teacher/unotice',uploadnotice.any(),function(req,res,next){

    console.log('notice uploaded');
	console.log(req.file);
	  res.send("notice uplaod successfully");

	});
	app.post('/home/teacher/usyllabus',uploadsyllabus.any(),function(req,res){

		console.log(req.file);
	  res.send("syllabus uplaod successfully");

	});

	app.get('/home/teacher/snotes',function(req,res){
      var links=req.query.link
      console.log(links);
	 file='uploads/notes/'+links;
	  res.download(file); 
	});
	app.get('/home/teacher/snotice',function(req,res){
      var links=req.query.link
      console.log(links);
	 file='uploads/notice/'+links;
	  res.download(file); 
	});
	app.get('/home/teacher/ssyllabus',function(req,res){
      var links=req.query.link
      console.log(links);
	 file='uploads/syllabus/'+links;
	  res.download(file); 
	});
        	app.post('/home/teacher/attendance',function(req,res){

    var batch =req.body.batch;
    var attendance=req.body.attendance;
     var datetime = new Date();
     var appData = {
			"error": 1,
			"data" :""
            }
     var userData = {
			"batch": batch,	
			"date": datetime,
			"attendance": attendance
           }

       console.log(userData);
		
		db.con.query('INSERT INTO attendance SET ?', userData, function(err, rows, fields) {
			if (!err) {
				appData.error = 0;
				appData["data"] = "attendance uplaod successfully";
				res.status(201).json(appData);
			} else {
				appData["data"] = err;
				res.status(400).json(appData);
			}
		});

	});


}
