package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var thirtyButton:Button
    lateinit var sixtyButton:Button
    lateinit var ninetyButton:Button
    lateinit var oneTwentyButton:Button

    var timeToCountDownInMs = 5000L
    var timeTicks = 1000L
    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
        }
        thirtyButton = findViewById<Button>(R.id.thirtyMinutes)
        thirtyButton.setOnClickListener(){
            thirtyMinutes()
        }
        sixtyButton = findViewById<Button>(R.id.sixtyMinutes)
        sixtyButton.setOnClickListener(){
            sixtyMinutes()
        }
        ninetyButton = findViewById<Button>(R.id.ninetyMinutes)
        ninetyButton.setOnClickListener(){
            ninetyMinutes()
        }
        oneTwentyButton = findViewById<Button>(R.id.oneTwentyMinutes)
        oneTwentyButton.setOnClickListener(){
            oneTwentyMinutes()
        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){
        if(isRunning){
            timer.cancel()
        }
        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                isRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }
        isRunning = true
        timer.start()
    }
    fun thirtyMinutes(){
        timeToCountDownInMs = 1800000L
    }
    fun sixtyMinutes(){
        timeToCountDownInMs = 3600000L
    }
    fun ninetyMinutes(){
        timeToCountDownInMs = 5400000L
    }
    fun oneTwentyMinutes(){
        timeToCountDownInMs = 7200000L
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}