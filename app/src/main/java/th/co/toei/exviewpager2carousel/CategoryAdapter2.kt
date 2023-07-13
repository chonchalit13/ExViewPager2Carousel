package th.co.toei.exviewpager2carousel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import th.co.toei.exviewpager2carousel.databinding.ViewHolderCategory2Binding
import th.co.toei.exviewpager2carousel.databinding.ViewHolderCategoryBinding

class CategoryAdapter2 : RecyclerView.Adapter<CategoryAdapter2.ViewHolder>() {

    var list: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewHolderCategory2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewHolderCategory2Binding.bind(view)

        fun bindView(data: Category, position: Int) {
            binding.tvTitle.text = data.name
            itemView.setOnClickListener {
//                binding.motionLayout.transitionToEnd()
                Log.e("toeiPOSI", "$position")
            }
        }
    }

    fun setListData(data: List<Category>) {
        list = data
        notifyDataSetChanged()
    }
}