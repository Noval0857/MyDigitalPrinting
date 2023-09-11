package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityRegistrasiBinding

class RegistrasiActivity : AppCompatActivity() {

    // Untuk akses binding
    lateinit var binding: ActivityRegistrasiBinding

    // Untuk akses Firebase
    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Untuk Button pindah ke halaman Login
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegis.setOnClickListener {

            // Membuat Variabel untuk menampung data
            val Regisnama = binding.Rnama.text.toString()
            val Regisemail = binding.Remail.text.toString()
            val Regispassword = binding.Rpassword.text.toString()

            // Pengkondisian (Jika variabel Regisnama kosong atau data nama kosong tidak akan bisa digunakan)
            if (Regisnama.isEmpty()) {
                binding.Rnama.error = "Nama tidak boleh kosong"
                binding.Rnama.requestFocus()
                return@setOnClickListener
            }

            // Pengkondisian (Jika variabel Regismail kosong atau data email tidak ada maka akan tidak bisa digunakan)
            if (Regisemail.isEmpty()) {
                binding.Remail.error = "Email tidak boleh kosong"
                binding.Remail.requestFocus()
                return@setOnClickListener
            }

            // Mengecek apakah betul yang diisi dengan format email atau tidak
            if (!Patterns.EMAIL_ADDRESS.matcher(Regisemail).matches()) {
                binding.Remail.error = "Email tidak sesuai"
                binding.Remail.requestFocus()
                return@setOnClickListener
            }

            // Pengkondisian (Jika variabel Regispassword kosong atau tidak ada data password maka fungsi akan tidak berjalan)
            if (Regispassword.isEmpty()) {
                binding.Rpassword.error = "Password minimal 8 karakter"
                binding.Rpassword.requestFocus()
                return@setOnClickListener
            }

            // Mengambil fungsi
            RegisterFirebase(Regisemail, Regispassword)
        }
    }

    // Membuat fungsi untuk menyimpan data ke database Firebase
    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("data_regis", "success")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Data anda tidak berhasil disimpan", Toast.LENGTH_LONG)
                        .show()
                }
            }

    }

}
//        val namaUser = binding.Rnama.text.toString()
//        val emailUser = binding.Remail.text.toString()
//        val passwordUser = binding.Rpassword.text.toString()
//        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
//        val idUser = database.push().key!!
//        val user = User(idUser, namaUser, passwordUser, emailUser)
//        database.child(idUser).setValue(user).addOnCompleteListener { databaseTask ->
//            if (databaseTask.isSuccessful) {
//                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
//            }
//            binding.Rnama.text?.clear()
//            binding.Remail.text?.clear()
//            binding.Rpassword.text?.clear()
//
//        }


//import android.annotation.SuppressLint
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.google.android.material.button.MaterialButton
//import com.google.android.material.textfield.TextInputEditText
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import umbjm.ft.inf.mydigitalprinting.R
//import umbjm.ft.inf.mydigitalprinting.SubmitModel
//import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit
//import umbjm.ft.inf.mydigitalprinting.utils.SessionRegister
//
//class RegistrasiActivity : AppCompatActivity() {
//
//    private lateinit var btnRegistrasi: MaterialButton
//    private lateinit var inputNama: TextInputEditText
//    private lateinit var inputEmail: TextInputEditText
//    private lateinit var inputAddress: TextInputEditText
//    private lateinit var inputPassword: TextInputEditText
//    private lateinit var inputConfirmPassword: TextInputEditText
//    private lateinit var SessionRegister: SessionRegister
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registrasi)
//
//        btnRegistrasi = findViewById(R.id.btnR)
//        inputNama = findViewById(R.id.nama)
//        inputEmail = findViewById(R.id.email)
//        inputAddress = findViewById(R.id.address)
//        inputPassword = findViewById(R.id.password)
//        inputConfirmPassword = findViewById(R.id.confirmpassword)
//        SessionRegister = SessionRegister(this)
//
//        btnRegistrasi.setOnClickListener {
//            register()
//        }
//    }
//
//    private fun register() {
//        val nama = inputNama.text.toString()
//        val email = inputEmail.text.toString()
//        val password = inputPassword.text.toString()
//        ApiRetrofit().endpoint.register(nama, email, password)
//            .enqueue(object : Callback<SubmitModel>{
//                override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
//
//                }
//
//                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
//
//                }
//            })
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//}