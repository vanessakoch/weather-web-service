const express = require('express');

const routes = express.Router();

const WeatherController = require('./controllers/WeatherController');
const weatherController = new WeatherController();

routes.get('/', weatherController.index);
routes.put('/', weatherController.edit);
routes.post('/', weatherController.create);
routes.delete('/', weatherController.delete);

routes.get('/:id', weatherController.show);

module.exports = routes;