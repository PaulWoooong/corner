canvas1 = new jsGraphics("x1");
canvas1.setColor("#00ff00");

canvas999 = new jsGraphics("b");


var nodeFrom = new Array()
var nodeTo = new Array()
var demoClicked = 0

var maxNodes = 100
var nodeCount = 0
var firstClick = 0
var littleEditUsed = ''
var elementCount = 0;
var whoSelected =''
var thisCorner = ''
var startTop = 80;
var startLeft = 80
var lastZ = 10

// build node array

for (x=0;x<maxNodes;x++) {
	nodeFrom[x]='';
	nodeTo[x]='';
}

/*
 * demo显示的时候，自动建立一个3个元素的
 */
function demo() {
	if(demoClicked) {
		return;
	}
	makeElement('Beer');
	makeElement('Ale');
	makeElement('Lager');
	addNode('Beerh1','Aleh1');
	addNode('Beerh1','Lagerh1')
	demoClicked = 1;
}


function moo(e) {
	alert(e.id)
}

function cleanUp() {
	editMe(littleEditUsed,0)
}

function clearDoubleClick() {
	firstClick = 0
}

function doubleClick(e,doThis,m) {
	now = new Date()
	if(firstClick==0) {
		firstClick=now.getTime();
		x = setTimeout("clearDoubleClick()",1000)		
	}
	else {		
		myId = $(e).id		
		eval(doThis+"('"+myId+"',"+m+")");
	}
	
}
/*
 * 删除了，用不到 
 */
function editMe(e,m) {
	if(e=='') {
		return;
	}
	myId = $(e).id
	
	if(littleEditUsed=='') {
		littleEditUsed=e;
		myText = $(e).innerHTML
		if(m==1) {
			// simple text edit
			l = myText.length+5
			myWidth = Element.getStyle(myId,'width')
			$(e).innerHTML = "<input type=text style='width:"+myWidth+"' class='editText'  name='littleEdit' onblur='editMe("+myId+","+m+")' value='"+myText+"'>"		
			document.f1.littleEdit.focus();
		}
		if(m==2) {
			// textarea
			//myWidth = Element.getStyle(myId,'width')
			//myHeight = Element.getStyle(myId,'height')
			
			myStyle = $(myId).style
			myWidth = myStyle.width
			myHeight = Element.getHeight(myId)+70
					
			//$('pos').innerHTML = e + "W:" + Element.getStyle('Title0Desc','offsetHeight') + " W2: " + myHeight
			if(myHeight<80) {
				myHeight=80
			}
			myText = myText.replace('<P>','\n')
			myText = myText.replace('<p>','\n')
			$(e).innerHTML = "<textarea style='width:"+myWidth+";height:"+myHeight+"' class='editTextArea'  name='littleEdit' onblur='editMe("+myId+","+m+")'>"+myText+"</textarea>"		
			document.f1.littleEdit.focus();
		}
	}
	else {

		myText = document.f1.littleEdit.value;
		myText = myText.replace('\n','<br>')
		myText = myText.replace('\f','<br>')
		$(littleEditUsed).innerHTML = myText
		littleEditUsed='';
	
	}
}

function stopSubmit() {
	document.f1.littleEdit.blur();
	$('pos').innerHTML = ""
	return false;
}


function selectMe(e) {
		temp = e.split('_')
		e = temp[0]
//		$('pos').innerHTML = e + ' - ' +whoSelected
		$(e+'_t').className='nodeSelected'
		Element.show(e+'_c_1')
		Element.show(e+'_c_2')
		Element.show(e+'_c_3')
		Element.show(e+'_c_4')
		if(whoSelected!='') {
			$(whoSelected+'_t').className='nodeNormal'
			Element.hide(whoSelected+'_c_1')
			Element.hide(whoSelected+'_c_2')
			Element.hide(whoSelected+'_c_3')
			Element.hide(whoSelected+'_c_4')
		}	
		whoSelected = e
}

/*
 * 增加节点连接时
 */
