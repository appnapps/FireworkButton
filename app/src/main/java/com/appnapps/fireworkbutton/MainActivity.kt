package com.appnapps.fireworkbutton

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.appnapps.snackx.SnackX

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val fireworkButton = findViewById<FireworkButton>(R.id.fireworkButton)
        fireworkButton.setParticleShape(Shape.STAR)
        fireworkButton.setParticleSize(34f) // 크기 크게
        fireworkButton.setAnimationDuration(2000L) // 3초 동안 지속

        // 클릭 리스너는 FireworkButton 내부에 이미 포함되어 있으므로
        // 추가로 할 게 없지만, 여기서 다른 동작을 추가할 수도 있음
        fireworkButton.setOnFireworkClickListener {
            SnackX.show(
                context = this,
                message = "저장되었습니다!",
                iconResId = R.drawable.ic_check,
                duration = 2500,
                position = SnackX.Position.BOTTOM,
                backgroundColor = Color.WHITE,
                textColor = Color.BLUE,
                animation = SnackX.AnimationStyle.BOUNCE,
                animationDuration = 2000L // 나타남/사라짐 애니메이션 속도 조절
            )
        }


    }
}

