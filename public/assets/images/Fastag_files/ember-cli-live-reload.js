(function() {
  var srcUrl = null;
  var host = location.hostname || 'localhost';
  var useCustomPort = false || location.port !== 4200;
  var defaultPort = location.port || (location.protocol === 'https:' ? 443 : 80);
  var port = useCustomPort ? 4200 : defaultPort;
  var path = '&path=_lr/livereload';
  var prefixURL = useCustomPort ? (location.protocol || 'http:') + '//' + host + ':' + 4200 : '';
  var src = srcUrl || prefixURL + '/_lr/livereload.js?port=' + port + '&host=' + host + path;
  var script    = document.createElement('script');
  script.type   = 'text/javascript';
  script.src    = src;
  document.getElementsByTagName('head')[0].appendChild(script);
}());