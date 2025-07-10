# 🎆 FireworkButton

A fun and beautiful animated button for Android apps.  
When clicked, it bursts into colorful particles — like fireworks!

---

## ✨ Features

- 🧨 Firework animation on click
- 🌈 Random colorful particles
- 🔺 Particle shape support: `CIRCLE`, `STAR`, `HEART`
- 📏 Customizable: particle size, speed, count, duration
- ⚡ Easy to integrate into any Android project
- ☄️ Compatible with Jetpack Compose or XML views

---

## 🚀 Installation (via JitPack)

Add the JitPack repository to your root `settings.gradle.kts` or `build.gradle`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

Then add this to your app-level build.gradle.kts:

```kotlin
dependencies {
    implementation("com.github.appnapps:fireworkbutton:1.0.0")
}
```
In XML
```kotlin
<com.appnapps.fireworkbutton.FireworkButton
    android:id="@+id/fireworkButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Click Me!"
    android:backgroundTint="#FF4081"
    android:textColor="#FFFFFF" />
```
In Kotlin
```kotlin
val button = findViewById<FireworkButton>(R.id.fireworkButton)

// Optional: Configure firework behavior
button.setParticleShape(Shape.STAR)            // Shape: CIRCLE, STAR, HEART
button.setParticleSize(24f)                    // Particle size in px
button.setAnimationDuration(3000L)             // Animation duration in ms

button.setOnFireworkClickListener {
    Toast.makeText(this, "Boom!", Toast.LENGTH_SHORT).show()
}

```
🌟 Customization Options
Method	Description	Example
setParticleShape(Shape)	Sets particle shape (CIRCLE, STAR, HEART)	button.setParticleShape(Shape.HEART)
setParticleSize(Float)	Sets particle size (in pixels)	button.setParticleSize(20f)
setAnimationDuration(Long)	Duration of animation (in milliseconds)	button.setAnimationDuration(2500L)

<img src="https://github.com/appnapps/fireworkbutton/blob/main/docs/FireworkButton.gif" width="320"/>
📸 Want to add your own GIF? Put it in docs/firework_demo.gif

