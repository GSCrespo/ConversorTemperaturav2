package br.edu.ifsp.dmo1.conversortemperaturav2.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.conversortemperaturav2.R
import br.edu.ifsp.dmo1.conversortemperaturav2.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.conversortemperaturav2.model.CelsiusStrategy
import br.edu.ifsp.dmo1.conversortemperaturav2.model.ConversorTemperatura
import br.edu.ifsp.dmo1.conversortemperaturav2.model.FahrenheitStrategy
import br.edu.ifsp.dmo1.conversortemperaturav2.model.KelvinStrategy
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var converterStrategy: ConversorTemperatura

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener(){

        binding.btnCelsius.setOnClickListener{
            handleConversion(CelsiusStrategy)
        }
        binding.btnFahrenheit.setOnClickListener{
            handleConversion(FahrenheitStrategy)
        }
        binding.btnKelvin.setOnClickListener{
            handleConversion(KelvinStrategy)
        }

    }

    private fun readTemperature(): Double{
        return try {
            val temp = binding.edittextTemperature.text.toString()
            if (temp.isEmpty()) {
                throw NumberFormatException("Input is empty")
            }
            temp.toDouble()
        } catch (e: NumberFormatException) {
            // Exibe uma mensagem de erro detalhada ao usuário
            Toast.makeText(this, "Invalid input, please enter a valid number", Toast.LENGTH_SHORT).show()
            throw e
        }
    }



    private fun handleConversion(strategy: ConversorTemperatura){
        converterStrategy = strategy

        try {
            val inputValue = readTemperature()
            val result = converterStrategy.converter(inputValue)


            binding.textviewResultNumber.text = String.format("%.2f %s",converterStrategy.converter(inputValue),
                converterStrategy.getScale())

            // Exibe a mensagem de conversão baseada na estratégia
            binding.textviewResultMessage.text = when (strategy) {
                is CelsiusStrategy -> getString(R.string.msgCtoF)
                is FahrenheitStrategy -> getString(R.string.msgFtoC)
                is KelvinStrategy -> getString(R.string.msgCtoK)
                else -> ""
            }
        }catch (e: Exception){
            Toast.makeText(
                this,getString(R.string.error_popup_notify),Toast.LENGTH_SHORT
            ).show()
            Log.e("APP DMO",e.stackTraceToString())
        }
    }
}