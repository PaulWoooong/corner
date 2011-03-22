package corner.orm.tapestry.translator;

import java.text.SimpleDateFormat;

import org.apache.tapestry.form.translator.DateTranslator;

/**定义英美习惯时间显示MM-dd-yyyy
 * @author gy
 * @version $Revision$
 * @since 2.5.2
 */
public class EnglishDateTranslator extends DateTranslator{
	/**
     * corner中日期类型使用的pattern
     */
    private static final String CORNER_DATE_PATTERN = "MM/dd/yyyy";
    
    /**
     * 用户回显时使用的Format
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(CORNER_DATE_PATTERN);


    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#defaultPattern()
     */
    @Override
    protected String defaultPattern() {
        return CORNER_DATE_PATTERN;
    }
}
