package com.myapp.logistics.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.myapp.logistics.databinding.EnterPointDialogBinding

class EnterPointDialog(context: Context) : Dialog(context) {

    private var binding: EnterPointDialogBinding? = null

    override fun onStart() {
        super.onStart()
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        this.window?.setLayout(width, -2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EnterPointDialogBinding.inflate(layoutInflater)
        binding?.root?.let { setContentView(it) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }
}
