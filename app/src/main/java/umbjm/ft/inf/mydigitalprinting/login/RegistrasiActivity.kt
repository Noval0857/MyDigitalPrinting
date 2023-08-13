package umbjm.ft.inf.mydigitalprinting.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.SubmitModel
import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit
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
        }
    }

    private fun register() {
        val nama = inputNama.text.toString()
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()
        ApiRetrofit().endpoint.register(nama, email, password)
            .enqueue(object : Callback<SubmitModel>{
                override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {

                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                }
            })
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}