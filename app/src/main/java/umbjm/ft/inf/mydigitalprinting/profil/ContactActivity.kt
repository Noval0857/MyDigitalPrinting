package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContactBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Untuk ADD NO telp
        val textViewPhoneNumber = findViewById<TextView>(R.id.call)
        val phoneNumber = textViewPhoneNumber.text.toString().trim()
        textViewPhoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
        // Untuk Pindah ke email
        val textViewEmail = findViewById<TextView>(R.id.goEmail)
        textViewEmail.setOnClickListener {
            sendEmail()
        }

        // Untuk Pindah ke WhatsApp
        val WhatsApp = findViewById<ImageView>(R.id.whatsapp)
        WhatsApp.setOnClickListener {
            openWhatsApp()
        }

        // Untuk Pindah ke Instagram
        val Instagram = findViewById<ImageView>(R.id.instagram)
        Instagram.setOnClickListener {
            openInstagram()
        }

        // Untuk Pindah ke halaman Youtube
        val Youtube = findViewById<ImageView>(R.id.youtube)
        Youtube.setOnClickListener {
            openYoutube()
        }
    }

    // Fungsi untuk pindah ke halaman Youtube
    private fun openYoutube() {
        val username = "Windah Basudara"
        val uri = Uri.parse("https://www.youtube.com/$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)

        val activities = packageManager.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe){
            startActivity(intent)
        } else {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Aplikasi Youtube tidak terinstall")
                .setContentText("Harap Install atau perbarui aplikasi Youtube Anda.")
                .show()
        }
    }

    // Fungsi untuk pindah ke halaman INSTAGRAM
    private fun openInstagram() {
        val username = "mydigital_printing"

        val uri = Uri.parse("https://www.instagram.com/$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)

        val activities = packageManager.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe){
            startActivity(intent)
        } else {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Aplikasi Instagram tidak terinstall")
                .setContentText("Harap Install atau perbarui aplikasi Instagram Anda.")
                .show()
        }
    }

    // FUNGSI UNTUK PINDAH KE WHATSAPP
    private fun openWhatsApp() {
        val PhoneNumber = "+6281549310752"

        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$PhoneNumber")

        val intent = Intent(Intent.ACTION_VIEW, uri)

        val activities = packageManager.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe){
            startActivity(intent)
        } else {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Tidak ada aplikasi Whatsapp")
                .setContentText("Harap Install atau perbarui aplikasi WhatsApp Anda.")
                .show()
        }
    }

    // FUNGSI UNTUK PINDAH KE EMAIL
    private fun sendEmail() {
        val email = "mydigitalumbjm@gmail.com"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

        val activities = packageManager.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe){
            startActivity(intent)
        } else {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Tidak ada aplikasi email")
                .setContentText("Harap Install atau perbarui aplikasi email Anda.")
                .show()
        }
    }
}