package ru.vpcb.map.notes.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.vpcb.map.notes.BuildConfig
import ru.vpcb.map.notes.utils.ERROR_GOOGLE_AUTH
import timber.log.Timber

class GoogleOneTap(private val activity: Activity?) {

    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null

    init {
        init(BuildConfig.WEB_CLIENT_ID)
    }
    fun reset() {
        oneTapClient = null
        signInRequest = null
    }

    private fun playServices(): Boolean {
        activity ?: return false
        try {
            return GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(activity) == ConnectionResult.SUCCESS
        } catch (e: Exception) {
            Timber.d(e)
        }
        return false
    }

    fun validate(): Boolean {
        return activity != null && oneTapClient != null && signInRequest != null
    }

    fun init(client:String) {
        try {
            if (activity == null || !playServices()) return

            oneTapClient = Identity.getSignInClient(activity);
            signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(client)
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build()

        } catch (e: Exception) {
            Timber.d(e)
            this.signInRequest = null
            this.oneTapClient = null
        }
    }

    fun token(intent: Intent?): String? {
        try {
            return oneTapClient?.getSignInCredentialFromIntent(intent)?.googleIdToken
        } catch (e: Exception) {
            Timber.d(e)
        }
        return null
    }

    fun signIn(result: ActivityResultLauncher<IntentSenderRequest>, success:()->Unit, error: (String) -> Unit) {
        activity ?: return
        val client = oneTapClient ?: return
        val request = signInRequest ?: return
        client.beginSignIn(request)
            .addOnSuccessListener(activity) {
                try {
                    val ib = IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                    result.launch(ib)
                    success()
                } catch (e: Exception) {
                    Timber.d(e)
                    error(ERROR_GOOGLE_AUTH)
                }
            }
            .addOnFailureListener(activity)
            {
                Timber.d(it)
                error(ERROR_GOOGLE_AUTH)
            }
    }

}