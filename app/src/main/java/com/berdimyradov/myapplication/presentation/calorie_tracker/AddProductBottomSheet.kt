package com.berdimyradov.myapplication.presentation.calorie_tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.berdimyradov.myapplication.R
import com.berdimyradov.myapplication.databinding.AddProductBottomSheetBinding
import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.presentation.calorie_tracker.adapters.AddProductAdapter
import com.berdimyradov.myapplication.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductBottomSheet : BottomSheetDialogFragment(),
    AddProductAdapter.OnItemClickListener {

    private val viewModel: CalorieTrackerViewModel by activityViewModels()
    private lateinit var _binding: AddProductBottomSheetBinding
    private lateinit var mAdapter: AddProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_product_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = AddProductBottomSheetBinding.bind(view)
        mAdapter = AddProductAdapter(this)
        _binding.editText.doOnTextChanged { text, start, before, count ->
            viewModel.searchProduct(text.toString())
        }
        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        _binding.productListRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productInfo.collect { event ->
                    when (event) {
                        is Resource.Success -> {
                            println("Succes: ${event.data}")
                            _binding.bottomSheetProgressBar.isVisible = false
                            mAdapter.submitList(event.data?.items)
                        }
                        is Resource.Empty -> {}
                        is Resource.Error -> {
                            _binding.bottomSheetProgressBar.isVisible = false
                            Snackbar.make(
                                requireView(),
                                event.message ?: "Something went wrong",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                            _binding.bottomSheetProgressBar.isVisible = true
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addedItemResponse.collect { event ->
                    when (event) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), event.data, Toast.LENGTH_LONG)
                                .show()
                        }
                        is Resource.Empty -> {}
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        is Resource.Loading -> {}
                    }
                }
            }
        }
    }

    override fun addProduct(item: Item) {
        viewModel.insertItem(item)
    }
}