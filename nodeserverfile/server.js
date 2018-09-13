const express= require('express');
const app=express();
const port=process.env.PORT||7000;
const routers= require('./router/routes');
const logins=require('./router/login');

logins(app);
routers(app);
app.use('/direct',function(req,res,next){
console.log('starting');
next();
},function(req,res,next){
console.log('starting2');
next();
});
app.listen(port);
console.log("app is lisneening on port "+port);