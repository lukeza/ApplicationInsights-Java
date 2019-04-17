package com.microsoft.applicationinsights.agent3.internal.agent;

import com.microsoft.applicationinsights.agent3.internal.agent.utils.Global;
import org.glowroot.engine.init.EngineModule;
import org.glowroot.engine.init.MainEntryPointUtil;
import org.slf4j.Logger;

import java.io.File;
import java.lang.instrument.Instrumentation;

public class MainEntryPoint {

    private MainEntryPoint() {}

    public static void premain(Instrumentation instrumentation, File agentJarFile) {
        Logger startupLogger;
        try {
            startupLogger = MainEntryPointUtil.initLogging("com.microsoft.applicationinsights.agent3", instrumentation);
        } catch (ThreadDeath td) {
            throw td;
        } catch (Throwable t) {
            System.err.println("Agent failed to start: "+t.getMessage());
            t.printStackTrace();
            return;
        }

        try {
            System.out.println("Starting Application Insights Agent v3");
            start(instrumentation, agentJarFile);
        } catch (ThreadDeath td) {
            throw td;
        } catch (Throwable t) {
            startupLogger.error("Agent failed to start.", t);
        }
    }

    private static void start(Instrumentation instrumentation, File agentJarFile) throws Exception {
        File tmpDir = new File(agentJarFile.getParentFile(), "tmp");

        ApplicationInsightsAgentImpl agent = new ApplicationInsightsAgentImpl();


        EngineModule.createWithManyDefaults(instrumentation, tmpDir, Global.getThreadContextThreadLocal(),
                new GlowrootServiceImpl(), agent, agentJarFile);// TODO use return value?

        // TODO additional agent init?
    }
}
