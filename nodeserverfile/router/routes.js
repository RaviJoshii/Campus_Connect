const bodyParser=require('body-parser');
const multer  = require('multer');
const db=require('./config');

module.exports=function(app){
	var count1=0;
	var count2=0;
	var storage1 = multer.diskStorage(
    {
        destination: "uploads/notice",
        filename: function ( req, file, cb ) {
            //req.body is empty...
            //How could I get the new_file_name property sent from client here?
            count1=count1+1;
            var new_file_name=count1.toString();
            cb( null,new_file_name);
        }
    }
);
	var storage2 = multer.diskStorage(
    {
        destination: "uploads/notesSyllabus",
        filename: function ( req, file, cb ) {
            count2=count2+1;
            var new_file_name=count2.toString()	;
            cb( null,new_file_name+mime.extension(file.mimetype));
        }
    }
);

	const uploadnotes = multer({ storage: storage2 });
	const uploadnotice= multer({ storage: storage1 });
	app.post('/home/teacher/unotes',uploadnotes.any(),function(req,res,next){

	// req.file is the `avatar` file
	  // req.body will hold the text fields, if there were any
	  console.log('notes uploaded');
	  console.log(req.file);
	  res.send("notes uplaod successfully");
});
	app.post('/home/teacher/unotice',uploadnotice.any(),function(req,res,next){

    console.log('notice uploaded');
	console.log(req.file);
	  res.send("notice uplaod successfully");

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
     app.post('/home/teacher/filesystem1',function(req,res){
		var appData = {
			"error": 1,
			"data" :""
		};
		var userData = {
			"key": count1,	
			"description": req.body.description


		}

        console.log(req.body.description);
		
		db.con.query('INSERT INTO filesystem SET?', userData, function(err, rows, fields) {
			if (!err) {
				appData.error = 0;
				appData["data"] = "User registered successfully!";
				res.status(201).json(appData);
			} else {
				appData["data"] = err;
				res.status(400).json(appData);
			}
		});

	});
     app.post('/home/teacher/filesystem2',function(req,res){
		var appData = {
			"error": 1,
			"data" :""
		};
		var userData = {
			"key": count2,	
			"description": req.body.description

		}


		
		db.con.query('INSERT INTO filesystem SET ?', userData, function(err, rows, fields) {
			if (!err) {
				appData.error = 0;
				appData["data"] = "uploaded";
				res.status(201).json(appData);
			} else {
				appData["data"] = err;
				res.status(400).json(appData);
			}
		});

	});
     app.get('/home/teacher/studfilesystem1',function(req,res){
     	 console.log('filesystem1');
     	var appData = {
			"error": 1,
			"data" :""
		};
      db.con.query('SELECT * FROM filesystem', function(err, rows, fields) {
			if (!err) {
				console.log("No problem");
				appData.error = 0;
				res.send(rows);
				 console.log(rows);
			} else {
				appData["data"] = err;
				res.status(400).json(appData);
			}
		});

	});
     app.get('/home/teacher/studfilesystem2',function(req,res){

     	var appData = {
			"error": 1,
			"data" :""
		};
      db.con.query('SELECT * FROM filesystem2', function(err, rows, fields) {
			if (!err) {
				appData.error = 0;
				res.send(rows);
			} else {
				appData["data"] = err;
				res.status(400).json(appData);
			}
		});

	});


}
