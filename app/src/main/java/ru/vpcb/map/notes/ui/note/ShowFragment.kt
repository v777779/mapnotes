package ru.vpcb.map.notes.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.databinding.FragmentShowBinding

import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.utils.formatCoordinates
import ru.vpcb.map.notes.utils.loadImage
import ru.vpcb.map.notes.utils.popBackStacked

@AndroidEntryPoint
class ShowFragment : Fragment() {

    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShowBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.linearTitle.imageBack.setOnClickListener{
            findNavController().popBackStack(R.id.fragment_home,false)
        }

        setupViews()
        mainViewModel.bottom(BottomSheetBehavior.STATE_HIDDEN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews(){
        mainViewModel.currentNote = null
        mainViewModel.currentNote?.let {note ->
            binding.textTitle.text = note.title
            binding.textBody.text = note.body
            binding.textCoord.text =  note.formatCoordinates(binding.root)
            binding.image.loadImage(note.image)

        }?:popBackStacked(R.id.fragment_home,false)



    }
}