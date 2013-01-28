var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');

module.exports.get = function(req, res) {
    res.send([{
        id: 1, 
        content:'http://soundcloud.com/world-records-dnb/drum-and-bass-dazer-destiny</a> Some drum n bass song hosted on SoundCloud',
        created: 'Some time ago'
    }, {
        id: 2,
        content:'<a href="http://www.youtube.com/watch?v=ZbcgyPtYBY0">http://www.youtube.com/watch?v=ZbcgyPtYBY0</a>',
        created: 'Some time ago'
    }]);
};

// module.exports.create = function(req, res) {
//     res.send({id:req.params.id, name: "The Name", description: "description"});
// };
