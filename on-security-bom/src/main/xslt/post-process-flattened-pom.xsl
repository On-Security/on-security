<?xml version="1.0" encoding="utf-8"?>
<!--
  ~     Copyright (C) 2022  恒宇少年
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     (at your option) any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program.  If not, see <https://www.gnu.org/licenses/>.
  -->

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://maven.apache.org/POM/4.0.0"
                exclude-result-prefixes="m">
    <xsl:output method="xml" encoding="utf-8" indent="yes"
                xslt:indent-amount="2" xmlns:xslt="http://xml.apache.org/xslt" />
    <xsl:strip-space elements="*" />
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>
    <xsl:template match="/m:project/m:properties">
        <xsl:copy>
            <xsl:apply-templates select="node()">
                <xsl:sort select="name()" />
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
    <xsl:template
            match="/m:project/m:dependencyManagement/m:dependencies/m:dependency/m:version/text()[. = '${revision}']">
        <xsl:value-of select="/m:project/m:version/text()" />
    </xsl:template>
    <xsl:template
            match="/m:project/m:build/m:pluginManagement/m:plugins/m:plugin/m:version/text()[. = '${revision}']">
        <xsl:value-of select="/m:project/m:version/text()" />
    </xsl:template>
    <xsl:template match="/m:project/m:properties/m:revision" />
    <xsl:template match="/m:project/m:properties/m:main.basedir" />
    <xsl:template match="/m:project/m:distributionManagement" />
</xsl:stylesheet>