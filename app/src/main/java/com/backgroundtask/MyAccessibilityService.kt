package com.backgroundtask

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast




class MyAccessibilityService: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        TODO("Not yet implemented")
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        return super.onKeyEvent(event)

        val action = event!!.action
        val keyCode = event!!.keyCode
        // the service listens for both pressing and releasing the key
        // so the below code executes twice, i.e. you would encounter two Toasts
        // in order to avoid this, we wrap the code inside an if statement
        // which executes only when the key is released
        // the service listens for both pressing and releasing the key
        // so the below code executes twice, i.e. you would encounter two Toasts
        // in order to avoid this, we wrap the code inside an if statement
        // which executes only when the key is released
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                Log.i("Check", "KeyUp")
                Toast.makeText(this, "KeyUp", Toast.LENGTH_SHORT).show()
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Log.i("Check", "KeyDown")
                Toast.makeText(this, "KeyDown", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onKeyEvent(event)
    }
}