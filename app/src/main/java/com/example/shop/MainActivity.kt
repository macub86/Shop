package com.example.shop


import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nickname.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.row_function.view.*
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {

    private val RC_NICKNAME=210
    private val RC_SIGNUP:Int=200
    var signup=false
    val auth=FirebaseAuth.getInstance()
    val functions= listOf<String>("Camera","Invite friend","Parking","Download coupons",
        "News", "Maps")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (!signup) {
//            val intent=Intent(this, SignUpActivity::class.java)
//            startActivityForResult(intent, RC_SIGNUP)
//        }
        auth.addAuthStateListener { auth ->
            authChanged(auth)
        }
        //Spinner
        val colors= arrayOf("Red","Green","Blue")
        val adapter=ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            Log.d("MainActivity","onItemSelected: ${colors[position]}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //RecyclerView
        recycler.layoutManager=LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter=FunctionAdapter()
    }

    inner class FunctionAdapter():RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view:View =LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function,parent,false)
            val holder=FunctionHolder(view)
            return holder
        }
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text=functions.get(position)
        }
        override fun getItemCount(): Int {
            return functions.size
        }
    }

    class FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        var nameText: TextView =view.name
    }

    override fun onResume() {
        super.onResume()
//        nickname.text=getNickname()
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(auth.currentUser!!.uid)
                .child("nickname")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        nickname.text = dataSnapshot.value as String
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
    }

    private fun authChanged(auth: FirebaseAuth) {
        if (auth.currentUser == null) {
            val intent=Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, RC_SIGNUP)
        }else{
            Log.d("MainActivity", "authChanged: ${auth.currentUser?.uid}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==RC_SIGNUP){
            if (resultCode==Activity.RESULT_OK) {
                val intent=Intent(this, NicknameActivity::class.java)
                startActivityForResult(intent,RC_NICKNAME)
            }
        }
        if (requestCode==RC_NICKNAME){

        }
    }
}

