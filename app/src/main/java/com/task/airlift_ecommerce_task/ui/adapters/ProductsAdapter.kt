package com.task.airlift_ecommerce_task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.ItemProductBinding
import com.task.airlift_ecommerce_task.databinding.ItemProductGridBinding

class ProductsAdapter(
    private val objects: ArrayList<ResponseProduct>,
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
            val binding: ItemProductBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_product,
                viewGroup,
                false
            )

            val viewHolder = ItemProductViewHolder(binding)
            viewHolder.lifecycle.currentState = Lifecycle.State.CREATED
            viewHolder
        } else {
            val binding: ItemProductGridBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_product_grid,
                viewGroup,
                false
            )

            val viewHolder = ItemProductGridViewHolder(binding)
            viewHolder.lifecycle.currentState = Lifecycle.State.CREATED
            viewHolder
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p: Int) {
        val position = viewHolder.adapterPosition

        val item = objects[position]
        if (viewHolder.itemViewType == ITEM_TYPE_LINEAR) {
            (viewHolder as ItemProductViewHolder).binding.apply {
                viewHolder.lifecycle.currentState = Lifecycle.State.RESUMED

                this.position = position
                this.item = item
                this.lytParent.setOnClickListener {
                    onItemTouchListener?.onClick(position, item)
                }
                this.btnAddToCart.setOnClickListener {
                    onItemTouchListener?.onAddToCartClick(position, item)
                }
                this.ibnAdd.setOnClickListener {
                    onItemTouchListener?.onAddToCartClick(position, item)
                }
                this.ibnRemove.setOnClickListener {
                    onItemTouchListener?.onRemoveFromCartClick(position, item)
                }

                lifecycleOwner = viewHolder

                executePendingBindings()
            }
        } else {
            (viewHolder as ItemProductGridViewHolder).binding.apply {
                viewHolder.lifecycle.currentState = Lifecycle.State.RESUMED

                this.position = position
                this.item = item
                this.lytParent.setOnClickListener {
                    onItemTouchListener?.onClick(position, item)
                }
                this.btnAddToCart.setOnClickListener {
                    onItemTouchListener?.onAddToCartClick(position, item)
                }
                this.ibnAdd.setOnClickListener {
                    onItemTouchListener?.onAddToCartClick(position, item)
                }
                this.ibnRemove.setOnClickListener {
                    onItemTouchListener?.onRemoveFromCartClick(position, item)
                }

                lifecycleOwner = viewHolder

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

    fun setData(data: List<ResponseProduct>) {
        objects.clear()
        objects.addAll(data)

        notifyDataSetChanged()
    }

    fun setListener(onItemTouchListener: OnItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener
    }

    /////////////////////////////////////////////////////////////////////
    ///////                        View Holder
    /////////////////////////////////////////////////////////////////////

    class ItemProductViewHolder(val binding: ItemProductBinding) :
        ViewHolder(binding.root), LifecycleOwner {
        var lifecycle = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return lifecycle
        }
    }

    class ItemProductGridViewHolder(val binding: ItemProductGridBinding) :
        ViewHolder(binding.root), LifecycleOwner {
        var lifecycle = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return lifecycle
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////                    Item Touch Listener
    /////////////////////////////////////////////////////////////////////

    interface OnItemTouchListener {
        fun onClick(position: Int, product: ResponseProduct)

        fun onAddToCartClick(position: Int, product: ResponseProduct)

        fun onRemoveFromCartClick(position: Int, product: ResponseProduct)
    }
}