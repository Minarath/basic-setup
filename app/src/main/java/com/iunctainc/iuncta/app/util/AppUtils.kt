package com.iunctainc.iuncta.app.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.provider.Settings
import android.text.TextPaint
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.iunctainc.iuncta.app.BuildConfig
import com.iunctainc.iuncta.app.data.beans.Constants
import java.text.CharacterIterator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object AppUtils {
    fun replaceFragment(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {
        val transaction = manager.beginTransaction()
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.replace(frameId, fragment, fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    fun addFragment(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {
        val transaction = manager.beginTransaction()
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.add(frameId, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }

    fun popFrag(manager: FragmentManager, fragment: Fragment, frameId: Int = 0) {
        for (i in 0 until manager.backStackEntryCount) {
            manager.popBackStack()
        }
        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }


    fun setTypefaceToInputLayout(inputLayout: TextInputLayout, typeFace: Typeface?) {
        try {
            inputLayout.editText!!.typeface = typeFace

            // Retrieve the CollapsingTextHelper Field
            val collapsingTextHelperField =
                inputLayout.javaClass.getDeclaredField("mCollapsingTextHelper")
            collapsingTextHelperField.isAccessible = true

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            val collapsingTextHelper = collapsingTextHelperField[inputLayout]
            val tpf = collapsingTextHelper.javaClass.getDeclaredField("mTextPaint")
            tpf.isAccessible = true

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            (tpf[collapsingTextHelper] as TextPaint).typeface = typeFace
        } catch (ignored: Exception) {
            // Nothing to do
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                activity.currentFocus,
                InputMethodManager.SHOW_IMPLICIT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTimeFromUtc(l: Long): String {
        return if (l == 0L) "" else try {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(l))
        } catch (e: Exception) {
            ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun localToUTC(dateFormat: String, datesToConvert: String): String {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormat)
        sdf.timeZone = TimeZone.getDefault()
        val gmt: Date
        val sdfOutPutToSend = SimpleDateFormat(dateFormat)
        sdfOutPutToSend.timeZone = TimeZone.getTimeZone("UTC")
        try {
            gmt = sdf.parse(datesToConvert)!!
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }

    @SuppressLint("SimpleDateFormat")
    fun uTCToLocal(
        dateFormatInPut: String,
        dateFomratOutPut: String,
        datesToConvert: String
    ): String? {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormatInPut)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val gmt: Date
        val sdfOutPutToSend = SimpleDateFormat(dateFomratOutPut)
        sdfOutPutToSend.timeZone = TimeZone.getDefault()
        try {
            gmt = sdf.parse(datesToConvert)!!
            dateToReturn = sdfOutPutToSend.format(gmt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateToReturn
    }

    fun uTCToLocal1(
        dateFormatInPut: String?,
        dateFormatOutPut: String?,
        datesToConvert: String?
    ): String? {
        var dateToReturn = datesToConvert
        val sdf = SimpleDateFormat(dateFormatInPut, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        try {
            var gmt = sdf.parse(datesToConvert)
            val sdfOutPutToSend = SimpleDateFormat(dateFormatOutPut, Locale.ENGLISH)
            sdfOutPutToSend.timeZone = TimeZone.getDefault()
            dateToReturn = sdfOutPutToSend.format(gmt)

        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("Error", ">" + e.message)
        }

        return dateToReturn
    }

    @JvmStatic
    @Synchronized
    fun getChatDateTime(date: Long): String {
        var str = ""
        if (date == 0L) {
            return str
        }

        val date_time = (date.toString() + "000").toLong()
        val simpleFormat = SimpleDateFormat(Constants.Extra.DATE_TIME_FORMAT, Locale.getDefault())
        simpleFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = simpleFormat.format(Date(date_time)) as String
        Log.d(ContentValues.TAG, "getChatDateTime: $dateTime")

        try {
            val past = simpleFormat.parse(dateTime) as Date
            val now = Date()
            Log.d(ContentValues.TAG, "getChatDateTime: $now dateTime: $past")

            var different = now.time - past.time

            val secondsInMilli = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val yearsInMilli: Long = daysInMilli.toLong() * 365

            val elapsedYear = different / yearsInMilli
            different %= yearsInMilli

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            Log.d(
                ContentValues.TAG,
                "years: $elapsedYear, days: $elapsedDays, hours: $elapsedHours, " +
                        "minutes: $elapsedMinutes, seconds: $elapsedSeconds"
            )

            str = when {
                elapsedYear.toInt() != 0 -> {
                    elapsedYear.toString() + "y"
                }
                elapsedDays.toInt() != 0 -> {
                    elapsedDays.toString() + "d"
                }
                elapsedHours.toInt() != 0 -> {
                    elapsedHours.toString() + "h"
                }
                elapsedMinutes.toInt() != 0 -> {
                    elapsedMinutes.toString() + "m"
                }
                elapsedSeconds.toInt() != 0 -> {
                    elapsedSeconds.toString() + "s"
                }
                else -> {
                    elapsedYear.toString() + "y"
                }
            }

        } catch (ex: java.lang.Exception) {
            Log.d(ContentValues.TAG, "getChatDateTime: $ex")
        }
        return str
    }

    @JvmStatic
    @Synchronized
    fun getChatDateTimeLong(date: Long): String {
        var str = ""
        if (date == 0L) {
            return str
        }

        val date_time = (date.toString() + "000").toLong()
        val simpleFormat = SimpleDateFormat(Constants.Extra.DATE_TIME_FORMAT, Locale.getDefault())
        simpleFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = simpleFormat.format(Date(date_time)) as String
        Log.d(ContentValues.TAG, "last seen getChatDateTime: $dateTime")
        val outputFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())

        try {
            val past = simpleFormat.parse(dateTime) as Date
            val hoursTimeDate = outputFormat.format(past)
            val now = Date()
            Log.d(
                ContentValues.TAG,
                "last seen getChatDateTime: $now dateTime: $past 12house: $hoursTimeDate"
            )

            var different = now.time - past.time

            val secondsInMilli = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val yearsInMilli: Long = daysInMilli.toLong() * 365

            val elapsedYear = different / yearsInMilli
            different %= yearsInMilli

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            Log.d(
                ContentValues.TAG,
                "last seen years: $elapsedYear, days: $elapsedDays, hours: " +
                        "$elapsedHours, minutes: $elapsedMinutes, seconds: $elapsedSeconds"
            )

            str = when {
                getFormattedDate(date_time) == "Yesterday" -> {
                    "yesterday at $hoursTimeDate"
                }
                elapsedYear.toInt() != 0 -> {
                    "$elapsedYear year ago"
                }
                elapsedDays.toInt() != 0 -> {
                    when {
                        elapsedHours.toInt() == 1 -> {
                            "yesterday at $hoursTimeDate"
                        }
                        elapsedHours.toInt() > 1 -> {
                            "$elapsedDays days ago"
                        }
                        else -> {
                            "yesterday at $hoursTimeDate"
                        }
                    }
                }
                elapsedHours.toInt() != 0 -> {
                    if (elapsedHours.toInt() >= 24) {
                        "yesterday at $hoursTimeDate"
                    } else {
                        "today at $hoursTimeDate"
                    }
                }
                elapsedMinutes.toInt() != 0 -> {
                    "$elapsedMinutes minute ago"
                }
                elapsedSeconds.toInt() != 0 -> {
                    "$elapsedSeconds second ago"
                }
                else -> {
                    "a second ago"
                }
            }

        } catch (ex: java.lang.Exception) {
            Log.d(ContentValues.TAG, "last seen getChatDateTime: $ex")
        }
        return str
    }

    private fun getFormattedDate(neededTimeMilis: Long): String {
        val nowTime = Calendar.getInstance()
        val neededTime = Calendar.getInstance()
        neededTime.timeInMillis = neededTimeMilis

        if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {
            if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {
                if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
                    //here return like "Tomorrow at 12:00"
                    return "Tomorrow"

                } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                    //here return like "Today at 12:00"
                    return "Today"

                } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                    //here return like "Yesterday at 12:00"
                    return "Yesterday"

                } else {
                    //here return like "May 31, 12:00"
                    return ""
                }
            } else {
                //here return like "May 31, 12:00"
                return ""
            }
        } else {
            //here return like "May 31 2010, 12:00" - it's a different year we need to show it
            return ""
        }
    }

    fun getTime(date: Long): String {
        var time = ""
        if (date == 0L) {
            return time
        }
        val dateTime = (date.toString() + "000").toLong()
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        val dateTime2 = inputFormat.parse(inputFormat.format(Date(dateTime)))
        time = outputFormat.format(dateTime2!!)
        return time
    }

    fun convertDateTH(inputDate: String, isSingleDate: Boolean): String {
        val inputPattern = Constants.Extra.DATE_TIME_FORMAT
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        var outputDate: Date? = Date()
        try {
            outputDate = inputFormat.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var format = SimpleDateFormat("d", Locale.getDefault())
        val date = format.format(outputDate!!)

        format = if (date.endsWith("1") && !date.endsWith("11")) {
            if (isSingleDate) {
                SimpleDateFormat("d'st' MMM, yyyy", Locale.getDefault())
            } else {
                SimpleDateFormat("dd'st' MMM, yyyy", Locale.getDefault())
            }
        } else if (date.endsWith("2") && !date.endsWith("12")) {
            if (isSingleDate) {
                SimpleDateFormat("d'nd' MMM, yyyy", Locale.getDefault())
            } else {
                SimpleDateFormat("dd'nd' MMM, yyyy", Locale.getDefault())
            }
        } else if (date.endsWith("3") && !date.endsWith("13"))
            if (isSingleDate) {
                SimpleDateFormat("d'rd' MMM, yyyy", Locale.getDefault())
            } else {
                SimpleDateFormat("dd'rd' MMM, yyyy", Locale.getDefault())
            }
        else {
            if (isSingleDate) {
                SimpleDateFormat("d'th' MMM, yyyy", Locale.getDefault())
            } else {
                SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault())
            }
        }
        return format.format(outputDate)
    }

    fun getDateFromMili(milSec: Long): String {
        val simpleDateFormat = SimpleDateFormat(Constants.Extra.DATE_TIME_FORMAT, Locale.ENGLISH)
        return simpleDateFormat.format(milSec)
    }

    fun getFormattedDob(date: String?): String {
        val send = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        try {
            return simpleDateFormat.format(send.parse(date!!)!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getGender(s: Int): String {
        return when (s) {
            Constants.Gender.GENDER_MALE -> "Male"
            Constants.Gender.GENDER_FEMALE -> "Female"
            else -> "NA"
        }
    }

    fun getCurrencyFormat(amount: Double): String {
        return try {
            // NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
            amount.toString()
        } catch (e: Exception) {
            "Error"
        }
    }


    @JvmStatic
    @Synchronized
    fun convertDpToPixel(dp: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt()
    }

    @Synchronized
    fun convertPxToDp(px: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt()
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return try {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            ""
        }
    }

    fun createBitmapFromView(context: Activity, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun humanReadableByteCountSI(bytes2: Long): String {
        var bytes = bytes2
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return String.format(Locale.US, "%.1f %cB", bytes / 1000.0, ci.current())
    }

    fun isDateEquals(startDate: Calendar, currentDate: Calendar): Boolean {
        return startDate[Calendar.YEAR] == currentDate[Calendar.YEAR] && startDate[Calendar.MONTH] == currentDate[Calendar.MONTH] && startDate[Calendar.DAY_OF_MONTH] == currentDate[Calendar.DAY_OF_MONTH]
    }

    fun getImageUrl(url: String?): String {
        return if (url != null) {
            if (url.startsWith("http")) url else BuildConfig.BASE_URL + url
        } else ""
    }

    fun timeToSeconds(time: String): String {
        val arr1: List<String> = time.split(":")
        val hours: Int = arr1[0].toInt()
        val minutes = arr1[1].toInt()
        val secs = arr1[2].toInt()
        val totalSeconds: Int = hours * 3600 + minutes * 60 + secs
        return totalSeconds.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun timeAgo(date: String): String {
        var final: String = ""
        var finalDate: String = ""
        try {
            finalDate = uTCToLocal1("yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd HH:mm:ss", date)!!
            var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var past = format.parse(finalDate)
            var now = Date()
            var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            var hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            var days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            Log.e(">>>>", "timeAgo: $finalDate")
            Log.e(">>>>", "timeAgo: $finalDate")
            Log.e(">>>>", "timeAgo: $finalDate")
            Log.e(">>>>", "timeAgo: $finalDate")

            if (seconds < 60) {
                final = "$seconds seconds ago"

            } else if (minutes < 60) {
                final = "$minutes minutes ago"
            } else if (hours < 24) {
                final = "$hours hours ago"
            } else {
                final = "$days days ago"
            }
        } catch (e: Exception) {
            return "Error"
        }
        return final
    }

    fun separate(text: String?): String {
        val final = text?.split("-")
        val last = final?.get(0)
        val first = final?.get(1)
        val middle = final?.get(2)
        return "$first/$middle/$last"
    }

    fun cardDisplay(number: String?): String {
        return "**** **** **** $number"
    }

}