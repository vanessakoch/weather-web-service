const data = require('../../data.json');
const fs = require('fs');

class WeatherController {
  
	index(request, response) {
		response.json(data.weathers);
	}

	show(request, response) {
		const { id } = request.params;

		const weather = data.weathers.filter(weather => weather.id == id);
		
		if(weather.length > 0) {
			return response.json(weather[0]);
		} else {
			return response.json({ message: 'Clima não encontrado.'})
		}
	}
  
	create(request, response) { 
		let { temperatura, umidade, luminosidade } = request.body;
		let id = 1;

		const lastWeather = data.weathers[data.weathers.length -1];
	
		if(lastWeather) {
			id = lastWeather.id + 1;
		};

		let now = new Date();
		let day = now.getDate();           
		let month = now.getMonth();    
		let year = now.getFullYear();       
		let hr = now.getHours();        
		let min = now.getMinutes();        
		let sec = now.getSeconds(); 
		
		let formated = [day, month+1, hr, min, sec];
		
		formated = formated.map(num => {
			if(num.toString().length === 1) {
				return num = "0" + num;
			}
			return num.toString();
		})

		let date = formated[0] + '/' + formated[1] + '/' + year;
		let hour = formated[2] + ':' + formated[3] + ':' + formated[4];
		
		temperatura = parseFloat(temperatura);
		umidade= parseFloat(umidade);
		luminosidade= parseFloat(luminosidade);
		
		const weather = {
			id,
			temperatura,
			umidade,
			luminosidade,
			createdAt: {
				date,
				hour
			}
		}
	
		data.weathers.push(weather)

		fs.writeFile("data.json", JSON.stringify(data, null, 2), err => {
			if (err) return response.json("Erro ao escrever o arquivo");    
			return response.json(weather);
		});
	}

	delete(request, response) {
		const { id } = request.body

		const filteredWeathers = data.weathers.filter(weather => weather.id != id);
		data.weathers = filteredWeathers

		fs.writeFile("data.json", JSON.stringify(data, null, 2), err => {
			if (err) return response.json({ message: "Erro ao deletar do arquivo" })
			return response.json(data.weathers);
		});
	}

	edit(request, response) {
		const { id } = request.body;
		let index = 0;
	
		const foundWeather = data.weathers.find((weather, foundIndex) => {
			if(weather.id === id) {
				index = foundIndex;
				return true;
			}
		})
	
		if(!foundWeather) return response.json({ message: "Clima não encontrado" });
	
		const weather = {
			...foundWeather,
			...request.body,
			id: Number(request.body.id)
		}
	
		data.weathers[index] = weather;
	
		fs.writeFile("data.json", JSON.stringify(data, null, 2), err => {
			if (err) return response.json({ message: "Erro ao escrever arquivo" });
			return response.json({ weather });
		});
	};

	getLast(request, response) {
		let lastWeather = data.weathers[data.weathers.length - 1];
		return response.json(lastWeather);
	}

	getByDateHour(request, response) {
		const { date, hour } = request.query;

		const foundWeather = data.weathers.find(weather => 
				weather.createdAt.date === date &&
				weather.createdAt.hour === hour
		);

		if(foundWeather) {
			return response.json(foundWeather);
		} else {
			return response.json({ message: "Medição não encontrada" });
		}

	}
};

module.exports = WeatherController;