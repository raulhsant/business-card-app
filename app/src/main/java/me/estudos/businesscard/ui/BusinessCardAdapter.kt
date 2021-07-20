package me.estudos.businesscard.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.estudos.businesscard.R
import me.estudos.businesscard.data.BusinessCard
import me.estudos.businesscard.databinding.ItemBusinessCardBinding

class BusinessCardAdapter :
    ListAdapter<BusinessCard, BusinessCardAdapter.ViewHolder>(DiffCallback()) {

    var listenerShare: (View) -> Unit = {}
    var listenerDelete: (BusinessCard, Int) -> Unit = { _: BusinessCard, _: Int -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ItemBusinessCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(businessCard: BusinessCard) {
            binding.nomeText.text = businessCard.nome
            binding.telefoneText.text = businessCard.telefone
            binding.emailText.text = businessCard.email
            binding.nomeEmpresaText.text = businessCard.empresa
            binding.card.setCardBackgroundColor(Color.parseColor(businessCard.fundoPersonalizado))
            binding.container.setOnClickListener { listenerShare(binding.card) }
            binding.dotsMenu.setOnClickListener { view ->
                val popupMenu = PopupMenu(view.context, binding.dotsMenu)
                popupMenu.inflate(R.menu.business_card_menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    if (item.itemId == R.id.share) {
                        listenerShare(binding.card)
                        return@OnMenuItemClickListener true
                    } else if (item.itemId == R.id.delete) {
                        listenerDelete(businessCard, adapterPosition)
                        return@OnMenuItemClickListener true
                    }
                    return@OnMenuItemClickListener false
                })
                popupMenu.show()
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<BusinessCard>() {
    override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem == newItem

    override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard) =
        oldItem.id == newItem.id

}
