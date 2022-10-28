package com.bukin.androidprofessionallevel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.databinding.FragmentGameBinding
import com.bukin.androidprofessionallevel.domain.entity.GameResult
import com.bukin.androidprofessionallevel.domain.entity.GameSettings
import com.bukin.androidprofessionallevel.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun parseArgs() {
        // requireArguments() - возвращает необходимые агрументы, когда фрагмент проинициализирован
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener {
            launchGameFinished(
                GameResult(
                    winner = true,
                    countOfRightAnswers = 10,
                    countOfQuestions = 11,
                    gameSettings = GameSettings(
                        11,
                        minCountOfRightAnswers = 10,
                        minPercentOfRightAnswers = 10,
                        gameTimeInSeconds = 50
                    )
                )
            )
        }
    }

    private fun launchGameFinished(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        /*
        * Так Level - класс, то стандартные типы putExtra() не подходят,
        * поэтому нужно использовать интерфейс Serializable, который
        * должен реализовывать класс Level
        * */
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}