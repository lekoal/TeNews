package com.private_projects.tenews.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.private_projects.tenews.databinding.ActivityStartScreenBinding
import com.private_projects.tenews.ui.main.MainActivity

class StartScreenActivity : AppCompatActivity() {
    private var _binding: ActivityStartScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Handler(mainLooper).postDelayed(
                    {
                        startActivity(
                            Intent(this@StartScreenActivity, MainActivity::class.java)
                        )
                        finish()
                    }, 1000L
                )
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}