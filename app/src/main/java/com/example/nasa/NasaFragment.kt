package com.example.nasa


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa.databinding.FragmentNasaBinding
import com.example.nasa.viewmodel.LoadAdapter
import com.example.nasa.viewmodel.NasaListAdapter
import com.example.nasa.viewmodel.NasaPagingSource
import com.example.nasa.viewmodel.NasaViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NasaFragment : Fragment() {
    companion object {
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundleKey"
    }


    private var _binding: FragmentNasaBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NasaListAdapter
    private lateinit var fm: FragmentManager

    private lateinit var viewModel: NasaViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNasaBinding.inflate(layoutInflater, container, false)

        initViews()
        fm = requireActivity().supportFragmentManager

        binding.pagingSource = NasaPagingSource()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recycler.adapter = adapter.withLoadStateFooter(LoadAdapter())

        viewModel = ViewModelProvider(this)[NasaViewModel::class.java]

        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        adapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pageNasa.onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        setupClickListener()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        adapter = NasaListAdapter(requireContext())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun setupClickListener() {
        adapter.onItemClickListener = {
            fm.setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to it))
            fm.beginTransaction()
                .replace(R.id.fragment_container, PhotoFragment())
                .addToBackStack(null)
                .commit()

        }

    }

}