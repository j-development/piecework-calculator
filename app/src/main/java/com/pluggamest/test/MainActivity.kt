package com.pluggamest.test

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.pluggamest.test.databinding.ActivityMainBinding
import java.util.*
import android.view.View
import kotlin.math.roundToInt


class workOrder(private var orderValue: Double, private var orderPace: Double){
    fun get_Value(): Double {
        return orderValue
    }
    fun set_Value(set_Value: Double) {
        orderValue = set_Value
    }
    fun get_Pace(): Double {
        return orderPace
    }
    fun set_Pace(set_Pace: Double) {
        orderPace = set_Pace
    }

}


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    var counter: Double = 0.0
    var orderminutes: Int = 0
    private lateinit var timer: CountDownTimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)







        fun displayETA(minutes_add: Int, hours_add: Int){
            val c = Calendar.getInstance()
            var minute = c.get(Calendar.MINUTE) + minutes_add
            var hour = c.get(Calendar.HOUR_OF_DAY) + hours_add


            if(minute>59) {
                minute = minute-60
                hour += 1
            }
            var minutes: String = minute.toString()
            if (minute < 10) minutes = "0$minutes"

            if(hour>23) hour = hour-24
            var hours: String = hour.toString()
            if (hour < 10) hours = "0$hours"


            binding.ETA.text = getString(R.string.eta, hours, minutes)

        }

        fun calculateETA(){

            if(orderminutes != 0 ) {
                val minutes_add =  orderminutes % 60
                val hours_add = (orderminutes - minutes_add) / 60
                displayETA(minutes_add, hours_add)
            }

        }





        var pace = 146
        binding.seekBarTakt.setProgress(20)


        binding.seekBarTakt.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val bas_timlon = 120
                pace = progress + bas_timlon
                val bas_and_kronor_string: String = pace.toString()
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






        binding.countdownSet.setOnClickListener {
            countdownStart(pace)
            calculateETA()
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
            binding.ETA.text = ""

        }


    }






    private fun countdownStart(pace: Int) {
        val stringInTextField = binding.betalningInput.text.toString()
        val inputbetalning = stringInTextField.toDoubleOrNull()
        if (inputbetalning == null || inputbetalning == 0.0) {
            binding.betalningInput.setText(R.string.basbetalning)
            return
        }
        binding.betalningInput.setText(getString(R.string.two_decimal_input, inputbetalning.toString()))
        var taktackord : Double= pace.toDouble() - 83.toDouble()
        var input: Double = binding.betalningInput.text.toString().toDouble()
        var result: Double = input / taktackord
        result = (result * 100).roundToInt() / 100.0
        var order_in_minutes = result*60
        //val orderminutes: Int = order_in_minutes.toInt()
        orderminutes = order_in_minutes.toInt()
        counter = result*3600
        binding.orderMinutes.text = getString(R.string.order_in_minutes, orderminutes.toString())
    }



    fun startTimeCounter(){
       timer =  object : CountDownTimer(counter.toLong()*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {

                binding.countTime.text = counter.toInt().toString()
                counter--
            }

            override fun onFinish() {
                binding.countTime.text = getString(R.string.On_Finish)
            }
        }.start()
    }




}