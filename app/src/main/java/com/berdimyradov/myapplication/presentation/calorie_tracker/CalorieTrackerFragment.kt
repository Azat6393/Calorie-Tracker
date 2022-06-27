package com.berdimyradov.myapplication.presentation.calorie_tracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.berdimyradov.myapplication.R
import com.berdimyradov.myapplication.databinding.FragmentCalorieTrackerBinding
import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.presentation.calorie_tracker.adapters.ProductListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalorieTrackerFragment : Fragment(R.layout.fragment_calorie_tracker),
    ProductListAdapter.OnItemClickListener {

    private lateinit var _binding: FragmentCalorieTrackerBinding
    private lateinit var mAdapter: ProductListAdapter
    private val viewModel: CalorieTrackerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalorieTrackerBinding.bind(view)
        mAdapter = ProductListAdapter(this)
        initRecyclerView()
        observe()

        _binding.addProductFab.setOnClickListener {
            val bottomSheet = AddProductBottomSheet()
            bottomSheet.show(requireActivity().supportFragmentManager, "AddProductBottomSheet")
        }
    }

    private fun initRecyclerView() {
        _binding.productRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productList.collect { items ->
                    fillTexts(items)
                    mAdapter.submitList(items)
                }
            }
        }
    }

    private fun fillTexts(items: List<Item>) {
        var calories = 0.0
        var fat = 0.0
        var protein = 0.0
        var carbs = 0.0
        items.forEach {
            calories += it.calories
            fat += it.fat_total_g
            protein += it.protein_g
            carbs += it.carbohydrates_total_g
        }
        _binding.apply {
            caloriesTv.text = String.format("%.2f", calories)
            fatTv.text = String.format("%.2f", fat)
            proteinTv.text = String.format("%.2f", protein)
            carbsTv.text = String.format("%.2f", carbs)
        }
    }

    override fun deleteProduct(item: Item) {
        viewModel.deleteItem(item)
        Snackbar.make(requireView(), "Product deleted", Snackbar.LENGTH_SHORT)
            .setAction("Undo", View.OnClickListener {
                viewModel.insertItem(item)
            })
            .show()
    }
}