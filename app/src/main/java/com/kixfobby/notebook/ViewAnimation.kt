package com.kixfobby.notebook

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

object ViewAnimation {
    fun expand(v: View, animListener: AnimListener) {
        val a: Animation = expandAction(v)
        a.setAnimationListener(object : Animation.AnimationListener {
            public override fun onAnimationStart(animation: Animation?) {}
            public override fun onAnimationEnd(animation: Animation?) {
                animListener.onFinish()
            }

            public override fun onAnimationRepeat(animation: Animation?) {}
        })
        v.startAnimation(a)
    }

    fun expand(v: View) {
        val a: Animation? = expandAction(v)
        v.startAnimation(a)
    }

    private fun expandAction(v: View): Animation {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targtetHeight: Int = v.getMeasuredHeight()
        v.getLayoutParams().height = 0
        v.setVisibility(View.VISIBLE)
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.getLayoutParams().height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targtetHeight * interpolatedTime) as Int
                v.requestLayout()
            }

            public override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.setDuration(
            (targtetHeight / v.getContext().getResources()
                .getDisplayMetrics().density) as Long
        )
        v.startAnimation(a)
        return a
    }

    fun collapse(v: View) {
        val initialHeight: Int = v.getMeasuredHeight()
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    v.setVisibility(View.GONE)
                } else {
                    v.getLayoutParams().height =
                        initialHeight - (initialHeight * interpolatedTime) as Int
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.setDuration(
            (initialHeight / v.getContext().getResources()
                .getDisplayMetrics().density) as Long
        )
        v.startAnimation(a)
    }

    fun flyInDown(v: View, animListener: AnimListener) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(0.0f)
        v.setTranslationY(0f)
        v.setTranslationY(-v.getHeight().toFloat())
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1.0f)
            .start()
    }

    fun flyOutDown(v: View, animListener: AnimListener) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(1.0f)
        v.setTranslationY(0f)
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .translationY(v.getHeight().toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(0.0f)
            .start()
    }

    fun fadeIn(v: View) {
        fadeIn(v, null)
    }

    fun fadeIn(v: View, animListener: AnimListener?) {
        v.setVisibility(View.GONE)
        v.setAlpha(0.0f)
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    v.setVisibility(View.VISIBLE)
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1.0f)
    }

    fun fadeOut(v: View) {
        fadeOut(v, null)
    }

    fun fadeOut(v: View, animListener: AnimListener?) {
        v.setAlpha(1.0f)
        // Prepare the View for the animation
        v.animate()
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(0.0f)
    }

    fun showIn(v: View) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(0f)
        v.setTranslationY(v.getHeight().toFloat())
        v.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun initShowOut(v: View) {
        v.setVisibility(View.GONE)
        v.setTranslationY(v.getHeight().toFloat())
        v.setAlpha(0f)
    }

    fun showOut(v: View) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(1f)
        v.setTranslationY(0f)
        v.animate()
            .setDuration(200)
            .translationY(v.getHeight().toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    v.setVisibility(View.GONE)
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }
            }) //.rotation(rotate ? 135f : 0f);
            .rotation(if (rotate) 0f else 0f)
        return rotate
    }

    fun fadeOutIn(view: View) {
        view.setAlpha(0f)
        val animatorSet: AnimatorSet = AnimatorSet()
        val animatorAlpha: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5f, 1f)
        ObjectAnimator.ofFloat(view, "alpha", 0f).start()
        animatorAlpha.setDuration(500)
        animatorSet.play(animatorAlpha)
        animatorSet.start()
    }

    fun showScale(v: View) {
        showScale(v, null)
    }

    fun showScale(v: View, animListener: AnimListener?) {
        v.animate()
            .scaleY(1f)
            .scaleX(1f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .start()
    }

    fun hideScale(v: View) {
        fadeOut(v, null)
    }

    fun hideScale(v: View, animListener: AnimListener) {
        v.animate()
            .scaleY(0f)
            .scaleX(0f)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                public override fun onAnimationEnd(animation: Animator?) {
                    if (animListener != null) animListener.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .start()
    }

    fun hideFab(fab: View) {
        val moveY: Int = 2 * fab.getHeight()
        fab.animate()
            .translationY(moveY.toFloat())
            .setDuration(300)
            .start()
    }

    fun showFab(fab: View) {
        fab.animate()
            .translationY(0f)
            .setDuration(300)
            .start()
    }

    open interface AnimListener {
        open fun onFinish()
    }
}