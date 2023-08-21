package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.model.UserModel
import umbjm.ft.inf.mydigitalprinting.utils.LoginPresenter
import umbjm.ft.inf.mydigitalprinting.utils.LoginView
import umbjm.ft.inf.mydigitalprinting.utils.SessionLogin

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var btnSignIn: MaterialButton
    private lateinit var sessionLogin: SessionLogin
    private lateinit var login : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi view
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        btnSignIn = findViewById(R.id.btnS)

        // Inisialisasi SessionLogin
        sessionLogin = SessionLogin(this)

        login = LoginPresenter(this)

        // Set nilai default untuk email dan password (jika tersimpan sebelumnya)
        inputEmail.setText(sessionLogin.email)
        inputPassword.setText(sessionLogin.password)

        // Menambahkan onClickListener ke tombol Sign In
        btnSignIn.setOnClickListener {

            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if(email.isEmpty()){
                inputEmail.error = "Email required"
                inputEmail.requestFocus()
                return@setOnClickListener

            }

            if(password.isEmpty()){
                inputPassword.error = "Password required"
                inputPassword.requestFocus()
                return@setOnClickListener

            }

            sessionLogin.saveLoginDetails(email, password)
            sessionLogin.createLoginSession(UserModel())
            login.login(email, password)


            /*ApiRetrofit().endpoint.login(email, password).enqueue(object : Callback<ResponseLogin>{
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful && response.body()?.status == 200){
                        Toast.makeText(this@LoginActivity,"Login Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,"Login Failed", Toast.LENGTH_SHORT).show()
                }

            })*/

            // Validasi email dan password sebelum sign in
            /*if (validateEmail(email) && validatePassword(password)) {
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
            }*/
        }

    }

    // Fungsi untuk validasi email
/*    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Fungsi untuk validasi password
    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }*/

    // Fungsi untuk melakukan sign in
    /*private fun performSignIn() {
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()


        ApiRetrofit().endpoint.login(email, password).enqueue(object : Callback<ResponseLogin>{
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (!response.body()?.error!!){
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                }

            })*/
/*        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)*/
    fun onCreateAccountClick(view: View) {
        val intent = Intent(this, RegistrasiActivity::class.java)
        startActivity(intent)
    }

    override fun onSuccessLogin(msg: String?) {
        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onFailedLogin(msg: String?) {
        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()

    }

}