package com.example.composedemo.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import java.util.*
import androidx.compose.material.icons.filled.Close


import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

//API


//coil
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Share

import androidx.compose.material.icons.Icons
import com.example.composedemo.KPojo.KPlanedTripBean
import com.example.composedemo.R
import com.senses.composabletask.model.Post
import com.senses.composabletask.viewmodel.PostViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image = intent.getStringExtra("KEY_IMG") ?: "No Name"
        val title = intent.getStringExtra("KEY_TITLE") ?: "No Name"
        val dates = intent.getStringExtra("KEY_DATE") ?: "No Name"
        val duration = intent.getStringExtra("KEY_DURATION") ?: "No Name"
        val city = intent.getStringExtra("KEY_CITY") ?: "No Name"
        setContent {
            // TripPlannerScreen()
            DashboardScreen(image, title, dates, duration, city)
            MainContent()

            /*val apiService = ApiService.create()
            val repository = PostRepository(apiService)
            val viewModelFactory = viewModelFactory {
                initializer { PostViewModel(repository) }
            }
            val viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
            PostScreen1(viewModel)*/
        }
    }
}
//@Preview(showBackground = true)
@Composable
fun DashboardScreen(image: String, title: String, dates: String, duration: String, city: String) {
    var selectedCity by remember { mutableStateOf("Select City") }
    var startDate by remember { mutableStateOf("Enter Date") }
    var endDate by remember { mutableStateOf("Enter Date") }
    var selectedDate by remember { mutableStateOf("Select Date") }


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
                Spacer(modifier = Modifier.height(54.dp))
                //painter = painterResource(id = R.drawable.trip_back),
                Image(
                    painter = rememberAsyncImagePainter(image),

                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
            }


            item {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = ""+city+"|"+dates+"|"+duration,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            item{
                Column(Modifier.padding(4.dp)) {
                    Row(
                        modifier = Modifier.padding(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Handle Trip Collaboration */ },
                            border = BorderStroke(1.dp, Color.Blue),
                            /* colors = ButtonDefaults.outlinedButtonColors(
                                 contentColor = Color.Blue
                             ),*/
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Group, // Replace with correct icon
                                contentDescription = "Trip Collaboration",
                                tint = Color.Blue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text =  "Trip Collaboration",
                                fontSize = 10.sp)
                        }

                        OutlinedButton(
                            onClick = { /* Handle Share Trip */ },
                            border = BorderStroke(1.dp, Color.Blue),
                            /* colors = OutlinedButtonDefaults(
                                 contentColor = Color.Blue
                             ),*/
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share, // Replace with correct icon
                                contentDescription = "Share Trip",
                                tint = Color.Blue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text =  "Share Trip",
                                fontSize = 10.sp)
                        }


                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp) // <-- FIX: Remove the extra `{}` here
                    ) {
                        Column(
                            modifier = Modifier.padding(0.dp).background(color = Color(0xFF000031))
                        ) {

                            Text(
                                text =  "Activities",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)

                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Build, personalize, and optimize your itineraries with our trip planner.", fontSize = 12.sp ,color = Color.White)
                                // Text(text =  "Unknown Title", fontSize = 14.sp ,color = Color.White)
                            }
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue, // Background color
                                contentColor = Color.White  // Text color
                            ),
                                onClick = { /* Handle view trip */

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = "Add Activity")
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp) // <-- FIX: Remove the extra `{}` here
                    ) {
                        Column(
                            modifier = Modifier.padding(0.dp).background(color = Color(0xFFE7F0FF))
                        ) {

                            Text(
                                text =  "Hotels",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)

                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Build, personalize, and optimize your itineraries with our trip planner.", fontSize = 12.sp ,color = Color.Black)
                                // Text(text =  "Unknown Title", fontSize = 14.sp ,color = Color.White)
                            }
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue, // Background color
                                contentColor = Color.White  // Text color
                            ),
                                onClick = { /* Handle view trip */

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = "Add Hotel")
                            }
                        }
                    }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp) // <-- FIX: Remove the extra `{}` here
                    ) {
                        Column(
                            modifier = Modifier.padding(0.dp).background(color = Color(0xFF0D6EFD))
                        ) {

                            Text(
                                text =  "Flight",
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)

                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Build, personalize, and optimize your itineraries with our trip planner.", fontSize = 12.sp ,color = Color.Black)
                                // Text(text =  "Unknown Title", fontSize = 14.sp ,color = Color.White)
                            }
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // Background color
                                contentColor = Color.Blue  // Text color
                            ),
                                onClick = { /* Handle view trip */

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = "Add Flight")
                            }
                        }
                    }
                    Text(
                        text =  "Trip itineraries",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)

                    )
                    Text(
                        text =  "Your trip itineraries are placed here",
                        fontSize = 10.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start=8.dp)

                    )

                }
                val trip = KPlanedTripBean(
                    title = "The Museum of Modern Art",
                    date = "Works from Van Gogh to Warhol & beyond plus a sculpture garden, 2 cafes & The modern restaurant",
                    duration = "3 days"
                )
                TripItem(trip = trip)
            }

            /*items(tripList) { trip ->
                TripItem(trip = trip)
            }*/
        }

    }
}

