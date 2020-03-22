package com.example.gitsearch

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_first, container, false);

        initView(view)

        init()
        return view
    }

    fun initView(root: View){

    }

    fun init(){
        // back 버튼 제거
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SearchFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }
}
