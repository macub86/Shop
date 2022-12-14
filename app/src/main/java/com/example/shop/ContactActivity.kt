package com.example.shop

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.*
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.*

class ContactActivity : AppCompatActivity() {
    private val RC_CONTACTS: Int=110
    private val TAG: String= ContactActivity::class.java.simpleName
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val permission:Int=
            ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
        if (permission!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            RC_CONTACTS)
        }else{
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==RC_CONTACTS){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readContacts()
            }
        }
    }

    @SuppressLint("Range", "SuspiciousIndentation")
    private fun readContacts() {
        val cursor: Cursor? = contentResolver.query(
            Contacts.CONTENT_URI,
            null, null, null, null
        )
        while (cursor?.moveToNext() == true) {
            val name: String = cursor.getString(
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        Log.d(TAG, "onCreate: $name")
        }
    }
}