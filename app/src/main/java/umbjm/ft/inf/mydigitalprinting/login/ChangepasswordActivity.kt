package umbjm.ft.inf.mydigitalprinting.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import umbjm.ft.inf.mydigitalprinting.R

// Digunakan untuk memanggil library
private lateinit var firebaseAuth: FirebaseAuth
private var currentUser: FirebaseUser? = null

// Digunakan untuk variable ID YANG ADA PADA XML
private lateinit var oldPasswordInputLayout: TextInputLayout
private lateinit var oldPasswordEditText: TextInputEditText
private lateinit var verifyPasswordButton: MaterialButton
private lateinit var newPasswordInputLayout: TextInputLayout
private lateinit var newPasswordEditText: TextInputEditText
private lateinit var confirmPasswordInputLayout: TextInputLayout
private lateinit var confirmPasswordEditText: TextInputEditText
private lateinit var savePassword: MaterialButton


class ChangepasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Memanggil semua Variabel yang sudah terindikasi dengan ID di XML
        oldPasswordInputLayout = findViewById(R.id.oldPasswordInput)
        oldPasswordEditText = findViewById(R.id.oldPasswordEdit)
        verifyPasswordButton = findViewById(R.id.verifikasiPassword)
        newPasswordInputLayout = findViewById(R.id.newPasswordInput)
        newPasswordEditText = findViewById(R.id.newPasswordEdit)
        confirmPasswordInputLayout = findViewById(R.id.ConfirmNewPasswordInput)
        confirmPasswordEditText = findViewById(R.id.ConfirmNewPasswordEdit)
        savePassword = findViewById(R.id.SavePassowrd)

        // Digunakan untuk memanggil variabel yang sudah terkoneksi dengan library
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser

        // Digunakan untuk password verifikasi, dan button untuk save password baru tidak akan bisa diklik jika tidak melakukan verifikasi
        savePassword.isEnabled = false

        // menarik fungsi untuk Verifikasi
        verifyPasswordButton.setOnClickListener {
                verifikasi()
            }

        savePassword.setOnClickListener {
//                SimpanPasswordBaru()
            }
        }

    // Fungsi untuk Verifikasi
    private fun verifikasi() {
        // Variable
        val oldPassword = oldPasswordEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmpassword = confirmPasswordEditText.text.toString()
        // Variable untuk melakukan pengecekan ulang terhadap user, CurrentUser adalah data pengguna yang ada pada firebase dan akan diambil sebagai pengecekan ulang
        val credential = EmailAuthProvider.getCredential(currentUser?.email ?: "", oldPassword)
        // Pengcekan data user, dan pengkondisian
        currentUser?.reauthenticate(credential)?.addOnSuccessListener {
            // jika sukses akan menampilkan pesan seperti ini
            Toast.makeText(this@ChangepasswordActivity, "Password Berhasil di Verifikasi", Toast.LENGTH_SHORT).show()
            // dan button untuk save password baru, jika sesuai dengan data dan benar maka akan bisa diklik
            savePassword.isEnabled = newPassword == confirmpassword
            // jika pemasukkan data tidak sesuai
        }?.addOnFailureListener { e ->
            // pesan yang akan ditampikan seperti ini
            oldPasswordInputLayout.error = "Password tidak sesuai"
            // dan button untuk save password baru tidak akan jalan
            savePassword.isEnabled = false
        }
    }

//    BAGIAN YANG MASIH ERROR
//    private fun SimpanPasswordBaru() {
//        val newPassword = newPasswordEditText.text.toString()
//        val confirmPassword = confirmPasswordEditText.text.toString()
//            if (newPassword.isEmpty() && newPassword == confirmPassword){
//                currentUser?.updatePassword(newPassword)
//                    ?.addOnSuccessListener {
//                        Toast.makeText(this@ChangepasswordActivity, "Password anda telah diperbarui", Toast.LENGTH_SHORT).show()
//                        savePassword.isEnabled = false
//                    }
//                    ?.addOnFailureListener {e ->
//                        Toast.makeText(this@ChangepasswordActivity, "Gagal menyimpan perbaruan password", Toast.LENGTH_SHORT).show()
//                    }
//            } else {
//                Toast.makeText(this@ChangepasswordActivity, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
//            }
//    }
}