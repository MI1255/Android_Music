package com.androidmusic.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidmusic.player.ui.theme.ExpressivePrimary
import com.androidmusic.player.viewmodel.MusicPlayerViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun MusicPlayerScreen(
    viewModel: MusicPlayerViewModel = viewModel()
) {
    val context = LocalContext.current
    val songs by viewModel.songs.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }
    
    LaunchedEffect(currentSong) {
        if (currentSong != null) {
            exoPlayer?.release()
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(currentSong!!.path)
                setMediaItem(mediaItem)
                prepare()
            }
        }
    }
    
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer?.play()
        } else {
            exoPlayer?.pause()
        }
    }
    
    LaunchedEffect(exoPlayer) {
        exoPlayer?.let { player ->
            while (player.isPlaying) {
                viewModel.updatePosition(player.currentPosition)
                kotlinx.coroutines.delay(100)
            }
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer?.release()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        MusicPlayerHeader()
        
        // Songs List
        if (songs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No songs found",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(songs) { song ->
                    SongItem(
                        song = song,
                        isSelected = song.id == currentSong?.id,
                        onClick = { viewModel.selectSong(song) }
                    )
                }
            }
        }
        
        // Mini Player
        if (currentSong != null) {
            MiniPlayer(
                song = currentSong!!,
                isPlaying = isPlaying,
                onPlayPauseClick = { viewModel.playPause() },
                onNextClick = { viewModel.nextSong() },
                onPreviousClick = { viewModel.previousSong() }
            )
        }
    }
}

@Composable
fun MusicPlayerHeader() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = ExpressivePrimary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Text(
            text = "Music Player",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun SongItem(
    song: com.androidmusic.player.data.Song,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = formatDuration(song.duration),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun MiniPlayer(
    song: com.androidmusic.player.data.Song,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousClick) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = "Previous"
                    )
                }
                
                FilledIconButton(
                    onClick = onPlayPauseClick,
                    modifier = Modifier.size(64.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = ExpressivePrimary
                    )
                ) {
                    Icon(
                        imageVector = if (isPlaying) 
                            Icons.Default.Pause 
                        else 
                            Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }
                
                IconButton(onClick = onNextClick) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = "Next"
                    )
                }
            }
        }
    }
}

fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}
