package ru.vpcb.map.notes.ui.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vpcb.map.notes.R

/**
 *  Empty fragment starting point of child navigation graph
 *  used to fill coordinator layout by nothing at app start
 */
class EmptyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_empty,container,false )
    }

}