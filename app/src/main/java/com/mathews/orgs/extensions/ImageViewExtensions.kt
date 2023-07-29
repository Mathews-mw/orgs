package com.mathews.orgs.extensions

import android.widget.ImageView
import coil.load
import com.mathews.orgs.R

fun ImageView.loadImage(url: String? = null) {
    load(url) {
        fallback(R.drawable.imagem_padrao);
        error(R.drawable.erro);
        // placeholder vai segurar uma imagem até que a imagem real seja totalmente carregada
        placeholder(R.drawable.imagem_padrao);
    }
}