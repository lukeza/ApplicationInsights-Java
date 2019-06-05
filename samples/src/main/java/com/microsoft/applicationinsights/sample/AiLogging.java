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

package com.microsoft.applicationinsights.sample;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SuppressWarnings("ALL")
public class AiLogging {
    public static void main(String[] args) throws IOException {
        TelemetryClient appInsights = new TelemetryClient();
/*
        if (args.length > 0) {
            appInsights.getContext().setInstrumentationKey(args[0]);
        }
        String iKey = appInsights.getContext().getInstrumentationKey();
        if (iKey == null)
        {
            System.out.println("Error: no iKey set in ApplicationInsights.xml or as a parameter for this program.");
            return;
        }
        System.out.println("Application iKey set to " + appInsights.getContext().getInstrumentationKey());
        //TelemetryConfiguration.getActive().getChannel().setDeveloperMode(true);
        appInsights.trackEvent("Test 1");

        appInsights.trackTrace("Trace message1", SeverityLevel.Warning, null);
*/
        // Logback
        Logger log = LoggerFactory.getLogger(AiLogging.class);
        System.out.println("\n--- Logging using Logback ---");
        log.trace("New Logback event3!");
        System.out.println("    Trace level log sent via logback logger3.");
        log.info("info: New Logback event4!");
        System.out.println("    Trace level log sent via logback logger4.");
        appInsights.flush();
    }
}
