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
import com.task.airlift_ecommerce_task.data.db.entities.Product
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.ItemCartBinding
import com.task.airlift_ecommerce_task.databinding.ItemProductBinding
import com.task.airlift_ecommerce_task.databinding.ItemProductGridBinding

class CartProductsAdapter(
    private val objects: ArrayList<Product>
) : RecyclerView.Adapter<CartProductsAdapter.ItemCartViewHolder>() {
    private var onItemTouchListener: OnItemTouchListener? = null

    /////////////////////////////////////////////////////////////////////
    ///////                  Adapter Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemCartViewHolder {
        val binding: ItemCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_cart,
            viewGroup,
            false
        )

        val viewHolder = ItemCartViewHolder(binding)
        viewHolder.lifecycle.currentState = Lifecycle.State.CREATED
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ItemCartViewHolder, p: Int) {
        viewHolder.lifecycle.currentState = Lifecycle.State.RESUMED
        val position = viewHolder.adapterPosition

        val item = objects[position]

        viewHolder.binding.apply {
            this.position = position
            this.item = item
            this.lytParent.setOnClickListener {
                onItemTouchListener?.onClick(position, item)
            }
            this.btnAddToCart.setOnClickListener {
                onItemTouchListener?.onAddToCartClick(position, item)
            }
            this.ibnDelete.setOnClickListener {
                onItemTouchListener?.onDelete(position, item)
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

    override fun getItemCount(): Int {
        return objects.size
    }

    /////////////////////////////////////////////////////////////////////
    ///////                     Other Methods
    /////////////////////////////////////////////////////////////////////

    fun setData(data: List<Product>) {
        objects.clear()
        objects.addAll(data)

        notifyDataSetChanged()
    }

    fun setListener(onItemTouchListener: OnItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener
    }

    fun removeItem(position: Int) {
        objects.removeAt(position)

        notifyItemRemoved(position)
    }

    /////////////////////////////////////////////////////////////////////
    ///////                        View Holder
    /////////////////////////////////////////////////////////////////////

    class ItemCartViewHolder(val binding: ItemCartBinding) :
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
        fun onClick(position: Int, product: Product)

        fun onDelete(position: Int, product: Product)

        fun onAddToCartClick(position: Int, product: Product)

        fun onRemoveFromCartClick(position: Int, product: Product)
    }
}