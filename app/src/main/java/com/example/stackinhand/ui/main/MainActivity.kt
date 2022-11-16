package com.example.stackinhand.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stackinhand.R
import com.example.stackinhand.databinding.ActivityMainBinding
import com.example.stackinhand.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() { }
}