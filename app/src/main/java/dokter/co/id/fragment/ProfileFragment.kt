package dokter.co.id.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dokter.co.id.adapter.ChatAdapter
import dokter.co.id.databinding.FragmentProfileBinding
import dokter.co.id.model.Message
import dokter.co.id.viewmodel.ViewModelMain

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private var listChat: MutableList<Message> = mutableListOf()
    private lateinit var viewModelMain: ViewModelMain

    val chatAdapter = ChatAdapter()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun loadview() {
        val recyclerView = binding!!.rvIsiChat
        // set click listener
//        chatAdapter.listener = this

        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(linearLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.adapter = chatAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()
        loadview()

        viewModelMain = ViewModelProvider(requireActivity()).get(ViewModelMain::class.java)
        viewModelMain.loadfirebase(requireActivity())
        // Mengamati perubahan pada sharedData
        viewModelMain.listChat.observe(viewLifecycleOwner, { data ->
            // Melakukan sesuatu dengan data yang diperbarui
            chatAdapter.setView(listOf(data))
            chatAdapter.notifyDataSetChanged()
        })

        onClick()

        return root
    }

    private fun onClick() {
        binding!!.btnSend.setOnClickListener {
            viewModelMain.sendChat(requireActivity(), binding!!.etIsiChat.text.toString())
        }
    }
}