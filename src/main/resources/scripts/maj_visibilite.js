db.election.find().forEach(function (data) {
    db.election.update({_id: data._id}, {$set: {"visibilit√©": "Privee"}}, false, false);
});