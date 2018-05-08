package byd.com.byd.androidanimation

import android.app.Application
import android.content.Context
import android.provider.Settings


/**
 * @author：byd666 on 2018/5/4 09:53
 */
class AnimationApplication : Application() {
    companion object {
        private var mContext: Context ? = null
        fun getAppContext()= mContext!!

    }

    override fun onCreate() {
        super.onCreate()
        mContext=applicationContext
    }
    fun getAndroidId(): String {
        return Settings.Secure.getString(
                getAppContext().contentResolver, Settings.Secure.ANDROID_ID)
    }
}
