package umbjm.ft.inf.mydigitalprinting.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.preferen.Constant
import umbjm.ft.inf.mydigitalprinting.preferen.SharedPreferences
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Untuk mengakses penuh Library Binding
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var shp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shp = SharedPreferences(this)

        checkLoginStatus()

        // Mengambil Variabel auth
        auth = FirebaseAuth.getInstance()

        // fungsi untuk pindah ke halaman Registrasi jika tidak memiliki akun
        binding.Registrasi.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
        }

        // fungsi untuk pindah ke halaman forgot password
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, NewPassword::class.java)
            startActivity(intent)
        }

        // Fungsi untuk Login
        binding.btnLogin.setOnClickListener {
            // Variabel untuk menampung data pada id
            val inpEmail = binding.inputEmail.text.toString()
            val inpPassword = binding.inputPassword.text.toString()

            // Fungsi agar email tidak boleh kosong
            if (inpEmail.isEmpty()) {
                binding.inputEmail.error = "Email tidak boleh kosong"
                binding.inputEmail.requestFocus()
                return@setOnClickListener
            }
            // Fungsi mengecek apakah sesuai dengan format email
            if (!Patterns.EMAIL_ADDRESS.matcher(inpEmail).matches()) {
                binding.inputEmail.error = "Email tidak sesuai"
                binding.inputEmail.requestFocus()
                return@setOnClickListener
            }

            // Fungsi untuk agar password tidak boleh kosong
            if (inpPassword.isEmpty()) {
                binding.inputPassword.error = "Password tidak boleh kosong"
                binding.inputPassword.requestFocus()
                return@setOnClickListener
            }

            // Mengambil fungsi
            LoginFirebase(inpEmail, inpPassword)
        }
    }
    // Mengambil data yang ada pada Registrasi Activity
    override fun onResume() {
        super.onResume()

        // Fungsi Alert Registrasi
        alertRegistrasi()
        // Fungsi Alert Reset Password
        alertResetPassword()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun alertResetPassword() {
        val dataUser = intent.getStringExtra("New_password")
        if (dataUser == "success"){
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil")
                .setContentText("Email Reset Password, Berhasil Dikirim!!")
                .show()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun alertRegistrasi() {
        val dataRegis = intent.getStringExtra("data_regis")
        if (dataRegis == "success"){
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil")
                .setContentText("Registrasi berhasil")
                .show()
        }
    }

    private fun checkLoginStatus(){
        val isLoggedIn = shp.getBoolean(Constant.PREF_LOGIN)
        if (isLoggedIn){
            moveIntent()
        }
    }

    // Fungsi untuk Sign menggunakan email dan password sesuai dengan yang ada di firebase
    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    shp.put(Constant.PREF_LOGIN, true)
                    moveIntent()
                } else {
                    Toast.makeText(
                        this,
                        "Email atau Password salah, silahkan cek ulang",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun moveIntent() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login_status", "success")
        startActivity(intent)
        finish()
    }
}