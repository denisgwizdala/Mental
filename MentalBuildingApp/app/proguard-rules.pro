# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

-keep class com.mentalbuilding.data.entities.** { *; }
-keepclassmembers class com.mentalbuilding.data.entities.** { *; }

# Keep Room annotations
-keep @androidx.room.Entity class *
-keep @androidx.room.Database class *
