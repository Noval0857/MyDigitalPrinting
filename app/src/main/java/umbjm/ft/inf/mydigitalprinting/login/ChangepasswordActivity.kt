package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityChangePasswordBinding
import umbjm.ft.inf.mydigitalprinting.profil.ProfileActivity



class ChangepasswordActivity : AppCompatActivity() {

    // Memanggil library dan ditampung kedalam variable
    lateinit var binding: ActivityChangePasswordBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // mengambil variable library
        auth = FirebaseAuth.getInstance()
        // menampung variable auth didalam variable user
        val user = auth.currentUser

        // binding button savePassword di nonaktifkan jika belum terverifikasi
        binding.SavePassowrd.isEnabled = false

        // mengarahkan button kembali ke halaman profile
        binding.backprofil.setOnClickListener backprofil@{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            return@backprofil
        }

        // Fungsi ketika verifikasi password di click
        binding.verifikasiPassword.setOnClickListener {

            // variable untuk menampung id oldPassword
            val oldPassword = binding.oldPasswordEdit.text.toString()

            // logika jika inputan oldpassword kosong maka kana menampilkan pesan error
            if (oldPassword.isEmpty()) {
                binding.oldPasswordEdit.error = "Password tidak boleh kosong"
                binding.oldPasswordEdit.requestFocus()
                return@setOnClickListener
            }

            // logika jika inputan oldpassword kurang dari 8 maka akan menampilkan pesan error
            if (oldPassword.length < 8) {
                binding.oldPasswordEdit.error = "Password kurang dari 8 karakter"
                binding.oldPasswordEdit.requestFocus()
                return@setOnClickListener
            }

            // logika pengecekan data user
            user.let {
                // menampung data user didalam variable userCredential
                val userCredential = EmailAuthProvider.getCredential(it?.email!!, oldPassword)
                // pengecekan data user, jika berhasil akan menampilkan pesan didalam for / when
                it.reauthenticate(userCredential).addOnCompleteListener { task ->
                    // logika
                    when {
                        // jika pengecekan sukses akan menampilkan pesan dan button savePassword akan bisa digunakan
                        task.isSuccessful -> {
                            val pesan = findViewById<View>(android.R.id.content)
                            val snackbar = Snackbar.make(pesan, "Verifikasi berhasil", Snackbar.LENGTH_SHORT)
                            snackbar.show()
                            binding.SavePassowrd.isEnabled = true
                        }

                        // jika pengecekan gagal akan menampilkan pesan error dan button savePassword akan tetap tidak berfungsi
                        task.exception is FirebaseAuthInvalidCredentialsException -> {
                            binding.oldPasswordEdit.error = "Password salah"
                            binding.oldPasswordEdit.requestFocus()
                            return@addOnCompleteListener
                            binding.SavePassowrd.isEnabled = false
                        }

                        // error jika dalam keadaan tidak ada internet
                        else -> {
                            Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            // fungsi untuk savePassword baru
            binding.SavePassowrd.setOnClickListener SavePassword@{
                // id inputan di tampung kedalam variable
                val newPassword = binding.newPasswordEdit.text.toString()
                val confirmPassword = binding.ConfirmNewPasswordEdit.text.toString()

                // logika jika newPassword kosong
                if (newPassword.isEmpty()){
                    binding.newPasswordEdit.error = "Password tidak boleh kosong"
                    binding.newPasswordEdit.requestFocus()
                    return@SavePassword
                }

                // logika jika newPassword kurang dari 8 karakter saat di inputkan
                if (newPassword.length < 8){
                    binding.newPasswordEdit.error = "Password min 8 karakter"
                    binding.newPasswordEdit.requestFocus()
                    return@SavePassword
                }

                // logika jika confirmPassword kosong
                if (confirmPassword.isEmpty()){
                    binding.ConfirmNewPasswordEdit.error = "Password tidak boleh kosong"
                    binding.ConfirmNewPasswordEdit.requestFocus()
                    return@SavePassword
                }

                // logika jika confirmPassword kurang dari 8 karakter saat input
                if (confirmPassword.length < 8){
                    binding.ConfirmNewPasswordEdit.error = "Password min 8 karakter"
                    binding.ConfirmNewPasswordEdit.requestFocus()
                    return@SavePassword
                }

                // logika jika newPassword dan confirmPassword tidak sesuai dengan inputan maka akan error
                if (newPassword != confirmPassword){
                    binding.ConfirmNewPasswordEdit.error = "Password tidak sama"
                    binding.ConfirmNewPasswordEdit.requestFocus()
                    return@SavePassword
                }

                // logika untuk menyimpan password baru
                user?.let {
                    // logika penyimpanan password baru
                    user.updatePassword(newPassword).addOnCompleteListener {
                        // jika penyimpanan berhasil akan langsung keluar dan diarahkan kembali ke arah login
                        if (it.isSuccessful){
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.putExtra("data_masuk", "success")
                            startActivity(intent)
                            // jika gagal maka akan mengulang password
                        } else {
                            val pesangagal = findViewById<View>(android.R.id.content)
                            val snackbar = Snackbar.make(pesangagal, "Gagal menyimpan password baru", Snackbar.LENGTH_SHORT)
                            snackbar.show()
                        }
                    }
                }

            }
        }
    }

    // fungsi untuk kembali ke halama login
    private fun successLogout() {
        // mengambil data auth user
        auth = FirebaseAuth.getInstance()
        // jika berhasil dan sesuai dengan data login makan akan dikembalikan ke halaman login / SignOut / Logout otomatis
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        Toast.makeText(this, "Silahkan login kembali", Toast.LENGTH_SHORT).show()
    }
}