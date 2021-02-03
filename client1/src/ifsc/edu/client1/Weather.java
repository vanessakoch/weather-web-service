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

	public float getTemperatura() {
		return Float.valueOf(df.format(temperatura).replace(',', '.'));
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public float getUmidade() {
		return Float.valueOf(df.format(umidade).replace(',', '.'));
	}

	public void setUmidade(float umidade) {
		this.umidade = umidade;
	}

	public float getLuminosidade() {
		return Float.valueOf(df.format(luminosidade).replace(',', '.'));
	}

	public void setLuminosidade(float luminosidade) {
		this.luminosidade = luminosidade;
	}

	@Override
	public String toString() {
		return "Weather [temperatura=" + temperatura + ", umidade=" + umidade + ", luminosidade=" + luminosidade + "]";
	}

}
