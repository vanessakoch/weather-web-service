const express = require('express');

const routes = express.Router();

const WeatherController = require('./controllers/WeatherController');
const weatherController = new WeatherController();

routes.get('/weather', weatherController.index);
routes.get('/weather/:id', weatherController.show);

routes.put('/', weatherController.edit);
routes.post('/', weatherController.create);
routes.delete('/', weatherController.delete);

routes.get('/search', weatherController.getByDateHour);
routes.get('/last', weatherController.getLast);

module.exports = routes;