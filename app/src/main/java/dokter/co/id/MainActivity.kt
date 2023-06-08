package dokter.co.id

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dokter.co.id.databinding.ActivityMainBinding
import dokter.co.id.fragment.HomeFragment
import dokter.co.id.fragment.ProfileFragment
import dokter.co.id.viewmodel.ViewModelMain


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelMain: ViewModelMain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        viewModelMain.setFragmentManager(supportFragmentManager)


        binding.btmView.menu.getItem(0).isCheckable = true
        viewModelMain.setFragment(HomeFragment())

        binding.btmView.setOnNavigationItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.home -> {
                    viewModelMain.setFragment(HomeFragment())
                    true
                }

                R.id.account -> {
                    viewModelMain.setFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }
}