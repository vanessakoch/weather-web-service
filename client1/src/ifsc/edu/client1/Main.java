package ifsc.edu.client1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		String baseUrl = "http://localhost:3333";
		Random random = new Random();

		while (true) {
			float temperature = -2 + random.nextFloat() * (50 - (-2));
			float humidity = 50 + random.nextFloat() * (99 - 50);
			float luminosity = 70 + random.nextFloat() * (520 - 70);

			Weather weather = new Weather(temperature, humidity, luminosity);
			postWeather(baseUrl, weather);

			Thread.sleep(10000);
		}

	}

	static void postWeather(String baseUrl, Weather weather) throws InterruptedException {
		URL url;
		HttpURLConnection connection = null;

		try {
			url = new URL(baseUrl);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-type", "application/json");
			connection.setDoOutput(true);

			String jsonInputString = "{\"temperatura\":\"" + weather.getTemperatura() + "\", \"umidade\":\""
					+ weather.getUmidade() + "\", \"luminosidade\":\"" + weather.getLuminosidade() + "\"}";

			try {
				OutputStream os = connection.getOutputStream();
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Falha : HTTP código : " + connection.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;
			System.out.println("Retorno do servidor .... \n");

			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
