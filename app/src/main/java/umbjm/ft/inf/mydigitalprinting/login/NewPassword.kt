package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityForgot1Binding

class NewPassword : AppCompatActivity() {

    // Mengambil Fungsi Library
    lateinit var binding: ActivityForgot1Binding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityForgot1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Memberikan aksi ke btnSend
        binding.btnSend.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val edtemail = binding.edtemail

            // Jika email kosong maka akan menampilkan pesan error
            if (email.isEmpty()){
                binding.edtemail.error = "Email tidak boleh kosong"
                binding.edtemail.requestFocus()
                return@setOnClickListener
            }

            // Jika pengisian email tidak sesuai dengan format email maka akan menampilkan pesanan error
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtemail.error = "Email tidak sesuai dengan format email"
                binding.edtemail.requestFocus()
                return@setOnClickListener
            }

            // Fungsi untuk memberikan reset password dengan cara mengirim link reset password ke email pengguna
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("New_password", "success")
                    startActivity(intent)
                } else {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Warning")
                        .setContentText("Reset Password Gagal Dikirim")
                        .show()
                }
            }
        }
    }
}