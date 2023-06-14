package ru.vpcb.map.notes.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.FragmentPassBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.utils.collects
import ru.vpcb.map.notes.utils.hideKeyboard
import ru.vpcb.map.notes.utils.popBackStacked
import ru.vpcb.map.notes.utils.showError
import ru.vpcb.map.notes.utils.toToast

@AndroidEntryPoint
class PassFragment : Fragment() {

    private var _binding: FragmentPassBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPassBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.register.collects(viewLifecycleOwner) {
            mainViewModel.progress(false)
            when (it) {
                is MResult.Loading -> mainViewModel.progress(true)
                is MResult.Success -> {
                    when (it.data) {
                        AuthState.PASS_RESET -> getString(R.string.pass_reset, it.message).toToast(context)
                        AuthState.PASS_CONFIRM -> {
                            popBackStacked(R.id.fragment_home, false)
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

        binding.linearTitle.imageBack.setOnClickListener {
            popBackStacked()
            mainViewModel.progress(false)
        }

        binding.buttonSend.setOnClickListener {
            val email = binding.inputEmail.edit.text?.toString()
            authViewModel.restore(email, networkViewModel)
            binding.root.hideKeyboard(activity)
        }

        binding.buttonConfirm.setOnClickListener {
            val link = binding.inputLink.edit.text?.toString()
            val pass = binding.inputPass.edit.text?.toString()
            val confirm = binding.inputConfirm.edit.text?.toString()
            authViewModel.password(link, pass, confirm, networkViewModel)
            binding.root.hideKeyboard(activity)
        }
        binding.root.setOnClickListener {
            binding.root.hideKeyboard(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}