package ru.vpcb.map.notes.ui.auth

import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.core.Core
import ru.vpcb.map.notes.data.remote.Stated
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.utils.CryptoPrefs
import ru.vpcb.map.notes.utils.EMPTY_STRING
import ru.vpcb.map.notes.utils.ERROR_CONNECT
import ru.vpcb.map.notes.utils.ERROR_CONNECT_TIMEOUT
import ru.vpcb.map.notes.utils.ERROR_GOOGLE_AUTH
import ru.vpcb.map.notes.utils.ERROR_GOOGLE_CLIENT
import ru.vpcb.map.notes.utils.ERROR_GOOGLE_ONE_TAP
import ru.vpcb.map.notes.utils.ERROR_GOOGLE_TOKEN
import ru.vpcb.map.notes.utils.ERROR_LOGIN
import ru.vpcb.map.notes.utils.ERROR_LOGIN_PASS
import ru.vpcb.map.notes.utils.ERROR_PASS_CONFIRM
import ru.vpcb.map.notes.utils.ERROR_PASS_RESET
import ru.vpcb.map.notes.utils.ERROR_REGISTER
import ru.vpcb.map.notes.utils.ERROR_REGISTER_CONFIRM
import ru.vpcb.map.notes.utils.ERROR_REGISTER_EMAIL
import ru.vpcb.map.notes.utils.ERROR_REGISTER_PASS
import ru.vpcb.map.notes.utils.ERROR_REGISTER_UPDATE
import ru.vpcb.map.notes.utils.ERROR_REGISTER_USER
import ru.vpcb.map.notes.utils.LOGIN_TIMEOUT
import ru.vpcb.map.notes.utils.dispatchCallback
import ru.vpcb.map.notes.utils.validateEmail
import ru.vpcb.map.notes.utils.validatePass
import ru.vpcb.map.notes.utils.withTimeouts
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel(), Signed {

    private val _state =
        MutableStateFlow<MResult<FirebaseUser?>>(MResult.None);  // именно shared при смене user
    val state = _state.asSharedFlow()

    private val _register = MutableSharedFlow<MResult<AuthState>>()
    val register = _register.asSharedFlow()


    private val callback = FirebaseAuth.AuthStateListener {
        viewModelScope.launch {
            _state.emit(MResult.Success(it.currentUser))
        }
    }

    /**
     * catch firebase timeout 30sec and send error to ui
     */
    private val dispatchCallback = dispatchCallback {
        viewModelScope.launch {
            _register.emit(MResult.Error(ERROR_CONNECT_TIMEOUT))
        }
    }

    init {
        Firebase.auth.addAuthStateListener(callback)
    }

    override fun onCleared() {
        Firebase.auth.removeAuthStateListener(callback)
        super.onCleared()
    }

    override fun logged() = Firebase.auth.currentUser != null
    override fun logout() {
        Firebase.auth.signOut()
        CryptoPrefs.resetUser()
    }

    /**
     * force logout
     */
    override fun userExpired(){
        Core.userExpired(this)
    }


// google

    private suspend fun googleAuthCoroutine(token: String) = withTimeouts { cont ->
        val credential = GoogleAuthProvider.getCredential(token, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnSuccessListener {
                cont.resume(MResult.Success(AuthState.GOOGLE_AUTH))
            }
            .addOnFailureListener { e ->
                cont.resume(MResult.Error(ERROR_GOOGLE_AUTH, e.message ?: EMPTY_STRING))
            }
    }


    fun googleAuth(googleOneTap: GoogleOneTap?, result: ActivityResult) {
        viewModelScope.launch(dispatchCallback) {
            try {
                val token = googleOneTap?.token(result.data) ?: CryptoPrefs.getToken()
                if (token == null) {
                    _register.emit(MResult.Error(ERROR_GOOGLE_TOKEN))
                } else {
                    _register.emit(MResult.Loading)
                    _register.emit(googleAuthCoroutine(token))
                }
            } catch (e: Exception) {
                Timber.d(e)
                _register.emit(MResult.Error(ERROR_GOOGLE_AUTH, e.message ?: EMPTY_STRING))
            }
            _register.emit(MResult.None)
        }
    }

    private suspend fun googleCoroutine(
        googleOneTap: GoogleOneTap?, result: ActivityResultLauncher<IntentSenderRequest>
    ): MResult<String> = withTimeouts { cont ->
        googleOneTap?.signIn(result, {
            cont.resume(MResult.Success(EMPTY_STRING))
        }) {
            cont.resume(MResult.Error(ERROR_GOOGLE_ONE_TAP, it))
        } ?: cont.resume(MResult.Error(ERROR_GOOGLE_CLIENT))
    }


    fun google(
        googleOneTap: GoogleOneTap?, result: ActivityResultLauncher<IntentSenderRequest>,
        stated: Stated
    ) {
        viewModelScope.launch(dispatchCallback) {
            try {
                if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
                else {
                    _register.emit(MResult.Loading)
                    googleCoroutine(googleOneTap, result).also {
                        if (it is MResult.Error) {
                            _register.emit(it)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.d(e)
                _register.emit(MResult.Error(ERROR_GOOGLE_ONE_TAP, e.message ?: EMPTY_STRING))
            }
        }
    }

// email

    private suspend fun codeCoroutine(code: String, pass: String) = withTimeouts { cont ->
        Firebase.auth.confirmPasswordReset(code, pass)
            .addOnSuccessListener {
                cont.resume(MResult.Success(AuthState.PASS_CONFIRM))
            }
            .addOnFailureListener { e ->
                cont.resume(MResult.Error(ERROR_PASS_CONFIRM, e.message ?: EMPTY_STRING))
            }
    }


    private fun parseLink(link: String?): String? {
        return try {
            if (link.isNullOrEmpty()) null
            else Uri.parse(link).getQueryParameters("oobCode")[0]
        } catch (e: Exception) {
            Timber.d(e)
            null
        }
    }

    fun password(link: String?, pass: String?, confirm: String?, stated: Stated) {
        viewModelScope.launch(dispatchCallback) {
            val code = parseLink(link)
            if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
            else if (code.isNullOrEmpty()) _register.emit(MResult.Error(ERROR_PASS_CONFIRM, EMPTY_STRING))
            else if (pass != confirm) _register.emit(MResult.Error(ERROR_REGISTER_CONFIRM))
            else if (!pass.validatePass(confirm)) _register.emit(MResult.Error(ERROR_REGISTER_PASS))
            else {
                _register.emit(MResult.Loading)
                _register.emit(codeCoroutine(code, pass!!))
            }
        }
    }

    private suspend fun restorePassCoroutine(email: String): MResult<AuthState> = withTimeouts { cont ->
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                cont.resume(MResult.Success(AuthState.PASS_RESET, email))
            }
            .addOnFailureListener { e ->
                cont.resume(MResult.Error(ERROR_PASS_RESET, e.message ?: EMPTY_STRING))
            }
    }


    fun restore(email: String?, stated: Stated) {
        viewModelScope.launch(dispatchCallback) {
            if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
            else if (!email.validateEmail()) _register.emit(MResult.Error(ERROR_REGISTER_EMAIL))
            else {
                _register.emit(MResult.Loading)
                _register.emit(restorePassCoroutine(email!!))
            }
        }
    }

    private suspend fun updateCoroutine(name: String, photoUrl: String? = null) = withTimeouts { cont ->
        val request = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(photoUrl?.run { Uri.parse(this) })
            .build()
        Firebase.auth.currentUser?.updateProfile(request)
            ?.addOnSuccessListener {
                cont.resume(MResult.Success(AuthState.REGISTER_UPDATE))
            }
            ?.addOnFailureListener {
                cont.resume(MResult.Error(ERROR_REGISTER_UPDATE, it.message ?: EMPTY_STRING))
            }
    }


    private suspend fun signUpCoroutine(email: String, pass: String) = withTimeouts(LOGIN_TIMEOUT) { cont ->
        Firebase.auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                cont.resume(MResult.Success(AuthState.REGISTER_SUCCESS))
            }
            .addOnFailureListener { e ->
                cont.resume(MResult.Error(ERROR_REGISTER, e.message ?: EMPTY_STRING))
            }
    }


    fun register(name: String?, email: String?, pass: String?, confirm: String?, stated: Stated) {
        viewModelScope.launch(dispatchCallback) {
            if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
            else if (name.isNullOrEmpty()) _register.emit(MResult.Error(ERROR_REGISTER_USER))
            else if (!email.validateEmail()) _register.emit(MResult.Error(ERROR_REGISTER_EMAIL))
            else if (pass != confirm) _register.emit(MResult.Error(ERROR_REGISTER_CONFIRM))
            else if (!pass.validatePass(confirm)) _register.emit(MResult.Error(ERROR_REGISTER_PASS))
            else {
                _register.emit(MResult.Loading)
                val result = signUpCoroutine(email!!, pass!!)
                if (result is MResult.Error) _register.emit(result)
                else _register.emit(updateCoroutine(name))
            }
        }
    }

    private suspend fun signInEmailCoroutine(email: String, pass: String): MResult<AuthState> =
        withTimeouts(LOGIN_TIMEOUT) { cont ->
            Firebase.auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    cont.resume(MResult.Success(AuthState.LOGIN_SUCCESS))
                }
                .addOnFailureListener { e ->
                    cont.resume(MResult.Error(ERROR_LOGIN, e.message ?: EMPTY_STRING))
                }
        }

    fun login(email: String?, pass: String?, stated: Stated) {
        viewModelScope.launch(dispatchCallback) {
            if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
            else if (!email.validateEmail()) _register.emit(MResult.Error(ERROR_REGISTER_EMAIL))
            else if (pass.isNullOrEmpty() || pass.length < 8) _register.emit(MResult.Error(ERROR_LOGIN_PASS))
            else {
                _register.emit(MResult.Loading)
                val result = signInEmailCoroutine(email!!, pass)
                _register.emit(result)
            }
        }
    }
}

enum class AuthState {
    LOGIN_SUCCESS, REGISTER_SUCCESS, REGISTER_UPDATE,
    GOOGLE_SUCCESS, GOOGLE_AUTH,
    PASS_RESET, PASS_CONFIRM, PASS_VERIFY
}