var _ = require('underscore');

var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');

var notes = [{
    id: '2',
    content:'<a href="http://www.youtube.com/watch?v=ZbcgyPtYBY0">http://www.youtube.com/watch?v=ZbcgyPtYBY0</a>',
    created: new Date()
},{
    id: '1',
    content:'http://soundcloud.com/world-records-dnb/drum-and-bass-dazer-destiny</a> Some drum n bass song hosted on SoundCloud',
    created: new Date()
}];

module.exports.get = function(req, res) {
    res.send(notes);
};

module.exports.post = function(req, res) {
    var note = {
        id: (notes.length ? parseInt(notes[0].id) + 1 : 1).toString(),
        content: req.body.content,
        created: new Date()
    };

    notes.unshift(note);
    res.send(200, note);
};

module.exports.delete = function(req, res) {
    var id = req.params.id;
    notes = _.reject(notes, function(note) {
        return note.id === id;
    });

    res.send(200);
};


// module.exports.create = function(req, res) {
//     res.send({id:req.params.id, name: "The Name", description: "description"});
// };
