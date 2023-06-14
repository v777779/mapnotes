package ru.vpcb.map.notes.ui.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.FragmentSearchBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.ui.auth.AuthViewModel
import ru.vpcb.map.notes.ui.bottom.adapter.NoteAdapter
import ru.vpcb.map.notes.utils.collects
import ru.vpcb.map.notes.utils.showError

@AndroidEntryPoint
class SearchFragment : Fragment(), NoteAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()


    private val noteAdapter by lazy {
        NoteAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.register.collects(viewLifecycleOwner) {
            mainViewModel.progress(false)
            when (it) {
                is MResult.Loading -> mainViewModel.progress(true)

                is MResult.Error -> showError(context, it)

                else -> mainViewModel.progress(false)
            }
        }

        mainViewModel.notes.collects(viewLifecycleOwner) {
            if (it is MResult.Success) {
                searchViewModel.updateNotes(it.data)
            }
        }
        searchViewModel.notes.collects(viewLifecycleOwner) {
            noteAdapter.submitList(it)
            mainViewModel.progress(false)
        }

        binding.imageReload.setOnClickListener{
            searchViewModel.notes(networkViewModel, authViewModel, true)
        }
        mainViewModel.bottom(BottomSheetBehavior.STATE_COLLAPSED)

        setupRecycler()
        searchViewModel.notes(networkViewModel, authViewModel)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainViewModel.bottom()
    }

    override fun onClick(note: Note) {
        mainViewModel.moveCamera(note.lat, note.lon)
    }


    private fun setupRecycler() {
        binding.recycler.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(context)
//            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//            addItemDecoration(divider)

            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.layoutPosition
                    val note = noteAdapter.currentList[position]
                    searchViewModel.note(networkViewModel, authViewModel, note, FirebaseRepo.Mode.DELETE)
                }
            }).attachToRecyclerView(this)

        }
    }


}