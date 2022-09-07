package xyz.v.socialx.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import xyz.v.socialx.databinding.FragmentSignupBinding

class SignupFragment :  BaseFragment<FragmentSignupBinding>() {
    private lateinit var auth: FirebaseAuth

    override fun getViewBinding() = FragmentSignupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.signupBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            if(validateDetails()){
                auth.createUserWithEmailAndPassword(binding.emailEt.text.toString(),binding.passwordEt.text.toString())
                    .addOnCompleteListener(requireActivity()){ task->
                        if (task.isSuccessful) {
                            binding.progress.visibility = View.GONE
                            Toast.makeText(context, "Authentication Succeed, Sign In using details", Toast.LENGTH_SHORT).show()
                        } else {
                            binding.progress.visibility = View.GONE
                            Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(context, "Fill all details to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateDetails():Boolean {
        return binding.emailEt.text.isNotBlank()&&binding.passwordEt.text.isNotBlank()&&binding.phoneEt.text.isNotBlank()&&binding.nameEt.text.isNotBlank()
    }
}