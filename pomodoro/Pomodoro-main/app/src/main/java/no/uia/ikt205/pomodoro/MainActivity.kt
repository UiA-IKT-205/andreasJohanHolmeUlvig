package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.SeekBar
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countDownDisplay:TextView
    lateinit var pauseCountDownDisplay:TextView
    lateinit var thirtyButton:Button
    lateinit var sixtyButton:Button
    lateinit var ninetyButton:Button
    lateinit var oneTwentyButton:Button

    var timeToCountDownInMs = 5000L
    var timeTicks = 1000L
    var isRunning = false
    var countDownDurationInMinutes = 5.0
    var pauseDurationInMinutes = 5.0
    var pauseDurationInMs = 5000L
    var repetitionsLeft = 0
    var isNextCountDownPause = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seek = findViewById<SeekBar>(R.id.setDurationSeekBar)
        seek?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                timeToCountDownInMs = minutesToMs(15 + 1*2.85*seek.progress)
                updateCountDownDisplay(countDownDisplay, timeToCountDownInMs)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                countDownDurationInMinutes = 15 + 1*2.85*seek.progress
                Toast.makeText(this@MainActivity,
                        "Lengde på arbeidsøkt:" + countDownDurationInMinutes + "Minutter",
                        Toast.LENGTH_SHORT).show()
                timeToCountDownInMs = minutesToMs(countDownDurationInMinutes)
            }
        })

        val numberOfRepetitions = findViewById<EditText>(R.id.enterRepetitions)
        numberOfRepetitions.setText("1")

        val notSeek = findViewById<SeekBar>(R.id.setPauseDurationSeekBar)
        notSeek?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                pauseDurationInMs = minutesToMs(0.15*seek.progress)
                updateCountDownDisplay(pauseCountDownDisplay, pauseDurationInMs)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                pauseDurationInMinutes = 0.15*seek.progress
                Toast.makeText(this@MainActivity,
                        "Lengde på pause:" + pauseDurationInMinutes + "Minutter",
                        Toast.LENGTH_SHORT).show()
                pauseDurationInMs = minutesToMs(pauseDurationInMinutes)
            }
        })

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            if(isRunning){
                timer.cancel()
            }
            isNextCountDownPause = true
            repetitionsLeft = Integer.parseInt(numberOfRepetitions.getText().toString())
            repetitionsLeft = (repetitionsLeft*2)-1
            if(repetitionsLeft == 0)
                repetitionsLeft = 1
            startCountDown(it, timeToCountDownInMs, repetitionsLeft)
            isRunning = true

        }
        countDownDisplay = findViewById<TextView>(R.id.countDownView)
        pauseCountDownDisplay = findViewById(R.id.pauseCountDownView)
    }

    fun startCountDown(v: View, durationInMs:Long, remainingRepetitions:Int){
        var nextCountdownDurationInMs = 5000L
        when (isNextCountDownPause) {
            true -> nextCountdownDurationInMs = pauseDurationInMs
            false -> nextCountdownDurationInMs = timeToCountDownInMs
        }
        timer = object : CountDownTimer(durationInMs,timeTicks) {
            override fun onFinish() {
                if (remainingRepetitions == 1) {
                    Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                    isRunning = false
                } else {
                    isNextCountDownPause = !isNextCountDownPause
                    startCountDown(v, nextCountdownDurationInMs, remainingRepetitions -1) // current progress. Implementing pauses and repetititions, attempting to do so recursively
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(countDownDisplay, millisUntilFinished)
            }
        }
        timer.start()
    }
    fun minutesToMs(timeInMinutes:Double): Long {
        var timeInMs = timeInMinutes.toLong() * 60 * 1000
        return(timeInMs)
    }

    fun updateCountDownDisplay(displayToChange:TextView, timeInMs:Long){
        displayToChange.text = millisecondsToDescriptiveTime(timeInMs)
    }
}