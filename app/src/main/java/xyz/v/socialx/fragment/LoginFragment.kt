package xyz.v.socialx.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import xyz.v.socialx.activity.DashboardActivity
import xyz.v.socialx.activity.GoogleSignInActivity
import xyz.v.socialx.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private lateinit var auth: FirebaseAuth
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.loginBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            if(validateDetails()){
                auth.signInWithEmailAndPassword(binding.emailEt.text.toString(),binding.passwordEt.text.toString())
                    .addOnCompleteListener(requireActivity()){ task->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "SignIn Succeed", Toast.LENGTH_SHORT).show()
                            binding.progress.visibility = View.GONE
                            startActivity(Intent(requireActivity(), DashboardActivity::class.java))
                        } else {
                            Toast.makeText(context, "SignIn Failed", Toast.LENGTH_SHORT).show()
                            binding.progress.visibility = View.GONE
                        }
                    }
            }else{
                binding.progress.visibility = View.GONE
            }
        }


        binding.googleIv.setOnClickListener {
            startActivity(Intent(requireActivity(), GoogleSignInActivity::class.java))
        }
    }

    private fun validateDetails():Boolean {
        return binding.emailEt.text.isNotBlank()&&binding.passwordEt.text.isNotBlank()
    }

}