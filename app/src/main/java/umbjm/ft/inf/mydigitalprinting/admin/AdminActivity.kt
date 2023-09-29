package umbjm.ft.inf.mydigitalprinting.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import umbjm.ft.inf.mydigitalprinting.admin.itembanner.ItemBannerActivity
import umbjm.ft.inf.mydigitalprinting.admin.itembrosur.ItemBrosurActivity
import umbjm.ft.inf.mydigitalprinting.admin.itemidcard.ItemIdcardActivity
import umbjm.ft.inf.mydigitalprinting.admin.itemsertifikat.ItemSertifikatActivity
import umbjm.ft.inf.mydigitalprinting.admin.itemstiker.ItemStickerActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityAdminBinding
import umbjm.ft.inf.mydigitalprinting.keranjang.KeranjangActivity
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity
import umbjm.ft.inf.mydigitalprinting.preferen.SharedPreferences

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    lateinit var auth: FirebaseAuth
    lateinit var shp : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.btnProduk.setOnClickListener {
//            startActivity(Intent(this, Item::class.java))
//        }

        binding.btnBanner.setOnClickListener {
            startActivity(Intent(this, ItemBannerActivity::class.java))
        }

        binding.btnBrosur.setOnClickListener {
            startActivity(Intent(this, ItemBrosurActivity::class.java))
        }

        binding.btnIdcard.setOnClickListener {
            startActivity(Intent(this, ItemIdcardActivity::class.java))
        }

        binding.btnSticker.setOnClickListener {
            startActivity(Intent(this, ItemStickerActivity::class.java))
        }

        binding.btnSertifikat.setOnClickListener {
            startActivity(Intent(this, ItemSertifikatActivity::class.java))
        }

        binding.btnUser.setOnClickListener {
            startActivity(Intent(this, KeranjangActivity::class.java))

        }

        binding.btnLogout.setOnClickListener {
            LogOut()
        }
    }

    private fun LogOut(){
        auth = FirebaseAuth.getInstance()
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Konfirmasi Logout")
            .setContentText("Apakah anda yakin ingin logout?")
            .setConfirmText("Ya")
            .setCancelText("Tidak")
            .setConfirmClickListener { dialog ->
                dialog.dismissWithAnimation()
                // Data penggunakan Logout dari database
                auth.signOut()

                // shp.put(Constant.PREF_LOGIN,false)

                shp.clear()

                // Alert Berhasil keluar
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Berhasil Logout")
                    .setConfirmText("Ok")
                    .setConfirmClickListener { SuccessDialog ->
                        SuccessDialog.dismissWithAnimation()
                        moveIntent()
                    }
                    .show()
            }
            // Alert tidak jadi keluar
            .setCancelClickListener { dialog ->
                dialog.dismissWithAnimation()
                val root = findViewById<View>(android.R.id.content)
                val snackbar = Snackbar.make(root, "Anda tidak logout", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            .show()
    }

    private fun moveIntent() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finishAffinity()
    }
}