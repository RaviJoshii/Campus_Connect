const express= require('express');
const app=express();
const port=process.env.PORT||7000;
const routers= require('./router/routes');
const logins=require('./router/login');

logins(app);
//routers(app);
app.listen(port);
console.log("app is lisneening on port "+port);
