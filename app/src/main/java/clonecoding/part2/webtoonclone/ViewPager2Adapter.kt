package clonecoding.part2.webtoonclone

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(mainActivity: MainActivity): FragmentStateAdapter(mainActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WebtoonWebViewFragment(position)
            1 -> WebtoonWebViewFragment(position)
            else -> WebtoonWebViewFragment(position)
        }
    }
}