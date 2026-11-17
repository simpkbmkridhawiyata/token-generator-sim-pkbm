package id.kridhawiyata.tokengenerator

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    private val tokenGeneratorUrl =
        "https://script.google.com/macros/s/AKfycbzT7fgLGO7DPG3JgEZNE1q1r97XYh_xZCMbJvIgzcrLVHVD1KzWcXsz4-H63rEITJ26TA/exec"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(tokenGeneratorUrl)
        ).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }

        try {
            startActivity(browserIntent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Browser tidak ditemukan. Silakan instal atau aktifkan Google Chrome.",
                Toast.LENGTH_LONG
            ).show()
        }

        finish()
    }
}
