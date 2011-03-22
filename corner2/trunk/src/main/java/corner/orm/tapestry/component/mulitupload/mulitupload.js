
	/**
	 * 增加一个节点
	 */
	function add()
		{

			
			var displayArea = dojo.byId("files");
			
			var fileCounter = dojo.byId("filecounter");
			var currentValue = fileCounter.value;
			
			var filenamesNode = dojo.byId('filenames');//保存上传文件名称的字符串
			var namesValue = filenamesNode.value;
			var fileName = 'file'+currentValue;
			
			var innerData = "<input type='file' name='"+fileName+"' id='"+fileName
							+"'/><input type='button' name='button"+currentValue+"' id='button"
							+currentValue+"' onclick=\"remove('"+currentValue+"')\" value=\"删除\"/>";
			
			var divNode=document.createElement("div");
			divNode.id = '_div_'+currentValue;
			divNode.innerHTML = innerData;
			
			displayArea.appendChild(divNode);
			
			
			
			if(namesValue && namesValue!=''){
				var jsStr = new Array(namesValue);
				jsStr.push(fileName);
				filenamesNode.value = jsStr.toString();
			} else {
				namesValue = new Array(fileName);
				filenamesNode.value = namesValue.toString();
			}
			
			currentValue++
			fileCounter.value = currentValue;
		}
		
		/**
		 * 删除选中节点
		 */
		function remove(obj)
		{
			var displayArea = dojo.byId("files");
			var nodeId = '_div_'+obj.toString();
			var divNode = dojo.byId(nodeId);
			dojo.debug(divNode);
			displayArea.removeChild(divNode);
			
			var filenamesNode = dojo.byId('filenames');//保存上传文件名称的字符串
			var namesValue = filenamesNode.value;
			//删除hidden中被保存的这个文件名称
			if(namesValue && namesValue!=''){
				var jsStr = dojo.string.splitEscaped(namesValue,',');
				var fileName = 'file'+obj.toString();
				var tmp = new Array();
				for(var i=0;i<jsStr.length;i++){
					if(jsStr[i] != fileName){
						tmp.push(jsStr[i]);
					}
				}
				
				filenamesNode.value = tmp.toString();
			}			
			
		}
		/**
		 * 删除全部节点
		 */
		function removeAll(){
			
			var filenamesNode = dojo.byId('filenames');//清空文件名称
			var namesValue = filenamesNode.value;
			
			if(namesValue && namesValue != ''){
				var displayArea = dojo.byId("files");
				var jsStr = dojo.string.splitEscaped(namesValue,',');	
				
				for(var i=0;i<jsStr.length;i++){
					var divName = '_div_'+ jsStr[i].substring(4);
					var divNode = dojo.byId(divName);
					if(divNode && divNode != null){
						displayArea.removeChild(divNode);
					}
				}
			}
			
			
			var fileCounter = dojo.byId("filecounter");
			filenamesNode.value='';
			fileCounter.value=0;
			
		}
