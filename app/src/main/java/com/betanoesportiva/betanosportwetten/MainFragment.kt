package com.betanoesportiva.betanosportwetten

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.betanoesportiva.betanosportwetten.databinding.FragmentMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

const val ARG_PARAM1 = "param1"

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var bundle = Bundle()

    private lateinit var webView:WebView

    private val fireBaseDataBase = FirebaseDatabase.getInstance()
    private val reference = fireBaseDataBase.reference
    val childReference = reference.child("url")
    private var param1 = reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        context?.let { FirebaseApp.initializeApp(it) }

        binding.btn.setOnClickListener {
            bundle.putString(ARG_PARAM1, param1.toString())
            val fragment = WebViewFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.container, fragment)?.commit()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}