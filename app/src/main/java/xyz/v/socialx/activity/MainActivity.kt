package xyz.v.socialx.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.transition.FragmentTransitionSupport
import xyz.v.socialx.databinding.ActivityMainBinding
import xyz.v.socialx.fragment.LoginFragment
import xyz.v.socialx.fragment.SignupFragment

class MainActivity : AppCompatActivity() {
    lateinit var bind:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        activateLogin()

        bind.loginBtn.setOnClickListener {
            activateLogin()
        }

        bind.signupBtn.setOnClickListener {
            activateSignUp()
        }
    }

    private fun activateLogin() {
        bind.loginBtn.isEnabled = false
        bind.signupBtn.isEnabled = true
        supportFragmentManager.beginTransaction()
            .replace(bind.fragmentContainerView.id,LoginFragment())
            .commit()
    }

    private fun activateSignUp() {
        bind.loginBtn.isEnabled = true
        bind.signupBtn.isEnabled = false
        supportFragmentManager.beginTransaction()
            .replace(bind.fragmentContainerView.id,SignupFragment())
            .commit()
    }
}