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

# Log4j specific warnings
-dontwarn org.apache.logging.log4j.**