function addNode(source,dest) {
	dojo.debug("Adding: " + source + ' ' + dest+ ' ');
	dojo.debug("Adding: nodeCount " + nodeCount);
	nodeFrom[nodeCount] = source;
	nodeTo[nodeCount] = dest;
	nodeCount ++;
}

function showNodes() {
	for(x=0;x<99;x++) {
		n1 = nodeFrom[x]
		n2 = nodeTo[x]
		$('pos').innerHTML = $('pos').innerHTML + "<br>Node "+x+": " + n1 +" to " + n2
	}	
}

/*
 * 遍历所有已存在的元素 
 */
function renderNodes() {
	y = 0
	while(y<nodeCount) {
		makePoly(nodeFrom[y],nodeTo[y]);
		y++
	}
}

function lineSelect(e) {
	$('pos').innerHTML = "Line selected: " + e
}
	
function getPos(e1,e2) {
	canvas1 = new jsGraphics("x1");
	canvas1.setColor("#00ff00");
	x1 = parseInt(Element.getStyle(e1,'left').replace('px',''))
	y1 = parseInt(Element.getStyle(e1,'top').replace('px',''))
	x2 = parseInt(Element.getStyle(e2,'left').replace('px',''))
	y2 = parseInt(Element.getStyle(e2,'top').replace('px',''))
	canvas1.drawLine(x1,y1,x2,y2)
	canvas1.paint();
}

function drawBox(x1,y1,x2,y2) {
	$('band').style.zindex=500;	
	canvasBand = new jsGraphics("band");
	canvasBand.setColor("#00ff00");
	canvasBand.drawLine(x1,y1,x2,y1)
	canvasBand.drawLine(x1,y1,x1,y2)
	canvasBand.drawLine(x2,y2,x2,y1)
	canvasBand.drawLine(x2,y2,x1,y2)
	canvasBand.paint();
}


function selectCorner(e) {
	if(littleEditUsed!='') {
		return
	}
	temp = whoSelected.split("_");
	// get parent position and size
	myParent = temp[0]
	myNode = myParent + "_c_"+thisCorner
	try {
			canvasBand.clear('band')			
		}
	catch (e) {}
	
	px = parseInt(Element.getStyle(myParent,'left').replace('px',''));
	py = parseInt(Element.getStyle(myParent,'top').replace('px',''));
	pw = parseInt(Element.getStyle(myParent,'width').replace('px',''));
	ph = parseInt(Element.getStyle(myParent,'height').replace('px',''));
	
	newTop = py
	newLeft = px
	// determine corner position
	if(thisCorner=='1') {
		cx = parseInt(Element.getStyle(myNode,'left').replace('px',''));
		cy = parseInt(Element.getStyle(myNode,'top').replace('px',''));
		newTop = py + cy + 10
		newLeft = px + cx + 10
		px = px + pw - 10
		py = py + ph - 10
	}
	if(thisCorner=='2') {
		cx = parseInt(Element.getStyle(myNode,'left').replace('px',''));
		cy = parseInt(Element.getStyle(myNode,'top').replace('px',''));
		newTop = py + cy + 10
		newLeft = px + pw + cx - 10
		py = py + ph - 10
		px = px + 10
	}
	if(thisCorner=='3') {
		cx = parseInt(Element.getStyle(myNode,'left').replace('px',''));
		cy = parseInt(Element.getStyle(myNode,'top').replace('px',''));
		newTop = py	+ ph + cy - 10	
		newLeft = px + cx + 10
		px = px + pw - 10
		py = py + 10
	}
	if(thisCorner=='4') {
		cx = parseInt(Element.getStyle(myNode,'left').replace('px',''));
		cy = parseInt(Element.getStyle(myNode,'top').replace('px',''));
		newTop = py	+ ph + cy - 10	
		newLeft = px + pw + cx - 10
		px = px + 10
		py = py + 10
	}
	//$('pos').innerHTML = myNode+ " x: " + px + " y: " + py +" to " + newLeft + ", " + newTop
	// draw eleasic
	drawBox(px,py,newLeft,newTop)
	
}

