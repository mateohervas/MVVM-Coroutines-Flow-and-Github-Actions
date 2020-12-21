package com.shadows.bitsodream.ui.auth


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.shadows.bitsodream.databinding.ActivitySplashBinding
import com.shadows.bitsodream.ui.BaseActivity
import com.shadows.bitsodream.ui.books.BooksActivity
import com.shadows.bitsodream.utils.presentation.viewBinding

class SplashActivity: AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        playAnimation()
    }

    private fun playAnimation(){
        binding.lotSplash.speed = 0.3f
        binding.lotSplash.playAnimation()
        goToProducts()
    }
    private fun goToProducts(){

        //Handler will wait for 3 seconds and then move to Books Activity

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, BooksActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        },3000)

    }


}