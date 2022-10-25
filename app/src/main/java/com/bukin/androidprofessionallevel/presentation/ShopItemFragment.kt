package com.bukin.androidprofessionallevel.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

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

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

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
        if (context is OnEditingFinishedListener){
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
    ): View? {
        /*
        * Для правильной передачи параметров во фрагмент исп. arguments типа Bundle?
        * requiredArguments() - not nullable
        * Этот метод прилетает в activity.onCreate(bundle: Bundle)
        * */

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    /*
    * До метода onViewCreated() View еще не создано и с ней нельзя работать
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    // Соответственно из activity:
    private fun launchEditMode() {
        // Получаем элмента по id
        viewModel.getShopItem(shopItemId)
        // Подписываемся на элемент
        viewModel.shopItem.observe(viewLifecycleOwner) {
            // После загрузки элмента присваиваем значения
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        // При клике на кнопку обновляем информацию
        buttonSave.setOnClickListener {
            viewModel.editShopItem(
                etName.text?.toString(), etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        // Не вставляем значения в поля ввода
        buttonSave.setOnClickListener {
            viewModel.addShopItem(
                etName.text?.toString(), etCount.text?.toString()
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
        // Подписываемся на остальные объекты для отображения ошибок
        // Т.к. жизненные циклы фрагмента и view отличаются,
        // то нельзя передать LifecycleOwner типа this
        // Передаем LifecycleOwner именно созданной view
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
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
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
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

    /*
    * Во фрагменте нет метода findViewById() (tilName = findViewById(R.id.til_name) не отработает)
    * Можно вызвать view из макета, и уже из него вызвать метод. Т.е. в конструктор
    * передать view.
    * */
    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
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