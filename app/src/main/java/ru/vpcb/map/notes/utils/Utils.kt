package ru.vpcb.map.notes.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import timber.log.Timber
import java.util.Locale
import java.util.regex.Pattern


// messages
const val ERROR_COMMON = "400_000"

const val ERROR_EMAIL = "400_005" // error email format
const val ERROR_PASS = "400_006" // error password format or not equal

const val ERROR_GOOGLE_SERVICE = "400_038"  // error google client not available
const val ERROR_GOOGLE_AUTH = "400_039"  // error google auth


const val ERROR_LOGIN = "400_051"  // error login exception
const val ERROR_LOGIN_INPUT = "400_052"  // error login input
const val ERROR_REGISTER = "400_053" // error register exception
const val ERROR_REGISTER_INPUT = "400_054"  // error register input
const val ERROR_REGISTER_NAME = "400_055"  // error register name
const val ERROR_REGISTER_UPDATE = "400_056"  // error register update

const val ERROR_GOOGLE_ONE_TAP = "400_057"  // error google one tap
const val ERROR_GOOGLE_AUTH_FB = "400_058"  // error google auth firebase
const val ERROR_GOOGLE_CLIENT = "400_059"  // error google client empty
const val ERROR_GOOGLE_TOKEN = "400_060"  // error google token empty

const val ERROR_REGISTER_USER = "400_061"  // error register name
const val ERROR_REGISTER_EMAIL = "400_062"  // error register email
const val ERROR_REGISTER_PASS = "400_063"  // error register pass strength
const val ERROR_REGISTER_CONFIRM = "400_064"  // error register pass equal

const val ERROR_LOGIN_PASS = "400_065" // error login pass empty
const val ERROR_PASS_RESET = "400_068"  // error pass reset
const val ERROR_PASS_CONFIRM = "400_069"  // error pass confirm
const val ERROR_PASS_VERIFY = "400_070"  // error pass verify

const val ERROR_FB_NOTES = "400_071"  // error database notes
const val ERROR_CONNECT_TIMEOUT = "400_072"  // error connection timeout
const val ERROR_CONNECT = "400_073"  // error connection
const val ERROR_AUTH = "400_074"  // error user not logged

const val ERROR_LOCATION_PERMISSIONS = "400_075"
const val ERROR_LOCATION_SETTINGS = "400_076"
const val ERROR_FINE_PERMISSIONS = "400_077"
const val ERROR_BACKGROUND_PERMISSIONS = "400_078"

const val ERROR_FB_USERS = "400_079"  // error database users
const val ERROR_FB_STORAGE = "400_080"  // error storage


private val messages = mapOf(
    ERROR_COMMON to R.string.message_400_000,

    ERROR_EMAIL to R.string.message_400_005,
    ERROR_PASS to R.string.message_400_006,

    ERROR_GOOGLE_SERVICE to R.string.message_400_038,
    ERROR_GOOGLE_AUTH to R.string.message_400_039,
    ERROR_LOGIN to R.string.message_400_051,
    ERROR_LOGIN_INPUT to R.string.message_400_052,
    ERROR_REGISTER to R.string.message_400_053,
    ERROR_REGISTER_INPUT to R.string.message_400_054,
    ERROR_REGISTER_NAME to R.string.message_400_055,
    ERROR_REGISTER_UPDATE to R.string.message_400_056,

    ERROR_GOOGLE_ONE_TAP to R.string.message_400_057,
    ERROR_GOOGLE_AUTH_FB to R.string.message_400_058,
    ERROR_GOOGLE_CLIENT to R.string.message_400_059,
    ERROR_GOOGLE_TOKEN to R.string.message_400_060,

    ERROR_REGISTER_USER to R.string.message_400_061,
    ERROR_REGISTER_EMAIL to R.string.message_400_062,
    ERROR_REGISTER_PASS to R.string.message_400_063,
    ERROR_REGISTER_CONFIRM to R.string.message_400_064,
    ERROR_LOGIN_PASS to R.string.message_400_065,

    ERROR_PASS_RESET to R.string.message_400_068,
    ERROR_PASS_CONFIRM to R.string.message_400_069,
    ERROR_PASS_VERIFY to R.string.message_400_070,
    ERROR_FB_NOTES to R.string.message_400_071,
    ERROR_CONNECT_TIMEOUT to R.string.message_400_072,
    ERROR_CONNECT to R.string.message_400_073,
    ERROR_AUTH to R.string.message_400_074,
    ERROR_LOCATION_PERMISSIONS to R.string.message_400_075,
    ERROR_LOCATION_SETTINGS to R.string.message_400_076,
    ERROR_FINE_PERMISSIONS to R.string.message_400_077,
    ERROR_BACKGROUND_PERMISSIONS to R.string.message_400_078,
    ERROR_FB_USERS to R.string.message_400_079,
    ERROR_FB_STORAGE to R.string.message_400_080,

    )


