package com.example.redditappandroid.models

import java.io.Serializable

data class Post(val autor:String, val created_utc:Long, val thummbnail:String, val numberOfComments:Int, val name:String):Serializable

