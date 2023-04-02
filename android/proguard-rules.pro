# Keep Kotlinx.serialization annotations and classes
-keepattributes *Annotation*
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}

# Keep the default constructor for Serializable classes
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable <init>(...);
}

# Keep custom serializers and generated code
-keep,includedescriptorclasses class **Serializer { *; }
-keepclassmembers class kotlinx.serialization.json.** { *; }
-keepclassmembers class kotlinx.serialization.internal.** { *; }
