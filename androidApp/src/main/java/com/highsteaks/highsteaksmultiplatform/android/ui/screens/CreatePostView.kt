package com.highsteaks.highsteaksmultiplatform.android.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.highsteaks.highsteaksmultiplatform.android.R
import com.highsteaks.highsteaksmultiplatform.android.ui.composables.DropDownList
import com.highsteaks.highsteaksmultiplatform.android.view_models.CreatePostViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun CreatePostView(
    navController: NavHostController,
    createPostViewModel: CreatePostViewModel = viewModel()
) {
    val context = LocalContext.current


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val images by createPostViewModel.uiState.collectAsState()
    val posted by createPostViewModel.postCreationState.collectAsState()

    fun createPost(){
        val inputData = context.contentResolver.openInputStream(createPostViewModel.uiState.value[0])?.readBytes()
        if (inputData != null) {
            createPostViewModel.createPost(title = title, byteArray =  inputData)
        }
    }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        if(uri != null)
            createPostViewModel.addPhoto(uri)
    }

    if(posted)
        navController.popBackStack()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberCoilPainter(
                    request = "http://proofof.dog/static/11cf6c18151cbb22c6a25d704ae7b313/9195b/doge-main.webp",
                    previewPlaceholder = R.drawable.ic_launcher_foreground
                ),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                text = "post.full_name"
            )
        }
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.padding(16.dp, 18.dp, 16.dp, 18.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.padding(16.dp, 18.dp, 16.dp, 18.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )
        DropDownList(
            list = arrayListOf("1", "2", "3"),
            request = {},
            modifier = Modifier.padding(16.dp, 18.dp, 16.dp, 18.dp)
        ) {
            category = it
        }

        LazyRow(
            modifier =
            Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            item {
                IconButton(
                    modifier = Modifier
                        .padding(16.dp, 18.dp, 16.dp, 18.dp)
                        .size(100.dp),
                    onClick = {
                        launcher.launch("image/*")
                    },

                    ) {
                    AddPhotoBox(modifier = Modifier.size(100.dp))
                }
            }
            items(images) { uri ->
                Image(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(16.dp, 18.dp, 16.dp, 18.dp)
                        .height(100.dp),
                    painter = rememberCoilPainter(
                        uri,
                        previewPlaceholder = R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = ""
                )
            }

        }
        Button(onClick = {
            createPost()
        }){
            Text("createPost")
        }
    }
}


@Composable
fun AddPhotoBox(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier,
        onDraw = {
            // Head
            drawRect(
                size = size,
                style = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                ),
                color = Color.Red
            )
            val ratio = 1f / 3f
            drawLine(
                start = Offset(x = size.width / 2, y = size.height * ratio),
                end = Offset(x = size.width / 2, y = size.height * (1 - ratio)),
                strokeWidth = 15f,
                color = Color.Red
            )
            drawLine(
                start = Offset(x = size.width * ratio, y = size.height / 2),
                end = Offset(x = size.width * (1 - ratio), y = size.height / 2),
                strokeWidth = 15F,
                color = Color.Red
            )
        }
    )
}