package com.mathews.orgs.ui.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mathews.orgs.databinding.ProdutoItemBinding
import com.mathews.orgs.extensions.loadImage
import com.mathews.orgs.model.Product
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosAdapter(
    private val context: Context,
    productList: List<Product> = emptyList(),
    var onClickItemListener: (produto: Product) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val productList = productList.toMutableList();

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Product;

        init {
            binding.root.setOnClickListener {
                Log.i("adpter-click", "Botão para ir para a tela foi clicado")
                if (::produto.isInitialized) {
                    Log.i("adpter-click", "Dentro da condição")
                    onClickItemListener(produto);
                }
            }
        }

        fun attach(product: Product) {
            produto = product;

            val nome = binding.produtoItemNome;
            val descricao = binding.produtoItemDescricao;
            val valor = binding.produtoItemValor;
            val image = binding.produtoItemImageView;

            nome.text = product.nome;
            descricao.text = product.descricao;

            val currencyInstanceFormated =
                NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(product.valor);
            valor.text = currencyInstanceFormated;

            val imageViewVibilityContainer = if (product.image != null) {
                View.VISIBLE;
            } else {
                View.GONE;
            }

            image.visibility = imageViewVibilityContainer;

            image.loadImage(product.image);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInfaler = LayoutInflater.from(context);
        val binding = ProdutoItemBinding.inflate(layoutInfaler, parent, false);
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position];
        holder.attach(product);
    }

    override fun getItemCount(): Int {
        return productList.size;
    }

    fun updateInfos(products: List<Product>) {
        this.productList.clear();
        this.productList.addAll(products);

        // informa para o adapter que os dados internos de um atributo de sua classe sofreu alguma modificação
        notifyDataSetChanged();
    }
}