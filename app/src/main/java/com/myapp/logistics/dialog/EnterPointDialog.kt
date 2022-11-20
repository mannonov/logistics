package com.myapp.logistics.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.myapp.logistics.databinding.EnterPointDialogBinding
import com.myapp.logistics.model.Point
import com.myapp.logistics.util.onClick

class EnterPointDialog(context: Context) : Dialog(context) {

    private var binding: EnterPointDialogBinding? = null
    private lateinit var addOnPointListener: PointListener

    override fun onStart() {
        super.onStart()
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        this.window?.setLayout(width, -2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EnterPointDialogBinding.inflate(layoutInflater)
        binding?.root?.let { setContentView(it) }
        binding?.btnLoad?.onClick {
            if (this@EnterPointDialog::addOnPointListener.isInitialized) {
                val point = Point()
                binding?.apply {
                    point.lat = tiePointLat.text.toString().toDouble()
                    point.lng = tiePointLng.text.toString().toDouble()
                    point.address = tiePointName.text.toString()
                }
                addOnPointListener.onAdd(point)
                dismiss()
            }
        }
    }

    fun setAddOnPointListener(addOnPointListener: PointListener) {
        this.addOnPointListener = addOnPointListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    class PointListener(val onAdd: (point: Point) -> Unit)
}
