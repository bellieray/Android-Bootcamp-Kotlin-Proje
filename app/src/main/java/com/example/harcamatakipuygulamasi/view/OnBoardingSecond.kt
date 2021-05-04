package com.example.harcamatakipuygulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.harcamatakipuygulamasi.R
import kotlinx.android.synthetic.main.fragment_on_boarding_second.*

class OnBoardingSecond : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSecondBoard.setOnClickListener {
            val action = OnBoardingSecondDirections.actionOnBoardingSecondToOnBoardingThird()
            Navigation.findNavController(it).navigate(action)
        }
    }



}