package com.example.nasa


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.nasa.NasaFragment.Companion.BUNDLE_KEY
import com.example.nasa.NasaFragment.Companion.REQUEST_KEY
import com.example.nasa.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var fm: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        fm = requireActivity().supportFragmentManager

        fm.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            val photo = bundle.get(BUNDLE_KEY) as String
            initVies(photo)
        }


        return binding.root

    }

    private fun initVies(photo: String) {
        binding.apply {
            context?.let {
                Glide
                    .with(requireContext())
                    .load(photo)
                    .into(binding.itemPhoto)
            }
        }
    }

}