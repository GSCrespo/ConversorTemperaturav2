package br.edu.ifsp.dmo1.conversortemperaturav2.model

object KelvinStrategy : ConversorTemperatura {

    override fun converter(temperature: Double): Double {
        return temperature + 273
    }

    override fun getScale(): String {
        return "K"
    }
}