package com.example.stackinhand.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {

    private lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding.root)
        initViewBinding()
        observeViewModel()
    }

    protected abstract fun initViewBinding(): T
    abstract fun observeViewModel()

}