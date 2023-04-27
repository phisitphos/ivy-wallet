-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.http.**
-dontwarn org.slf4j.helpers.**
-dontwarn kotlinx.serialization.internal.**
-dontwarn org.slf4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.http.client.utils.CloneUtils
-dontwarn org.apache.http.impl.client.CloseableHttpResponseProxy
-dontwarn org.apache.http.nio.protocol.HttpAsyncRequestExecutor
-dontwarn kotlinx.serialization.internal.PlatformKt

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