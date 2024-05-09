package com.example.reproductormusica

import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.reproductormusica.AppConstant.Companion.CURRENT_SONG_INDEX
import com.example.reproductormusica.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.reproductormusica.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.reproductormusica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0
    private lateinit var currentSong: Song
    private var currentSongIndex: Int = 0
    private var isPlaying: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(LOG_MAIN_ACTIVITY, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentSong = AppConstant.songs[currentSongIndex]

        savedInstanceState?.let{
            position = it.getInt(MEDIA_PLAYER_POSITION)
        }

        updateUiSong()

        binding.playPauseButton.setOnClickListener { playOrPauseMusic() }
        binding.playNextButton.setOnClickListener { playNextSong() }
        binding.playPreviousButton.setOnClickListener { playPreviousSong() }


    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_MAIN_ACTIVITY,"onStart()")
        mediaPlayer = MediaPlayer.create(this,currentSong.audioResId)
        if(isPlaying) mediaPlayer?.start()

    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY,"onResume()")
        updateUiSong()
        mediaPlayer?.seekTo(position)
        if(isPlaying) {
            isPlaying = true
            mediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_MAIN_ACTIVITY,"onPause()")
        if(mediaPlayer != null)
            position = mediaPlayer!!.currentPosition

        mediaPlayer?.pause()

    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY,"onStop()")
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false

    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY,"onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY,"onDestroy()")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MEDIA_PLAYER_POSITION,position)
        //outState.putInt(CURRENT_SONG_INDEX,position)
    }

    /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        position = savedInstanceState.getInt(MEDIA_PLAYER_POSITION)
        currentSongIndex = savedInstanceState.getInt(CURRENT_SONG_INDEX)
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.seekTo(position)
        mediaPlayer?.start()

    }*/

    /**
     * CICLO DE VIDA
     */

    private fun updateUiSong(){
        binding.titleTextView.text = currentSong.title
        binding.albumCoverImageView.setImageResource(currentSong.imageResId)
        updatePlayPause()

    }

    private fun playOrPauseMusic(){
        if(isPlaying){
            mediaPlayer?.pause()
        }else{
            mediaPlayer?.start()
        }

        isPlaying = !isPlaying
        updatePlayPause()

    }

    private fun updatePlayPause(){
        binding.playPauseButton.text = if (isPlaying) "Pause" else "Play"
    }

    private fun playNextSong(){
        currentSongIndex = (currentSongIndex + 1) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this,currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUiSong()
    }

    private fun playPreviousSong() {
        currentSongIndex = (currentSongIndex - 1 + AppConstant.songs.size) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUiSong()
    }


}