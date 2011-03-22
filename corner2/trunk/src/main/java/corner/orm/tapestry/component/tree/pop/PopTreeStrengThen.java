//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.tree.pop;

import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;

import corner.model.tree.ITreeAdaptor;
import corner.orm.tapestry.component.tree.ITreeSelectModel;

/**
 * 增加过滤叶节点功能
 * 
 * @author <a href=mailto:wlh@bjmaxinfo.com>wlh</a>
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class PopTreeStrengThen extends PopTree {
	
	@Parameter(defaultValue = "ognl:true")
	public abstract boolean getDisplayLeaf();
	/**
	 * @see corner.orm.tapestry.component.tree.pop.PopTree#leftTreeNodeJson(corner.orm.tapestry.component.tree.ITreeSelectModel)
	 */
	protected JSONObject leftTreeNodeJson(ITreeSelectModel model) {
		JSONObject jsonObject = null;
		
		if(this.getDisplayLeaf()){
			jsonObject = super.leftTreeNodeJson(model);
		}else{
			JSONArray jsonArr = new JSONArray();
			
			JSONObject subjson = null;
			
			JSONObject datejson = null;
			
			for(ITreeAdaptor node : model.getTreeList()){
				//判断如果是叶节点则跳过
				if((node.getRight() - node.getLeft()) <= 1){
					continue;
				}
				subjson = new JSONObject();
				subjson.put("id", node.getId());
				subjson.put("type", "leftTreeSite");
				
				datejson = new JSONObject();
				datejson.put("name", node.getNodeName());
				datejson.put("left", node.getLeft());
				datejson.put("right", node.getRight());
				datejson.put("depth", node.getDepth());
				datejson.put("thisEntity", this.getDataSqueezer().squeeze(node));
				
				if(model.getReturnValues() != null){	//如果不等于空则反抓出数据
					returnPropertys(datejson,node,model.getReturnValues());
				}
				
				subjson.put("data", datejson);
				
				jsonArr.put(subjson);
			}
			
			jsonObject = new JSONObject();
			jsonObject.put("nodes", jsonArr);
		}
		
		return jsonObject;
	}
}
