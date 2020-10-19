package me.gv7.woodpecker.plugin;

public class WoodpeckerPluginManager implements IPluginManager{

    public void registerPluginManagerCallbacks(IPluginManagerCallbacks iPluginManagerCallbacks) {
        final JenkinsRCE jenkinsRCE = new JenkinsRCE();
        iPluginManagerCallbacks.registerVulPlugin(jenkinsRCE);
    }
}
