package com.optimistic.vgpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import com.optimistic.vgpt.api_client.InterceptorHTTPClientCreator
import com.optimistic.vgpt.chooseClass.ChooseClass2


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InterceptorHTTPClientCreator.createInterceptorHTTPClient(this)
        //val service = RetrofitSdk.Builder().build(this).service

        val animation1 = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_up
        )
        logoSplash.startAnimation(animation1)

        Handler().postDelayed(
            {
                val intent=Intent(this, ChooseClass2::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            },3000
        )
    }
}
