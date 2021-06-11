package com.hym.netdemo

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年06月10日 7:51 上午
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 重试请求的网络拦截器
 */
class RetryInterceptor(private val maxRetry:Int = 0) : Interceptor {

    private var retriedNum: Int = 0//已经重试的次数，注意，设置maxRetry重试次数，作用于重试，所以总的请求次数，可能就是原始的1+maxRetry

    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request = chain.request()
        LogUtils.d("intercept:当前retriedNum = $retriedNum")
        var response = chain.proceed(request)
        while (!response.isSuccessful && retriedNum < maxRetry){
            retriedNum++
            LogUtils.d("intercept:当前retriedNum = $retriedNum")
            response = chain.proceed(request)
        }
        return response
    }
}