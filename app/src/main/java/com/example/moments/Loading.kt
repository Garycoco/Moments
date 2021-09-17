package com.example.moments

import android.app.Activity
import android.app.AlertDialog

class Loading (val mActivity: Activity) {
    private lateinit var dialog : AlertDialog
    fun startLoading(){
        val builder = AlertDialog.Builder(mActivity)
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_dialogue,null)

        builder.setView(dialogView)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
    }
    fun dismiss(){
        dialog.dismiss()
    }
}