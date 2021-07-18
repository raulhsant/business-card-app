package me.estudos.businesscard.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import me.estudos.businesscard.App
import me.estudos.businesscard.R
import me.estudos.businesscard.constants.AppConstants
import me.estudos.businesscard.data.BusinessCard
import me.estudos.businesscard.databinding.ActivityAddBusinessCardBinding

class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }

    private fun insertListeners() {
        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.confirmButton.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.nomeInput.editText?.text.toString(),
                telefone = binding.telefoneInput.editText?.text.toString(),
                email = binding.emailInput.editText?.text.toString(),
                empresa = binding.empresaInput.editText?.text.toString(),
                fundoPersonalizado = binding.corInput.editText?.text.toString()
            )

            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()

        }
    }
}
