package com.rjt.coroutinepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "result1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener{

            CoroutineScope(IO).launch {
                fakeApiResult()
            }

        }


    }

    private fun setNewText(input: String){

        val newText = text.text.toString() + "\n$input"

        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){

        withContext(Main){

            setNewText(input)
        }
    }

    private suspend fun fakeApiResult(){

        logThread("fakeApiResult")

        val result_1 = getResult1FromApi()
        setTextOnMainThread(result_1)

        val result_2 = getResult2FromApi()
        setTextOnMainThread(result_2)


    }

    private suspend fun getResult1FromApi(): String{

      logThread("getResultFromApi")

        delay(1000)

        return "Result#1"
    }

    private suspend fun getResult2FromApi(): String{

        logThread("getResult2FromApi")

        delay(1000)

        return "Result#2"
    }

    private fun logThread(methodName: String) {

        println("debug: ${methodName}: + ${Thread.currentThread().name} ")

    }
}