function findCorner(e) {
	if(littleEditUsed!='') {
		return
	}
	$(e).className='cornerNormal'
	myNode = e.split("_");
	// get parent position and size
	try {
			canvasBand.clear('band')			
		}
	catch (e) {}
	sourceOffset = Position.offsetParent($(e));
	px = parseInt(Element.getStyle(sourceOffset,'left').replace('px',''));
	py = parseInt(Element.getStyle(sourceOffset,'top').replace('px',''));
	pw = parseInt(Element.getStyle(sourceOffset,'width').replace('px',''));
	ph = parseInt(Element.getStyle(sourceOffset,'height').replace('px',''));
	
	newTop = py
	newLeft = px
	// determine corner position
	if(myNode[2]=='1') {
		cx = parseInt(Element.getStyle(e,'left').replace('px',''));
		cy = parseInt(Element.getStyle(e,'top').replace('px',''));
		newWidth = pw - cx
		newHeight = ph - cy
		newTop = py + cy
		newLeft = px + cx
	}
	if(myNode[2]=='2') {
		cx = parseInt(Element.getStyle(e,'left').replace('px',''));
		cy = parseInt(Element.getStyle(e,'top').replace('px',''));
		newWidth = pw + cx
		newHeight = ph - cy
		newTop = py + cy
	}
	if(myNode[2]=='3') {
		cx = parseInt(Element.getStyle(e,'left').replace('px',''));
		cy = parseInt(Element.getStyle(e,'top').replace('px',''));
		newWidth = pw - cx
		newHeight = ph + cy
		newLeft = px + cx
	}
	if(myNode[2]=='4') {
		cx = parseInt(Element.getStyle(e,'left').replace('px',''));
		cy = parseInt(Element.getStyle(e,'top').replace('px',''));
		//$('pos').innerHTML = $('pos').innerHTML + " " +e+ " cx: " + cx + " cy: " + cy
		newWidth = pw + cx
		newHeight = ph + cy
	}
	
	// resize parent
	pStyle = $(sourceOffset).style
	pStyle.width = newWidth
	pStyle.height = newHeight
	pStyle.top = newTop
	pStyle.left = newLeft
	renderNodes();
}

/*
 * 新建一个元素
 * name 元素名称
 */
