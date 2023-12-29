package clonecoding.part2.webtoonclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import clonecoding.part2.webtoonclone.databinding.FragmentWebtoonwebviewBinding

class WebtoonWebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebtoonwebviewBinding

    // super.onBackPressed()의 deprecated 로 인한 코드 변경 -- 검증 및 최적화 필요
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack())
                    binding.webView.goBack()
                else {
                    remove()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebtoonwebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://comic.naver.com/webtoon/detail?titleId=819217&no=1&week=mon")
    }

    /* super.onBackPressed()의 deprecated 로 인한 코드 변경
    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    fun goBack() {
        binding.webView.goBack()
    }
     */
}