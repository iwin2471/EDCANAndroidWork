var fs = require('fs');
var gcm = require('node-gcm');

var message = new gcm.Message();
var server_api_key = 'AIzaSyA2uvjx6rdxODv3qOYFCyztG28hizJk-Pg';
var sender = new gcm.Sender(server_api_key);

module.exports.sendRegSu = function sendRegSu(token) {
    var message = new gcm.Message({
      collapseKey: 'demo',
      delayWhileIdle: true,
      timeToLive: 3,
        data: {
         title: "title",
         message: '정상적으로 등록완료되었습니다',
         custom_key1: 'custom data1',
         custom_key2: 'custom data2'
    	}
     });

    sender.send(message, token, 4, function (err, result) {
     	if(!err){
        }
    });

}



