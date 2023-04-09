package com.taruns.herway.bottomNav

import android.os.Bundle
import android.util.Log
import com.taruns.herway.bottomNav.SOSFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.taruns.herway.R
import com.taruns.herway.adapter.emergency_cont_adapter
import com.taruns.herway.databinding.FragmentOTPBinding
import com.taruns.herway.databinding.FragmentSosBinding
import com.taruns.herway.models.ContactModel
import com.taruns.herway.models.UserModel

/**
 * A simple [Fragment] subclass.
 * Use the [SOSFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOSFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
     var userDataModel:UserModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private var binding: FragmentSosBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSosBinding.inflate(inflater, container, false)

        userDataModel= arguments?.getSerializable("userDataModel") as UserModel?
        Log.i("check",userDataModel.toString())

        SetRecv()
        return binding!!.root

    }

    private fun SetRecv() {
        val layoutManager = LinearLayoutManager(view?.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recv?.layoutManager = layoutManager



        var cont_list=userDataModel?.eContacts as MutableList<ContactModel>
        //  for(i in 0..34)
        //cont_list.add(i,contactListModel)


        var adapter = emergency_cont_adapter()
        adapter.contact_list=cont_list
        binding?.recv?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {

    }
}