package com.cse234.assets.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cse234.assets.data.ActivityData
import com.cse234.assets.screens.ActivityViewModel

@Composable
fun ActivityHistoryScreen(activityViewModel: ActivityViewModel , navController: NavHostController) {
    val activities by activityViewModel.activities.collectAsState()
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        Text(
            text = "Activity History : ${activityViewModel.selectedActivity}",
            modifier = Modifier.padding(10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
        items(activities) {
            when(activities) {
                emptyList<ActivityData>() -> {
                    Text(text = "No activities found")
                }
                else -> {
                    ActivityCard(activity = it)
                }
            }


        }
    }
    }

}

@Composable
fun ActivityCard(activity: ActivityData) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Text(text = activity.activityType)
    }

}