var express = require('express');
var router = express.Router();
var gcm = require('./sendGcm');

router.post('/reg', function(req, res, next) {
  	var email = req.param('Email');
        var pw = req.param('pw');
        var Token = req.param('Token');
 	
        var current = new Users({
             email: email,
             passwd: pw,
	     GCMToken: Token,
        });

        current.save(function(err, data) {
           if (err) { // TODO handle the error
                console.log("error");
		return  res.send(301,"DB error");
            }
	      console.log(Token);
	      gcm.sendRegSu(Token);
	      return res.send(300,"su");
        });

});


router.post('/login', function(req, res, next) {
        var email = req.param('Email');
        var pw = req.param('pw');
	var Token = req.param('Token');

	 Users.findOne({"email": email, "passwd": pw}, function(err, member) {
	    if (err) { // TODO handle the error
                console.log("error");
                return  res.send(301,"DB error");
            }
   	    if(member !== null){
	     if(member.GCMToken !== Token){
		 Users.update({email: member.email}, {$set:{GCMToken: Token}}, function(err, result) {
                  if (err){
   		       res.send(301,"DB error");
		   }
			 return res.send(300,"su");
                });
	      }else{
		  return res.send(300,"su");
	      }
	    }else{
	      return res.send(302,"없는유저입니다");
	     }
         });
});

module.exports = router;
