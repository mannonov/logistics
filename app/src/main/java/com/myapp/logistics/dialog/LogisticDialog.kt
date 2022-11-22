package com.myapp.logistics.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.myapp.logistics.databinding.LogisticsDialogBinding
import com.myapp.logistics.util.onClick

class LogisticDialog(context: Context, private val text: String) : Dialog(context) {

    private lateinit var binding: LogisticsDialogBinding
    private lateinit var yesClickListener: YesClickListener

    override fun onStart() {
        super.onStart()
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        this.window?.setLayout(width, -2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LogisticsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvContentText.text = text
        binding.btnNo.onClick {
            dismiss()
        }
        binding.btnYes.onClick {
            if (this@LogisticDialog::yesClickListener.isInitialized) {
                yesClickListener.onYes()
            }
        }
    }

    private fun setYesClickListener(yesClickListener: YesClickListener) {
        this.yesClickListener = yesClickListener
    }

    class YesClickListener(val onYes: () -> Unit)
}
