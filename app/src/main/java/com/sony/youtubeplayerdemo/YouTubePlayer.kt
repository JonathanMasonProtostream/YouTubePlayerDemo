package com.sony.youtubeplayerdemo

import android.content.Context
import android.content.res.AssetManager
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Base64
import android.view.MotionEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView


/**
 *
 */
class YouTubePlayer(context: Context, attrs: AttributeSet): WebView(context, attrs) {
    var currentPlayerState = -1

    init {
        clearCache(true)
        settings.setAppCacheEnabled (true)
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.mediaPlaybackRequiresUserGesture = false

        val youTubeWebInterface = object {

            @JavascriptInterface
            fun youTubePlaybackFinished() {
                mainHandler.post(myRunnable)
            }

            @JavascriptInterface
            fun youTubePlayerStateChange(playerState: Int) {
                currentPlayerState = playerState
            }

            @JavascriptInterface
            fun getVideoId(): String {
                //return "M7lc1UVf-VE"
                return "-2qpKUExOnE"
            }
        }

        addJavascriptInterface(youTubeWebInterface, "Android")
        loadUrl("file:///android_asset/youtube_player.html")

        val inputStream = context.assets.open("styles.css")
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        val encoded = Base64.encodeToString(buffer , Base64.NO_WRAP)
        loadUrl(
            "javascript:(function() {" +
                    "var f = document.getElementById('player');" +
                    "console.log(f);" +
                    "var parent = f.contentWindow.document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('LyogSGlkZSBtb3JlIHZpZGVvcyBwYW5lbC4gKi8KLnl0cC1wYXVzZS1vdmVybGF5IHsKICBkaXNwbGF5OiBub25lICFpbXBvcnRhbnQ7Cn0=');" +
                    "parent.appendChild(style);" +
                    "})"
        )
    }

    var mainHandler = Handler(Looper.getMainLooper())

    val myRunnable = Runnable {
        visibility = GONE
        clearCache(true)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}