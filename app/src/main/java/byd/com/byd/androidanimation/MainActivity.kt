package byd.com.byd.androidanimation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

/**
 * @author：byd666 on 2018/ic5/ic2 10:02
 */
class MainActivity : AppCompatActivity(),View.OnClickListener {
    private var viewAnim: Button? = null
    private var drableAnim: Button? = null
    private var valueAnim: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        viewAnim=findViewById(R.id.btn_view_animation)
        drableAnim=findViewById(R.id.btn_animation_drawable)
        valueAnim=findViewById(R.id.btn_value_animation)
        viewAnim!!.setOnClickListener(this)
        drableAnim!!.setOnClickListener(this)
        valueAnim!!.setOnClickListener(this)

    }
    fun jump(clz :Class<Any>){
        var intent = Intent (MainActivity@this,clz)
        intent.putExtra("code",1)
        startActivity(intent)
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            //View动画
            R.id.btn_view_animation-> {
                jump(ViewAnimationActivity().javaClass)
            }
            //跳转到帧动画
            R.id.btn_animation_drawable->{
                jump(FrameAnimationActivity().javaClass)
            }
            //属性动画
            R.id.btn_value_animation->{
                jump(ValueAnimationActivity().javaClass)
            }
        }
    }

}