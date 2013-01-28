var http = require('http');
var config = require('./config');
var express = require('express');

//routes
var notes = require('./routes/notes');


var app = express();

//routes
app.get('/notes', notes.get);

//server public files
app.use('/public', express.static(__dirname + '/public'));

app.listen(config.port);

console.log('Server running at http://127.0.0.1:'+config.port+'/');