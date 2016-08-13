var express = require('express');
var router = express.Router();

router.post('/reg', function(req, res, next) {
  	var email = req.param('Email');
        var pw = req.param('pw');

        var current = new Users({
             email: email,
             passwd: pw,
        });

        current.save(function(err, data) {
           if (err) { // TODO handle the error
                console.log("error");
		return  res.send(301,"DB error");
            }
	      return res.send(300,"su");
        });

});

module.exports = router;
