module.exports.get = function(req, res) {
    res.send([{name:'wine1'}, {name:'wine2'}]);
};

// module.exports.create = function(req, res) {
//     res.send({id:req.params.id, name: "The Name", description: "description"});
// };
