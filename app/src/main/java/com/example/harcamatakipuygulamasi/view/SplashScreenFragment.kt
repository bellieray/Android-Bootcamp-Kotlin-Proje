package com.example.harcamatakipuygulamasi.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.harcamatakipuygulamasi.R
import kotlinx.android.synthetic.main.fragment_splash_screen.*


class SplashScreenFragment : Fragment() {

   lateinit var  animationForImage: Animation
   lateinit var  animationForText : Animation
   lateinit var  handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFirst()
            Navigation.findNavController(view).navigate(action)
        }, 4000)

        animationForImage = AnimationUtils.loadAnimation(activity, R.anim.splash_anim)
        animationForText = AnimationUtils.loadAnimation(activity, R.anim.splah_text)

        imageView.animation = animationForImage
        textGiris.animation = animationForText
    }
}