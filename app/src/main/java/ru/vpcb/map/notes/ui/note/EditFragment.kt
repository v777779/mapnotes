package ru.vpcb.map.notes.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.FragmentEditBinding
import ru.vpcb.map.notes.model.TypeFragment
import ru.vpcb.map.notes.model.toNavArgs

import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.ui.auth.AuthViewModel
import ru.vpcb.map.notes.ui.bottom.SearchViewModel
import ru.vpcb.map.notes.utils.formatCoordinate
import ru.vpcb.map.notes.utils.loadImage
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.popBackStacked
import ru.vpcb.map.notes.utils.setIcon
import ru.vpcb.map.notes.utils.setNotNullOrEmpty

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    private val navArgs by lazy {
        arguments.toNavArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.linearTitle.imageBack.setOnClickListener {
            findNavController().popBackStack(R.id.fragment_home, false)
        }

        binding.photo.linear.setOnClickListener {
            popBackStacked(R.id.fragment_home, false)
            navigated(R.id.fragment_show)
        }

        binding.drive.linear.setOnClickListener {

        }
        binding.unsplash.linear.setOnClickListener {

        }
        binding.video.linear.setOnClickListener {

        }
        binding.audio.linear.setOnClickListener {

        }
        binding.button.linear.setOnClickListener {
            searchViewModel.note(networkViewModel, authViewModel, mainViewModel, FirebaseRepo.Mode.UPDATE)
        }
        binding.buttonClose.linear.setOnClickListener {
            popBackStacked(R.id.fragment_home, false)
        }
        mainViewModel.bottom(BottomSheetBehavior.STATE_HIDDEN)

    }

    override fun onResume() {
        super.onResume()
        setupViews()  // for edits
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {

        binding.button.title = getString(
            if (navArgs.type == TypeFragment.ADD) R.string.create
            else R.string.edit
        )

        val note = mainViewModel.currentNote
        binding.editTitle.setNotNullOrEmpty(note?.title)
        binding.editBody.setNotNullOrEmpty(note?.body)
        binding.editLat.setNotNullOrEmpty(note?.lat.formatCoordinate(context))
        binding.editLon.setNotNullOrEmpty(note?.lon.formatCoordinate(context))
        note?.image.loadImage(binding.image)
        note?.video.setIcon(
            binding.chipVideo, R.drawable.baseline_videocam_24, R.drawable.baseline_videocam_off_24
        )
        note?.audio?.setIcon(
            binding.chipAudio, R.drawable.baseline_music_note_24, R.drawable.baseline_music_off_24
        )

    }


}