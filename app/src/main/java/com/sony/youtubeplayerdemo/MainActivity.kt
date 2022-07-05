package com.sony.youtubeplayerdemo

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


/**
 * Loads [MainFragment].
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Delayed removal of status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            findViewById<View>(R.id.youtube_player_holder).windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            findViewById<View>(R.id.youtube_player_holder).systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        findViewById<View>(R.id.youtube_player_overlay).setOnClickListener {
            handlePlayPause()
        }
    }

    fun handlePlayPause() {
        Toast.makeText(this, "Play/Pause", Toast.LENGTH_SHORT).show()
        val youTubePlayer = findViewById<YouTubePlayer>(R.id.youtube_player)
        when(youTubePlayer.currentPlayerState) {
            1 -> {
                youTubePlayer.loadUrl("javascript:pauseVideo();")
            }
            2 -> {
                youTubePlayer.loadUrl("javascript:playVideo();")
            }
        }
    }

    override fun onStart() {
        super.onStart()

        findViewById<View>(R.id.youtube_player_overlay).requestFocus()
    }

    /**
     * Overridden to notify fragments of key presses.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when(keyCode) {
            KeyEvent.KEYCODE_PROG_BLUE -> onBlueButtonPressed()
            KeyEvent.KEYCODE_DPAD_CENTER -> onDpadCentreButtonPressed()
            KeyEvent.KEYCODE_DPAD_UP -> onDpadUpButtonPressed()
            KeyEvent.KEYCODE_DPAD_LEFT -> onDpadLeftButtonPressed()
            KeyEvent.KEYCODE_DPAD_RIGHT -> onDpadRightButtonPressed()
            KeyEvent.KEYCODE_DPAD_DOWN -> onDpadDownButtonPressed()
            KeyEvent.KEYCODE_BACK -> onBackButtonPressed()
            else -> super.onKeyDown(keyCode, event)
        }
    }

    /**
     * Handle BLUE button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onBlueButtonPressed(): Boolean {
        return false
    }

    /**
     * Handle Dpad centre button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onDpadCentreButtonPressed(): Boolean {
        handlePlayPause()
        return true
    }

    /**
     * Handle Dpad up button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onDpadUpButtonPressed(): Boolean {
        return false
    }

    /**
     * Handle Dpad left button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onDpadLeftButtonPressed(): Boolean {
        return false
    }

    /**
     * Handle Dpad right button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onDpadRightButtonPressed(): Boolean {
        return false
    }

    /**
     * Handle Dpad down button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onDpadDownButtonPressed(): Boolean {
        return false
    }

    /**
     * Handle Back Button press.
     * To be overridden by subclasses performing operations when key pressed.
     * @return Key press not handled.
     */
    open fun onBackButtonPressed(): Boolean {
        return false
    }
}