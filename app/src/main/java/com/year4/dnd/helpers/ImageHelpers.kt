package com.year4.dnd.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.year4.dnd.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_character_image.toString())
    intentLauncher.launch(chooseFile)
}