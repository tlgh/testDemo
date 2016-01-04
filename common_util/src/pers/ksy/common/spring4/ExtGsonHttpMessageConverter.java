/**
 * 
 */
package pers.ksy.common.spring4;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.GsonBuilder;

/**
 * simple introduction
 *
 * <p>detailed comment
 * @author boil_000 2015年4月3日
 * @see
 * @since 1.0
 */
public class ExtGsonHttpMessageConverter extends GsonHttpMessageConverter {
    private String datePattern;
    
    
    
    /**
     * @param datePattern
     */
    public ExtGsonHttpMessageConverter(String datePattern) {
        super();
        this.datePattern = datePattern;
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(datePattern);
        setGson(builder.create());
    }

    /**
     * @see org.springframework.http.converter.json.GsonHttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // TODO Auto-generated method stub
        super.writeInternal(o, outputMessage);
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
    
    
}
