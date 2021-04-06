package com.lomovskiy.lib.imagepicker.sample

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.lomovskiy.lib.imagepicker.PickImageFromGalleryContract
import com.lomovskiy.lib.ui.showToast
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

inline fun ActivityResultLauncher<*>.launch() {
    launch(null)
}

class MainActivity : AppCompatActivity() {

    private lateinit var buttonCamera: Button
    private lateinit var buttonGallery: Button

    private var uri: Uri? = null

    private val launcher = registerForActivityResult(PickImageFromGalleryContract) {
        lifecycleScope.launch {
            if (it != null) {
                val file = AppLoader.imagePicker.handleResult(it)
                showToast(file.absolutePath)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCamera = findViewById(R.id.button_camera)
        buttonGallery = findViewById(R.id.button_gallery)
        buttonCamera.setOnClickListener {
            val file = File("${cacheDir.path}${File.separator}${UUID.randomUUID()}.jpg${File.separator}")
            uri = FileProvider.getUriForFile(
                this,
                "${packageName}.imagepicker.fileprovider",
                file
            )
        }
        buttonGallery.setOnClickListener {
            launcher.launch()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}