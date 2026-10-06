package com.example.productserviceconsumer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Product(
    val id: Int,
    val name: String
) : Parcelable