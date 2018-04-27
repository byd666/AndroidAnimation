package byd.com.byd.androidanimation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import byd.com.byd.androidanimation.weiget.Rotate3dAnimation

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var iv: ImageView? = null
    private var translate: Button? = null
    private var scale: Button? = null
    private var rotate: Button? = null
    private var alpha: Button? = null
    private var selfDefine: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        iv = findViewById(R.id.iv_main)
        translate = findViewById(R.id.btn_translate)
        scale = findViewById(R.id.btn_scale)
        rotate = findViewById(R.id.btn_rotate)
        alpha = findViewById(R.id.btn_alpha)
        selfDefine = findViewById(R.id.btn_self_define)

        translate!!.setOnClickListener(this)
        scale!!.setOnClickListener(this)
        rotate!!.setOnClickListener(this)
        alpha!!.setOnClickListener(this)
        selfDefine!!.setOnClickListener(this)
    }

    /**
     * 平移
     * */
    private fun translateView() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.view_translate)
        iv!!.startAnimation(loadAnimation)
    }

    /**
     * 缩放
     */
    private fun scaleView() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.view_scale)
        iv!!.startAnimation(loadAnimation)
    }

    /**
     * 旋转
     */
    private fun rotateView() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.view_rotate)
        iv!!.startAnimation(loadAnimation)
    }

    /**
     * 透明度变化
     */
    private fun alphaView() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.view_alpha)
        iv!!.startAnimation(loadAnimation)
    }

    /**
     * 自定义的动画
     */
    private fun rotate3dAnimation(){
        var rotate3dAnimation:Rotate3dAnimation?= Rotate3dAnimation(0.0f, 360F,10f,0f,0f,true,Rotate3dAnimation.DIRECTION.Y)
        rotate3dAnimation!!.duration=3000
        iv!!.startAnimation(rotate3dAnimation)
    }
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_translate -> translateView()
            R.id.btn_scale -> scaleView()
            R.id.btn_rotate-> rotateView()
            R.id.btn_alpha -> alphaView()
            R.id.btn_self_define ->rotate3dAnimation()
        }
    }

}
