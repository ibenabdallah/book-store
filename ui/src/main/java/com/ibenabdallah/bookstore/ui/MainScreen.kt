package com.ibenabdallah.bookstore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.ibenabdallah.bookstore.domain.model.BookModel
import com.ibenabdallah.bookstore.ui.theme.BookStoreTheme

@Composable
fun AppScreen() {

    val viewModel = hiltViewModel<DocViewModel>()

    val state = remember { viewModel.getAll() }.collectAsLazyPagingItems()

    UIStateView(state.loadState.refresh) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
        ) {
            items(state.itemCount) { index ->
                state[index]?.let {
                    BookItem(item = it) {

                    }
                }
            }
        }
    }

}

@Composable
fun BookItem(item: BookModel, onClick: (BookModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
        ) {

            item.cover?.let {
                Image(
                    painter = rememberAsyncImagePainter(item.cover),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "PlaceHolder"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = item.titleSort,
                    style = TextStyle(
                        fontSize = 10.85.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFFDEDEDE),
                    )
                )

                Text(
                    text = item.title,
                    maxLines = 2,
                    style = TextStyle(
                        fontSize = 15.2.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                    )
                )

                Text(
                    text = item.authorName.joinToString(", "),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 10.85.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}

@Composable
fun UIStateView(
    state: LoadState,
    content: @Composable (LoadState) -> Unit,
) {
    when (state) {
        is LoadState.Error -> UIFailure(state)

        is LoadState.Loading -> UILoading()

        is LoadState.NotLoading -> content(state)
    }
}

@Composable
private fun UILoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun UIFailure(failure: LoadState.Error) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = failure.error.message.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookStoreTheme {
        AppScreen()
    }
}