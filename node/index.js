var config = require('./config');
var express = require('express');

//routes
var notes = require('./routes/notes');


var app = express();

app.configure(function () {
    app.use(express.bodyParser());
    app.use(express.query());
    app.use(express.methodOverride());
});

//routes
app.all('*', function(req, res, next) {
    console.log('started route: %s %s', req.method, req.url);
    next();
});
app.get('/notes', notes.get);
app.post('/notes', notes.post);
app.delete('/notes/:id', notes.delete);

//server public files
app.use('/', express.static(__dirname + '/public'));

app.listen(config.port);

console.log('Server running at http://127.0.0.1:'+config.port+'/');