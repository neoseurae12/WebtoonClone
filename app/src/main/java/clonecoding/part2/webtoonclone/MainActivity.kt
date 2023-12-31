package clonecoding.part2.webtoonclone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import clonecoding.part2.webtoonclone.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), onTabNameChangedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        val sharedPreferences = getSharedPreferences(WebtoonWebViewFragment.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tab0Name = sharedPreferences.getString("tab0_name", "월요웹툰")
        val tab1Name = sharedPreferences.getString("tab1_name", "수요웹툰")
        val tab2Name = sharedPreferences.getString("tab2_name", "금요웹툰")

        binding.viewPager2.adapter = ViewPager2Adapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            run {
                /* customView 를 사용하지 않았다면
                tab.text = "position $position"
                 */

                val customTextView = TextView(this@MainActivity)
                customTextView.text = when (position) {
                    0 -> tab0Name
                    1 -> tab1Name
                    else -> tab2Name
                }
                customTextView.gravity = Gravity.CENTER

                tab.customView = customTextView
            }
        }.attach()
    }

    override fun tabNameChanged(position: Int, changedTabName: String) {
        val tab = binding.tabLayout.getTabAt(position)
        /* customView 를 사용하지 않았다면
        tab?.text = changedTabName
         */
        val textView = tab?.customView as TextView
        textView.text = changedTabName
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