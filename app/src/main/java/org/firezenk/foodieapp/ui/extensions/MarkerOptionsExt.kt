package org.firezenk.foodieapp.ui.extensions

import android.content.Context
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import org.firezenk.foodieapp.R
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.graphics.drawable.DrawableCompat
import android.os.Build
import android.support.v4.content.ContextCompat

fun MarkerOptions.setVectorIcon(context: Context, drawableId: Int) {
    var drawable = ContextCompat.getDrawable(context, drawableId)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable!!).mutate()
    }

    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    val iconFactory = IconFactory.getInstance(context)
    val icon = iconFactory.fromBitmap(bitmap)

    setIcon(icon)
}