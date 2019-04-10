package com.microsoft.applicationinsights.agent.internal.agent;

import com.microsoft.applicationinsights.agent.internal.agent.utils.Global;
import org.glowroot.engine.init.EngineModule;
import org.glowroot.engine.init.MainEntryPointUtil;
import org.slf4j.Logger;

import java.io.File;
import java.lang.instrument.Instrumentation;

public class MainEntryPoint {

    private MainEntryPoint() {}

    public static void premain(Instrumentation instrumentation, File agentJarFile) {
        System.out.println("MainEntryPoint.premain");
        Logger startupLogger;
        try {
            startupLogger = MainEntryPointUtil.initLogging("com.microsoft.agent3", instrumentation);
        } catch (ThreadDeath td) {
            throw td;
        } catch (Throwable t) {
            System.err.println("Agent failed to start: "+t.getMessage());
            t.printStackTrace();
            return;
        }

        try {
            start(instrumentation, agentJarFile);
        } catch (ThreadDeath td) {
            throw td;
        } catch (Throwable t) {
            startupLogger.error("Agent failed to start.", t);
        }
    }

    private static void start(Instrumentation instrumentation, File agentJarFile) throws Exception {
        System.out.println("start");
        File tmpDir = new File(agentJarFile.getParentFile(), "tmp");

        System.out.println("Create agentImpl");
        ApplicationInsightsAgentImpl agent = new ApplicationInsightsAgentImpl();


        System.out.println("create EngineModule");
        EngineModule.createWithManyDefaults(instrumentation, tmpDir, Global.getThreadContextThreadLocal(),
                new GlowrootServiceImpl(), agent, agentJarFile);

        // TODO additional agent init?
    }
}
