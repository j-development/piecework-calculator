package com.pluggamest.test

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.pluggamest.test.databinding.ActivityMainBinding
import java.util.*
import android.view.View
import java.text.SimpleDateFormat
import kotlin.math.roundToInt
import kotlin.time.seconds


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var counter: Double = 0.0
    private lateinit var timer: CountDownTimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        var hours: String = hour.toString()
        if (hour < 10){
            hours = "0$hours"
        }

        val minute = c.get(Calendar.MINUTE)
        var minutes: String = minute.toString()
        if (minute < 10){
            minutes = "0$minutes"
        }
        binding.ETA.text = getString(R.string.eta, hours, minutes)


        var takt = 146
        binding.seekBarTakt.setProgress(20)


        binding.seekBarTakt.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val bas_timlon = 120
                takt = progress + bas_timlon
                val bas_and_kronor_string: String = takt.toString()
                // Write code to perform some action when progress is changed.

                //bindingETA.text = hours + ":" + minutes
                binding.TVTimlon.text = getString(R.string.timlon, bas_and_kronor_string)

            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                //Toast.makeText(this@MainActivity, "Progress is " + seekBar.progress + "%", Toast.LENGTH_SHORT).show()

            }


        })






        binding.countdownStart.setOnClickListener {
            countdownStart(takt)
        }

        binding.fabStart.setOnClickListener{
            startTimeCounter()
            binding.fabReset.visibility = View.VISIBLE
        }

        binding.fabReset.setOnClickListener{
            timer.cancel()
            counter = 0.0
            binding.countTime.text = ""
            binding.orderMinutes.text = ""
        }


    }

    private fun countdownStart(takt: Int) {
        val stringInTextField = binding.betalningInput.text.toString()
        val inputbetalning = stringInTextField.toDoubleOrNull()
        if (inputbetalning == null || inputbetalning == 0.0) {
            binding.betalningInput.setText(R.string.basbetalning)
            return
        }
        binding.betalningInput.setText(getString(R.string.two_decimal_input, inputbetalning.toString()))
        var taktackord : Double= takt.toDouble() - 83.toDouble()
        var input: Double = binding.betalningInput.text.toString().toDouble()
        var result: Double = input / taktackord
        result = (result * 100).roundToInt() / 100.0
        var order_in_minutes = result*60
        counter = result*3600
        binding.orderMinutes.text = getString(R.string.order_in_minutes, order_in_minutes.toString())
    }



    fun startTimeCounter(){
       timer =  object : CountDownTimer(counter.toLong()*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {

                binding.countTime.text = counter.toString()
                counter--
            }

            override fun onFinish() {
                binding.countTime.text = getString(R.string.On_Finish)
            }
        }.start()
    }



}