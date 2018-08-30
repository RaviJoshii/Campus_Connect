const mysql= require('mysql');
const con = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'cse@Ravi',
  database : 'student_management'
});
con.connect(function(err) {
  if (err) {console.log('Internal server error');
  throw err;
}
  console.log("Connected!");
});

module.exports.con=con;