package clonecoding.part2.webtoonclone

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(private val mainActivity: MainActivity): FragmentStateAdapter(mainActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WebtoonWebViewFragment(position, "https://comic.naver.com/webtoon?tab=mon").apply {
                listener = mainActivity
            }
            1 -> WebtoonWebViewFragment(position, "https://comic.naver.com/webtoon?tab=wed").apply {
                listener = mainActivity
            }
            else -> WebtoonWebViewFragment(position, "https://comic.naver.com/webtoon?tab=fri").apply {
                listener = mainActivity
            }
        }
    }
}