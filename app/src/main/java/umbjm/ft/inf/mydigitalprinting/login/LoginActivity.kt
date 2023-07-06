package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.utils.SessionLogin

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
                performSignIn(email, password)
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

        // Menambahkan doOnTextChanged listener untuk validasi real-time
        inputEmail.doOnTextChanged { text, _, _, _ ->
            val email = text.toString()
            if (email.isNotEmpty()) {
                if (!validateEmail(email)) {
                    inputEmail.error = "Invalid email"
                } else {
                    inputEmail.error = null
                }
            } else {
                inputEmail.error = null
            }
        }

        inputPassword.doOnTextChanged { text, _, _, _ ->
            val password = text.toString()
            if (password.isNotEmpty()) {
                if (!validatePassword(password)) {
                    inputPassword.error = "Invalid password"
                } else {
                    inputPassword.error = null
                }
            } else {
                inputPassword.error = null
            }
        }
    }

    // Fungsi untuk validasi email
    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Fungsi untuk validasi password
    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    // Fungsi untuk melakukan sign in
    private fun performSignIn(email: String, password: String) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun onCreateAccountClick(view: View) {
        val intent = Intent(this, Registrasi::class.java)
        startActivity(intent)
    }
}