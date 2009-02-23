package lichen.pages.base;

import lichen.services.EntityService;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.slf4j.Logger;

/**
 * 抽象的对实体进行操作表单类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public abstract class AbstractEntityForm<T> {

	/** 前端的用来提交的表单页面 **/
	@SuppressWarnings("unused")
	@Component 
	private Form entityForm;

	/** logger **/
	@Inject 
	private Logger logger;

	/** Entity Service Object **/
	@Inject 
	private EntityService entityService;
	@Inject
	private PropertyConduitSource source;


	
	@Inject 
	private LinkFactory linkFactory;
	/** 用来操作的model对象 **/
	private T entity;

	
	/**
	 * 保存当前操作的实体
	 */
	@OnEvent(value = "success", component = "entityForm")
	Object doSaveOrUpdateEntity() {
		logger.debug("save or update entity ");
		this.saveOrUpdate();
		return linkFactory.createPageLink(getReturnPage(), false);
	}

	/**
	 * 当保存后，跳转的页面.
	 * @return 跳转的页面
	 */
	protected abstract String getReturnPage();

	@OnEvent(value = "validateForm", component = "entityForm")
	boolean doValidateNewEntityForm() {
		validateEntityForm(this.entityForm,this.entity);
		return entityForm.isValid();
	}

	/**
	 * 对提交的表单进行手工的校验
	 * @param entityForm
	 * @param entity
	 */
	protected void validateEntityForm(Form entityForm, T entity) {
		
		
	}

	/**保存对象**/
	protected void saveOrUpdate() {
		entityService.saveOrUpdate(this.entity);
	}
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return source.create(this.getClass(),"entity").getPropertyType();
	}
	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

}