package group.uchain.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author project
 * @title: AllocationInfoUtil
 * @projectName project
 * @date 19-7-15 上午9:20
 */
public class AllocationInfoUtil {

    private static final String ANY_DISTRIBUTION = "任意分配";

    /**
     * @param allocationInfo  分配信息
     * @return  负责人分数最小占比
     */
    public static Integer getInfo(String allocationInfo){
        if (allocationInfo.contains(ANY_DISTRIBUTION)){
            //  任意比例的话 负责人的分配占比占比要大于等于0
            return 0;
        }

        //非任意分配情况
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(allocationInfo);
        return Integer.valueOf(m.replaceAll("").trim());
    }

}
