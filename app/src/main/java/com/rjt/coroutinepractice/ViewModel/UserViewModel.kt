package com.rjt.coroutinepractice.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rjt.coroutinepractice.Model.UserModel
import com.rjt.coroutinepractice.Repository.Repository

class UserViewModel: ViewModel() {

    //Use it as a trigger to initiate the request
    private val _userId : MutableLiveData<String> = MutableLiveData()

    //This user is going to have LiveData user object that we are returning from the repository
    val user: LiveData<UserModel> = Transformations
        //if the userId object changes then this switchmap will trigger and it will execute the action inside of here
            //if it triggers it is going to take in the userId object which is a string and return a type of user
            //it is taking in a string and returning a user object
            //it's listening to that string and if that string changes, it's mapping out a user object
        // and we are mapping out a user object by calling by calling Repository.getUser
        .switchMap(_userId){

            Repository.getUser(it)


        }

    //Creating a method to set that userId
    fun setUserId(userId: String){
        //Just a new userId value
        val updated_userId = userId

        //Basically if the userId value hasn't changed then do nothing
        if(_userId.value == updated_userId){
             return
        }
        //otherwise, set the userId value to be the new/updated userId value
        else{ _userId.value == updated_userId}
    }

    //Cancel Jobs
    fun cancelJobs(){
        Repository.cancelJob()
    }
}