package com.androidmusic.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidmusic.player.ui.theme.AndroidMusicTheme
import com.androidmusic.player.viewmodel.MusicPlayerViewModel

@Composable
fun MusicPlayerApp(
    viewModel: MusicPlayerViewModel = viewModel()
) {
    AndroidMusicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MusicPlayerScreen(viewModel = viewModel)
        }
    }
}
