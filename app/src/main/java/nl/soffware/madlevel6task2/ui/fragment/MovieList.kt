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
import kotlinx.android.synthetic.main.movie_list_fragment.*
import nl.soffware.madlevel6task2.R
import nl.soffware.madlevel6task2.model.Movie
import nl.soffware.madlevel6task2.ui.adapter.MovieAdapter
import nl.soffware.madlevel6task2.view_model.MovieListViewModel

class MovieList : Fragment() {

    private val movies = arrayListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list_fragment, container, false)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner.visibility = View.GONE
        setupRecyclerView()
        observePopularMovies()
        addSearchListener()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(movies, ::onMovieClick)
        val gridLayoutManager = GridLayoutManager(this.context, 1, RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = gridLayoutManager
        // Add Global Layout Listener to calculate the span count.
        rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })
        rvMovies.adapter = movieAdapter
    }

    private fun addSearchListener() {
        btnSearch.setOnClickListener {
            val year = etYear.text.toString().toInt()
            spinner.visibility = View.VISIBLE
            viewModel.getPopularMoviesForYear(year)
        }
    }


    private fun observePopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
            spinner.visibility = View.GONE
        })

        // Observe the error message.
        viewModel.errorText.observe(viewLifecycleOwner, Observer {
            spinner.visibility = View.GONE
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun onMovieClick(movie: Movie) {
        viewModel.currentMovie = movie
        this.findNavController().navigate(R.id.action_movieList_to_movieDetail)
    }

    /**
     * Calculate the number of spans for the recycler view based on the recycler view width.
     * @return int number of spans.
     */
    private fun calculateSpanCount(): Int {
        val viewWidth = rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val cardViewMargin = resources.getDimension(R.dimen.margin_medium)
        val spanCount = Math.floor((viewWidth / (cardViewWidth + cardViewMargin)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }


}