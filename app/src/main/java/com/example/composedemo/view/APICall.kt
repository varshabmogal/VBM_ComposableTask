package com.example.composedemo.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.senses.composabletask.interfaces.ApiService
import com.senses.composabletask.model.Post
import com.senses.composabletask.repository.PostRepository
import com.senses.composabletask.viewmodel.PostViewModel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
        //coil
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R


class APICall : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiService = ApiService.create()
        val repository = PostRepository(apiService)
        val viewModelFactory = viewModelFactory {
            initializer { PostViewModel(repository) }
        }
        setContent {
            val viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
            PostScreen(viewModel)
            MainContents()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()

    var selectedCity by remember { mutableStateOf("Select City") }
    var startDate by remember { mutableStateOf("Enter Date") }
    var endDate by remember { mutableStateOf("Enter Date") }
    var selectedDate by remember { mutableStateOf("Select Date") }

    Scaffold(
       // topBar = { TopAppBar(title = { Text("Posts") }) }
    ) { paddingValues ->
       // Box{
            if (posts.isEmpty()) {
                //CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFB3E5FC))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            Text(
                                text = "Plan Your Dream Trip in Minutes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 10.dp, // Padding on the left
                                    top = 60.dp
                                )
                            )
                            Text(
                                text = "Build,personalize and optimize your itineraries with our\n" +
                                        "trip planner.Perfect for getaways,remote work cations, and any spontaneous escapade",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .paint(
                                        // Replace with your image id
                                        painterResource(id = R.drawable.back_img),
                                        contentScale = ContentScale.FillBounds
                                    )

                            ) {
                                Spacer(modifier = Modifier.height(34.dp))
                                var showTripItem by remember { mutableStateOf(false) }
                                var showDialog by remember { mutableStateOf(false) }
                                androidx.compose.material3.Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 30.dp, start = 6.dp, end = 8.dp,),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // City Selection Row

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp)
                                                .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                                                .background(Color.Gray.copy(alpha = 0.1f)),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            androidx.compose.material3.Icon(
                                                painter = painterResource(id = R.drawable.location),
                                                contentDescription = null,
                                                modifier = Modifier.size(35.dp)
                                                    .padding(start = 10.dp)
                                            )
                                            Column(
                                                modifier = Modifier.padding(10.dp) // <-- FIX: Remove `{}` here
                                            ) {
                                                Text(
                                                    text = "Where to?",
                                                    fontSize = 13.sp,
                                                    color = Color.Gray
                                                )
                                                Text(
                                                    text = selectedCity,
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Date Selection Row
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            DatePickerComponent("Start Date")
                                            DatePickerComponent("End Date")
                                        }
                                        Spacer(modifier = Modifier.height(24.dp))
                                        // Create Trip Button
                                        androidx.compose.material3.Button(
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Blue, // Background color
                                                contentColor = Color.White  // Text color
                                            ),
                                            onClick = { /* Handle Trip Creation */
                                                // showTripItem = !showTripItem
                                                showDialog = true
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                            //,
                                            //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
                                        ) {

                                            Text(
                                                text = "Create a Trip",
                                                color = Color.White,
                                                fontSize = 16.sp
                                            )
                                        }
                                        if (showDialog) {
                                            CreateTripDialogs(onDismiss = { showDialog = false
                                            })

                                        }

                                    }
                                }

                            }
                        }

                        item {
                            Text(
                                text = "Your Trips",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = "Your trip itineraries and planned trips are placed here.",
                                fontSize = 13.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        //DropDown
                        item{
                            var mExpanded by remember { mutableStateOf(false) }

                            // Create a list of cities
                            val mCities = listOf("Family Trip", "Solo Trip", "Friends Trip")

                            // Create a string value to store the selected city
                            var mSelectedText by remember { mutableStateOf("") }

                            var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

                            // Up Icon when expanded and down icon when collapsed
                            val icon = if (mExpanded)
                                Icons.Filled.KeyboardArrowUp
                            else
                                Icons.Filled.KeyboardArrowDown

                            Column(Modifier.padding(8.dp)) {

                                // Create an Outlined Text Field
                                // with icon and not expanded
                                OutlinedTextField(
                                    value = mSelectedText,
                                    onValueChange = { mSelectedText = it },
                                    modifier = Modifier
                                        .fillMaxWidth()

                                        .onGloballyPositioned { coordinates ->
                                            // This value is used to assign to
                                            // the DropDown the same width
                                            mTextFieldSize = coordinates.size.toSize()
                                        },
                                    label = {Text("Planned Trips")},
                                    trailingIcon = {
                                        androidx.compose.material3.Icon(icon, "contentDescription",
                                            Modifier.clickable { mExpanded = !mExpanded })
                                    }
                                )

                                // Create a drop-down menu with list of cities,
                                // when clicked, set the Text Field text as the city selected
                                DropdownMenu(
                                    expanded = mExpanded,
                                    onDismissRequest = { mExpanded = false },
                                    modifier = Modifier
                                        .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                                ) {
                                    mCities.forEach { label ->
                                        DropdownMenuItem(onClick = {
                                            mSelectedText = label
                                            mExpanded = false
                                        }) {
                                            Text(text = label)
                                        }
                                    }
                                }
                            }
                        }

                        items(posts) { post ->
                            PostItem(post)
                        }
                    }
                }//ethe
            }
       // } Box
    }
}



