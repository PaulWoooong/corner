//==============================================================================
// file :       $Id: NumTranslatorTest.java 4009 2008-04-11 09:07:17Z xf $
// project:     corner
//
// last change: date:       $Date: 2008-04-11 17:07:17 +0800 (星期五, 11 四月 2008) $
//              by:         $Author: xf $
//              revision:   $Revision: 4009 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.translator;

import java.util.Locale;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidatorTestCase;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 4009 $
 * @since 2.3
 */
public class NumTranslatorTest extends BaseValidatorTestCase{

	@Test
	public void test_parse_thousand() throws ValidatorException{
		NumTranslator translator=new NumTranslator();
		translator.setPattern("{6:2}");
		
		IFormComponent field=newField();
		ValidationMessages messages=this.newMessages();
		EasyMock.expect(messages.getLocale()).andReturn(Locale.getDefault());
		String text="12321.12";
		
		replay();
		Number n=(Number) translator.parseText(field, messages, text);
		assertEquals((double)12321.12,n);
		verify();
	}
	@Test
	public void test_parse_negative() throws ValidatorException{
		NumTranslator translator=new NumTranslator();
		translator.setPattern("{6:2}");
		
		IFormComponent field=newField();
		ValidationMessages messages=this.newMessages();
		EasyMock.expect(messages.getLocale()).andReturn(Locale.getDefault());
		String text="-21.12";
		
		replay();
		Number n=(Number) translator.parseText(field, messages, text);
		assertEquals((double)-21.12,n);
		verify();
	}
	@Test
	public void test_parse() throws ValidatorException{
		NumTranslator translator=new NumTranslator();
		translator.setPattern("{6:2}");
		
		IFormComponent field=newField();
		ValidationMessages messages=this.newMessages();
		EasyMock.expect(messages.getLocale()).andReturn(Locale.getDefault());
		String text="21.12";
		
		replay();
		Number n=(Number) translator.parseText(field, messages, text);
		assertEquals((double)21.12,n);
		verify();
	}
	@Test
	public void test_parse2() throws ValidatorException{
		NumTranslator translator=new NumTranslator();
		translator.setPattern("{6:4}");
		
		IFormComponent field=newField();
		ValidationMessages messages=this.newMessages();
		EasyMock.expect(messages.getLocale()).andReturn(Locale.getDefault());
		String text="21.12";
		
		replay();
		Number n=(Number) translator.parseText(field, messages, text);
		assertEquals((double)21.12,n);
		verify();
	}
	@Test
	public void test_parse3() throws ValidatorException{
		NumTranslator translator=new NumTranslator();
		translator.setPattern("{6:2}");
		
		IFormComponent field=newField();
		ValidationMessages messages=this.newMessages();
		EasyMock.expect(messages.getLocale()).andReturn(Locale.getDefault());
		String text="21.1232";
		
		replay();
		Number n=(Number) translator.parseText(field, messages, text);
		assertEquals((double)21.1232,n);
		verify();
	}
	@Test
	public void test_format() throws ValidatorException{
		NumTranslator translator=new NumTranslator(){
			/**
			 * @see corner.orm.tapestry.translator.NumTranslator#getPattern()
			 */
			@Override
			protected String getPattern() {
				return "{6:2}";
			}
		};
		
		IFormComponent field=newField();
		
		
		Locale locale=Locale.getDefault();
		Object object=new Double(21.2323);
		replay();
		String str=translator.format(field, locale, object);
		assertEquals("21.23",str);
		verify();
	}
	@Test
	public void test_format2() throws ValidatorException{
		NumTranslator translator=new NumTranslator(){
			/**
			 * @see corner.orm.tapestry.translator.NumTranslator#getPattern()
			 */
			@Override
			protected String getPattern() {
				return "{6:4}";
			}
		};
		
		IFormComponent field=newField();
		
		
		Locale locale=Locale.getDefault();
		Object object=new Double(21.23);
		replay();
		String str=translator.format(field, locale, object);
		assertEquals("21.2300",str);
		verify();
	}
	
	@Test
	public void test_format_zero() throws ValidatorException{
		NumTranslator translator=new NumTranslator(){
			/**
			 * @see corner.orm.tapestry.translator.NumTranslator#getPattern()
			 */
			@Override
			protected String getPattern() {
				return "{6:4}";
			}
		};
		
		IFormComponent field=newField();
		
		
		Locale locale=Locale.getDefault();
		Object object=new Double(0.01);
		replay();
		String str=translator.format(field, locale, object);
		assertEquals("0.0100",str);
		verify();
	}
}
