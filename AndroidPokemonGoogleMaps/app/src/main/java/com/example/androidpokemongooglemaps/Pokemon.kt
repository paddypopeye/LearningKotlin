package com.example.androidpokemongooglemaps

import android.location.Location

class Pokemon {
    var name:String? = null
    var desc: String? = null
    var image: Int? = null
    var power: Double? = null
    var isCaught:Boolean? =null
    var location: Location? = null

       constructor(name:String,
                desc:String,
                image:Int,
                power:Double,
                lat:Double,
                long:Double)
    {
        this.name = name
        this.desc = desc
        this.image = image
        this.power = power
        this.location = Location(name)
        this.location!!.latitude = lat
        this.location!!.longitude = long
        this.isCaught=false
    }
}