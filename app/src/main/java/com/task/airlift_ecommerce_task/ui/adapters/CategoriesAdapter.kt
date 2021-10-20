package com.task.airlift_ecommerce_task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.ItemCategoryBinding
import com.task.airlift_ecommerce_task.databinding.ItemCategoryGridBinding

class CategoriesAdapter(
    private val objects: ArrayList<Category>,
    private val isGrid: Boolean = false
) : RecyclerView.Adapter<ViewHolder>() {
    private var onItemTouchListener: OnItemTouchListener? = null

    companion object {
        const val ITEM_TYPE_LINEAR = 0
        const val ITEM_TYPE_GRID = 1
    }

    /////////////////////////////////////////////////////////////////////
    ///////                  Adapter Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return if (i == ITEM_TYPE_LINEAR) {
            val binding: ItemCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_category,
                viewGroup,
                false
            )

            ItemCategoryViewHolder(binding)
        } else {
            val binding: ItemCategoryGridBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_category_grid,
                viewGroup,
                false
            )

            ItemCategoryGridViewHolder(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p: Int) {
        val position = viewHolder.adapterPosition

        val item = objects[position]
        if (viewHolder.itemViewType == ITEM_TYPE_LINEAR) {
            (viewHolder as ItemCategoryViewHolder).binding.apply {
                this.position = position
                this.item = item
                this.lytParent.setOnClickListener {
                    onItemTouchListener?.onClick(position, item)
                }

                executePendingBindings()
            }
        } else {
            (viewHolder as ItemCategoryGridViewHolder).binding.apply {
                this.position = position
                this.item = item
                this.lytParent.setOnClickListener {
                    onItemTouchListener?.onClick(position, item)
                }

                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return objects.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (!isGrid) {
            ITEM_TYPE_LINEAR
        } else {
            ITEM_TYPE_GRID
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////                     Other Methods
    /////////////////////////////////////////////////////////////////////

    fun setData(data: List<Category>) {
        val diffCallback = CategoriesDiffCallback(objects, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        objects.clear()
        objects.addAll(data)

        diffResult.dispatchUpdatesTo(this)
    }

    fun setListener(onItemTouchListener: OnItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener
    }

    /////////////////////////////////////////////////////////////////////
    ///////                       Diff Callback
    /////////////////////////////////////////////////////////////////////

    class CategoriesDiffCallback(
        private val oldList: List<Category>,
        private val newList: List<Category>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////                        View Holder
    /////////////////////////////////////////////////////////////////////

    class ItemCategoryViewHolder(val binding: ItemCategoryBinding) :
        ViewHolder(binding.root)

    class ItemCategoryGridViewHolder(val binding: ItemCategoryGridBinding) :
        ViewHolder(binding.root)

    /////////////////////////////////////////////////////////////////////
    ///////                    Item Touch Listener
    /////////////////////////////////////////////////////////////////////

    interface OnItemTouchListener {
        fun onClick(position: Int, category: Category)
    }
}