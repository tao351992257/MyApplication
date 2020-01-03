package com.example.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.utils.SoftKeyboardListener

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/3
 */
class KeyboardUtil {
    companion object {
        @JvmStatic
        fun showKeyboard(v: EditText?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v?.focusable = View.FOCUSABLE
            }
            v?.isFocusableInTouchMode = true
            v?.requestFocus()

            v?.postDelayed({
                val inputManager = v.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(v, 0)
                v.setSelection(v.text.toString().length)
            }, 100)
        }

        /**
         * 隐藏键盘
         *
         * @param v View
         */
        @JvmStatic
        fun hideKeyboard(v: View) {
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }

        @JvmStatic
        fun adjustKeyboardLayout(activity: Activity, root: View, scrollToView: View) {
            SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
                override fun keyBoardShow(height: Int) {
                    val rect = Rect()
                    // 获取root在窗体的可视区域
                    root.getWindowVisibleDisplayFrame(rect)
                    //软键盘弹出来的时候
                    val location = IntArray(2)
                    // 获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location)
                    // 计算root滚动高度，使scrollToView在可见区域的底部
                    val scrollHeight = location[1] + scrollToView
                            .height - rect.bottom
                    root.scrollTo(0, scrollHeight + 20)
                }

                override fun keyBoardHide(height: Int) {
                    root.scrollTo(0, 0)
                }

            })
        }

        @JvmStatic
        fun setKeyboardListener(activity: Activity, listener: SoftKeyboardListener.OnSoftKeyBoardChangeListener) {
            SoftKeyboardListener.setListener(activity, listener)
        }
    }
}