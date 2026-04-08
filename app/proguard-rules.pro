# Keep core POI and OpenCSV classes safe from shrinking
-keep class org.apache.poi.** { *; }
-keep class com.opencsv.** { *; }

# Tell R8 to ignore missing optional dependencies inside Apache POI / Log4j
-dontwarn org.apache.poi.**
-dontwarn aQute.bnd.annotation.spi.**
-dontwarn javax.xml.stream.**
-dontwarn net.sf.saxon.**
-dontwarn org.osgi.framework.**

# Log4j specific warnings
-dontwarn org.apache.logging.log4j.**
