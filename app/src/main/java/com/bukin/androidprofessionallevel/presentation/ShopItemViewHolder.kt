package com.bukin.androidprofessionallevel.presentation

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/*
* Т.к. все классы binding наследуются от ViewDataBinding,
* так что посредстом полиморфизама можно исп. род-й класс
* */
class ShopItemViewHolder(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)