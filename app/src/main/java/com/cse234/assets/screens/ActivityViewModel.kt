package com.cse234.assets.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse234.assets.data.ActivityData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class ActivityViewModel : ViewModel(){
    private val db = Firebase.firestore
    private val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid
    private val _isLoaded = MutableStateFlow<Boolean?>(null)
    val isLoaded : StateFlow<Boolean?> = _isLoaded.asStateFlow()
    var selectedActivity = ""
    private val _activities = MutableStateFlow<List<ActivityData>>(emptyList())
    val activities : StateFlow<List<ActivityData>> = _activities.asStateFlow()
    private val _dailyActivities = MutableStateFlow<List<ActivityData>>(emptyList())




    fun loadDataToFireStore(activityData : ActivityData?){
        activityData?.let {
            db.collection("activities")
                .add(it)
                .addOnSuccessListener {
                    Log.d("db", "activity added")
                    _isLoaded.value = true
                }
                .addOnFailureListener {
                    Log.d("db", "activity could not be added")
                    _isLoaded.value = false
                }
        }
    }

    fun fetchFromFireStore(selectedActivity : String){
        viewModelScope.launch { db.collection("activities")
            .whereEqualTo("userId", userId)
            .whereEqualTo("activityType", selectedActivity)
            .get()
            .addOnSuccessListener {result ->
                Log.d("db_fetch", "${result.documents.size} activities fetched (${selectedActivity})")
                _activities.value = result.documents.mapNotNull { it.toObject(ActivityData::class.java) }
            }
            .addOnFailureListener {
                Log.d("db_fetch", "could not fetch activities")
                Log.d("db_fetch", it.message.toString())
            }
        }


    }

    fun resetIsLoaded(){
        _isLoaded.value = null
    }


    fun calculateTotalDistanceForToday() : Double{
        if (_dailyActivities.value.isEmpty()) {
            viewModelScope.launch {
                db.collection("activities")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener { result ->
                        Log.d("fetchAllActivities", "Fetched ${result.documents.size} activities")
                        _dailyActivities.value = result.documents.mapNotNull { it.toObject(ActivityData::class.java) }
                    }
                    .addOnFailureListener {
                        Log.e("fetchAllActivities", "Error fetching activities", it)
                    }
            }
        }
        return updateTotalDistance()
    }
    private fun updateTotalDistance() : Double {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.time

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.time

        val result = _dailyActivities.value.filter { activity ->
            activity.date.after(startOfDay) && activity.date.before(endOfDay)
        }.sumOf { activity -> activity.distance }

        Log.d("totaldistance", "Total distance is calculated : $result meters")

        return result
    }
}

