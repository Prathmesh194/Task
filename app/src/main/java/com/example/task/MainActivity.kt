package com.example.task

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener {
            val firstName = inputFirstName.text.toString()

            saveFireStore(firstName)

        }

        readFireStoreData()
    }



    private fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .get()
                .addOnCompleteListener {

                    val result: StringBuffer = StringBuffer()

                    if(it.isSuccessful) {
                        for(document in it.result!!) {
                            result.append(document.data.getValue("firstName")).append("\n\n")

                        }
                        textViewResult.setText(result)
                    }
                }
    }

    private fun saveFireStore(firstName: String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["firstName"] = firstName
        

        db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this@MainActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
                }

    }


}