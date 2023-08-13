package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit
import umbjm.ft.inf.mydigitalprinting.model.ResponseLogin
import umbjm.ft.inf.mydigitalprinting.utils.SessionLogin
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var btnSignIn: MaterialButton
    private lateinit var sessionLogin: SessionLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi view
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        btnSignIn = findViewById(R.id.btnS)

        // Inisialisasi SessionLogin
        sessionLogin = SessionLogin(this)

        // Set nilai default untuk email dan password (jika tersimpan sebelumnya)
        inputEmail.setText(sessionLogin.email)
        inputPassword.setText(sessionLogin.password)

        // Menambahkan onClickListener ke tombol Sign In
        btnSignIn.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            // Validasi email dan password sebelum sign in
            if (validateEmail(email) && validatePassword(password)) {
                // Simpan detail login menggunakan SessionLogin
                sessionLogin.saveLoginDetails(email, password)

                // Implementasikan logika sign in di sini
                performSignIn()

            } else {
                // Tampilkan pesan kesalahan jika email atau password tidak valid
                if (!validateEmail(email)) {
                    inputEmail.error = "Invalid email"
                }
                if (!validatePassword(password)) {
                    inputPassword.error = "Invalid password"
                }
            }
        }
    }

    // Fungsi untuk validasi email
    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Fungsi untuk validasi password
    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    // Fungsi untuk melakukan sign in
    private fun performSignIn() {
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()
        ApiRetrofit().endpoint.login(email, password)
            .enqueue(object : Callback<ResponseLogin>{
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {

                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                }

            })
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun onCreateAccountClick(view: View) {
        val intent = Intent(this, RegistrasiActivity::class.java)
        startActivity(intent)
    }
}