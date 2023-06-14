package ru.vpcb.map.notes.ui.auth

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.core.Core
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.FragmentLoginBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.utils.CryptoPrefs
import ru.vpcb.map.notes.utils.collects
import ru.vpcb.map.notes.utils.hideKeyboard
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.popBackStacked
import ru.vpcb.map.notes.utils.showError

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()

    private var googleOneTap: GoogleOneTap? = null

    private val oneTapResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        authViewModel.googleAuth(googleOneTap, it)
    }

    /**
     * resolves loop home -> login with gesture
     */
    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    private val backInvokedCallback =
        OnBackInvokedCallback {
            requireActivity().finish()
        }

    /**
     * resolves loop home -> login when back pressed
     */
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.state.collects(viewLifecycleOwner) {
            if (it is MResult.Success && it.data != null) CryptoPrefs.setToken(it.data.uid)
        }
        authViewModel.register.collects(viewLifecycleOwner) {
            mainViewModel.progress(false)
            when (it) {
                is MResult.Loading -> mainViewModel.progress(true)
                is MResult.Success -> {
                    when (it.data) {
                        AuthState.LOGIN_SUCCESS -> {
                            Core.userSigned()
                            popBackStacked(R.id.fragment_home, true)
                            navigated(R.id.fragment_home)
                        }

                        else -> {}
                    }
                }

                is MResult.Error -> {
                    showError(context, it)
                }

                else -> {}
            }
        }

        binding.root.setOnClickListener {
            binding.root.hideKeyboard(activity)
        }

        binding.linearTitle.imageBack.setOnClickListener {
            popBackStacked(R.id.fragment_home, false)
            mainViewModel.progress(false)
        }
        binding.buttonGoogle.linear.setOnClickListener {
            authViewModel.google(googleOneTap, oneTapResult, networkViewModel)
            binding.root.hideKeyboard(activity)
        }
        binding.textSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_SYNC_SETTINGS))
            mainViewModel.progress(false)
        }
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.edit.text?.toString()
            val pass = binding.inputPass.edit.text?.toString()
            authViewModel.login(email, pass, networkViewModel)
            binding.root.hideKeyboard(activity)
        }
        binding.textRegister.setOnClickListener {
            navigated(R.id.fragment_register)
            mainViewModel.progress(false)
        }
        binding.textRestore.setOnClickListener {
            navigated(R.id.fragment_pass)
            mainViewModel.progress(false)
        }
        googleOneTap = GoogleOneTap(activity)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT, backInvokedCallback
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().onBackInvokedDispatcher.unregisterOnBackInvokedCallback(
                backInvokedCallback
            )
        }
    }


}