package cn.x.codegen.utils;

/**
 * @author xslong
 * @time 2017/11/6 16:01
 */
public class NameUtils {

    /**
     * 大驼峰式命名
     *
     * @param str
     * @return
     */
    public static String upperCamelCase(String str) {
        StringBuffer name = new StringBuffer();
        String[] names = str.split("_");
        for (int i = 0, len = names.length; i < len; i++) {
            name.append(names[i].substring(0, 1).toUpperCase() + names[i].substring(1));
        }
        return name.toString();
    }

    /**
     * 小驼峰式命名
     *
     * @param str
     * @return
     */
    public static String lowerCamelCase(String str) {
        String s = upperCamelCase(str);
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * 包名
     *
     * @param str
     * @return
     */

    public static String packageName(String str) {
        int lastDot = str.lastIndexOf(".");
        return str.substring(0, lastDot);
    }

    /**
     * 类名
     *
     * @param str
     * @return
     */
    public static String simpleClassName(String str) {
        int lastDot = str.lastIndexOf(".");
        int li = str.lastIndexOf("<");
        if (li == -1) {
            return str.substring(lastDot + 1);
        } else {
            return str.substring(lastDot + 1, li);
        }
    }

    /**
     * 泛型
     * @param str
     * @return
     */
    public static String[] generic(String str) {
        int li = str.lastIndexOf("<");
        int gi = str.lastIndexOf(">");
        if (li == -1) {
            return null;
        }
        String s = str.substring(li+1, gi);
        return s.replaceAll(" ", "").split(",");
    }
}
