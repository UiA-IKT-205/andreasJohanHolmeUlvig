package com.example.superpiano

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.*
import com.example.superpiano.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    private val TAG:String = "superpiano:MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth

    private lateinit var piano:Piano




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()

        piano = supportFragmentManager.findFragmentById(binding.piano.id) as Piano

        piano.onSave = {
            this.upload(it)
        }

    }

    private fun upload(file:Uri){
        Log.d(TAG, "Upload file $file")

        val ref = FirebaseStorage.getInstance().reference.child("melodies/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file to Firebase ${it.toString()}")
        }.addOnFailureListener{
            Log.d(TAG, "Error saving file to Firebase", it)
        }

    }

    private fun signInAnonymously(){

        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login Success ${it.user?.toString()}")
        }.addOnFailureListener {
            Log.d(TAG, "Login failed", it)
        }
    }
}