function makeElement(name) {
	if(name==0) {
		name="Title"+elementCount;
		elementCount++;
	}
	
	wrapper1 = '<table border=0 cellspacing=0 cellpadding=0 height=100% width=100% id="'+name+'ch"><tbody>'
	wrapper1 = wrapper1 + '<tr><td height=10 width=10><div id="'+name+'_c_1"><img src="images/BlackSQ.gif" height=10 width=10></div></td><td></td><td height=10 width=10><div id="'+name+'_c_2"><img src="images/BlackSQ.gif" height=10 width=10></div></td></tr>'
	wrapper1 = wrapper1 + '<tr><td></td><td>'
	wrapper2 = '</td><td></td></tr>'
	wrapper2 = wrapper2 + '<tr><td height=10 width=10><div id="'+name+'_c_3"><img src="images/BlackSQ.gif" height=10 width=10></div></td><td></td><td height=10 width=10><div id="'+name+'_c_4"><img src="images/BlackSQ.gif" height=10 width=10></div></td></tr>'
	wrapper2 = wrapper2 + '</td></tr></tbody></table>'
	
	body1 = '<table border="0" width=100% height=100% id="'+name+'_t" class="nodeNormal"><tbody>'
	body1 = body1 +'<tr><th id="'+name+'Title" valign=top height=0%>'+name+'<br>double-click to edit</th></tr><tr><td id="'+name+'Desc" valign=top height=100%>double-click to edit description</td></tr>'
	body1 = body1 +'<tr><td align="center" height=20><div id="'+name+'h1"><img height=14 width=17 src="images/GreenArrowDown.gif"></div>'
	body2 ='</td></tr></tbody></table>'

	holder = $('placeholder');
	newBox = document.createElement('div');
	holder.appendChild(newBox);
	newBox.id=name
	$(newBox).style.position='absolute';
	$(newBox).style.zindex=10;
	$(newBox).style.top=startTop;
	$(newBox).style.left=startLeft;
	$(newBox).style.width=180;
	$(newBox).style.height=200;
	$(newBox).style.zindex=lastZ;
	$(newBox).innerHTML = wrapper1 + body1 + body2 + wrapper2
	startTop = startTop + 15
	startLeft = startLeft + 10
	lastZ++
	Element.hide(name+'_c_1')
	Element.hide(name+'_c_2')
	Element.hide(name+'_c_3')
	Element.hide(name+'_c_4')
	Event.observe(name+'_t','click',function(e) {selectMe(name)},false);
	
	//删除编辑功能，会报缺少style错误
//	Event.observe(name+'Title','click',function(e) {doubleClick(name+'Title','editMe',1)},false);
//	Event.observe(name+'Desc','click',function(e) {doubleClick(name+'Desc','editMe',2)},false);


	/*
	 * 拖动的时候
	 */
	new Draggable(name, {revert:false,
		zindex:100,
		endeffect:function(e) {			
		},
		change:function(e) {
			renderNodes();
		}		
	})
	
	/*
	 * 连接的时候
	 */
	new Draggable(name+'h1', {revert:true,
		zindex:100,
		endeffect:function(e) {			
		},
		change:function(e) {
			renderNodes();
		}		
	})
	new Draggable(name+'_c_1', {revert:true,
		zindex:100,
		endeffect:function(e) {
			findCorner($(e).id);			
		},
		change:function(e) {
			thisCorner=1; 
			selectCorner();			
		}		
	})
	new Draggable(name+'_c_2', {revert:true,
		zindex:100,
		endeffect:function(e) {
			findCorner($(e).id);			
		},
		change:function(e) {
			thisCorner=2; 
			selectCorner();
		}		
	})
	new Draggable(name+'_c_3', {revert:true,
		zindex:100,
		endeffect:function(e) {
			findCorner($(e).id);			
		},
		change:function(e) {
			thisCorner=3; 
			selectCorner();
		}		
	})
	new Draggable(name+'_c_4', {revert:true,
		zindex:100,
		endeffect:function(e) {
			findCorner($(e).id);			
		},
		change:function(e) {
			thisCorner=4; 
			selectCorner();
		}		
	})
	Droppables.add(name+'h1', {
	onDrop: function(source,dest) {	
			addNode(source.id,dest.id);
			renderNodes();
		}
	})

}

/*
 * 不知道干什么 
 */
