package com.example.stackinhand.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.lifecycleScope
import com.example.stackinhand.R
import com.example.stackinhand.constants.AppConstants
import com.example.stackinhand.databinding.ActivitySplashBinding
import com.example.stackinhand.ui.base.BaseActivity
import com.example.stackinhand.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private var job : Job? = null

    override fun initViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startJob()
    }

    private fun startJob() {
        job = lifecycleScope.launch {
            delay(AppConstants.SPLASH_TIME)
            goToMainActivity()
        }
    }

    override fun observeViewModel() { }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}