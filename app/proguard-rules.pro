# Keep core POI and OpenCSV classes safe from shrinking
-keep class org.apache.poi.** { *; }
-keep class com.opencsv.** { *; }

# CRITICAL FOR DOCX AND XLSX: Keep XML parsing classes safe from R8 shrinking
-keep class org.openxmlformats.** { *; }
-keep class org.apache.xmlbeans.** { *; }
-keep class schemaorg_apache_xmlbeans.** { *; }
-keep class * extends org.apache.xmlbeans.XmlObject { *; }

# Tell R8 to ignore missing optional dependencies inside Apache POI / Log4j
-dontwarn org.apache.poi.**
-dontwarn aQute.bnd.annotation.spi.**
-dontwarn javax.xml.stream.**
-dontwarn net.sf.saxon.**
-dontwarn org.osgi.framework.**
-dontwarn org.apache.logging.log4j.**

# --- NEW FIXES FOR MISSING CLASSES ---
# Ignore missing build tools (Ant, Maven, JavaParser) referenced by xmlbeans
-dontwarn com.github.javaparser.**
-dontwarn org.apache.maven.**
-dontwarn org.apache.tools.ant.**
-dontwarn com.sun.org.apache.xml.internal.resolver.**

# Ignore missing extended Microsoft, OpenXML, and security schemas
-dontwarn com.microsoft.schemas.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn org.etsi.uri.**
-dontwarn org.w3.x2000.**
