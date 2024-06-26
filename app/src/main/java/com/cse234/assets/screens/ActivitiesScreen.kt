package com.cse234.assets.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cse234.assets.R
import kotlinx.coroutines.launch

@Composable
fun ActivitiesScreen(navController : NavHostController , activityViewModel: ActivityViewModel) {

    LaunchedEffect(Unit) {//aynısını energy consumption sayfasına da yaz 
        activityViewModel.clearDailyActivities()
        activityViewModel.fetchDailyActivities()
    }

    val coroutineScope = rememberCoroutineScope()

    val totalDistance = remember { mutableStateOf("0.0") }

    LaunchedEffect(activityViewModel.dailyActivities.collectAsState().value) {
        totalDistance.value = activityViewModel.calculateTotalDistance()
    }



    val activities = listOf(
        "Walking",
        "Jogging",
        "Running",
        "Cycling",
        "Swimming",
        "Yoga")
    val selectedActivity = remember { mutableStateOf(activities[0]) }

    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navController, onItemClick = { navController.navigate(it.route)})
        },
        topBar = {
            Text(text = "Activities",
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 36.dp),
                fontSize = 44.sp, fontFamily = FontFamily.Serif,
                color = colorResource(R.color.black)
            )
        },
        containerColor = colorResource(R.color.user_page_bg)
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Card ( // ACTIVITY LIST
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(50.dp),
            ){

                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    items(activities) { activity ->
                        val isSelected = activity == selectedActivity.value

                        TextButton(
                            onClick = {
                                selectedActivity.value = activity
                                activityViewModel.selectedActivity = selectedActivity.value
                            },
                            colors = if(isSelected) {
                                ButtonDefaults.textButtonColors(
                                    contentColor = colorResource(R.color.user_page_bg),
                                    containerColor = colorResource(R.color.black)
                                )
                            } else {
                                ButtonDefaults.textButtonColors(
                                    contentColor = colorResource(R.color.black),
                                    containerColor = colorResource(R.color.white))
                            }
                        ) {
                            Text(text = activity)
                        }
                    }
                }
            }
            // START ACTIVITY ACCORDING TO SELECTED ACTIVITY
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(// SELECTED ACTIVITY HISTORY
                    Icons.TwoTone.DateRange,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Exercise history")
                IconButton(onClick = {
                    coroutineScope.launch {
                        activityViewModel.fetchFromFireStore(activityViewModel.selectedActivity)
                    }
                    navController.navigate("ActivityHistoryScreen")
                }) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "")
                }
            }
            Divider(modifier = Modifier
                .padding(horizontal = 28.dp, vertical = 5.dp)
                .height(2.dp)
                .width(350.dp),
                color = colorResource(R.color.black)
            )
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(410.dp)
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = colorResource(R.color.black)
                    ),
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.start_exercise1),
                                        colorResource(R.color.start_exercise2),
                                        colorResource(R.color.start_exercise3),
                                        colorResource(R.color.start_exercise4)
                                    )
                                )
                            )
                    ){
                        Text(text = "Start Exercise",
                            modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                .shadow(
                                    elevation = 30.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    clip = false,
                                    ambientColor = colorResource(R.color.green),
                                    spotColor = colorResource(R.color.green)
                                ),
                            fontSize = 24.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )
                        Row(
                            modifier = Modifier.padding(start = 24.dp, top = 16.dp) ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(text = "today's total distance" , fontWeight = FontWeight.Light , fontSize = 20.sp)
                            Icon(Icons.TwoTone.ArrowForward, contentDescription = "")
                            Text(text = totalDistance.value+"m", fontSize = 18.sp , overflow = TextOverflow.Visible , fontWeight = FontWeight.Light)
                        }
                        Button(
                            onClick = {
                                activityViewModel.selectedActivity = selectedActivity.value
                                navController.navigate("TimerScreen")
                                      },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp, top = 100.dp)
                                .padding(horizontal = 24.dp)
                                .height(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.start_exercise1),
                                contentColor = colorResource(R.color.black)

                            )
                        ) {
                            Text(text = "START ACTIVITY" , fontSize = 24.sp , fontWeight = FontWeight.Bold , fontFamily = FontFamily.Serif)
                        }

                    }
                }
            }

        }
    }
}


