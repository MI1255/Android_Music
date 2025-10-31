package com.androidmusic.player.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androidmusic.player.data.Song
import com.androidmusic.player.data.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = SongRepository(application)
    
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs.asStateFlow()
    
    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    
    init {
        loadSongs()
    }
    
    private fun loadSongs() {
        viewModelScope.launch {
            try {
                val loadedSongs = repository.getAllSongs()
                _songs.value = loadedSongs
                if (loadedSongs.isNotEmpty() && _currentSong.value == null) {
                    _currentSong.value = loadedSongs.first()
                }
            } catch (e: Exception) {
                Log.e("MusicPlayerViewModel", "Error loading songs", e)
            }
        }
    }
    
    fun selectSong(song: Song) {
        _currentSong.value = song
        _currentPosition.value = 0L
    }
    
    fun playPause() {
        _isPlaying.value = !_isPlaying.value
    }
    
    fun pause() {
        _isPlaying.value = false
    }
    
    fun play() {
        _isPlaying.value = true
    }
    
    fun nextSong() {
        val currentIndex = _songs.value.indexOfFirst { it.id == _currentSong.value?.id }
        if (currentIndex != -1 && currentIndex < _songs.value.size - 1) {
            _currentSong.value = _songs.value[currentIndex + 1]
            _currentPosition.value = 0L
        }
    }
    
    fun previousSong() {
        val currentIndex = _songs.value.indexOfFirst { it.id == _currentSong.value?.id }
        if (currentIndex != -1 && currentIndex > 0) {
            _currentSong.value = _songs.value[currentIndex - 1]
            _currentPosition.value = 0L
        }
    }
    
    fun seekTo(position: Long) {
        _currentPosition.value = position
    }
    
    fun updatePosition(position: Long) {
        _currentPosition.value = position
    }
}
