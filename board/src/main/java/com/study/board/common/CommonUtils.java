package com.study.board.common;

import java.util.List;
import java.util.Map;

public class CommonUtils {

    /*

     * 공백 또는 null 체크

     */
    public static boolean isEmpty(Object obj) {
        if(obj == null) return true;
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) { return true; }
        if (obj instanceof Map) { return ((Map<?, ?>) obj).isEmpty(); }
        if (obj instanceof Map) { return ((Map<?, ?>)obj).isEmpty(); }
        if (obj instanceof List) { return ((List<?>)obj).isEmpty(); }
        if (obj instanceof Object[]) { return (((Object[])obj).length == 0); }

        return false;

    }
}
