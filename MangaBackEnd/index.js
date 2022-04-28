/*
RESTFul Services by NodeJS
*/

var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Connect to MySQL
var con = mysql.createConnection({
	host:'localhost',
	user:'root',
	password:'',
	database:'testmanga' // Name of database
});

//Create RESTFul
var app=express();
var publicDir=(__dirname+'/public/'); // Set static dir for display image local by url
app.use(express.static(publicDir));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
//GET ALL BANNER
app.get("/banner",(req,res,next)=>{
	con.query('SELECT * FROM banner',function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No comic here"));
		}
	})
});

//GET ALL COMIC
app.get("/comic",(req,res,next)=>{
	con.query('SELECT * FROM manga',function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No comic here"));
		}
	})
});

//GET CHAPTER BY MANGA ID
app.get("/chapter/:mangaid",(req,res,next)=>{
	con.query('SELECT * FROM chapter where MangaID=?',[req.params.mangaid],function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No chapter here"));
		}
	})
});

//GET IMAGES BY Chapter ID
app.get("/links/:chapterid",(req,res,next)=>{
	con.query('SELECT * FROM link where ChapterId=?',[req.params.chapterid],function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No chapter here"));
		}
	})
});

//GET ALL CATEGORIES
app.get("/categories",(req,res,next)=>{
	con.query('SELECT * FROM Category',function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No category here"));
		}
	})
});

//GET ALL COMIC BY MULTIPLE CATEGORIES
app.post("/filter",(req,res,next)=>{
	var post_data = req.body; // GET POST DATA from POST REQUEST
	var array = JSON.parse(post_data.data); // GET json array form 'data' field and convert to array object
	var query = "SELECT * FROM manga WHERE ID IN (SELECT MangaID FROM mangacategory"; // default query
	if(array.length > 0)
	{
		query+=" GROUP BY MangaID";
		if(array.length == 1) // If use just submit 1 category
			query+=" HAVING SUM(CASE WHEN CategoryID = "+array[0]+" THEN 1 ELSE 0 END) > 0)";
		else // If user submit more than 1 category
		{
			for(var i=0;i<array.length;i++)
			{
				if(i == 0) // first condition
					query+=" HAVING SUM(CASE WHEN CategoryID="+array[0]+" THEN 1 ELSE 0 END)>0 AND";
				else if(i==array.length-1) // Last condition
					query+=" SUM(CASE WHEN CategoryID="+array[i]+" THEN 1 ELSE 0)";
				else
					query+=" SUM(CASE WHEN CategoryID="+array[i]+" THEN 1 ELSE 0 AND";
			}
		}
		con.query(query,function(error,result,fields){
		con.on('error',function(err){
				console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No comic here"));
		}
	})
	}
});

//SEARCH COMIC BY NAME
app.post("/search",(req,res,next)=>{
	var post_data = req.body;
	var name_search = post_data.search; // GET DATA from 'search' field

	var query = "SELECT * FROM manga WHERE Name LIKE '%"+name_search+"%'";
	con.query(query,function(error,result,fields){
		con.on('error',function(err){
			console.log('[MY SQL ERROR]',err);
		});

		if(result && result.length)
		{
			res.end(JSON.stringify(result));
		}
		else
		{
			res.end(JSON.stringify("No comic here"));
		}
	})
});

//Start Server
app.listen(3000,()=>{
	console.log('EDMTDev Comic API running on port 3000');
})