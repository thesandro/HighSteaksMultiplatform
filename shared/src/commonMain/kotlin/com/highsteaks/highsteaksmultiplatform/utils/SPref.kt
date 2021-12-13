package com.highsteaks.highsteaksmultiplatform.utils

expect class SPref

expect fun SPref.getInt(key: String) : Int
expect fun SPref.setInt(key: String, value: Int)

expect fun SPref.getString(key: String) : String?
expect fun SPref.setString(key: String, value: String)

class KMMStorage(val context: SPref) {

    fun getInt(key: String): Int {
        return context.getInt(key)
    }

    fun putInt(key: String, value: Int) {
        context.setInt(key,value)
    }

    fun getString(key:String): String? {
        return context.getString(key)
    }

    fun putString(key:String, value:String) {
        context.setString(key,value)
    }
}