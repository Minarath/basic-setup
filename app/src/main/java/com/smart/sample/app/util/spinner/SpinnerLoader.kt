package com.smart.sample.app.util.spinner

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout.LayoutParams
import com.smart.sample.app.R


class SpinnerLoader(context: Context) : Dialog(context) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.loader, null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

}
