package com.mathews.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mathews.orgs.database.AppDataBase
import com.mathews.orgs.databinding.ActivityProductFormBinding
import com.mathews.orgs.databinding.FormularioImagemBinding
import com.mathews.orgs.extensions.loadImage
import com.mathews.orgs.model.Product
import com.mathews.orgs.ui.dialog.ImageFormDialog
import java.math.BigDecimal

class ProductFormctivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductFormBinding;

    private lateinit var editTextNome: EditText;
    private lateinit var editTextDescricao: EditText;
    private lateinit var editTextValor: EditText;
    private lateinit var buttonSaveForm: Button;
    private lateinit var productImageView: ImageView;

    private var productId = 0L;

    private val bindingFormularioImagem by lazy {
        FormularioImagemBinding.inflate(layoutInflater);
    }
    private val productsDao by lazy {
        AppDataBase.instanceDb(this).productDao()
    }

    private var imageUrl: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductFormBinding.inflate(layoutInflater)
        setContentView(binding.root);

        title = "Cadastrar Produto";

        editTextNome = binding.activityProductFormNome;
        editTextDescricao = binding.activityProductFormDescricao;
        editTextValor = binding.activityProductFormValor;
        buttonSaveForm = binding.activityProductFormButtonSaveForm;
        productImageView = binding.activityProductFormImageview;

        productImageView.setOnClickListener {
            ImageFormDialog(this).dialogBuilder(imageUrl) { image ->
                imageUrl = image;
                productImageView.loadImage(imageUrl);
            }
        }

        productId = intent.getLongExtra(PRODUCT_ID_KEY, 0L)

        handlerSaveForm();
    }

    override fun onResume() {
        super.onResume()

        productsDao.findById(productId)?.let {
            title = "Editar Produto";
            fillValues(it);
        }
    }

    private fun fillValues(product: Product) {
        imageUrl = product.image;
        editTextNome.setText(product.nome);
        editTextDescricao.setText(product.descricao);
        editTextValor.setText(product.valor.toPlainString());
        productImageView.loadImage(product.image);
    }

    fun handlerSaveForm() {
        buttonSaveForm.setOnClickListener {
            val nome = editTextNome.text.toString();
            val descricao = editTextDescricao.text.toString();
            val valor = if (editTextValor.text.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(editTextValor.text.toString())
            }

            val newProduct =
                Product(nome = nome, descricao = descricao, valor = valor, image = imageUrl);

            productsDao.save(newProduct);
            finish(); // Após finalizar o processo de cadastro de um produto, o método finish irá retornar para a tela anterior.
        }
    }
}