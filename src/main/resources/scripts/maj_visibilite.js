db.election.find().forEach(function (data) {
    db.election.update({_id: data._id}, {$set: {"visibilité": "Privee"}}, false, false);
});