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


}