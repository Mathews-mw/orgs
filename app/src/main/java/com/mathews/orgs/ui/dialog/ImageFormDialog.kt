package com.mathews.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.mathews.orgs.databinding.FormularioImagemBinding
import com.mathews.orgs.extensions.loadImage

class ImageFormDialog(private val context: Context) {

    private lateinit var imageUrl: String;

    fun dialogBuilder(prevStateUrl: String? = null, onLoadImage: (imageUrl: String) -> Unit) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {
            prevStateUrl?.let {
                formularioImagemImageview.loadImage(it);
                imageFormInputUrl.setText(it)
            }

            formularioImagemButtonUpload.setOnClickListener {
                imageUrl = imageFormInputUrl.text.toString();
                formularioImagemImageview.loadImage(imageUrl);
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    onLoadImage(imageUrl)
                }
                .setNegativeButton("Cancelar") { _, _ ->

                }
                .show();
        }
    }
}