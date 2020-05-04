package com.lambadam.projectwords.scores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.lambadam.projectwords.R
import com.lambadam.projectwords.basecontract.FragmentOnBackPressed
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.models.HighScore
import com.lambadam.projectwords.scores.adapter.DifficultyAdapter
import com.lambadam.projectwords.scores.adapter.ScoresAdapter
import com.lambadam.projectwords.scores.contract.DifficultyAdapterContract
import kotlinx.android.synthetic.main.fragment_scores.*

/**
 * A simple [Fragment] subclass.
 */
class ScoresFragment : Fragment(), DifficultyAdapterContract, FragmentOnBackPressed {
    private lateinit var viewModel: ScoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScoresViewModel::class.java)

        setRecyclerViewLayoutManagers()

        setObservers()

        viewModel.getDifficulties()
    }

    private fun setRecyclerViewLayoutManagers() {
        rvDifficulty.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvScores.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    private fun setObservers() {
        viewModel.difficultiesState.observe(viewLifecycleOwner, Observer { setDifficultyAdapter(it) })

        viewModel.scoresState.observe(viewLifecycleOwner, Observer { setScoresAdapter(it) })
    }

    private fun setDifficultyAdapter(list: List<Difficulty>) {
        rvDifficulty.adapter = DifficultyAdapter(list,this)
    }

    private fun setScoresAdapter( list: List<HighScore>) {
        if(list.isEmpty()) {
            rvScores.visibility = GONE
            tvError.visibility = VISIBLE
            tvError.text = resources.getString(R.string.no_scores_title)
        }
        rvScores.adapter = ScoresAdapter(list)
    }

    override fun onDifficultyClick(difficulty: Difficulty) {
        viewModel.getScores(difficulty)
        rvDifficulty.visibility = View.GONE
        rvScores.visibility = View.VISIBLE
    }

    override fun onBackPressed(): Boolean {
        return if (rvScores.visibility == View.VISIBLE || tvError.visibility == VISIBLE) {
            rvDifficulty.visibility = View.VISIBLE
            rvScores.visibility = View.GONE
            tvError.visibility = GONE
            true
        } else {
            false
        }
    }


}
