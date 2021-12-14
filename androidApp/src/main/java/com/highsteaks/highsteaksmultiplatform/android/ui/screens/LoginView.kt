package com.highsteaks.highsteaksmultiplatform.android.ui.screens

import android.app.Application
import android.util.Log.d
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.highsteaks.highsteaksmultiplatform.android.R
import com.highsteaks.highsteaksmultiplatform.android.ui.navigation.Screen
import com.highsteaks.highsteaksmultiplatform.android.view_models.CreatePostViewModel
import com.highsteaks.highsteaksmultiplatform.android.view_models.LoginViewModel


@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun LoginView(navController: NavHostController,loginViewModel: LoginViewModel = viewModel()){
    var login by remember { mutableStateOf("thesandro1998@gmail.com") }
    var password by remember { mutableStateOf("123123") }
    val posted by loginViewModel.loginState.collectAsState()
    val context = LocalContext.current
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    fun navigateToPosts(){
        loginViewModel.authorize(login,password)
    }
    if(posted)
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.route)
        }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            painter = rememberImagePainter(
                data = "https://www.kickassfacts.com/wp-content/uploads/2021/04/bear.jpg",
                imageLoader = LocalImageLoader.current
            ),
            contentDescription = ""
        )
        TextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Email") },
            keyboardActions = KeyboardActions(
                onNext = { focusRequester.requestFocus() }
            ),
            singleLine = true,
            modifier = Modifier.padding(16.dp, 18.dp, 16.dp, 18.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .padding(16.dp, 18.dp, 16.dp, 18.dp).focusRequester(focusRequester),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)

        )
        Button(
            onClick = {
                navigateToPosts()
                Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
        ) {
            Text("Enter")
        }
    }
}