package clonecoding.part2.webtoonclone

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.edit
import clonecoding.part2.webtoonclone.databinding.FragmentWebtoonwebviewBinding

class WebtoonWebViewFragment(private val position: Int) : Fragment() {

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

        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar) { url ->
            activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)?.edit {
                putString("tab$position", url)
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://comic.naver.com/webtoon/detail?titleId=819217&no=1&week=mon")


        binding.backToLastButton.setOnClickListener {
            val sharedPreference = activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)
            val url = sharedPreference?.getString("tab$position", "")


            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "돌아갈 마지막 시점이 없습니다.", Toast.LENGTH_LONG).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }
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