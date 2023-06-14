package ru.vpcb.map.notes.ui.bottom

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.FragmentAddBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.model.TypeFragment
import ru.vpcb.map.notes.model.toBundle

import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.ui.auth.AuthViewModel
import ru.vpcb.map.notes.ui.home.LocationViewModel
import ru.vpcb.map.notes.utils.collects
import ru.vpcb.map.notes.utils.loadImage
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.setNotNullOrEmpty
import ru.vpcb.map.notes.utils.showError

@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private val locationViewModel by activityViewModels<LocationViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    private val geocoder by lazy {
        context?.let { Geocoder(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.register.collects(viewLifecycleOwner) {
            mainViewModel.progress(false)
            when (it) {
                is MResult.Loading -> mainViewModel.progress(true)

                is MResult.Success -> mainViewModel.navigate(R.id.fragment_home)

                is MResult.Error -> showError(context, it)

                else -> mainViewModel.progress(false)
            }
        }

        searchViewModel.notes.collects(viewLifecycleOwner) {
            mainViewModel.badge(it.size)
            mainViewModel.progress(false)
        }
        searchViewModel.note.collects(viewLifecycleOwner) {
            setupViews(it)
            mainViewModel.progress(false)
        }


        binding.chipVideo.setOnClickListener {

        }
        binding.chipAudio.setOnClickListener {

        }
        binding.chipScreen.setOnClickListener {
            searchViewModel.updateNote(binding, mainViewModel, locationViewModel)
            activity?.navigated(
                R.id.nav_host_fragment_content_main, R.id.fragment_edit,
                TypeFragment.ADD.toBundle()
            )
        }
        binding.chipCreate.setOnClickListener {
            searchViewModel.createNote(binding, mainViewModel, networkViewModel, authViewModel)

        }


        binding.photo.linear.setOnClickListener {

        }
        binding.drive.linear.setOnClickListener {

        }
        binding.unsplash.linear.setOnClickListener {

        }
        binding.video.linear.setOnClickListener {

        }
        binding.audio.linear.setOnClickListener {

        }

        mainViewModel.bottom(BottomSheetBehavior.STATE_COLLAPSED)
        searchViewModel.initNote(geocoder, mainViewModel, locationViewModel)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainViewModel.bottom()
    }

    private fun setupViews(note: Note?) {
        binding.editBody.setNotNullOrEmpty(note?.body)
        binding.editLat.setNotNullOrEmpty(context, note?.lat)
        binding.editLon.setNotNullOrEmpty(context, note?.lon)
        binding.image.loadImage(note?.image)
    }


}