package agent;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Scanner;

public class AgentLoader {
    public static void main(String[] args) throws Exception {
            new AgentLoader().run(args);
    }

    public static void run(String[] args) throws URISyntaxException {
        File file = new File("");
        String filePath = AgentLoader.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
//        String filePath = file.getAbsolutePath() + "\\" +file.getName();

        Scanner scanner = new Scanner(System.in);
        System.out.println("agentFilePath:");
        String agentFilePath = filePath; //scanner.nextLine(); // "/home/adi/Desktop/agent-1.0.0-jar-with-dependencies.jar";
        System.out.println("agentFilePath:"+filePath);
        System.out.println("app name:");
        String applicationName = scanner.nextLine(); // "bank-system";
        System.out.println("app name:"+applicationName);
        scanner.close();

        //iterate all jvms and get the first one that matches our application name
        Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                .stream()
                .filter(jvm -> {
                    System.out.println("jvm:"+ jvm.displayName());
                    return jvm.displayName().contains(applicationName);
                })
                .findFirst().get().id());

        if(!jvmProcessOpt.isPresent()) {
            System.out.println("Target Application not found");
            return;
        }
        File agentFile = new File(agentFilePath);
        try {
            String jvmPid = jvmProcessOpt.get();
            System.out.println("Attaching to target JVM with PID: " + jvmPid);
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            jvm.loadAgent(agentFile.getAbsolutePath());
            jvm.detach();
            System.out.println("Attached to target JVM and loaded Java agent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}