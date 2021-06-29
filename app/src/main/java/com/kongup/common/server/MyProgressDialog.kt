package com.kongup.common.server

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.TypedValue
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.kongup.main.R


class MyProgressDialog(context: Context, resourceIdOfImage: Int) : Dialog(context, R.style.TransparentProgressDialog)
{
    lateinit var mAnimation: AnimationDrawable

    init {
        setTitle(null)
        setCanceledOnTouchOutside(false)
        setOnCancelListener(null)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val nDp = convertPixelsToDp(60f )

        val params = LinearLayout.LayoutParams(nDp, nDp)
        val mImg = ImageView(context)
        mImg.setImageResource(resourceIdOfImage)
        layout.addView(mImg, params)

        if (mImg.drawable != null)
            mAnimation = mImg.drawable as AnimationDrawable

        addContentView(layout, params)

        window?.clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND )
    }

    override fun show()
    {
        super.show()
        mAnimation.start()
    }

    fun convertPixelsToDp(px: Float): Int
    {
        val value = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics ).toInt()

        return value
    }

}