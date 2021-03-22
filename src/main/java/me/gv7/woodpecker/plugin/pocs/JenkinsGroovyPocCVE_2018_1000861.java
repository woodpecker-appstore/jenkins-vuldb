package me.gv7.woodpecker.plugin.pocs;

import me.gv7.woodpecker.plugin.*;

public class JenkinsGroovyPocCVE_2018_1000861 implements IPoc {

    @Override
    public IScanResult doVerify(ITarget target, IResultOutput resultOutput) throws Throwable {
        String address = target.getAddress();
        IScanResult result = JenkinsRCE.pluginHelper.createScanResult();

        if(JenkinsCommonsUtils.CVE_2018_1000861Check(address)){
            result.setExists(true);
            resultOutput.successPrintln("地址: "+ address +" 存在CVE_2018_100861");
        }
        return result;
    }
}
