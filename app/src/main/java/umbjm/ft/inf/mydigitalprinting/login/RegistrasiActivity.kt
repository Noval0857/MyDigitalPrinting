package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
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

        binding.btnRegister.setOnClickListener {

            // Membuat Variabel untuk menampung data
            val Regisemail = binding.Remail.text.toString()
            val Regispassword = binding.Rpassword.text.toString()

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
                    val exception = it.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warning")
                            .setContentText("Email yang anda gunakan sudah di daftarkan")
                            .show()
                    } else {
                        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Gagal")
                            .setContentText("Silahkan cek internet anda")
                            .show()
                    }
                }
            }
        }
    }