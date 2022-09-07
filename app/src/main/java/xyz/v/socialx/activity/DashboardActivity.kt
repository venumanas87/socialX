package xyz.v.socialx.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import xyz.v.socialx.R
import xyz.v.socialx.adapters.NewsAdapter
import xyz.v.socialx.databinding.ActivityDashboardBinding
import xyz.v.socialx.models.Article
import xyz.v.socialx.utils.Resource
import xyz.v.socialx.viewmodel.NewsViewModel

class DashboardActivity : AppCompatActivity() {
    lateinit var bind:ActivityDashboardBinding
    lateinit var nvm:NewsViewModel
    var list:ArrayList<Article> = ArrayList()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)
        auth = Firebase.auth
        nvm = ViewModelProvider(this)[NewsViewModel::class.java]
        nvm.getNews()
        setObservers()
        setListeners()

    }

    private fun setListeners() {
        bind.searchEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(textView.text);
                true
            }
            true
        }

        bind.signoutIv.setOnClickListener {
            if(auth.currentUser!=null){
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun performSearch(text: CharSequence) {
        nvm.searchNews(text.toString())
    }

    private fun setObservers() {
        nvm.newsLiveData.observe(this){
            when(it){
                is Resource.Error -> {
                    bind.progress.visibility = View.GONE
                }
                is Resource.Loading -> {
                    bind.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    bind.progress.visibility = View.GONE
                    it.data?.articles?.let { it1 ->

                        println("venu list ${it1.size} ")
                        list.clear()
                        for (art:Article in it1){
                            list.add(art)
                        }
                        bind.recyclerView.adapter  =  NewsAdapter(list)
                    }
                    bind.recyclerView.adapter  =  NewsAdapter(list)
                }
            }
        }
    }

    private fun showAlert(view: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure you want to Logout ?")
        val customLayout: View = layoutInflater.inflate(R.layout.logout_alert_dialog, null)
        builder.setView(customLayout)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val yesBtn: Button = customLayout.findViewById(R.id.yes_btn)
        val noBtn: Button = customLayout.findViewById(R.id.no_btn)

        yesBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        noBtn.setOnClickListener {
            dialog.dismiss()
        }

    }


}