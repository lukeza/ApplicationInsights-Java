/*
 * ApplicationInsights-Java
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the ""Software""), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package com.microsoft.applicationinsights.agent.internal.config.builder;

import com.google.common.base.Strings;
import com.microsoft.applicationinsights.internal.logger.InternalLogger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XmlParserUtils {

    private final static String ENABLED_ATTRIBUTE = "enabled";

    public static Element getFirst(NodeList nodes) {
        if (nodes == null || nodes.getLength() == 0) {
            return null;
        }
        Node node = nodes.item(0);
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }
        return (Element) node;
    }

    public static boolean getEnabled(Element element, String attributeName) {
        if (element == null) {
            return true;
        }
        try {
            String strValue = element.getAttribute(ENABLED_ATTRIBUTE);
            if (!Strings.isNullOrEmpty(strValue)) {
                return Boolean.valueOf(strValue);
            }
            return true;
        } catch (Exception e) {
            InternalLogger.INSTANCE.error("Failed to parse attribute '%s' of '%s', default value (%b) will be used.",
                    ENABLED_ATTRIBUTE, attributeName, true);
        }
        return true;
    }

    /**
     * Method to get the attribute value for W3C
     */
    static boolean w3cEnabled(Element element, String attributeName, boolean defaultValue) {
        try {
            String strValue = element.getAttribute(attributeName);
            if (!Strings.isNullOrEmpty(strValue)) {
                return Boolean.valueOf(strValue);
            }
            return defaultValue;
        } catch (Exception e) {
            InternalLogger.INSTANCE.error("cannot parse the correlation format, will default"
                    + "to AI proprietary correlation", ExceptionUtils.getStackTrace(e));
        }
        return defaultValue;
    }

    public static long getLongAttribute(Element element, String elementName, String attributeName, long defaultValue) {
        if (element == null) {
            return defaultValue;
        }
        try {
            String strValue = element.getAttribute(attributeName);
            if (!Strings.isNullOrEmpty(strValue)) {
                return Long.valueOf(strValue);
            }
            return defaultValue;
        } catch (Exception e) {
            InternalLogger.INSTANCE.error("Failed to parse attribute '%s' of '%s', default value (%d) will be used.",
                    attributeName, elementName, defaultValue);
        }
        return defaultValue;
    }

    public static Long getLong(Element element, String elementName) {
        if (element == null) {
            return null;
        }
        try {
            Node node = element.getFirstChild();
            if (node == null) {
                return null;
            }
            String strValue = node.getTextContent();
            if (!Strings.isNullOrEmpty(strValue)) {
                return Long.valueOf(strValue);
            }
            return null;
        } catch (Exception e) {
            InternalLogger.INSTANCE.error("Failed to parse attribute '%s' of '%s'", ENABLED_ATTRIBUTE, elementName);
        }
        return null;
    }
}
