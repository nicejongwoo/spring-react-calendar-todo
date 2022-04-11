const proxy = require('http-proxy-middleware')

module.exports = function(app) {
    app.use(proxy('/*'), {target: 'http://localhost:8081'})
    app.use(proxy('/*'), {target: 'http://localhost:8082'})
}