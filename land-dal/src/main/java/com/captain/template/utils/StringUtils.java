package com.captain.template.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wurz on 2018/6/8.
 */
public class StringUtils {
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰 小驼峰为jackCaptain 大驼峰为JackCaptain
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern firstWordPattern= Pattern.compile("([a-z\\d]+)?");
        Matcher firstWordMatcher = firstWordPattern.matcher(line);
        if (firstWordMatcher.find()){
            String firstWord = firstWordMatcher.group();
            sb.append(firstWord).append(firstWordMatcher.end()==line.length()?"":"_");
        }
        Pattern pattern= Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }

    /**
     * Description: Replaces all {n} placeholder use params
     *
     * @param originalStr a string such as :
     *            "select * from table where id={0}, name={1}, gender={3}"
     * @param replacementParams
     *            real params: 1,yinshi.nc,male
     * @note n start with 0
     */
    public static String replaceSequenced( String originalStr, Object... replacementParams ) {

        if ( org.springframework.util.StringUtils.isEmpty(originalStr) ) {
            return "";
        }
        if ( null == replacementParams || 0 == replacementParams.length ) {
            return originalStr;
        }

        for ( int i = 0; i < replacementParams.length; i++ ) {
            String elementOfParams = replacementParams[i] + "";
            if (  "null".equalsIgnoreCase( elementOfParams) ) {
                elementOfParams = "";
            }
            originalStr = originalStr.replace( "{" + i + "}", elementOfParams  );
        }

        return originalStr;
    }
}