function makePoly(source,dest) {
	$('pos').innerHTML = ""
	flip2 = false
	flip3 = false
	sourceOffset = Position.offsetParent($(source));
	destOffset = Position.offsetParent($(dest));
	x1p = parseInt(Element.getStyle(sourceOffset,'left').replace('px',''));
	y1p = parseInt(Element.getStyle(sourceOffset,'top').replace('px',''))-10;
	x2p = parseInt(Element.getStyle(destOffset,'left').replace('px',''));
	y2p = parseInt(Element.getStyle(destOffset,'top').replace('px',''))+10;
	
	sourceDim = Element.getDimensions(sourceOffset)
	destDim = Element.getDimensions($(destOffset))
	// find location in node array
	for(x=0;x<maxNodes;x++) {
		if(nodeFrom[x]==source&&nodeTo[x]==dest) {
			myCanvas = x
		}
	}
	
	try {
			eval("canvas"+myCanvas+".clear('x"+myCanvas+"')")
		}
	catch (e) {}
	// make line from bottom of src to top of dest
	startx = x1p + parseInt(sourceDim.width/2)
	starty = y1p + sourceDim.height
	endx = x2p +parseInt((destDim.width/2))
	endy = y2p 
	eval("canvas"+myCanvas+"=new jsGraphics('x'+myCanvas)");
	Event.stopObserving('x'+myCanvas,'click', function(e) {lineSelect('x'+myCanvas)},true);
	Event.observe('x'+myCanvas,'click', function(e) {lineSelect('x'+myCanvas)},true);

	
	// make poly line
	
	// calculate delta from src to dest
	dx = x2p - x1p
	dy = y2p - y1p
	
	// segment 1
	
	// if bottom of desc lower than src
	if(y2p>=y1p+sourceDim.height) {
		//set seg 1 length to 1/2 of delta
		seg1y = parseInt(starty+((dy-sourceDim.height)/2))
	}
	else {
		seg1y = starty+20
		flip2 = true
	}
	eval("canvas"+myCanvas+".setColor('#0000cc')");
	eval("canvas"+myCanvas+".setStroke(2)")
	eval("canvas"+myCanvas+".drawLine(startx,starty,startx,seg1y)")

	
	// segment 2
	
	// if dest is right of src
	if(x2p>x1p+sourceDim.width+20) {		
		if(flip2) {
			seg2x = parseInt(startx +  (dx/2)) 
		}
		else {
			seg2x = startx + dx //parseInt(startx+((dx-sourceDim.width)/2))			
		}
	}
	else {
		// if src is at or right of dest
		if(x1p<x2p) {
			if(flip2) {
				seg2x = x1p - 20
				flip3 = true				
			}
			else {
				seg2x = x2p + parseInt(destDim.width/2)								
			}
		}
		else {
			if(flip2) {
				seg2x = x1p + destDim.width + 20
				
			}
			else {
				seg2x = x2p + parseInt(destDim.width/2)		
			}
		}
		
		
	}

	eval("canvas"+myCanvas+".drawLine(startx,seg1y,seg2x,seg1y)")

	
	// segment 3
	if(!flip2) {
		eval("canvas"+myCanvas+".drawLine(seg2x,seg1y,seg2x,y2p)")
		endPointx1 = seg2x + 1
		endPointx2 = seg2x - 8
		endPointx3 = seg2x + 8
		endPointy1 = y2p
		endPointy2 = y2p - 8
	}
	else {
		seg3y = y2p - 20
		if(flip3) {
			if(y1p<y2p&&y1p>y2p-sourceDim.height-20) {
				seg3y = parseInt(seg1y - sourceDim.height - 20)				
			}			
		}
		else {
			if(y1p<y2p&&y1p>y2p-sourceDim.height-20) {
				seg3y = parseInt(seg1y - sourceDim.height - 40)
			}
			if(y2p<x1p) {
				seg3y = y2p - 20
			}
		}
		eval("canvas"+myCanvas+".drawLine(seg2x,seg1y,seg2x,seg3y)")
	
	
	//segment 4
	
	seg4x = parseInt(x2p + (destDim.width/2))
	eval("canvas"+myCanvas+".drawLine(seg2x,seg3y,seg4x,seg3y)")


	
	//segment 5
	seg5y = seg3y + (y2p-seg3y) 
	eval("canvas"+myCanvas+".drawLine(seg4x,seg3y,seg4x,seg5y)")
	endPointx1 = seg4x + 1
	endPointx2 = seg4x - 8
	endPointx3 = seg4x + 8
	endPointy1 = seg5y
	endPointy2 = seg5y - 8
	}
	
	// draw endpoint
	eval("canvas"+myCanvas+".fillPolygon(new Array(endPointx1,endPointx2,endPointx3), new Array(endPointy1,endPointy2,endPointy2))")

	// paint this stuff
	eval("canvas"+myCanvas+".paint()");
}


function ascii_value (c)
{
	c = c . charAt (0);
	var i;
	for (i = 0; i < 256; ++ i)
	{
		// convert i into a 2-digit hex string
		var h = i . toString (16);
		if (h . length == 1)
			h = "0" + h;
		h = "%" + h;
		h = unescape (h);
		if (h == c)
			break;
	}
	return i;
}

// end of functions