var express = require('express');
var router = express.Router();
var gcm = require('./regGcm');

/* GET home page. */
router.post('/', function(req, res, next) {
	var token = req.body.token;
	gcm.reg(token);
	console.log("새로운 유저등록");
});

module.exports = router;
