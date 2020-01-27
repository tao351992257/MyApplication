package com.example.utils

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

class QMUIDialog {

    companion object {
        fun showQMUIDialog(context: Context, msg: String) {
            val messageDialogBuilder = QMUIDialog.MessageDialogBuilder(context)
            messageDialogBuilder.setMessage(msg)
            messageDialogBuilder.show()
        }

        fun showQMUIDialogLoding(context: Context, msg: String) {

        }
    }
}