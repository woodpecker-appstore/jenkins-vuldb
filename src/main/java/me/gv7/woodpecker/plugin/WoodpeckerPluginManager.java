package me.gv7.woodpecker.plugin;

public class WoodpeckerPluginManager implements IPluginManager{

    public void registerPluginManagerCallbacks(IPluginManagerCallbacks iPluginManagerCallbacks) {
        final JenkinsDeserializationRCE jenkinsRCE = new JenkinsDeserializationRCE();
        iPluginManagerCallbacks.registerVulPlugin(jenkinsRCE);
    }
}
