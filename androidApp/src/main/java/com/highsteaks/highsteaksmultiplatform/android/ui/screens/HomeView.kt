package com.highsteaks.highsteaksmultiplatform.android.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.highsteaks.highsteaksmultiplatform.android.R
import com.highsteaks.highsteaksmultiplatform.network.model.Post
import com.highsteaks.highsteaksmultiplatform.android.ui.composables.LoadingView
import com.highsteaks.highsteaksmultiplatform.android.ui.navigation.Screen
import com.highsteaks.highsteaksmultiplatform.android.view_models.PostsViewModel
import com.google.accompanist.coil.rememberCoilPainter


@ExperimentalCoilApi
@Composable
fun HomeView(
    navController: NavHostController,
    postsViewModel: PostsViewModel = viewModel()
) {
    val posts: LazyPagingItems<Post> = postsViewModel.posts.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier =
            Modifier
                .background(color = Color.Gray)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            item {
                FeedHeader(navController = navController)
            }
            items(posts) { post ->
                Post(post!!)
                posts.apply {
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            LoadingView(modifier = Modifier.fillParentMaxSize())
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun FeedHeader(navController: NavHostController) {

    fun navigateToCreatePost() {
        navController.navigate(Screen.CreatePost.route)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp, 2.dp, 10.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = "http://proofof.dog/static/11cf6c18151cbb22c6a25d704ae7b313/9195b/doge-main.webp",
            ),
            contentDescription = ""
        )

        Button(
            onClick = {
                navigateToCreatePost()
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(50)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Gray
            )
        )
        {
            Text(
                "Do you want to make a post?",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun Post(post: Post) {
    val configuration = LocalConfiguration.current
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
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(
                    data = "https://www.kickassfacts.com/wp-content/uploads/2021/04/bear.jpg",
                ),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                text = post.full_name
            )
        }
        Text(
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 5.dp),
            text = post.description
        )
        if (post.urls.isNotEmpty()) {
            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth(post.urls[0].width.toFloat())
                    .requiredHeight(with(LocalDensity.current) {
                        (
                                if (post.urls[0].width.toDp() >= configuration.screenWidthDp.dp)
                                    post.urls[0].height.toDp()*(configuration.screenWidthDp.dp/post.urls[0].width.toDp())
                                else post.urls[0].height.toDp())
                    })
                    .background(color = colorResource(R.color.post_color)),
                painter = rememberImagePainter(
                    data = post.urls[0].url
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }
    }
}