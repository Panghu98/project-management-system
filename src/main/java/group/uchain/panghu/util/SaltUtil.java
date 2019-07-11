package group.uchain.panghu.util;

import java.util.UUID;

/**
 * @author panghu
 * @Title: SaltUtil
 * @ProjectName oil-supply-chain
 * @date 19-3-29 上午8:18
 */
public class SaltUtil {


    /**
     * 生成长UUID
     */
    private static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getSalt(){
        return randomUUID().substring(12,20);
    }


}