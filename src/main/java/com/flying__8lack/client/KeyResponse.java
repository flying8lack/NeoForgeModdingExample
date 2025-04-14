package com.flying__8lack.client;

public class KeyResponse {

    private static int DELAY = 30;
    private static boolean FLYING_BTN = false;

    private static boolean JUMPKIT_ENABLED = true;

    public static boolean isFLYING_BTN() {
        return FLYING_BTN;
    }

    public static void pass(){
        if(DELAY > 0){
            DELAY -= 1;
        }
    }

    public static void toggleFLYING_BTN() {
        if(DELAY > 0){
            return;
        }
        FLYING_BTN = !FLYING_BTN;
        DELAY = 50;
    }


    public static boolean isJumpkitEnabled() {
        return JUMPKIT_ENABLED;
    }

    public static void setJumpkitEnabled(boolean jumpkitEnabled) {
        JUMPKIT_ENABLED = jumpkitEnabled;
    }

    public static void toggleJumpkitEnabled() {
        if(DELAY > 0){
            return;
        }
        JUMPKIT_ENABLED = !JUMPKIT_ENABLED;
        DELAY = 30;
    }
}
