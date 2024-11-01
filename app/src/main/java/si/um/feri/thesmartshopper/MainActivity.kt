package si.um.feri.thesmartshopper

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.internal.ContextUtils.getActivity

import si.um.feri.thesmartshopper.recycler.SuggestActivity
import si.um.feri.thesmartshopper.ui.delete.DeleteActivity
import si.um.feri.thesmartshopper.ui.login.LoginActivity
import si.um.feri.thesmartshopper.ui.reset.ResetActivity
import timber.log.Timber
import java.util.*
import java.util.jar.Manifest

const val MY_SP_FILE_NAME = "shared.data"
lateinit var us: String

class MainActivity : AppCompatActivity() {

    private lateinit var user : TextView
    private lateinit var shopsAndArticles : Button
    private lateinit var register : Button
    private lateinit var resetPass : Button
    private lateinit var deleteAcc : Button
    private lateinit var logOut : Button
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = findViewById(R.id.user)
        shopsAndArticles = findViewById(R.id.shops_and_articles)
        register = findViewById(R.id.register)
        resetPass = findViewById(R.id.reset_pass)
        deleteAcc = findViewById(R.id.delete_acc)
        logOut = findViewById(R.id.logout_button)

        initShared()
        if (!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    register.visibility = INVISIBLE
                    val data: Intent? = result.data
                    val n = data?.getStringExtra("name")

                    user.text = getString(R.string.welcome, n)
                    us = n!!
                    user.visibility = VISIBLE
                    shopsAndArticles.visibility = VISIBLE
                    resetPass.visibility = VISIBLE
                    deleteAcc.visibility = VISIBLE
                    logOut.visibility = VISIBLE
                }
            }

        shopsAndArticles.setOnClickListener {
            val i = Intent(this, SuggestActivity::class.java)
            startActivity(i)
        }


        register.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            getContent.launch(intent)
        }

        resetPass.setOnClickListener {
            val intent = Intent(this, ResetActivity::class.java)
            startActivity(intent)
        }

        deleteAcc.setOnClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

        logOut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finishAffinity()
        }
    }

    private fun saveID(id: String) {
        with(sharedPref.edit()) {
            putString("ID", id)
            apply()
        }
    }

    private fun initShared() {
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun containsID(): Boolean {
        return sharedPref.contains("ID")
    }
}