//constants
const val INTEGER_FAIL = -1
const val INTEGER_ZERO = 0
const val EMPTY_STRING = ""

// register
const val PASS_LENGTH = 8           // server side limit
const val PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,}$"

// connect
const val CONNECT_TIMEOUT = 30_000L // 30 sec
const val LOGIN_EXPIRED = 3600_000L  // 1 hour
const val NOTES_EXPIRED = 4 * 60 * 60 * 1000L  // 4 hours
const val LOGIN_TIMEOUT = 300 * 1000L // 5 min for suspend login

// navigation
const val NAVIGATE_HOME = "ru.vpcb.map.mm:id/fragment_home"
const val NAVIGATE_ADD = "ru.vpcb.map.mm:id/fragment_add"
const val NAVIGATE_SEARCH = "ru.vpcb.map.mm:id/fragment_search"
const val NAVIGATE_PROFILE = "ru.vpcb.map.mm:id/fragment_profile"
const val NAVIGATE_LOGIN = "ru.vpcb.map.mm:id/fragment_login"

const val NAVIGATE_BUNDLE_TYPE = "navigate_bundle_type"

// map
const val MAP_LAT_DEFAULT = 56.2614189
const val MAP_LON_DEFAULT = 44.0154223
const val MAP_CITY_DEFAULT = "Nizhny Novgorod"
const val MAP_ZOOM_DEFAULT = 14.0f

// location
const val LOCATION_INTERVAL = 5_000L
const val LOCATION_UPDATE_KEY = "location_update_key"

const val LOCATION_SETTINGS = "account_location_settings_key"
const val LOCATION_PERMISSIONS = "location_permissions_key"
const val LOCATION_FINE_PERMISSIONS = "location_fine_permissions_key"
const val BACKGROUND_PERMISSION = "location_background_permissions_key"
const val PERMISSION_STATE = "account_permission_state_key"

// notes adapter

const val NOTE_ADAPTER_EVEN = 0
const val NOTE_ADAPTER_ITEM = 1

// coroutine

/**
 * dispatchCallback for withTimeout
 * catch firebase timeout 30sec and send error to ui
 */
fun dispatchCallback(action: (Throwable) -> Unit = {}) = CoroutineExceptionHandler { _, t ->
    action(t)
}

/**
 *  translates callback to cancellable coroutine with 30sec timeout
 *  viewModelScope.launch(dispatchCallback) is mandatory
 */
suspend fun <T> withTimeouts(
    timeout: Long = CONNECT_TIMEOUT,
    action: (CancellableContinuation<MResult<T>>) -> Unit
): MResult<T> {
    return withTimeout(timeout) {
        suspendCancellableCoroutine() { cont ->
            action(cont)
            cont.invokeOnCancellation {
                throw Exception(ERROR_CONNECT_TIMEOUT)
            }
        }
    }
}
// format

