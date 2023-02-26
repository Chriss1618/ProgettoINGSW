package com.ratatouille.GUI.Animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Manager_Animation {

    private static Animation fadeOut(int milliseconds){
        Animation fadeout = new AlphaAnimation(1.0f, 0.0f );
        fadeout.setDuration(milliseconds);
        return fadeout;
    }
    private static Animation fadeIn(int milliseconds){
        Animation fadeIn = new AlphaAnimation(0.0f , 1.0f);
        fadeIn.setDuration(milliseconds);
        return fadeIn;
    }

    public static AnimationSet getFadeOut(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        set.addAnimation(fadeOut(milliseconds));

        return set;
    }

    public static AnimationSet getFadeIn(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        set.addAnimation(fadeIn(milliseconds));

        return set;
    }


    public static AnimationSet getTranslateAnimatioOUT(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,-0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(1.0f , 0.0f);
        fadeOut.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }
    public static AnimationSet getTranslateAnimatioOUTtoRight(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(1.0f , 0.0f);
        fadeOut.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }

    public static AnimationSet getTranslateAnimatioINfromRight(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeIn = new AlphaAnimation(0.0f , 1.0f);
        fadeIn.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeIn);
        return set;
    }
    public static AnimationSet getTranslateAnimatioINfromLeft(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,-0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeIn = new AlphaAnimation(0.0f , 1.0f);
        fadeIn.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeIn);
        return set;
    }

    public static ScaleAnimation getCircleReveal(){
        ScaleAnimation fade_in =  new ScaleAnimation(0f, 8f, 0f, 8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(500);     // animation duration in milliseconds
        fade_in.setFillAfter(true);
        return fade_in;
    }

    public static AnimationSet getTranslateLogoDown(){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,-1f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.63f);
        TA.setDuration(500);
        TA.setInterpolator(new LinearInterpolator());

        set.addAnimation(TA);
        return set;
    }
    public static AnimationSet getTranslateLogoUp(){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,-0.33f);
        TA.setDuration(500);
        TA.setInterpolator(new LinearInterpolator());

        set.addAnimation(TA);
        return set;
    }

    public static AnimationSet getTranslationINfromDown(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,1f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(0.0f , 1.0f);
        fadeOut.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }
    public static AnimationSet getTranslationINfromUp(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,-1f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(0.0f , 1.0f);
        fadeOut.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }

    public static AnimationSet getTranslationOUTtoDown(int milliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,1f);
        TA.setDuration(milliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(1.0f , 0.0f);
        fadeOut.setDuration(milliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }
    public static AnimationSet getTranslationOUTtoUp(int miliseconds){
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation TA = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0f,
                TranslateAnimation.RELATIVE_TO_PARENT,-1f);
        TA.setDuration(miliseconds);
        TA.setInterpolator(new LinearInterpolator());

        Animation fadeOut = new AlphaAnimation(1.0f ,0.0f);
        fadeOut.setDuration(miliseconds);

        set.addAnimation(TA);
        set.addAnimation(fadeOut);
        return set;
    }

}
