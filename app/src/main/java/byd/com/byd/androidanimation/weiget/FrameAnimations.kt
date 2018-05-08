package byd.com.byd.androidanimation.weiget

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Handler
import android.widget.ImageView
import byd.com.byd.androidanimation.AnimationApplication
import byd.com.byd.androidanimation.R
import java.lang.ref.SoftReference


/**
 * @author：byd666 on 2018/5/4 09:50
 */
class FrameAnimations {
    var FPS = 50  // 每秒播放帧数，fps = 1/t，t-动画两帧时间间隔
    private var resId = R.array.frame_anim //图片资源
    private val mContext = AnimationApplication.getAppContext()
    // 单例
    private var mInstance: FrameAnimations? = null

    //获取单例
    fun getInstance(resId: Int, fps: Int): FrameAnimations {
        if (mInstance == null)
            mInstance = FrameAnimations()
        mInstance!!.setResId(resId, fps)
        return mInstance as FrameAnimations
    }

    fun setResId(resId: Int, fps: Int) {
        this.resId = resId
        this.FPS = fps
    }

    /**
     * @param imageView
     * @return progress dialog animation
     */
    fun createProgressDialogAnim(imageView: ImageView): FramesSequenceAnimation {
        return FramesSequenceAnimation(imageView, getData(resId), FPS)
    }


    /**
     * 循环读取帧---循环播放帧
     */
    inner class FramesSequenceAnimation(imageView: ImageView, private val mFrames: IntArray // 帧数组
                                        , fps: Int) {
        // 当前帧
        private var mIndex: Int = 0
        // 开始/停止播放用
        private var mShouldRun: Boolean = false
        // 动画是否正在播放，防止重复播放
        private var mIsRunning: Boolean = false
        // 软引用ImageView，以便及时释放掉
        private val mSoftReferenceImageView: SoftReference<ImageView>
        private var mHandler: Handler? = null
        private val mDelayMillis: Long
        //播放停止监听
        private var mOnAnimationStoppedListener: OnAnimationStoppedListener? = null

        private var mBitmap: Bitmap? = null
        //Bitmap管理类，可有效减少Bitmap的OOM问题
        private var mBitmapOptions: BitmapFactory.Options? = null
        //循环读取下一帧
        private val next: Int
            get() {
                mIndex++
                if (mIndex >= mFrames.size)
                    mIndex = 0
                return mFrames[mIndex]
            }

        init {
            mHandler = Handler()
            mIndex = -1
            mSoftReferenceImageView = SoftReference(imageView)
            mShouldRun = false
            mIsRunning = false
            //帧动画时间间隔，毫秒
            mDelayMillis = (1000 / fps).toLong()

            imageView.setImageResource(mFrames[0])

            // 当图片大小类型相同时进行复用，避免频繁GC
            if (Build.VERSION.SDK_INT >= 11) {
                val bmp = (imageView.drawable as BitmapDrawable).bitmap
                val width = bmp.width
                val height = bmp.height
                val config = bmp.config
                mBitmap = Bitmap.createBitmap(width, height, config)
                mBitmapOptions = BitmapFactory.Options()
                //设置Bitmap内存复用,Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                mBitmapOptions!!.inBitmap = mBitmap
                //解码时返回可变Bitmap
                mBitmapOptions!!.inMutable = true
                //缩放比例
                mBitmapOptions!!.inSampleSize = 1
            }
        }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        @Synchronized
        fun start() {
            mShouldRun = true
            if (mIsRunning)
                return

            val runnable = object : Runnable {
                override fun run() {
                    val imageView = mSoftReferenceImageView.get()
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false
                        if (mOnAnimationStoppedListener != null) {
                            mOnAnimationStoppedListener!!.AnimationStopped()
                        }
                        return
                    }

                    mIsRunning = true
                    //新开线程去读下一帧
                    mHandler!!.postDelayed(this, mDelayMillis)
                    if (imageView.isShown) {
                        val imageRes = next
                        // so Build.VERSION.SDK_INT >= 11
                        if (mBitmap != null) {
                            var bitmap: Bitmap? = null
                            try {
                                bitmap = BitmapFactory.decodeResource(imageView.resources, imageRes, mBitmapOptions)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap)
                            } else {
                                imageView.setImageResource(imageRes)
                                mBitmap!!.recycle()
                                mBitmap = null
                            }
                        } else {
                            imageView.setImageResource(imageRes)
                        }
                    }

                }
            }
            mHandler!!.post(runnable)
        }
        /**
         * 停止播放
         */
        @Synchronized
        fun stop() {
            mShouldRun = false
        }

        /**
         * 设置停止播放监听
         * @param listener
         */
        fun setOnAnimStopListener(listener: OnAnimationStoppedListener) {
            this.mOnAnimationStoppedListener = listener
        }
    }

    /**
     * 从xml中读取帧数组
     * @param resId
     * @return
     */
    private fun getData(resId: Int): IntArray {
        val array = mContext.getResources().obtainTypedArray(resId)

        val len = array.length()
        val intArray = IntArray(array.length())

        for (i in 0 until len) {
            intArray[i] = array.getResourceId(i, 0)
        }
        array.recycle()
        return intArray
    }

    /**
     * 停止播放监听
     */
    interface OnAnimationStoppedListener {
        fun AnimationStopped()
    }

}