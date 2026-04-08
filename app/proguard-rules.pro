# === Keep Apache POI core classes ===
-keep class org.apache.poi.** { *; }
-keep class com.opencsv.** { *; }

# === Keep OpenXML schema classes (critical for DOCX/XLSX) ===
-keep class org.openxmlformats.schemas.** { *; }
-keep class com.microsoft.schemas.** { *; }
-keep class org.etsi.uri.** { *; }
-keep class org.w3.x2000.** { *; }

# === Keep XmlBeans classes (POI uses reflection heavily) ===
-keep class org.apache.xmlbeans.** { *; }
-keep class schemaorg_apache_xmlbeans.** { *; }
-keep class * extends org.apache.xmlbeans.XmlObject { *; }
-keep class org.apache.xmlbeans.impl.** { *; }

# === Keep XML parsing interfaces ===
-keep interface javax.xml.stream.** { *; }
-keep class javax.xml.stream.** { *; }
-keep class org.w3c.dom.** { *; }

# === Suppress warnings for Android-missing optional classes ===
# Java AWT/Swing (not on Android)
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn com.sun.java.**

# Apache POI optional dependencies
-dontwarn org.apache.poi.**
-dontwarn org.apache.xmlbeans.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn com.microsoft.schemas.**
-dontwarn com.graphbuilder.**

# ETSI XML signature classes (optional for basic parsing)
-dontwarn org.etsi.uri.x01903.v13.**
-dontwarn org.etsi.uri.x01903.v14.**

# W3C XML digital signature (optional)
-dontwarn org.w3.x2000.x09.xmldsig.**

# XML streaming/parsing (Android provides alternatives)
-dontwarn javax.xml.stream.**
-dontwarn org.w3c.dom.**
-dontwarn com.sun.org.apache.**
-dontwarn com.sun.xml.**

# Logging frameworks (not bundled)
-dontwarn org.apache.logging.log4j.**
-dontwarn org.slf4j.**
-dontwarn commons-logging.**

# Build tools / optional POI integrations
-dontwarn com.github.javaparser.**
-dontwarn org.apache.maven.**
-dontwarn org.apache.tools.ant.**
-dontwarn aQute.bnd.annotation.spi.**
-dontwarn org.osgi.framework.**

# === Optimization hints for R8 ===
# Allow R8 to remove unused POI features we don't use
-assumenosideeffects class org.apache.poi.** {
    public *** set***(...);
    public *** get***();
}

# === Keep AndroidPdfViewer native bindings ===
-keep class com.shockwave.**
-keep class com.github.barteksc.pdfviewer.** { *; }
