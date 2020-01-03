package com.example.utils

/**
 *  Author : JinTao Li
 *  Create Time : 2020/1/3
 */
class TextUtils {
    companion object{
        /**
         * 将字符串格式化成JSON的格式
         * @param strJson                           字符串
         * @return                                  json
         */
        @JvmStatic
        fun stringToJson(strJson: String): String? { // 计数tab的个数
            var tabNum = 0
            val jsonFormat = StringBuilder()
            val length = strJson.length
            var last = 0.toChar()
            for (i in 0 until length) {
                val c = strJson[i]
                if (c == '{') {
                    tabNum++
                    jsonFormat.append(c).append("\n")
                    jsonFormat.append(getSpaceOrTab(tabNum))
                } else if (c == '}') {
                    tabNum--
                    jsonFormat.append("\n")
                    jsonFormat.append(getSpaceOrTab(tabNum))
                    jsonFormat.append(c)
                } else if (c == ',') {
                    jsonFormat.append(c).append("\n")
                    jsonFormat.append(getSpaceOrTab(tabNum))
                } else if (c == ':') {
                    jsonFormat.append(c).append(" ")
                } else if (c == '[') {
                    tabNum++
                    val next = strJson[i + 1]
                    if (next == ']') {
                        jsonFormat.append(c)
                    } else {
                        jsonFormat.append(c).append("\n")
                        jsonFormat.append(getSpaceOrTab(tabNum))
                    }
                } else if (c == ']') {
                    tabNum--
                    if (last == '[') {
                        jsonFormat.append(c)
                    } else {
                        jsonFormat.append("\n").append(getSpaceOrTab(tabNum)).append(c)
                    }
                } else {
                    jsonFormat.append(c)
                }
                last = c
            }
            return jsonFormat.toString()
        }

        /**
         * 换行操作
         * @param tabNum                            tabNum
         * @return
         */
        private fun getSpaceOrTab(tabNum: Int): String? {
            val sbTab = java.lang.StringBuilder()
            for (i in 0 until tabNum) {
                sbTab.append('\t')
            }
            return sbTab.toString()
        }

        /**
         * 将内容转化为html格式
         * @param content                           内容
         * @return
         */
        fun stringToHtml(content: String?): String? {
            return null
        }
    }
}