@Composable
fun CityItem(
    cityName: String = "Niphad",
    cityDescription: String = "Near about Godavari"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(MaterialTheme.colors.surface, RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_location_on_24),
            contentDescription = "Location Icon",
            tint = Color.Gray,
            modifier = Modifier
                .size(22.dp)
                .padding(end = 6.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cityName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = cityDescription,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.baseline_location_on_24),
            contentDescription = "Country Icon",
            tint = Color.Gray,
            modifier = Modifier
                .size(30.dp)
                .padding(4.dp)
        )
    }
}


@Composable
fun PostItem(post: Post) {
    val mContext = LocalContext.current
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp) // <-- FIX: Remove the extra `{}` here
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            /*AsyncImage(
                model = ImageRequest.Builder(mContext)
                    .data(post.img)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image from URL",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )*/


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust height as needed
            ) {
                // Coil Image
                Image(
                    painter = rememberAsyncImagePainter(post.img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top-Right Text
                Text(
                    text = post.city,
                    color = Color.White, // Adjust text color
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Aligns text to the top-right
                        .padding(8.dp) // Adds padding from the edges
                        .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp)) // Optional background
                        .padding(horizontal = 6.dp, vertical = 4.dp) // Padding inside the background
                )
            }


               // modifier = Modifier.size(100.dp)
            //)
            /*.transformations(CircleCropTransformation()) // Example transformation
            placeholder = placeholder,
            error = error,*/
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.title, style = MaterialTheme.typography.h6, modifier = Modifier.padding(start = 10.dp) )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = post.date, style = MaterialTheme.typography.body2)
                Text(text = post.duration, style = MaterialTheme.typography.body2)
            }
            androidx.compose.material3.Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue, // Background color
                    contentColor = Color.White  // Text color
                ),
                onClick = { /* Handle view trip */
                    //mContext.startActivity(Intent(mContext, DashboardActivity::class.java))
                    val intent = Intent(mContext, DashboardActivity::class.java).apply {
                        // Pass data using putExtra
                        putExtra("KEY_IMG", post.img)
                        putExtra("KEY_CITY", post.city)
                        putExtra("KEY_TITLE", post.title)
                        putExtra("KEY_DATE", post.date)
                        putExtra("KEY_DURATION", post.duration)
                    }
                    mContext.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "View")
            }

        }
    }
}
@Composable
fun MainContents() {
    // Create a boolean variable
    // to store the display menu state
    var mDisplayMenu by remember { mutableStateOf(false) }
    // fetching local context
    val mContext = LocalContext.current
    // Creating a Top bar
    TopAppBar(
        title = { Text("APPSQR_TaskVBM", color = Color.White) } ,backgroundColor = Color(0xff0D6EFD),//#0D6EFD
        actions = {
        }
    )
}



