<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : about.xsl
    Created on : October 18, 2005, 5:16 PM
    Author     : kitfox
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:java="http://xml.apache.org/xslt/java"
version="1.0">
    <xsl:output method="html" encoding="ISO-8859-1" doctype-public="-//W3C/DTD HTML 1.0 Transitional//EN"/>
    <!--xsl:output method="html" encoding="ISO-8859-1" doctype-public="-//W3C/DTD HTML 1.0 Transitional//EN"/-->

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">

<xsl:variable name="dateformat" select="'yyyy, MMMMM, d hh:mm'"/>
<xsl:variable name="formatter" select="java:java.text.SimpleDateFormat.new($dateformat)"/>
<xsl:variable name="date" select="java:java.util.Date.new()"/>


<html>
  <head>
    <title>About SVG Salamander</title>
  </head>
  <body>

    <div style="text-align: center;">
    <h3>SVG Salamander</h3>
    Created by Mark McKay<br/>
    Copyright 2005<br/>
    <br/>
    http://svgsalamander.dev.java.net<br/>
    http://www.kitfox.com<br/>
    <br/>
    Last built: <xsl:value-of select="java:format($formatter, $date)"/><br/>
    </div>

  </body>
</html>

    </xsl:template>

</xsl:stylesheet>
