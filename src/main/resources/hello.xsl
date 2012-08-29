<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
        version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <title>Today's greeting</title>
            </head>
            <body>
                <p>
                    <xsl:value-of select="greeting"/>
                </p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>