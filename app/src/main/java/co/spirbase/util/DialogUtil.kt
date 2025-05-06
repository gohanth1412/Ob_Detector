package co.spirbase.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import co.spirbase.R
import co.spirbase.databinding.DialogDemoBinding
import androidx.core.graphics.drawable.toDrawable
import co.spirbase.databinding.DialogAddTodoBinding

fun Context.showDemoDialog(
    lifecycle: Lifecycle,
    onClose: () -> Unit
) {
    val dialog = Dialog(this)
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_demo, null)
    dialog.setContentView(view)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    val binding = DialogDemoBinding.bind(view)

    lifecycle.addObserver(LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                dialog.dismiss()
            }

            else -> {

            }
        }
    })

    binding.apply {
        btnClose.setOnClickListener {
            dialog.dismiss()
            onClose.invoke()
        }
    }


    if (!dialog.isShowing) {
        dialog.show()
    }
}

fun Context.showAddTodoDialog(
    lifecycle: Lifecycle,
    onAdd: (String, String) -> Unit
) {
    val dialog = Dialog(this)
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_todo, null)
    dialog.setContentView(view)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    val binding = DialogAddTodoBinding.bind(view)

    lifecycle.addObserver(LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                dialog.dismiss()
            }

            else -> {

            }
        }
    })

    binding.apply {
        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        tvAdd.setOnClickListener {
            if (edtDes.text.isNotBlank() && edtName.text.isNotBlank()) {
                dialog.dismiss()
                onAdd.invoke(binding.edtName.text.toString(), binding.edtDes.text.toString())
            } else {
                Toast.makeText(this@showAddTodoDialog, "Please enter info", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    if (!dialog.isShowing) {
        dialog.show()
    }
}