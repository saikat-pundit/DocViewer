# === Keep Apache POI core classes ===
-keep class org.apache.poi.** { *; }
-keep class com.opencsv.** { *; }

# === Keep OpenXML schema classes (critical for DOCX/XLSX) ===
-keep class org.openxmlformats.schemas.** { *; }
-keep class com.microsoft.schemas.** { *; }
-keep class org.etsi.uri.** { *; }
-keep class org.w3.x2000.** { *; }

# === Keep XmlBeans classes (POI uses reflection) ===
-keep class org.apache.xmlbeans.** { *; }
-keep class schemaorg_apache_xmlbeans.** { *; }
-keep class * extends org.apache.xmlbeans.XmlObject { *; }
-keep class org.apache.xmlbeans.impl.** { *; }

# === Keep XML parsing interfaces ===
-keep interface javax.xml.stream.** { *; }
-keep class javax.xml.stream.** { *; }

# === Suppress warnings for Android-missing optional classes ===
-dontwarn org.apache.poi.**
-dontwarn org.apache.xmlbeans.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn com.microsoft.schemas.**
-dontwarn javax.xml.stream.**
-dontwarn org.w3c.dom.**
-dontwarn com.sun.org.apache.**
-dontwarn org.apache.logging.log4j.**
-dontwarn com.github.javaparser.**
-dontwarn org.apache.maven.**
-dontwarn org.apache.tools.ant.**
