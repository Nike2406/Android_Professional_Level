package com.bukin.androidprofessionallevel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bukin.androidprofessionallevel.databinding.FragmentChooseLevelBinding
import com.bukin.androidprofessionallevel.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            binding.buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            binding.buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            binding.buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            binding.buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
        /*
        * ChooseLevelFragmentDirections - класс, в котором хранятся все напрвления,
        * куда можно перейти из ChooseLevelFragment
        * */
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}