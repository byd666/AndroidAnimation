package byd.com.byd.androidanimation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import byd.com.byd.androidanimation.weiget.FrameAnimations

class FrameAnimationActivity : AppCompatActivity() {
    var iv: ImageView? = null
    var btnStart: Button? = null
    private var start = false
    private var code: Int = 0
    private var animationFrame: FrameAnimations .FramesSequenceAnimation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_animation)
        iv = findViewById(R.id.iv_drawable_animation)
        btnStart = findViewById(R.id.btn_start)
        code=intent.getIntExtra("code",0)
        if (code == 0) {
            btnStart!!.setOnClickListener({
                iv!!.setBackgroundResource(R.drawable.frame_animation)
                var frameAnim = iv!!.background as AnimationDrawable
                if (!switchBtn()) {
                    frameAnim.start()
                } else {
                    frameAnim.stop()
                }
            })
        } else {
            //优化帧动画
            btnStart!!.setOnClickListener({
                if (animationFrame == null) {
                    animationFrame = FrameAnimations().getInstance(R.array.frame_anim, 16).createProgressDialogAnim(iv!!)
                }
                if(!switchBtn()){
                    animationFrame!!.start()
                }else {
                    animationFrame!!.stop()
                }
            })
        }
    }

    //控制开关
    private fun switchBtn(): Boolean {
        val returnV = start
        start = !start

        btnStart!!.text = if (start === false) "START" else "STOP"
        return returnV
    }

}
