package com.example.kotlinmangareader_46043zkn.Model

class Comic {
    var ID:Int=0
    var Name:String?=null
    var Image:String?=null

    constructor() {

    }
    constructor(ID:Int,name:String,image:String)
    {
        this.ID = ID
        this.Name=name
        this.Image = image
    }
}