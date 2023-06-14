package ru.vpcb.map.notes.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.databinding.FragmentProfileBinding
import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.utils.popBackStacked

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel> ()
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearTitle.imageBack.setOnClickListener {
            popBackStacked(R.id.fragment_home, false)
        }

        binding.buttonLogout.setOnClickListener {
            authViewModel.logout()
            popBackStacked(R.id.fragment_home, false)

        }
    }

    override fun onResume() {
        super.onResume()
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        binding.inputName.edit.setText(Firebase.auth.currentUser?.displayName?:"")
        binding.inputEmail.edit.setText(Firebase.auth.currentUser?.email?:"")

    }


}