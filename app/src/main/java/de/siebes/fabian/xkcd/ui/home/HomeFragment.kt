package de.siebes.fabian.xkcd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import de.siebes.fabian.xkcd.R
import de.siebes.fabian.xkcd.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.comicNumber.observe(viewLifecycleOwner) {
            if (it != null) {
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.title_home) + " #$it"
            } else {
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.title_home)
            }
        }

        homeViewModel.title.observe(viewLifecycleOwner) {
            binding.txtTitle.text = it
        }

        homeViewModel.dateStr.observe(viewLifecycleOwner) {
            binding.txtDate.text = it
        }

        homeViewModel.imgUrl.observe(viewLifecycleOwner) {
            binding.imgComic.load(it) {
                crossfade(true)
                placeholder(R.drawable.loading_image_black_24dp)
            }
        }

        homeViewModel.altText.observe(viewLifecycleOwner) {
            TooltipCompat.setTooltipText(binding.imgComic, it)
        }

        homeViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressComicLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.txtError.visibility = View.VISIBLE
                binding.imgError.visibility = View.VISIBLE
                binding.txtTitle.visibility = View.GONE
                binding.txtDate.visibility = View.GONE
                binding.imgComic.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.GONE
                binding.imgError.visibility = View.GONE
                binding.txtTitle.visibility = View.VISIBLE
                binding.txtDate.visibility = View.VISIBLE
                binding.imgComic.visibility = View.VISIBLE
            }
        }

        homeViewModel.loadComic() // load current comic

        binding.clPreviousComic.setOnClickListener {
            homeViewModel.loadComic(homeViewModel.comicNumber.value?.minus(1)) // if loaded comic is null -> comicNumber is null -> load current comic
        }

        binding.clNextComic.setOnClickListener {
            homeViewModel.loadComic(homeViewModel.comicNumber.value?.plus(1)) // if loaded comic is null -> comicNumber is null -> load current comic
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}