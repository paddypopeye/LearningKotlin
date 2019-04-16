package com.example.zooapp

class Animal {
    var name : String? = null
    var desc : String? = null
    var image : Int? = null
    var isKiller : Boolean


    constructor(name:String, desc: String, image:Int, isKiller: Boolean = false){
        this.name = name
        this.desc = desc
        this.image = image
        this.isKiller = isKiller
    }
}