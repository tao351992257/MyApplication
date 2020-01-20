package com.example.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import com.example.myapplication.BuildConfig
import java.util.*

/**
 * Desc:
 * Created by SunHang on 2018/6/14.
 * Email：sunh@eetrust.com
 */
object JumpPermissionSetting {
    fun jump(context: Activity, requestCode: Int) {
        val brand = Build.BRAND//手机厂商
        if (TextUtils.equals(brand.toLowerCase(Locale.getDefault()), "redmi") || TextUtils.equals(brand.toLowerCase(Locale.getDefault()), "xiaomi")) {
            gotoMiuiPermission(context, requestCode)//小米
        } else if (TextUtils.equals(brand.toLowerCase(Locale.getDefault()), "meizu")) {
            gotoMeizuPermission(context, requestCode)
        } else if (TextUtils.equals(brand.toLowerCase(Locale.getDefault()), "huawei") || TextUtils.equals(brand.toLowerCase(Locale.getDefault()), "honor")) {
            gotoHuaweiPermission(context, requestCode)
        } else {
            context.startActivityForResult(getAppDetailSettingIntent(context), requestCode)
        }
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private fun gotoMiuiPermission(context: Activity, requestCode: Int) {
        try { // MIUI 8
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
            localIntent.putExtra("extra_pkgname", context.packageName)
            context.startActivityForResult(localIntent, requestCode)
        } catch (e: Exception) {
            try { // MIUI 5/6/7
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                localIntent.putExtra("extra_pkgname", context.packageName)
                context.startActivityForResult(localIntent, requestCode)
            } catch (e1: Exception) { // 否则跳转到应用详情
                context.startActivityForResult(getAppDetailSettingIntent(context), requestCode)
            }

        }

    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private fun gotoMeizuPermission(context: Activity, requestCode: Int) {
        try {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
            context.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
            context.startActivityForResult(getAppDetailSettingIntent(context), requestCode)
        }

    }

    /**
     * 华为的权限管理页面
     */
    private fun gotoHuaweiPermission(context: Activity, requestCode: Int) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.LauncherActivity")//华为权限管理
            intent.component = comp
            context.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
            context.startActivityForResult(getAppDetailSettingIntent(context), requestCode)
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，<span style="font-size:18px;">也可以先把用户引导到系统设置页面</span>）
     *
     * @return
     */
    private fun getAppDetailSettingIntent(context: Activity): Intent {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", context.packageName, null)
        return localIntent
    }
}