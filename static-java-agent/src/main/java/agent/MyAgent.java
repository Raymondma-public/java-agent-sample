package agent;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    private static String className = "bank.Account";
    private static String classPath = "C:\\Users\\heihe\\Downloads\\java-samples-master\\drive\\java-agent-sample\\bank-system\\build\\libs\\";

    public static void premain(
            String agentArgs, Instrumentation inst) {

        System.out.println("[Agent] In premain method");
        transformClass(className, inst);
    }

    public static void agentmain(
            String agentArgs, Instrumentation inst) {

        System.out.println("[Agent] In agentmain method");
        transformClass(className, inst);
    }

    private static void transformClass(
            String className, Instrumentation instrumentation) {
        System.out.println("transformClass: " + className);
        Class<?> targetCls = null;
        ClassLoader targetClassLoader = null;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, instrumentation);
            return;
        } catch (Exception ex) {
            System.out.println("Class [{}] not found with Class.forName");
        }
        // otherwise iterate all loaded classes and find what we want
//        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
//            System.out.println("clazz.getName(): "+clazz.getName());
//            if (clazz.getName().equals(className)) {
//                targetCls = clazz;
//                targetClassLoader = targetCls.getClassLoader();
//                transform(targetCls, targetClassLoader, instrumentation);
//                return;
//            }
//        }
        throw new RuntimeException(
                "Failed to find class [" + className + "]");
    }

    private static void transform(
            Class<?> clazz,
            ClassLoader classLoader,
            Instrumentation instrumentation) {
        SourceCodeTransformer dt = new SourceCodeTransformer(classPath, clazz.getName(), classLoader);
        instrumentation.addTransformer(dt, true);
        System.out.println("transformer added");
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Transform failed for: [" + clazz.getName() + "]", ex);
        }
    }
}
