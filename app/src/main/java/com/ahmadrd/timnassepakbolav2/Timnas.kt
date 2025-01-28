package com.ahmadrd.timnassepakbolav2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timnas(
    val name: String,
    val desc: String,
    val photo: String
): Parcelable
