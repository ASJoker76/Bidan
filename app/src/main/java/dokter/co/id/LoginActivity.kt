package dokter.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dokter.co.id.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            val email = binding!!.etEmail.text.toString()
            val password = binding!!.etPassword.text.toString()
//            checkEmailAndPassword(this, email, password)
        }
    }
}