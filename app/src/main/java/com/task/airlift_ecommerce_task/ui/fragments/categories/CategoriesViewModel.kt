package com.task.airlift_ecommerce_task.ui.fragments.categories

import android.content.Context
import androidx.lifecycle.ViewModel
import com.task.airlift_ecommerce_task.data.DataRepository
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val dataRepository: DataRepository
) : ViewModel() {
    private val weakContext = WeakReference(context)

    /////////////////////////////////////////////////////////////////////
    ///////                 View setup related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                 Preferences related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                  Database related Code
    /////////////////////////////////////////////////////////////////////

    fun getAllCategories(onResponse: (categories: List<Category>?) -> Unit) {
        dataRepository.database.getAllCategories(onResponse)
    }

    /////////////////////////////////////////////////////////////////////
    ///////                   Remote related Code
    /////////////////////////////////////////////////////////////////////

}