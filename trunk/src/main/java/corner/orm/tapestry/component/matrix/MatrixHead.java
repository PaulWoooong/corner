//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.components.ForBean;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * 显示矩阵头的部分.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author Ghost
 * @version $Revision$
 * @since 2.2.2
 */
public abstract class MatrixHead extends BaseComponent {
	@Component(type="For",bindings={"source=refVector","value=headObj"})
	public abstract ForBean getHeadIter();
	
	
	public abstract void setHeadObj(Object headObj);
	public abstract Object getHeadObj();
	
	
	@Parameter(required=true)
	public abstract MatrixRow getRefVector();
	
	@Parameter(defaultValue="-1")
	public abstract int getRefSize();
	
	/**
	 * 是否继续循环.
	 * @return
	 */
	public  boolean isLoop(){
		if(this.getRefSize()==-1){
			return true;
		}
		return this.getRefSize()>this.getHeadIter().getIndex();
	}
	/**
	 * 是否需要补齐剩下的空格
	 * @return 
	 */
	public boolean isNeedFill(){
		return this.getRefSize()>(this.getRefVector()!=null?this.getRefVector().size():0);
	}
	
	public int [] getFillSource(){
		return new int[this.getRefSize()-this.getRefVector().size()];
	}
}
