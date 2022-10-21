package com.bukin.androidprofessionallevel.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.R

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        // Из activity нельзя изменять ViewModel
        viewModel.errorInputName.value = false
    }
}