package de.siebes.fabian.xkcd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil3.load
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

        val tvTitle: TextView = binding.txtTitle
        homeViewModel.title.observe(viewLifecycleOwner) {
            tvTitle.text = it
        }

        homeViewModel.dateStr.observe(viewLifecycleOwner) {
            binding.txtDate.text = it
        }

        homeViewModel.imgUrl.observe(viewLifecycleOwner) {
            binding.imgComic.load(it)
        }

        homeViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressComicLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.comicNumber.observe(viewLifecycleOwner) {
            if(it != null) {
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home) + " #$it"
            } else {
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home)
            }
        }

        homeViewModel.loadComic() // load current comic

        homeViewModel.altText.observe(viewLifecycleOwner) {
            val altText = it
            binding.imgComic.setOnLongClickListener {
                Toast.makeText(context, altText, Toast.LENGTH_SHORT).show()
                true
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}