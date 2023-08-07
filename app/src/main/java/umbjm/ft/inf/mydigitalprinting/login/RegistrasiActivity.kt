package umbjm.ft.inf.mydigitalprinting.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.utils.SessionRegister

class RegistrasiActivity : AppCompatActivity() {

    private lateinit var btnRegistrasi: MaterialButton
    private lateinit var inputNama: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputAddress: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputConfirmPassword: TextInputEditText
    private lateinit var SessionRegister: SessionRegister

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        btnRegistrasi = findViewById(R.id.btnR)
        inputNama = findViewById(R.id.nama)
        inputEmail = findViewById(R.id.email)
        inputAddress = findViewById(R.id.address)
        inputPassword = findViewById(R.id.password)
        inputConfirmPassword = findViewById(R.id.confirmpassword)
        SessionRegister = SessionRegister(this)

        btnRegistrasi.setOnClickListener {
            register()
//            sendRegistrationData()
        }
    }

    private fun register() {
        val nama = inputNama.text.toString()
        val email = inputEmail.text.toString()
        val address = inputAddress.text.toString()
        val password = inputPassword.text.toString()
        val confirmPassword = inputConfirmPassword.text.toString()

        // Validasi input
        if (nama.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Menampilkan pesan kesalahan jika ada input yang kosong
            SessionRegister.error = getString(R.string.empty_field_error)
            return
        }

        if (password != confirmPassword) {
            // Menampilkan pesan kesalahan jika password tidak cocok
            SessionRegister.error = getString(R.string.password_mismatch_error)
            return
        }

        // Data registrasi valid, lakukan tindakan selanjutnya
        // Misalnya, kirim data registrasi ke server

        // Contoh: Pindah ke aktivitas Login setelah berhasil mendaftar
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Menutup aktivitas Registrasi agar pengguna tidak dapat kembali ke halaman ini
    }

////    private fun sendRegistrationData() {
////        // Implementasikan logika untuk mengirim data registrasi ke server di sini
////        // Anda dapat menggunakan Retrofit, Volley, atau metode pengiriman data lainnya
////
////        // Setelah berhasil mendaftar, Anda dapat membuka aktivitas lain
////        val intent = Intent(this, LoginActivity::class.java)
////        startActivity(intent)
////        finish() // Opsional, menutup aktivitas saat ini setelah beralih ke aktivitas berikutnya
//    }
}