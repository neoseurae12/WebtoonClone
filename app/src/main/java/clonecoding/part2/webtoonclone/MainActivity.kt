package clonecoding.part2.webtoonclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import clonecoding.part2.webtoonclone.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager2.adapter = ViewPager2Adapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            run {
                //tab.text = "position $position"

                val customTextView = TextView(this@MainActivity)
                customTextView.text = "position $position"
                customTextView.gravity = Gravity.CENTER

                tab.customView = customTextView
            }
        }.attach()
    }

    /* super.onBackPressed()의 deprecated 로 인한 코드 변경
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[binding.viewPager2.currentItem]
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