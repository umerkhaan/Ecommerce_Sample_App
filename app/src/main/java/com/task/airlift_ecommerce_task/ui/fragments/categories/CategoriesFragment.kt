package com.task.airlift_ecommerce_task.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.databinding.FragmentCategoriesBinding
import com.task.airlift_ecommerce_task.ui.adapters.CategoriesAdapter
import com.task.airlift_ecommerce_task.ui.fragments.home.HomeFragmentDirections
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import com.task.airlift_ecommerce_task.utils.misc.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private lateinit var categoriesAdapter: CategoriesAdapter

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)

        setupViews()
        setupViewsListeners()

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {
        activity?.let { a ->
            val gridLayoutManager = GridLayoutManager(a, Constants.GRID_COLUMNS)
            gridLayoutManager.isItemPrefetchEnabled = false

            categoriesAdapter = CategoriesAdapter(ArrayList(), true)

            binding.rvCategories.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = categoriesAdapter
                layoutManager = gridLayoutManager
                isNestedScrollingEnabled = false
            }

            categoriesViewModel.getAllCategories { categories ->
                categories?.let {
                    categoriesAdapter.setData(it)
                }
            }
        }
    }

    private fun setupViewsListeners() {
        categoriesAdapter.setListener(object : CategoriesAdapter.OnItemTouchListener {
            override fun onClick(position: Int, category: Category) {
                val action =
                    CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(
                        category.title
                    )
                findNavController().navigate(action)
            }
        })
    }
}