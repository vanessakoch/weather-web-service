package ifsc.edu.client1;

import java.text.DecimalFormat;

public class Weather {
	DecimalFormat df = new DecimalFormat("0.00");

	private float temperatura;
	private float umidade;
	private float luminosidade;

	public Weather(float temperatura, float umidade, float luminosidade) {
		super();
		this.temperatura = temperatura;
		this.umidade = umidade;
		this.luminosidade = luminosidade;
	}

	public String getTemperatura() {
		return String.valueOf(df.format(temperatura));
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public String getUmidade() {
		return String.valueOf(df.format(umidade));
	}

	public void setUmidade(float umidade) {
		this.umidade = umidade;
	}

	public String getLuminosidade() {
		return String.valueOf(df.format(luminosidade));
	}

	public void setLuminosidade(float luminosidade) {
		this.luminosidade = luminosidade;
	}

	@Override
	public String toString() {
		return "Weather [temperatura=" + temperatura + ", umidade=" + umidade + ", luminosidade=" + luminosidade + "]";
	}

}
