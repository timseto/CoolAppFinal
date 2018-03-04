var express = require('express');
var router = express.Router();
var fs = require('fs');
var json = require('json');

/* GET home page. */
router.get('/', function(req, res, next) {
  fs.readFile('./views/list.json', function(err, data) {
    res.writeHead(200, {'Content-Type': 'text/json'});
    res.write(data);
    res.end();
  });
});

module.exports = router;
