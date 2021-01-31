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
			return response.json(weather);
		} else {
			return response.status(400).json({ message: 'Clima não encontrado.'})
		}
	}
  
	create(request, response) { 
		let id = 1;

		const lastWeather = data.weathers[data.weathers.length -1];
	
		if(lastWeather) {
			id = lastWeather.id + 1;
		};

		let now = new Date();
		let dia = now.getDate();           
		let mes = now.getMonth();    
		let ano4 = now.getFullYear();       
		let hora = now.getHours();        
		let min = now.getMinutes();        
		let seg = now.getSeconds(); 

		let date = dia + '/' + (mes+1) + '/' + ano4;
		let hour = hora + ':' + min + ':' + seg;

		const weather = {
			id,
			...request.body,
			createdAt: {
				date,
				hour
			}
		}
	
		data.weathers.push(weather)

		fs.writeFile("data.json", JSON.stringify(data, null, 2), function(err) {
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
				console.log(foundIndex)
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
	
		fs.writeFile("data.json", JSON.stringify(data, null, 2), function(err) {
			if (err) return response.json({ message: "Erro ao escrever arquivo" });
			return response.json({ weather });
		});
	};
};

module.exports = WeatherController;