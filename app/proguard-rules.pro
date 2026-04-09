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

# 👇 NEW: Keep the Android XML Parsers we just added
-keep class javax.xml.stream.** { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.stax2.** { *; }

# === Suppress warnings for Android-missing optional classes ===
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn com.sun.java.**
-dontwarn org.apache.poi.**
-dontwarn org.apache.xmlbeans.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn com.microsoft.schemas.**
-dontwarn com.graphbuilder.**
-dontwarn org.etsi.uri.x01903.v13.**
-dontwarn org.etsi.uri.x01903.v14.**
-dontwarn org.w3.x2000.x09.xmldsig.**
-dontwarn org.w3c.dom.**
-dontwarn com.sun.org.apache.**
-dontwarn com.sun.xml.**
-dontwarn org.apache.logging.log4j.**
-dontwarn org.slf4j.**
-dontwarn commons-logging.**
-dontwarn com.github.javaparser.**
-dontwarn org.apache.maven.**
-dontwarn org.apache.tools.ant.**
-dontwarn aQute.bnd.annotation.spi.**
-dontwarn org.osgi.framework.**

# === Keep AndroidPdfViewer native bindings ===
-keep class com.shockwave.**
-keep class com.github.barteksc.pdfviewer.** { *; }
