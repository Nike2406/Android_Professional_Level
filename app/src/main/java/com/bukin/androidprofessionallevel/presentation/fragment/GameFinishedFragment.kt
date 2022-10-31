package com.bukin.androidprofessionallevel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bukin.androidprofessionallevel.databinding.FragmentGameFinishedBinding
import com.bukin.androidprofessionallevel.domain.entity.GameResult


class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * Т.к. у фрагмента нет метода onBackPressed(), нам нужно переопределить этот
        * метод у Activity
        * Чтобы не происходило учетек памяти, т.к. ссылка на фрагмент, еще остается
        * в памяти, следует вызвать перегруженный метод с viewLifecycleOwner
        * */
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        // При возвращении перскакиваем на выбор уровня
        requireActivity().supportFragmentManager
            .popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
//                    putSerializable(KEY_GAME_RESULT, gameResult)
                    /*
                    * Serializable - довольно медленный метод, т.к. вся
                    * реализация происходит под капотом
                    * Parcelable - более предпочтительный метод, т.к.
                    * реализация делается вручную, что ускоряет работу приложения
                    * (Для его работы требуется переопределить методы объекта)
                    * */
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}