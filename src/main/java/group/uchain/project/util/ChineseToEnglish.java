//package group.uchain.project.util;
//
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//
///**
// * @author panghu
// */
//public class ChineseToEnglish {
//    /**
//     *  将汉字转换为全拼
//     * @param src 字符串
//     * @return 英文字母
//     */
//    public static String getPingYin(String src) {
//
//        char[] t1;
//        t1 = src.toCharArray();
//        String[] t2;
//        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
//
//        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
//        StringBuilder t4 = new StringBuilder();
//        try {
//            for (char c : t1) {
//                // 判断是否为汉字字符
//                if (Character.toString(c).matches(
//                        "[\\u4E00-\\u9FA5]+")) {
//                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
//                    t4.append(t2[0]);
//                } else {
//                    t4.append(c);
//                }
//            }
//            return t4.toString();
//        } catch (BadHanyuPinyinOutputFormatCombination e1) {
//            e1.printStackTrace();
//        }
//        return t4.toString();
//    }
//
//    /**
//     * 返回中文的首字母
//     * @param str　字符串
//     * @return 每个汉字的头一个字母
//     */
//    public static String getPinYinHeadChar(String str) {
//
//        StringBuilder convert = new StringBuilder();
//        for (int j = 0; j < str.length(); j++) {
//            char word = str.charAt(j);
//            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//            if (pinyinArray != null) {
//                convert.append(pinyinArray[0].charAt(0));
//            } else {
//                convert.append(word);
//            }
//        }
//        return convert.toString();
//    }
//
//    /**
//     * 将字符串转移为ASCII码
//     * @param cnStr 中文字符串
//     * @return  ASCII码
//     */
//    public static String getCnASCII(String cnStr) {
//        StringBuilder strBuf = new StringBuilder();
//        byte[] bgbk = cnStr.getBytes();
//        for (byte b : bgbk) {
//            strBuf.append(Integer.toHexString(b & 0xff));
//        }
//        return strBuf.toString();
//    }
//
//    public static void main(String[] args) {
//        System.out.println(getPingYin("綦江qq县"));
//        System.out.println(getPinYinHeadChar("綦江县"));
//        System.out.println(getCnASCII("綦江县"));
//    }
//}
