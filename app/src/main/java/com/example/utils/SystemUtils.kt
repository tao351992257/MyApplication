package com.example.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import androidx.annotation.RequiresApi

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/6
 */
class SystemUtils {
    companion object {
        const val HUAWEI = "huawei"
        const val HONOR = "honor"
        const val XIAOMI = "xiaomi"
        const val OPPO = "oppo"
        const val VIVO = "vivo"
        const val MEIZU = "meizu"
        const val SAMSUNG = "samsung"
        const val LETV = "letv"
        const val SMARTISAN = "smartisan"

        @RequiresApi(api = Build.VERSION_CODES.M)
        fun isIgnoringBatteryOptimizations(context: Context): Boolean {
            var isIgnoring = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                if (powerManager != null) {
                    isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.packageName)
                }
            } else {
                isIgnoring = true
            }
            return isIgnoring
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        fun requestIgnoreBatteryOptimizations(context: Context) {
            try {
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun Settings(context: Context) {
            when (Build.BRAND.toLowerCase()) {
                HUAWEI, HONOR -> goHuaweiSetting(context)
                XIAOMI -> goXiaomiSetting(context)
                OPPO -> goOPPOSetting(context)
                VIVO -> goVIVOSetting(context)
                MEIZU -> goMeizuSetting(context)
                SAMSUNG -> goSamsungSetting(context)
                LETV -> goLetvSetting(context)
                SMARTISAN -> goSmartisanSetting(context)
            }
        }

        fun isHuawei(): Boolean {
            return if (Build.BRAND == null) {
                false
            } else {
                Build.BRAND.toLowerCase() == "huawei" || Build.BRAND.toLowerCase() == "honor"
            }
        }

        private fun goHuaweiSetting(context: Context) {
            try {
                showActivity(context, "com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
            } catch (e: Exception) {
                showActivity(context, "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.bootstart.BootStartActivity")
            }
        }

        fun isXiaomi(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "xiaomi"
        }

        private fun goXiaomiSetting(context: Context) {
            showActivity(context, "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity")
        }

        fun isOPPO(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "oppo"
        }

        private fun goOPPOSetting(context: Context) {
            try {
                showActivity(context, "com.coloros.phonemanager")
            } catch (e1: Exception) {
                try {
                    showActivity(context, "com.oppo.safe")
                } catch (e2: Exception) {
                    try {
                        showActivity(context, "com.coloros.oppoguardelf")
                    } catch (e3: Exception) {
                        showActivity(context, "com.coloros.safecenter")
                    }
                }
            }
        }

        /**
         * 跳转到指定应用的首页
         */
        private fun showActivity(context: Context, packageName: String) {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            context.startActivity(intent)
        }

        /**
         * 跳转到指定应用的指定页面
         */
        private fun showActivity(context: Context, packageName: String, activityDir: String) {
            val intent = Intent()
            intent.component = ComponentName(packageName, activityDir)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }


        fun goVIVOSetting(context: Context) {
            showActivity(context, "com.iqoo.secure")
        }


        fun goMeizuSetting(context: Context) {
            showActivity(context, "com.meizu.safe")
        }


        fun goSamsungSetting(context: Context) {
            try {
                showActivity(context, "com.samsung.android.sm_cn")
            } catch (e: Exception) {
                showActivity(context, "com.samsung.android.sm")
            }
        }


        fun goLetvSetting(context: Context) {
            showActivity(context, "com.letv.android.letvsafe",
                    "com.letv.android.letvsafe.AutobootManageActivity")
        }

        fun goSmartisanSetting(context: Context) {
            showActivity(context, "com.smartisanos.security")
        }
    }


}