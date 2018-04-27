package byd.com.byd.androidanimation.weiget

import android.graphics.Camera
import android.graphics.Matrix
import android.view.animation.Animation
import android.view.animation.Transformation



/**
 * @author：byd666 on 2018/4/27 14:49
 */
class Rotate3dAnimation : Animation {
    private  var mFromDegrees : Float? =null
    private  var mToDegrees : Float? =null
    private  var mCenterX : Float? =null
    private  var mCenterY : Float? =null
    private  var mDepthZ : Float? =null
    private  var mReverse : Boolean? =null
    private  var mCamera: Camera ? = null

    //X轴方向，或Y轴方向
    enum class DIRECTION {
        X, Y
    }

    var direction = DIRECTION.Y

    constructor(fromDegrees: Float,toDegrees:Float,centerX :Float,centerY :Float,depthZ :Float,reverse :Boolean,direction:DIRECTION){
        mFromDegrees=fromDegrees
        mToDegrees=toDegrees
        mCenterX=centerX
        mCenterY=centerY
        mDepthZ=depthZ
        mReverse=reverse
        this.direction =direction
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera= Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        var fromDegrees=mFromDegrees
        var degrees= fromDegrees!! + ((mToDegrees!! - mFromDegrees!!) * interpolatedTime)
        var matix: Matrix?=t!!.matrix
        mCamera!!.save()
        if(mReverse!!){
            mCamera!!.translate(0.0f,0.0f,mDepthZ!!*interpolatedTime)
        }else{
            mCamera!!.translate(0.0f,0.0f,mDepthZ!!*(1.0f-interpolatedTime))
        }
        if (direction!! == DIRECTION.Y) mCamera!!.rotateY(degrees) else {
            mCamera!!.rotateX(degrees)
        }
        mCamera!!.getMatrix(matix)
        mCamera!!.restore()

        matix!!.preTranslate(-mCenterX!!,-mCenterY!!)
        matix!!.postTranslate(mCenterX!!,mCenterY!!)
    }
}