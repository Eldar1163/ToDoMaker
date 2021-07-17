package com.hfad.todomaker

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class CreateTaskDialog : DialogFragment(), DialogInterface.OnClickListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.enter_info))
                    .setView(R.layout.add_task_dialog_layout)
                    .setPositiveButton(getString(R.string.add_btn_text), this)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val title = (dialog as Dialog).findViewById<EditText>(R.id.editTextTaskTitle).text.toString()
        (activity as MainActivity).addBtnClicked(title)
    }
}