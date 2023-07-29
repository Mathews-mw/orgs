package com.mathews.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mathews.orgs.database.AppDataBase
import com.mathews.orgs.database.dao.ProductDAO
import com.mathews.orgs.databinding.ActivityMainBinding
import com.mathews.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater);
    }

    private lateinit var recyclerView: RecyclerView;
    private lateinit var fabAddProduct: FloatingActionButton;
    private lateinit var productDao: ProductDAO;

    private val adapter = ListaProdutosAdapter(context = this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(binding.root);

        setupRecyclerView();
        itentFormActivity();
    }

    override fun onResume() {
        super.onResume();

        val db = AppDataBase.instanceDb(context = this);
        productDao = db.productDao();

        adapter.updateInfos(productDao.indexAll());
    }

    private fun itentFormActivity() {
        fabAddProduct = binding.mainActivityFabAAddProduct;
        fabAddProduct.setOnClickListener {
            val intent = Intent(this, ProductFormctivity::class.java)
            startActivity(intent);
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.mainActivityRecyclerView;
        recyclerView.adapter = adapter;

        adapter.onClickItemListener = {
            Log.i("click", "Botão para ir para a tela foi clicado")
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra(PRODUCT_ID_KEY, it.id);
            }
            startActivity(intent);
        }
    }
}