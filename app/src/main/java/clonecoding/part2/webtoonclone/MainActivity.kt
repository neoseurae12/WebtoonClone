package clonecoding.part2.webtoonclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clonecoding.part2.webtoonclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonA.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, WebtoonWebViewFragment())
                commit()
            }
        }

        binding.buttonB.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, BFragment())
                commit()
            }
        }
    }

    /* super.onBackPressed()의 deprecated 로 인한 코드 변경
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[0]
        if (currentFragment is WebtoonWebViewFragment) {
            if (currentFragment.canGoBack())
                currentFragment.goBack()
            else
                super.onBackPressed()
        } else
            super.onBackPressed()
    }
     */
}