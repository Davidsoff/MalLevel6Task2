package nl.soffware.madlevel6task2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.movie_list_fragment.*
import nl.soffware.madlevel6task2.R
import nl.soffware.madlevel6task2.model.Movie
import nl.soffware.madlevel6task2.ui.adapter.MovieAdapter
import nl.soffware.madlevel6task2.view_model.MovieListViewModel

class MovieDetail : Fragment() {

    private val viewModel: MovieListViewModel by activityViewModels()
    private lateinit var movie : Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = viewModel.currentMovie
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        tvRating.text = movie.vote_average
        tvReleaseDate.text = movie.release_date

        Glide.with(requireContext()).load(movie.getPosterUrl()).into(ivPoster)
        Glide.with(requireContext()).load(movie.getBackdropUrl()).into(ivBackdrop)
    }

}