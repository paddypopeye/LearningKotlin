package com.example.zooapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_animal_info.*

class AnimalInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_info)

        val bundle:Bundle? = intent.extras
        val name = bundle?.getString("name")
        val desc = bundle?.getString("desc")
        val image = bundle?.getInt("image")
        ivName.setImageResource(image!!)
        txtDesc.text = desc
        txtTitle.text = name
    }
}
