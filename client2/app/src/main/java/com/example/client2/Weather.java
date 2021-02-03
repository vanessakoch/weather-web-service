package com.example.client2;

public class Weather {
    private float temperatura;
    private float umidade;
    private float luminosidade;
    private Update createdAt;

    public Weather(float temperatura, float umidade, float luminosidade, Update createdAt) {
        super();
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.luminosidade = luminosidade;
        this.createdAt = createdAt;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getUmidade() {
        return umidade;
    }

    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    public float getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(float luminosidade) {
        this.luminosidade = luminosidade;
    }

    public Update getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Update createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperatura='" + temperatura + '\'' +
                ", umidade='" + umidade + '\'' +
                ", luminosidade='" + luminosidade + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

