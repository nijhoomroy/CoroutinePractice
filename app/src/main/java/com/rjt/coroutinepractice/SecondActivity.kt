package com.rjt.coroutinepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SecondActivity : AppCompatActivity() {

    private val PROGRESS_START = 0
    private val PROGRESS_MAX = 100
    private val JOB_TIME = 4000 //ms
    private lateinit var job: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btn_progresscheck.setOnClickListener{

            if(!::job.isInitialized){

                jobinit()
            }
            progress_horizontal.startOrCancel(job)
        }
    }

    private fun jobinit() {
        btn_progresscheck.setText("Start Job")
        updateJobTextView("")

        job = Job()

        job.invokeOnCompletion {
            it?.message.let{

                var msg = it
                if(msg.isNullOrBlank()){

                    msg = "Unknown cancellation error!"
                }
                else{
                    Log.e("Nijhoom", "${job} was cancelled due to ${msg}")

                    showToast(msg)
                }


            }

            progress_horizontal.max = PROGRESS_MAX
            progress_horizontal.progress = PROGRESS_START
        }
    }

    fun ProgressBar.startOrCancel(job: Job){

        if(this.progress > 0){
            resetJob()
        }

        else{
            btn_progresscheck.setText("Cancel Job")
            CoroutineScope(IO + job).launch {
                Log.d("Nijhoom", "coroutine ${this} is activated with job: ${job}")

                for(i in PROGRESS_START..PROGRESS_MAX){

                    delay((JOB_TIME/PROGRESS_MAX).toLong())

                    this@startOrCancel.progress = i
                }

                updateJobTextView("Job is complete!")
                showToast("Job is Complete!")
            }
        }
    }

    private fun resetJob() {

        if(job.isActive || job.isCompleted){

           job.cancel(CancellationException("Resetting Job"))
        }

        jobinit()
    }

    private fun showToast(toastText: String) {

        GlobalScope.launch(Main) {

            Toast.makeText(this@SecondActivity, toastText, Toast.LENGTH_LONG).show()
        }

    }

    private fun updateJobTextView(text: String) {
        GlobalScope.launch(Main) {

            text_view_job_complete.setText(text)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
