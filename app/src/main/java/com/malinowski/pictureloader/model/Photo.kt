package com.malinowski.pictureloader.model

import android.graphics.Bitmap

data class Photo(val title:String, val url:String, var image:Bitmap? = null)