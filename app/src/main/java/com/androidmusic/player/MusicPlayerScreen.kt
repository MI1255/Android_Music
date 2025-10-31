package com.androidmusic.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidmusic.player.ui.theme.AccentGreen
import com.androidmusic.player.ui.theme.AccentPurple
import com.androidmusic.player.ui.theme.ExpressivePrimary
import com.androidmusic.player.viewmodel.MusicPlayerViewModel
import androidx.media3.common.MediaItem
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
    var selectedCategory by remember { mutableStateOf(0) }
    
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
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Profile
            ProfileHeader()
            
            // Categories
            CategoryTabs(selectedCategory = selectedCategory) { selectedCategory = it }
            
            // Content
            if (songs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyState()
                }
            } else {
                ContentSection(
                    songs = songs,
                    currentSong = currentSong,
                    onSongClick = { viewModel.selectSong(it) }
                )
            }
        }
        
        // Mini Player
        if (currentSong != null) {
            MiniPlayer(
                song = currentSong!!,
                isPlaying = isPlaying,
                onPlayPauseClick = { viewModel.playPause() },
                onNextClick = { viewModel.nextSong() },
                onPreviousClick = { viewModel.previousSong() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        } else {
            // Bottom Navigation (when no song playing)
            BottomNavBar(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
                
                // Greeting
                Column {
                    Text(
                        text = "Hi, Samantha",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                }
            }
            
            // Action Icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { /* TODO: Search */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* TODO: Favorites */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorites",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryTabs(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    val categories = listOf("All", "New Release", "Trending", "Top")
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEachIndexed { index, category ->
            FilterChip(
                selected = selectedCategory == index,
                onClick = { onCategorySelected(index) },
                label = { Text(category) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AccentGreen,
                    containerColor = Color(0x1FFFFFFF)
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun ContentSection(
    songs: List<com.androidmusic.player.data.Song>,
    currentSong: com.androidmusic.player.data.Song?,
    onSongClick: (com.androidmusic.player.data.Song) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 100.dp)
    ) {
        item {
            FeaturedCard()
            Spacer(modifier = Modifier.height(32.dp))
            PlaylistsHeader()
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        items(songs) { song ->
            PlaylistItem(
                song = song,
                songsCount = songs.size,
                isPlaying = song.id == currentSong?.id,
                onClick = { onSongClick(song) }
            )
        }
    }
}

@Composable
fun FeaturedCard() {
    Text(
        text = "Curated & trending",
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold
        ),
        color = Color.White,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        AccentPurple,
                        Color(0xFF673AB7)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Discover weekly",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The original slow instrumental best playlists.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilledIconButton(
                    onClick = { /* TODO */ },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = AccentPurple
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Top daily playlists",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.clickable { /* TODO */ }
        )
    }
}

@Composable
fun PlaylistsList(
    songs: List<com.androidmusic.player.data.Song>,
    currentSong: com.androidmusic.player.data.Song?,
    onSongClick: (com.androidmusic.player.data.Song) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(songs) { song ->
            PlaylistItem(
                song = song,
                songsCount = songs.size,
                isPlaying = song.id == currentSong?.id,
                onClick = { onSongClick(song) }
            )
        }
    }
}

@Composable
fun PlaylistItem(
    song: com.androidmusic.player.data.Song,
    songsCount: Int,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cover
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B),
                            Color(0xFF4ECDC4)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Playlist",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        
        // Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "By ${song.artist} • ${songsCount} Songs",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        
        // Play Button
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White
            )
        }
    }
}

@Composable
fun EmptyState() {
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
}

@Composable
fun MiniPlayer(
    song: com.androidmusic.player.data.Song,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 8.dp
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

@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(0) }
    
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = AccentGreen
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            icon = { Icon(Icons.Default.Folder, contentDescription = "Folders") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = AccentGreen
            )
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { selectedTab = 2 },
            icon = { Icon(Icons.Default.Refresh, contentDescription = "Sync") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = AccentGreen
            )
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { selectedTab = 3 },
            icon = { Icon(Icons.Default.List, contentDescription = "Lists") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = AccentGreen
            )
        )
        NavigationBarItem(
            selected = selectedTab == 4,
            onClick = { selectedTab = 4 },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = AccentGreen
            )
        )
    }
}

fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}