package com.alfikri.signhandtranslator.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.utils.RESULT_LOGIN

class SplashFragment : Fragment() {

    private val displayLength = 1500

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.actionBar?.hide()

        Handler(getMainLooper()).postDelayed({
            view.findNavController().navigate(R.id.action_splash_to_login)
        }, displayLength.toLong())

        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_LOGIN) {
                view.findNavController().navigate(R.id.action_splash_to_login)
            }

        }
    }

}