@Composable
fun TripItem(trip: KPlanedTripBean) {
    val mContext = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp) // <-- FIX: Remove the extra `{}` here
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Activities",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = trip.title?: "The Museum of Modern Art",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = trip.date?: "Works from Van Gogh to Warhol & beyond plus a sculpture garden, 2 cafes & The modern restaurant", fontSize = 12.sp)
                // Text(text = trip.duration?: "Unknown Title", fontSize = 14.sp)
            }
            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .background(color = Color(0xFFFBEAE9))
            ) {
                Text(
                    text = "Remove",
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

//Date selection




@Composable
fun MainContent() {

    // Create a boolean variable
    // to store the display menu state
    var mDisplayMenu by remember { mutableStateOf(false) }

    // fetching local context
    val mContext = LocalContext.current

    // Creating a Top bar
    TopAppBar(
        title = { Text("Plan A Task", color = Color.White) } ,backgroundColor = Color(0xff0D6EFD),//#0D6EFD
        actions = {

            // Creating Icon button favorites, on click
            // would create a Toast message
            /* IconButton(onClick = { Toast.makeText(mContext, "Favorite", Toast.LENGTH_SHORT).show() }) {
                 Icon(Icons.Default.Favorite, "")
             }

             // Creating Icon button for dropdown menu
             IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                 Icon(Icons.Default.MoreVert, "")
             }*/

            // Creating a dropdown menu
            /*DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(onClick = { Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Settings")
                }

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(onClick = { Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Logout")
                }
            }*/
        }
    )
}



// Date Picker UI Component
@Composable
fun DatePickerComponent(label: String) {
    var selectedDate by remember { mutableStateOf("Enter Date") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            selectedDate = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(
        modifier = Modifier
            //.weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable { datePickerDialog.show() }
            .padding(10.dp),

        verticalAlignment = Alignment.CenterVertically
    )

    {
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(start = 10.dp, end = 10.dp)
        )
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = selectedDate, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}
@Composable
fun CustomDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        /* title = {
             Text(
                 text = "Confirmation",
                 fontSize = 20.sp,
                 fontWeight = FontWeight.Bold
             )
         },*/


        text = {
            Text(text = "Are you sure you want to proceed?")
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                // colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "No", color = Color.White)
            }
        }
    )
}


@Composable
fun FeedbackDialogs(onDismiss: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    Toast.makeText(context, "Trip created", Toast.LENGTH_SHORT).show()
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue, // Background color
                    contentColor = Color.White  // Text color
                )
            ) {
                Text(text = "Submit", fontSize = 16.sp, color = Color.White)
            }
        },
        /*dismissButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue, // Background color
                contentColor = Color.White  // Text color
            )
            ) {
                Text(text = "Cancel", fontSize = 16.sp, color = Color.White)
            }
        },*/
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Create Trip",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Trip Name", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Enter the Trip name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Travel Style", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                /* TextField(
                     value = mobileNumber,
                     onValueChange = { mobileNumber = it.take(10) },
                     placeholder = { Text("Enter mobile number") },
                     keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                     modifier = Modifier.fillMaxWidth()
                 )*/
                var mExpanded by remember { mutableStateOf(false) }
                // Create a list of cities
                val mCities = listOf("Solo", "Couple", "Family","Group")
                // Create a string value to store the selected city
                var mSelectedText by remember { mutableStateOf("") }
                var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
                // Up Icon when expanded and down icon when collapsed
                val icon = if (mExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown
                Column(Modifier.padding(2.dp)) {
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
                        label = {Text("Select your travel style")},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
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

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Description", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Enter description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
@Composable
fun PostScreen1(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()

    Scaffold(
        // topBar = { TopAppBar(title = { Text("Posts") }) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (posts.isEmpty()) {
                //CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn {
                    items(posts) { post ->

                        PostItem1(post)
                    }
                }
            }
        }
    }
}
@Composable
fun PostItem1(post: Post) {
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
            Text(text = post.title, style = androidx.compose.material.MaterialTheme.typography.h6, modifier = Modifier.padding(start = 10.dp) )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = post.date, style = androidx.compose.material.MaterialTheme.typography.body2)
                Text(text = post.duration, style = androidx.compose.material.MaterialTheme.typography.body2)
            }

        }
    }
}


// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent()
    //DashboardScreen()
    /*val apiService = ApiService.create()
    val repository = PostRepository(apiService)
    val viewModelFactory = viewModelFactory {
        initializer { PostViewModel(repository) }
    }
    val viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
    PostScreen(viewModel)*/
}
