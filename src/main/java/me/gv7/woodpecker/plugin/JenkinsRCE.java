package me.gv7.woodpecker.plugin;

import me.gv7.woodpecker.plugin.exploits.JenkinsGroovyExecuteCVE_2018_1000861;
import me.gv7.woodpecker.plugin.exploits.JenkinsGroovyMemshellCVE_2018_1000861;
import me.gv7.woodpecker.plugin.payloads.JenkinsGroovyPayloadsCVE_2018_1000861;
import me.gv7.woodpecker.plugin.pocs.JenkinsGroovyPocCVE_2018_1000861;

import java.util.ArrayList;
import java.util.List;

public class JenkinsRCE implements IVulPlugin{
    public static IVulPluginCallbacks callbacks;
    public static IPluginHelper pluginHelper;

    @Override
    public void VulPluginMain(IVulPluginCallbacks vulPluginCallbacks) {
        callbacks = vulPluginCallbacks;
        pluginHelper = vulPluginCallbacks.getPluginHelper();

        callbacks.setVulPluginName("Jenkins 反序列化");
        callbacks.setVulPluginAuthor("notyeat");
        callbacks.setVulPluginVersion("0.1.0");
        callbacks.setVulCVSS(9.0);
        callbacks.setVulName("Jenkins 反序列化");
        callbacks.setVulId("CVE-2015-8103");
        callbacks.setVulSeverity(vulPluginCallbacks.VUL_CATEGORY_RCE);
        callbacks.setVulProduct("Jenkins");
        callbacks.setVulDescription("Jenkins<1.638");

        List<IExploit> exploitList = new ArrayList<>();
        exploitList.add(new JenkinsGroovyExecuteCVE_2018_1000861());
        exploitList.add(new JenkinsGroovyMemshellCVE_2018_1000861());
        callbacks.registerExploit(exploitList);

        List<IPayloadGenerator> payloadGenerators = new ArrayList<>();
        payloadGenerators.add(new JenkinsGroovyPayloadsCVE_2018_1000861());
        callbacks.registerPayloadGenerator(payloadGenerators);

        callbacks.registerPoc(new JenkinsGroovyPocCVE_2018_1000861());
    }
}
