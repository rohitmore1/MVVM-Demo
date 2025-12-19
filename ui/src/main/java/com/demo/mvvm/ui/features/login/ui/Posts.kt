package com.demo.mvvm.ui.features.login.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.demo.domain.model.PostItem
import com.demo.mvvm.ui.features.login.viewModel.PostsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RenderPostsScreen(
    navController: NavController,
    postsViewModel: PostsViewModel
) {
    val postList by postsViewModel.posts

    postList?.let { posts ->
        PostsScreen(posts = posts)
    } ?: run {
        EmptyPostsView()
    }
}

@Composable
fun EmptyPostsView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No posts available",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun PostsScreen(
    posts: List<PostItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = posts,
            key = { it.id ?: "" }
        ) { post ->
            PostItemView(post)
        }
    }
}


@Composable
fun PostItemView(post: PostItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = post.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Post #${post.id.orEmpty()} • User ${post.userId.orEmpty()}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    val samplePosts = listOf(
        PostItem(
            userId = "1",
            id = "1",
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        ),
        PostItem(
            userId = "1",
            id = "2",
            title = "qui est esse",
            body = "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        ),
        PostItem(
            userId = "1",
            id = "3",
            title = "ea molestias quasi exercitationem repellat qui ipsa sit aut",
            body = "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
        )
    )
    MaterialTheme {
        PostsScreen(posts = samplePosts)
    }
}
