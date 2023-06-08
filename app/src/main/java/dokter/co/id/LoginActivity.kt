package dokter.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dokter.co.id.databinding.ActivityLoginBinding
import dokter.co.id.viewmodel.ViewModelMain

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)

        binding.btnLogin.setOnClickListener {
            val email = binding!!.etEmail.text.toString()
            val password = binding!!.etPassword.text.toString()
            viewModelMain.checkEmailAndPassword(this, email, password)
        }
    }
}