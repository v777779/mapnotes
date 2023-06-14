package ru.vpcb.map.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.ui.auth.AuthViewModel

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()
    private val networkViewModel by viewModels<NetworkViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        // any lifecycle state allowed
        lifecycleScope.launch {
            splashViewModel.state.collectLatest {
                if (it) {
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        splashViewModel.init(baseContext, networkViewModel, authViewModel)

    }

}