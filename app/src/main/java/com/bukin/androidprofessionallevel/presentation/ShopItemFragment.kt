package com.bukin.androidprofessionallevel.presentation

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
* Передача параметров во фрагмент
* */
class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

//    private var screenMode = MODE_UNKNOWN
//    private var shopItemId = ShopItem.UNDEFINED_ID

    /*
    * При создании фрагемента, сначала он прикрепляется к activity
    * Потом из макета создается View
    * После создается View
    * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    /*
    * До метода onViewCreated() View еще не создано и с ней нельзя работать
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
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
        // Если работа с экраном завершена, закрываем его
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            finish() // Завершение view
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
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw java.lang.RuntimeException("Param screen mode is absent")
        }

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw java.lang.RuntimeException("Param shop item id is absent")
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


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}