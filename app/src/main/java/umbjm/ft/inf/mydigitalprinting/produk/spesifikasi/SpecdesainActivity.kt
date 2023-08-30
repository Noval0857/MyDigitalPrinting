package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.SubmitModel
import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit
import umbjm.ft.inf.mydigitalprinting.produk.PembayaranActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SpecdesainActivity : AppCompatActivity() {

    private lateinit var bayar:MaterialButton
    private lateinit var jenis_spanduk: TextInputEditText
    private lateinit var teks_utama: TextInputEditText
    private lateinit var teks_lainnya: TextInputEditText
    private lateinit var keterangan: TextInputEditText
    private lateinit var panjang: TextInputEditText
    private lateinit var lebar: TextInputEditText
    private lateinit var image: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spesifikasidesain)

        bayar = findViewById(R.id.btnS1)
        jenis_spanduk = findViewById(R.id.jenis_spanduk)
        teks_utama = findViewById(R.id.teks_utama)
        teks_lainnya = findViewById(R.id.teks_lainnya)
        keterangan = findViewById(R.id.keterangan)
        panjang = findViewById(R.id.panjang)
        lebar = findViewById(R.id.lebar)
        image = findViewById(R.id.image)


        bayar.setOnClickListener {
            tambahSDesian()
        }
        image.setOnClickListener {
            openFile()
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            image.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private fun tambahSDesian() {
        val jenisSpanduk = jenis_spanduk.text.toString()
        val teksUtama = teks_utama.text.toString()
        val teksLainnya = teks_lainnya.text.toString()
        val keterangan = keterangan.text.toString()
        val panjang = panjang.text.toString()
        val lebar = lebar.text.toString()

        val image = selectedImageUri?.let { imageUri ->
            val fileName = getFileName(imageUri)
            contentResolver.openInputStream(imageUri)?.use { inputStream ->
                val byteArray = inputStream.readBytes()
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
                MultipartBody.Part.createFormData("image", fileName, requestFile)
            }
        }


        ApiRetrofit().endpoint.tambahSDesain(
            jenisSpanduk,
            teksUtama,
            teksLainnya,
            keterangan,
            panjang,
            lebar,
            image!!
        ).enqueue(object : Callback<SubmitModel> {
            override fun onResponse(
                call: Call<SubmitModel>,
                response: Response<SubmitModel>
            ) {

                if (response.isSuccessful) {

                } else {

                }
            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

            }
        })
        val intent = Intent(this, PembayaranActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

}
