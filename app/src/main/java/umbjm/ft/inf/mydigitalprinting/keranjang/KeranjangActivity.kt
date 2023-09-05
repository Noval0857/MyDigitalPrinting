package umbjm.ft.inf.mydigitalprinting.keranjang

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit

class KeranjangActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    lateinit var keranjangItem: ArrayList<KeranjangItem>
    lateinit var keranjangAdapter: KeranjangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang2)

        initLayout()
    }

    private fun initLayout() {
        recyclerView = findViewById(R.id.rv_keranjang)
        recyclerView.setHasFixedSize(true)

        keranjangItem = ArrayList()

        keranjangAdapter = KeranjangAdapter(keranjangItem)
        recyclerView.adapter = keranjangAdapter
        ApiRetrofit().endpoint.getProduk()
            .enqueue(object : Callback<Keranjang>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Keranjang>, response: Response<Keranjang>) {
                    if (response.isSuccessful){
                        val keranjang = response.body()
                        if (keranjang != null){
                            keranjangItem.addAll(keranjang.data)
                            keranjangAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<Keranjang>, t: Throwable) {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }


            })

    }

}

