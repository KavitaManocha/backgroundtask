package com.backgroundtask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    fun exampleMethodUsingLaunch() {
        // Starts a new coroutine within the scope
        viewModelScope.launch {
            // New coroutine that can call suspend functions
            sampleRunBlocking()
            //To Switch the context of Dispatchers
            withContext(Dispatchers.Main){
            }
        }
        viewModelScope
    }


    fun sampleRunBlocking(){
        var i = 0
        Log.i("Hello",i.toString())
        runBlocking {
            for (i in 0..10){
//                Log.i("Hello",i.toString())
                var j = i+1
                delay(5000)
                Log.i("Hello",j.toString())
//                delay(5000)

            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}