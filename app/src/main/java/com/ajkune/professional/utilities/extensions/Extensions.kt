package com.ajkune.professional.utilities.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ajkune.professional.application.glide.GlideApp
import com.bumptech.glide.request.RequestOptions
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap

/**
 * Using UTF-8 encoding
 */
//val String.base64Decoded
//    get() = try {
//        String(Base64.getDecoder().decode(toByteArray()))
//    } catch (e: IllegalArgumentException) {
//        null
//    }

/**
 * Using UTF-8 encoding
 */
//val String.base64Encoded: String get() = Base64.getEncoder().encodeToString(toByteArray())

/**
 * Splits by spaces, newlines, and tabs only
 */

//fun ImageView.loadUrl(url: String) {
//    GlideApp
//        .with(context)
//        .load(url)
//        .into(this)
//}

//fun ImageView.loadUrlTest(url: String, placeholder: Int) {
//
//    GlideApp.with(context).load(url).placeholder(placeholder)
//        .apply(RequestOptions.fitCenterTransform())
//        .into(this)
//}

val String.camelCased: String
    get() {
        val split = toLowerCase().split(' ', '\n', '\t').toMutableList()
        if (split.size > 1) {
            for (i in 1..split.size - 1) {
                if (split[i].length > 1) {
                    val charArray = split[i].toCharArray()
                    charArray[0] = charArray[0].toUpperCase()
                    split[i] = String(charArray)
                }
            }
        }
        return split.joinToString("")
    }

val String.containsLetters get() = matches(".*[a-zA-Z].*".toRegex())

val String.containsNumbers get() = matches(".*[0-9].*".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isAlphanumeric get() = matches("^[a-zA-Z0-9]*$".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isAlphabetic get() = matches("^[a-zA-Z]*$".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isNumeric get() = matches("^[0-9]*$".toRegex())

val String.isEmail get() = matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}".toRegex())

/**
 * Only matches URLs starting with a scheme (e.g. http://, file://, ftp://, etc)
 */
val String.isUrl
    get() = try {
        URL(this) != null
    } catch (e: MalformedURLException) {
        false
    }

val String.removeWhiteSpaces: String
    get() {
        return replace("\\s+".toRegex(), "")
    }
/**
 * If there is more than one most common character, this returns the character that occurred first in the String
 */
val String.mostCommonCharacter: Char?
    get() {
        if (length == 0) return null
        val map = HashMap<Char, Int>()
        for (char in toCharArray()) map.put(char, (map[char] ?: 0) + 1)
        var maxEntry = map.entries.elementAt(0)
        for (entry in map) maxEntry = if (entry.value > maxEntry.value) entry else maxEntry
        return maxEntry.key
    }

val String.isUsername get() = matches("^[a-zA-Z0-9-._\"]*$".toRegex())

fun Any?.isnotNull():Boolean{
    return this != null
}

fun ImageView.loadUrl(url: String) {
    GlideApp
        .with(context)
        .load(url)
        .into(this)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}
