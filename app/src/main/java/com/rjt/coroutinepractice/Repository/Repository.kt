package com.rjt.coroutinepractice.Repository

import androidx.lifecycle.LiveData
import com.rjt.coroutinepractice.Model.UserModel
import com.rjt.coroutinepractice.NetworkCall.RetroBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    lateinit var job: CompletableJob

    //Passing userId as input because we need userid to make that request
    //Return LiveData of type UserModel

    fun getUser(userId: String): LiveData<UserModel> {

        //Please initialize the job first
        job = Job()

        return object : LiveData<UserModel>() {

            //When the LiveData object becomes active/ when the method is called
            override fun onActive() {
                super.onActive()

                //Using job to make asynchronous request to the network
                //If the job isn't null then run the code inside of here
                job?.let {

                    //Create a new CoroutineScope
                    //Inside CoroutineScope, IO dispatcher is called to do work on the background thread
                    // +job creates a unique coroutine in the background thread for this job
                    CoroutineScope(IO + job).launch {

                        //Get the userobject from the retrofit
                        val user = RetroBuilder.apiService.getUserAsync(userId)

                        //You can't set a value on a background thread so with coroutine context, switch over to the main thread
                        withContext(Main) {
                            //Setting the value
                            value = user

                            //Marking the job as complete
                            it.complete()
                        }
                    }
                }
            }
        }


    }
    //To cancel any existing jobs
    //In the viewmodel there will be another cancelJobs() method to cancel jobs from viewmodel when the activity is finished
    fun cancelJob() {

        Repository.job?.cancel()
    }
}


