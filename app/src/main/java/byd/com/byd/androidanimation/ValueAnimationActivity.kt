package byd.com.byd.androidanimation

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button

class ValueAnimationActivity : AppCompatActivity(), View.OnClickListener{
    private var view: View ?=null
    private var btn1: Button ?=null
    private var btn2: Button ?=null
    private var btn3: Button ?=null
    private var btn4: Button ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animation)
        view=findViewById(R.id.value_anim_iv)
        btn1=findViewById(R.id.btn1)
        btn2=findViewById(R.id.btn2)
        btn3=findViewById(R.id.btn3)
        btn4=findViewById(R.id.btn4)
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
        btn4!!.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.btn1 -> sample1()
            R.id.btn2 -> sample2()
            R.id.btn3 -> sample3()
           // R.id.btn4 -> sample4()
            R.id.btn4 -> sample5(btn4 as View,btn4!!.width,500)
        }
    }

    /**
     *  方法三
     *  让Button的宽度在2秒钟之内增加到500px
     */
    private fun sample5(target: View,start:Int,end:Int) {
        var value=ValueAnimator.ofInt(1,100)
        value.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            var intEvaluator= IntEvaluator()
            //获得当前的进度值
            var currentValue:Int= it.animatedValue as Int
            Log.e("sample5", "当前的进度$currentValue")
            //获得当前进度占整个动画的比例
            var fraction:Float=it.animatedFraction
            target.layoutParams.width=intEvaluator.evaluate(fraction,start,end)
            target.requestLayout()
        })
        value.setDuration(2000).start()
    }

    /**
     * 方法二
     * 让Button的宽度在2秒钟之内增加到200px
     */
    private fun sample4() {
        var wrapper= ViewWrapper(btn4 as View)
        ObjectAnimator.ofInt(wrapper!!,"width",200).setDuration(2000).start()
    }

    /**
     * 改变View的背景色
     */
    private fun sample3() {
        var value :ValueAnimator = ObjectAnimator.ofInt(view!!,"backgroundColor", Color.RED,Color.BLUE)
        value!!.duration = 500
        value!!.setEvaluator(ArgbEvaluator())
        value!!.repeatMode=ValueAnimator.REVERSE
        value!!.repeatCount=ValueAnimator.INFINITE
        value!!.start()
    }

    /**
     * 动画的集合
     */
    private fun sample2() {
        var animSet= AnimatorSet()
        animSet!!.playTogether(
                ObjectAnimator.ofFloat(view!!,"rotationX",0f,360f),
                ObjectAnimator.ofFloat(view!!,"rotationY",0f,180f),
                ObjectAnimator.ofFloat(view!!,"rotation",0f,-90f),
                ObjectAnimator.ofFloat(view!!,"translationX",0f,90f),
                ObjectAnimator.ofFloat(view!!,"translationY",0f,90f),
                ObjectAnimator.ofFloat(view!!,"scaleX",1f,1.5f),
                ObjectAnimator.ofFloat(view!!,"scaleY",1f,0.5f),
                ObjectAnimator.ofFloat(view!!,"alpha",1f,0.25f,1f)
        )
        animSet!!.duration=5*1000
        animSet!!.start()
    }

    /**
     * 在Y轴的方向上平移
     */
    private fun sample1() {
        var ofFloat = ObjectAnimator.ofFloat(view!!,"translationY", (-view!!.height).toFloat()) as ObjectAnimator
        ofFloat!!.start()
    }

    private class ViewWrapper constructor(target:View){
        private var mTarget : View?=null
        init {
            this.mTarget=target
        }
        fun  getWidth() : Int{
            return mTarget!!.layoutParams.width
        }
        fun setWidth(width:Int){
                mTarget!!.layoutParams.width=width
            mTarget!!.requestLayout()
        }
    }
}
