package com.mifdev.mathgame

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var addition : Button
    private lateinit var subtraction : Button
    private lateinit var multiplication : Button
    private lateinit var distribution : Button

    // Untuk Suara Klik
    private var soundPool: SoundPool? = null
    private var clickSoundId: Int = 0

    // Untuk Musik Latar
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisasi SoundPool untuk suara klik
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        // ID suara - pastikan file button_click.mp3 ada di res/raw
        clickSoundId = soundPool?.load(this, R.raw.button_click, 1) ?: 0

        // Inisialisasi MediaPlayer untuk musik latar - pastikan background_music.wav ada di res/raw
        // Gunakan try-catch agar aplikasi tidak crash jika file tidak terbaca
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
            mediaPlayer?.isLooping = true // Musik berulang
        } catch (e: Exception) {
            e.printStackTrace()
        }

        addition = findViewById(R.id.buttonAdd)
        subtraction = findViewById(R.id.buttonSub)
        multiplication = findViewById(R.id.buttonMulti)
        distribution = findViewById(R.id.buttonDistri)

        addition.setOnClickListener {
            playClickSound()
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }

        subtraction.setOnClickListener {
            playClickSound()
            val intent = Intent(this@MainActivity, GameActivity2::class.java)
            startActivity(intent)
        }

        multiplication.setOnClickListener {
            playClickSound()
            val intent = Intent(this@MainActivity, GameActivity3::class.java)
            startActivity(intent)
        }

        distribution.setOnClickListener {
            playClickSound()
            val intent = Intent(this@MainActivity, GameActivity4::class.java)
            startActivity(intent)
        }
    }

    private fun playClickSound() {
        if (clickSoundId != 0) {
            soundPool?.play(clickSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    override fun onResume() {
        super.onResume()
        // Mulai musik saat aplikasi dibuka/kembali
        try {
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        // Jeda musik saat aplikasi di latar belakang
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Lepaskan sumber daya saat aplikasi ditutup
        soundPool?.release()
        soundPool = null
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}