db.sondage.find().forEach(function (data) {
    if (data.dateCréation == undefined) {
        db.sondage.update({_id: data._id}, {$set: {"dateCréation": ISODate("2012-03-01T00:00:00.581Z")}}, false, false);
    }
});