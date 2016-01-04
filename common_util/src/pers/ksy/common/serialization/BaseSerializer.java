/**
 * 
 */
package pers.ksy.common.serialization;

/**
 * simple introduction
 *
 * <p>
 * detailed comment
 * 
 * @author 孔思宇 2015年4月3日
 * @see
 * @since 1.1
 */
public abstract class BaseSerializer implements Serializer {
    /**
     * 序列化时间格式
     */
    protected String datePattern;

    /**
     * @param datePattern the dateFormat to set
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

}
