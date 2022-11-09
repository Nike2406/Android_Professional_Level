package com.bukin.androidprofessionallevel.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bukin.androidprofessionallevel.databinding.FragmentShopItemBinding
import com.bukin.androidprofessionallevel.domain.ShopItem

/*
* Передача параметров во фрагмент(так делать нельзя, см. ниже) будет работать только
* при первом создании фрагемента (!)
*
* При пересоздании фрагмента (например при повороте экрна),
* вызывается пустой конструктор фрагмента
* */
class ShopItemFragment() : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    // Инициализация объекта
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    /*
    * Метод прикрепления к activity
    * context - то activity, к которому прикрепляемся
    * */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            // Показываем, что в MainActivity нужно реализовать интерфейс
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    /*
        * При создании фрагемента, сначала он прикрепляется к activity
        * Потом из макета создается View
        * После создается View
        * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        /*
        * Для правильной передачи параметров во фрагмент исп. arguments типа Bundle?
        * requiredArguments() - not nullable
        * Этот метод прилетает в activity.onCreate(bundle: Bundle)
        * */
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    * До метода onViewCreated() View еще не создано и с ней нельзя работать
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    // Соответственно из activity:
    private fun launchEditMode() {
        // Получаем элмента по id
        viewModel.getShopItem(shopItemId)
        // При клике на кнопку обновляем информацию
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(
                binding.etName.text?.toString(), binding.etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        // Не вставляем значения в поля ввода
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(
                binding.etName.text?.toString(), binding.etCount.text?.toString()
            )
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        /*
        Если работа с экраном завершена, закрываем его
        У фргамента нет метода finish(), поэтому, в данном случае, нужно
        вызвать метод onBackPressed() у активити, к которой прикреплен данный
        фрагмент
        */
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            /*
            * Т.к. activity может вернуть любую activity, то делаем явный каст в MainActivity
            * ! В данном случаое, мы можем использовать только MainActivity и фрагмент может
            * управлять activity -_-
            * Вместо этого следует использовать интерфейс
            * */
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun addTextChangeListeners() {
        // Скрывание ошибки при вводе текста
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    /*
    * Во фрагменте не используются intent, поэтому можно исмользовать метод
    * requiredActivity(), который возвращает ссылку на activity, к которому
    * прикреплен данный фрагмент (!requireActivity().intent.hasExtra(EXTRA_SCREEN_MODE))
    *
    * В данном случае это плохой вариант, т.к. фрагмент будет требовать от
    * activity доп параметров. Т.е. activity не может быть запущена определенным образом
    * */
    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw java.lang.RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw java.lang.RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw java.lang.RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
//            val args = Bundle()
            // Вставляем в Bundle параметры
//            args.putString(EXTRA_SCREEN_MODE, MODE_ADD)
//            val fragment = ShopItemFragment()
//            fragment.arguments = args
//            return fragment
            // apply{} - сначала создается объект, а потом применяются к нему методы
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}