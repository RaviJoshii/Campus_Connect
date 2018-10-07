const db=require('./config');
var bodyParser=require('body-parser');
module.exports=function(app){
	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded( {extended: true}));

	app.get('/getTuser',function(req,res){
		db.con.query("SELECT * FROM Teacher", function (err, result, fields) {
			if (err) throw err;
			res.json(result);
		});
	});




	app.get('/getSuser',function(req,res){
		db.con.query("SELECT * FROM student", function (err, result, fields) {
			if (err) throw err;
			res.json(result);
		});
	});

    app.post('/home/Studentlogin',function(req,res){
			var appData = {};
			var rollno = req.body.username;
			var password = req.body.password;
			
					db.con.query('SELECT * FROM student WHERE rollno = ?', [rollno], 
						function(err, rows, fields) {
							if (err) {
								//console.log(err);
								appData.error = 1;
								appData["data"] = "Error Occured!";
								res.status(400).json(appData);
								console.log("Error Occured!");
							} 
							else {
								if (rows.length > 0) {
									if (rows[0].password == password) {
										appData.error = 0;
										appData["data"] = "permission granted";
										res.status(200).json(appData);
										console.log("permission granted");
										
									} else {
										appData.error = 1;
										appData["data"] = "rollno and Password does not match";
										res.status(200).json(appData);
										console.log("rollno and Password does not match");
										
									}
								} else {
									appData.error = 1;
										appData["data"] = "user does not exists!";
										res.status(200).json(appData);
										console.log("user does not exists!");
									
								}
							}
						});
					

			
		});
    app.post('/home/Teacherlogin',function(req,res){
			var appData = {};
			var username = req.body.username;
			var password = req.body.password;
			
					db.con.query('SELECT * FROM Teacher WHERE username = ?', [username], 
						function(err, rows, fields) {
							if (err) {
								//console.log(err);
								appData.error = 1;
								appData["data"] = "Error Occured!";
								res.status(400).json(appData);
								console.log("Error Occured!");
							} 
							else {
								if (rows.length > 0) {
									if (rows[0].password == password) {
										appData.error = 2;
										appData["data"] = "permission granted";
										res.status(200).json(appData);
										console.log("permission granted");
										
									} else {
										appData.error = 1;
										appData["data"] = "username and Password does not match";
										res.status(200).json(appData);
										console.log("rollno and Password does not match");
										
									}
								} else {
									appData.error = 1;
										appData["data"] = "user does not exists!";
										res.status(200).json(appData);
										console.log("user does not exists!");
									
								}
							}
						});
					

			
		});

        app.post('/home/Teacher/add',function(req,res){
		var today = new Date();
		var appData = {
			"error": 1,
			"data" :""
		};
		var userData = {
			"Name": req.body.Name,	
			"rollno": req.body.rollno,
			"password": req.body.password,
			"department":req.body.department,
			"year":req.body.year,
            "sex":req.body.sex,
			"created": today

		}


		
		db.con.query('INSERT INTO student SET ?', userData, function(err, rows, fields) {
			if (!err) {
				appData.error = 0;
				appData["data"] = "User registered successfully!";
				res.json(appData);
			} 
			else {
				appData.error = 1;
				appData["data"] = "unsuccessfull";
				res.json(appData);
			}
		});

	});





        app.post('/home/Student/changepassword',function(req,res){
			var appData = {};
			var rollno = req.body.rollno;
			var oldpassword = req.body.oldpassword;
			var newpassword = req.body.newpassword;
            
                    db.con.query('SELECT * FROM student where rollno=?' ,[rollno],
                    	function(err,rows,fields){
                            if(err){
								appData.error = 1;
								appData["data"] = "Error Occured!";
								res.status(400).json(appData);
								console.log("Error Occured!");
							} 
							else if(rows.length > 0){
								
									if (rows[0].password == oldpassword) {
										db.con.query('UPDATE student SET password = ? WHERE rollno = ? AND password = ?',[newpassword,rollno,oldpassword]);

										appData.error = 0;
							            appData["data"] = "password changed successfully";
							            res.status(200).json(appData);
							            console.log("paswword change successfully");
										
									} else if(rows[0].password != oldpassword) {
										appData.error = 1;
										appData["data"] = "rollno and Password does not match";
										res.status(200).json(appData);
										console.log("rollno and Password does not match");
										
									 }
									}
									
								else {
									appData.error = 1;
										appData["data"] = "user does not exists!";
										res.status(200).json(appData);
										console.log("user does not exists!");
									}
									
								


                    });

                });





}