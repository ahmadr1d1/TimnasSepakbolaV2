package com.ahmadrd.timnassepakbolav2

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.timnassepakbolav2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvTimnas: RecyclerView
    private val list = ArrayList<Timnas>()
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()
        // Keep the splash screen visible for this condition.
        splashScreen.setKeepOnScreenCondition {
            isLoading
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Gunakan coroutine untuk menunda splash screen
        CoroutineScope(Dispatchers.Main).launch {
            delay(3800) // Tunda selama 3,8 detik
            isLoading = false
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvTimnas = binding.rvTimnas
        rvTimnas.setHasFixedSize(true)

        list.addAll(getListTimnas())
        showRecyclerList()
    }

    private fun getListTimnas(): ArrayList<Timnas> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDesc: Array<String> = try {
            resources.getStringArray(R.array.data_desc).also { array ->
                array.forEachIndexed { index, item ->
                    Log.d("Debug", "Item $index: $item")
                }
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("Debug", "Resource not found: ${e.message}")
            arrayOf("Data tidak ditemukan.")
        } catch (e: NullPointerException) {
            Log.e("Debug", "NullPointerException: ${e.message}")
            arrayOf("Data null. Harap periksa resource XML.")
        } catch (e: Exception) {
            Log.e("Debug", "Unknown error: ${e.message}")
            arrayOf("Terjadi kesalahan.")
        }
        // Tampilkan error ke UI
//        Toast.makeText(this, dataDesc.joinToString("\n"), Toast.LENGTH_SHORT).show()

        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listTimnas = ArrayList<Timnas>()
        for (i in dataName.indices) {
            val timnas = Timnas(dataName[i], dataDesc[i], dataPhoto[i])
            listTimnas.add(timnas)
        }

        return listTimnas
    }

    private fun showRecyclerList() {
        rvTimnas.layoutManager = LinearLayoutManager(this)
        val listTimnasAdapter = ListTimnasAdapter(list)
        rvTimnas.adapter = listTimnasAdapter

        listTimnasAdapter.setOnItemClickCallback(object : ListTimnasAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Timnas) {
                val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetail.putExtra("DETAIL", data)
                showSelectedTimnas(data)
                startActivity(intentDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_about -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSelectedTimnas(data: Timnas) {
        Toast.makeText(this, "Kamu memilih " + data.name, Toast.LENGTH_SHORT).show()
    }
}