package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cn.pedant.SweetAlert.SuccessTickView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityProfilBinding
import umbjm.ft.inf.mydigitalprinting.login.ChangepasswordActivity
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    // Untuk mengakses Library
    lateinit var binding: ActivityProfilBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Untuk pindah ke halaman change password
        binding.changepassword.setOnClickListener {
            val intent = Intent(this, ChangepasswordActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        // Memberikan Aksi ke btnkeluar
        binding.btnKeluar.setOnClickListener {
            // Mengambil fungsi btnlogout dan menampungnya ke btnkeluar
            btnLogout()
        }
    }

    // mengambil data yang ada pada change password
    override fun onResume() {
        super.onResume()

        // menampung data kedalam variable
        val pesan = intent.getStringExtra("data_masuk")
        // jika sukses maka akan menampilkan sebuah alert
        if (pesan == "success"){
            // ini alert yang akan ditampilkan
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText("Berhasil update password")
                .show()
            // data akan diremove jika sudah berhasil, agar dapat diakases ulang
            intent.removeExtra("data_masuk")
        }
    }

    // Memberikan fungsi ke btnlogout
    private fun btnLogout() {
        // mengambil Authentikasi di firebaseAuth
        auth = FirebaseAuth.getInstance()
        // Alert
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Konfirmasi Logout")
            .setContentText("Apakah anda yakin ingin logout?")
            .setConfirmText("Ya")
            .setCancelText("Tidak")
            .setConfirmClickListener { dialog ->
                dialog.dismissWithAnimation()
                // Data penggunakan Logout dari database
                auth.signOut()

                // Alert Berhasil keluar
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Berhasil Logout")
                    .setConfirmText("Ok")
                    .setConfirmClickListener { SuccessDialog ->
                        SuccessDialog.dismissWithAnimation()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .show()
            }
            // Alert tidak jadi keluar
            .setCancelClickListener { dialog ->
                dialog.dismissWithAnimation()
                val root = findViewById<View>(android.R.id.content)
                val snackbar = Snackbar.make(root, "Anda tidak logout", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            .show()
    }
}