fun Note?.formatCoordinates(view:View?):String{
    this?:return EMPTY_STRING
    val context = view?.context?:return EMPTY_STRING
    val lat = lat?:return EMPTY_STRING
    val lon = lon?:return EMPTY_STRING
    return context.getString(R.string.coordinates_format,lat,lon)
}
fun ImageView?.loadImage(url: String?) {
    this ?: return
     url?:return
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .into(this)
}
fun String?.loadImage(imageView: ImageView) {
    this ?: return
    Glide.with(imageView)
        .load(this)
        .placeholder(R.drawable.placeholder)
        .into(imageView)
}

fun String?.setIcon(chip: Chip, on: Int, off: Int) =
    chip.setChipIconResource(this?.let { on } ?: off)

fun EditText?.toDoubleOrNull(): Double? = this?.text?.toString()?.toDoubleOrNull()

fun EditText?.toNotNullOrEmpty(): String = this?.text?.toString()?: EMPTY_STRING
fun EditText?.setNotNullOrEmpty(context: Context?, value: Double?) {
    this ?: return
    if (context == null || value == null) {
        this.setText(EMPTY_STRING)
    } else {
        val text = String.format(Locale.getDefault(), context.getString(R.string.coordinate_format, value))
        this.setText(text)
    }
}

fun EditText?.setNotNullOrEmpty(value: String?) = this?.setText(value ?: EMPTY_STRING)

fun Double?.formatCoordinate(context: Context?): String {
    this ?: return EMPTY_STRING
    context ?: return EMPTY_STRING
    return String.format(Locale.getDefault(), context.getString(R.string.coordinate_format, this))
}

// snackBar
fun Activity?.toSnackBar(key: String, action: (View) -> Unit) {
    this ?: return
    try {
        val view = findViewById<View>(android.R.id.content)
        val resId = messages[key] ?: return
        Snackbar.make(view, getString(resId), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.ok), action)
            show()
        }
    } catch (e: Exception) {
        Timber.d(e)
    }
}

// toast
fun String?.toMResult(code: String, message: String? = null) = MResult.Error(code, message ?: EMPTY_STRING)
fun Exception?.toMResult(code: String) = MResult.Error(code, this?.message ?: EMPTY_STRING)

fun showError(context: Context?, code: String?, message: String? = null) {
    try {
        context ?: return
        val value = messages[code] ?: return
        if (message == null) context.getString(value).toToast(context)
        else context.getString(value, message).toToast(context)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

fun showError(context: Context?, error: MResult.Error) {
    showError(context, error.code, error.message)
}

fun View.hideKeyboard(activity: Activity?) {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.apply {
        hideSoftInputFromWindow(this@hideKeyboard.windowToken, 0)
    }
}

// navigate
fun NavController.popBackStacked(@IdRes resId: Int? = null, inclusive: Boolean = false) {
    try {
        if (resId == null) popBackStack()
        else popBackStack(resId, inclusive)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

fun NavController.navigated(@IdRes resId: Int, args: Bundle? = null) {
    try {
        navigate(resId, args)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

/**
 *  protected navigation from wrong place
 */
fun Fragment.popBackStacked(@IdRes resId: Int? = null, inclusive: Boolean = false) {
    try {
        if (resId == null) findNavController().popBackStack()
        else findNavController().popBackStack(resId, inclusive)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

fun Activity.navigated(@IdRes navId: Int, @IdRes resId: Int, args: Bundle? = null) {
    try {
        findNavController(navId).navigate(resId, args)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

fun Fragment.navigated(@IdRes resId: Int, args: Bundle? = null) {
    try {
        findNavController().navigate(resId, args)
    } catch (e: Exception) {
        Timber.d(e)
    }
}

fun String?.toToast(context: Context?) {
    this ?: return
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun <T> Flow<T>.collects(owner: LifecycleOwner, action: (T) -> Unit) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest {
                action.invoke(it)
            }
        }
    }
}


fun String?.validateEmail(): Boolean {
    if (isNullOrEmpty()) return false
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.validatePass(confirm: String? = this): Boolean {
    if (isNullOrEmpty() || confirm.isNullOrEmpty() || this != confirm || this.length < PASS_LENGTH) return false
    return Pattern.compile(PASS_REGEX).matcher(this).matches()

}
