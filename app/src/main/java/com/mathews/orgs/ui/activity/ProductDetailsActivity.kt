package com.mathews.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mathews.orgs.R
import com.mathews.orgs.database.AppDataBase
import com.mathews.orgs.databinding.ActivityProductDetailsBinding
import com.mathews.orgs.extensions.brlFormatter
import com.mathews.orgs.extensions.loadImage
import com.mathews.orgs.model.Product

class ProductDetailsActivity : AppCompatActivity() {

    private var product: Product? = null;
    private var productId: Long = 0L;
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater);
    }
    private val productDao by lazy {
        AppDataBase.instanceDb(this).productDao();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root);

        loadProduct();
    }

    override fun onResume() {
        super.onResume()

        product = productDao.findById(productId);

        product?.let {
            fillValues(it);
        } ?: finish();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_details_edit -> {
                Intent(this, ProductFormctivity::class.java).apply {
                    putExtra("produto", product);
                    startActivity(this);
                };
            }

            R.id.menu_product_details_remove -> {
                product?.let {
                    productDao.delete(it);
                }
                finish();
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadProduct() {
        productId = intent.getLongExtra(PRODUCT_ID_KEY, productId);
    }

    private fun fillValues(product: Product) {
        with(binding) {
            activityProductDetailsNome.text = product.nome;
            activityProductDetailsDescricao.text = product.descricao;
            activityProductDetailsValor.text = product.valor.brlFormatter();
            activityProductDetailsImage.loadImage(product.image);
        }
    }
}