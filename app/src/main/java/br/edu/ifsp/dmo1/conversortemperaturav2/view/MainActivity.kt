package br.edu.ifsp.dmo1.conversortemperaturav2.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.conversortemperaturav2.R
import br.edu.ifsp.dmo1.conversortemperaturav2.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.conversortemperaturav2.model.FahrenheitToCelsiusStrategy
import br.edu.ifsp.dmo1.conversortemperaturav2.model.ConversorTemperatura
import br.edu.ifsp.dmo1.conversortemperaturav2.model.CelsiusToFahrenheitStrategy
import br.edu.ifsp.dmo1.conversortemperaturav2.model.CelsiusToKelvinStrategy

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

        binding.btnCelsiusFahrenheit.setOnClickListener{
            handleConversion(CelsiusToFahrenheitStrategy)
        }
        binding.btnFahrenheitCelsius.setOnClickListener{
            handleConversion(FahrenheitToCelsiusStrategy)
        }
        binding.btnCelsiusKelvin.setOnClickListener{
            handleConversion(CelsiusToKelvinStrategy)
        }

    }

    private fun readTemperature(): Double{
        return try{

            binding.textviewTemperature.text.toString().toDouble()
        }catch(e: NumberFormatException){
            throw NumberFormatException("Input Error")
        }
    }


    @SuppressLint("DefaultLocale")
    private fun handleConversion(strategy: ConversorTemperatura){
        converterStrategy = strategy

        try{
            val inputValue = readTemperature()
            binding.textviewResultNumber.text = String.format(
                "%.2f %s",
                converterStrategy.converter(inputValue),
                converterStrategy.getScale()
            )
            binding.textviewResultMessage.text = if(this.converterStrategy is FahrenheitToCelsiusStrategy){
                getString(R.string.msgFtoC)
            }else if(this.converterStrategy is CelsiusToFahrenheitStrategy){
                getString(R.string.msgCtoF)
            }else{
                getString(R.string.msgCtoK)
            }


        }catch (e: Exception){
            Toast.makeText(
                this,getString(R.string.error_popup_notify),Toast.LENGTH_SHORT
            ).show()
            Log.e("APP DMO",e.stackTraceToString())
        }
    }
}