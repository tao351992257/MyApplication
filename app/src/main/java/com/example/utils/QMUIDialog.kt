package com.example.utils

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

class QMUIDialog {

    companion object {
        var qmuiTipDialog: QMUITipDialog? = null

        fun showQMUITipDialogLoding(context: Context) {
            qmuiTipDialog = QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .create()
        }

        fun showQMUITipDialogNothing(context: Context) {
            qmuiTipDialog = QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                    .create()

        }

        fun showQMUITipDialogSuccess(context: Context) {
            qmuiTipDialog = QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .create()

        }

        fun showQMUITipDialogFail(context: Context) {
            qmuiTipDialog = QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .create()

        }

        fun show(){
            qmuiTipDialog?.show()
        }

        fun QMUITipDialogDismiss() {
            qmuiTipDialog?.dismiss()
        }
    }
}