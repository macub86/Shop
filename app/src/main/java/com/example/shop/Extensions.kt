package com.example.shop

import android.app.Activity
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nickname.*

fun Activity.setNickname(nickname: String){
    getSharedPreferences("shop", Context.MODE_PRIVATE)
        .edit()
        .putString("NICKNAME",nickname)
        .apply()
}

fun Activity.getNickname(): String =
    getSharedPreferences("shop", Context.MODE_PRIVATE).getString("NICKNAME", "").toString()