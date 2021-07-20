package me.estudos.businesscard.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import me.estudos.businesscard.App
import me.estudos.businesscard.R
import me.estudos.businesscard.data.BusinessCard
import me.estudos.businesscard.databinding.ActivityMainBinding
import me.estudos.businesscard.util.Image


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { BusinessCardAdapter() }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.cardsView.adapter = adapter
        getAllBusinessCard()
        insertListeners()
    }

    private fun insertListeners() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }

        adapter.listenerShare = { card -> Image.share(this@MainActivity, card) }

        adapter.listenerDelete =
            { businessCard: BusinessCard, position: Int -> deleteCard(businessCard, position) }
    }

    private fun deleteCard(businessCard: BusinessCard, position: Int) {
        mainViewModel.delete(businessCard)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, adapter.currentList.size)
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this, { cards -> adapter.submitList(cards) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.business_card_menu, menu)
        return true
    }
}
