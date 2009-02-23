
package lichen.entities.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.annotations.Cache;

/**
 * 基础模型类。
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 46 $
 * @since 0.0.1
 */
@MappedSuperclass
@Cache(usage=org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY)
public class BaseModel {
	private int id;
	
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NonVisual
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
    /* bean properties begin */
    public static final String ID_PRO_NAME="id";
    /* bean properties end */
}
