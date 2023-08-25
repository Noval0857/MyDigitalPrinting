package umbjm.ft.inf.mydigitalprinting.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityForgot1Binding

class NewPassword : AppCompatActivity() {

    lateinit var binding: ActivityForgot1Binding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityForgot1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val edtemail = binding.edtemail

            if (email.isEmpty()){
                binding.edtemail.error = "Email tidak boleh kosong"
                binding.edtemail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtemail.error = "Email tidak sesuai dengan format email"
                binding.edtemail.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Reset email berhasil dikirim", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Reset email tidak berhasil dikirim", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}