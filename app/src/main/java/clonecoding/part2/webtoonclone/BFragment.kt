package clonecoding.part2.webtoonclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import clonecoding.part2.webtoonclone.databinding.FragmentABinding
import clonecoding.part2.webtoonclone.databinding.FragmentBBinding

class BFragment : Fragment() {

    private lateinit var binding: FragmentBBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBBinding.inflate(inflater)
        return binding.root
    }
}