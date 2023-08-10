-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

# Retrofit问题的修复方案 https://github.com/square/retrofit/issues/3751
# 保留Call和Response的通用签名（R8全模式会从非保留项中删除签名）
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# 在R8全模式下，对于未保留的类，泛型签名会被删除。挂起函数会被包装在继续函数中，其中使用了类型参数。
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation