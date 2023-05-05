package com.year4.dnd.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DndModel(var id: Long = 0, var image: Uri = Uri.EMPTY, var title: String = "", var description: String = "", var age: String = "", var abilities: String = "") : Parcelable

