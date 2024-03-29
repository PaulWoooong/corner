/* 
 * 基本显示框
 */
Panel=function()
{
  this.cornerWidth  = 15;
  this.cornerHeight = 15;

  Node.call(this);
  this.setDimension(200,150);
  this.originalHeight =-1;
}

/** base class of my example double click figure 
 * You can use circle, oval,.....too
 **/
Panel.prototype = new Node;
Panel.prototype.type="Panel";


/**
 * Create the HTML for the round corner object.
 * Each corner of the figure is a DIV with a corresponding background image.
 * 
 * @private
 **/
Panel.prototype.createHTMLElement=function()
{
    var item = document.createElement('div');
    item.id        = this.id;
    item.style.position="absolute";
    item.style.left   = this.x+"px";
    item.style.top    = this.y+"px";
    item.style.height = this.width+"px";
    item.style.width  = this.height+"px";
    item.style.margin = "0px";
    item.style.padding= "0px";
    item.style.outline= "none";
    item.style.zIndex = ""+Figure.ZOrderBaseIndex;

    this.top_left = document.createElement('div');
    this.top_left.style.background= "url(circle.png) no-repeat top left";
    this.top_left.style.position="absolute";
    this.top_left.style.width= this.cornerWidth+"px";
    this.top_left.style.height=this.cornerHeight+"px";
    this.top_left.style.left="0px";
    this.top_left.style.top="0px";
    this.top_left.style.fontSize="2px";

    this.top_right = document.createElement('div');
    this.top_right.style.background= "url(circle.png) no-repeat top right";
    this.top_right.style.position="absolute";
    this.top_right.style.width= this.cornerWidth+"px";
    this.top_right.style.height=this.cornerHeight+"px";
    this.top_right.style.left="0px";
    this.top_right.style.top="0px";
    this.top_right.style.fontSize="2px";

    this.bottom_left = document.createElement('div');
    this.bottom_left.style.background= "url(circle.png) no-repeat bottom left";
    this.bottom_left.style.position="absolute";
    this.bottom_left.style.width= this.cornerWidth+"px";
    this.bottom_left.style.height=this.cornerHeight+"px";
    this.bottom_left.style.left="0px";
    this.bottom_left.style.top="0px";
    this.bottom_left.style.fontSize="2px";

    this.bottom_right = document.createElement('div');
    this.bottom_right.style.background= "url(circle.png) no-repeat bottom right";
    this.bottom_right.style.position="absolute";
    this.bottom_right.style.width= this.cornerWidth+"px";
    this.bottom_right.style.height=this.cornerHeight+"px";
    this.bottom_right.style.left="0px";
    this.bottom_right.style.top="0px";
    this.bottom_right.style.fontSize="2px";

    this.header = document.createElement('div');
    this.header.style.position="absolute";
    this.header.style.left=this.cornerWidth+"px";
    this.header.style.top="0px";
    this.header.style.height=(this.cornerHeight)+"px";
    this.header.style.backgroundColor="#CCCCFF";
    this.header.style.borderTop="3px solid #666666";
    this.header.style.fontSize="9px";
    this.header.style.textAlign="center";

    this.footer = document.createElement('div');
    this.footer.style.position="absolute";
    this.footer.style.left=this.cornerWidth+"px";
    this.footer.style.top="0px";
    this.footer.style.height=(this.cornerHeight-1)+"px";
    this.footer.style.backgroundColor="white";
    this.footer.style.borderBottom="1px solid #666666";
    this.footer.style.fontSize="2px";

    this.textarea = document.createElement('div');
    this.textarea.style.position="absolute";
    this.textarea.style.left="0px";
    this.textarea.style.top=this.cornerHeight+"px";
    this.textarea.style.backgroundColor="white";
    this.textarea.style.borderTop="2px solid #666666";
    this.textarea.style.borderLeft="1px solid #666666";
    this.textarea.style.borderRight="1px solid #666666";
    this.textarea.style.overflow="auto";
    this.textarea.style.fontSize="9pt";
    this.disableTextSelection(this.textarea);

    item.appendChild(this.top_left);
    item.appendChild(this.header);
    item.appendChild(this.top_right);
    item.appendChild(this.textarea);
    item.appendChild(this.bottom_left);
    item.appendChild(this.footer);
    item.appendChild(this.bottom_right);

    return item;
}



/**
 * Adjuste the corner DIV elements to the new dimension of the figure.
 * Additional the ports must be adjust to the new height/width of the figure.
 *
 **/
Panel.prototype.setDimension=function(/*:int*/ w, /*:int*/ h )
{
  Node.prototype.setDimension.call(this,w, h);

  // Adjust the different layer/div/img object of the figure
  //
  if(this.top_left!=null)
  {
    this.top_right.style.left  = (this.width-this.cornerWidth)+"px";
    this.bottom_right.style.left  = (this.width-this.cornerWidth)+"px";
    this.bottom_right.style.top  = (this.height-this.cornerHeight)+"px";
    this.bottom_left.style.top  = (this.height-this.cornerHeight)+"px";

    this.textarea.style.width  = (this.width-2)+"px";
    this.textarea.style.height  = (this.height-this.cornerHeight*2)+"px";

    this.header.style.width  = (this.width-this.cornerWidth*2)+"px";

    this.footer.style.width  = (this.width-this.cornerWidth*2)+"px";
    this.footer.style.top  = (this.height-this.cornerHeight)+"px";
  }


  // Adjust the Output ports to the new dimension
  //
  if(this.outputPort!=null)
    this.outputPort.setPosition(this.width+5, this.height/2);

  // Adjust the Input ports to the new dimension
  //
  if(this.inputPort!=null)
    this.inputPort.setPosition(-5, this.height/2);
}

/**
 * Utility function to set the title of the figure.
 *
 **/
Panel.prototype.setTitle=function(/*:String*/ title)
{
   this.header.innerHTML=title;
}

/**
 * 获得标题
 *
 **/
Panel.prototype.getTitle=function()
{
   return this.header.innerHTML;
}


/**
 * Utility function to set the content of the figure.
 *
 **/
Panel.prototype.setContent=function(/*:String*/ content)
{
   this.textarea.innerHTML=content;
}
/**
 * 返回内容
 **/
Panel.prototype.getContent=function()
{
   return this.textarea.innerHTML;
}



/**
 * The round corner figure can only be dragged with the title bar.
 * REASON: Unable to use the scrollbar of the textarea DIV.
 *
 * @param {int} x x position of the mouse in the window
 * @param {int} y y position of the mouse in the window
 * @returns Returns [true] if the window can be draged. False in the other case
 * @type boolean
 **/
Panel.prototype.onDragstart = function(/*:int*/ x, /*:int*/ y)
{
  var originalResult = Node.prototype.onDragstart.call(this,x,y);
  // no titlebar => no drag drop
  // Reson: The titlebar is the DragDrop handle.
  //
  if(this.header==null)
    return false;

  // expand/collapse
  if(y<this.cornerHeight && x<this.width && x>(this.width-this.cornerWidth))
  {
    this.toggle();
    return false;
  }

  // DragDrop check if the figure is expanded.
  // Return only true if the user klicks into the header.
  //
  if(this.originalHeight==-1)
  {
   if(this.canDrag==true && x<parseInt(this.header.style.width) && y<parseInt(this.header.style.height))
      return true;
  }
  // DragDrop check if the figure is collapsed.
  // Return only true if the user klicks into the header.
  //
  else
  {
     return originalResult;
  }
}


/**
 * Only the header has the Drag&Drop cursor.
 *
 * @param {boolean} flag The flag which handles the drag drop behaviour of this window.
 *
 **/
Panel.prototype.setCanDrag=function(/*:boolean*/flag)
{
  Node.prototype.setCanDrag.call(this,flag);
  this.html.style.cursor="";
  if(this.header==null)
    return;

  if(flag)
    this.header.style.cursor="move";
  else
    this.header.style.cursor="";
}

/**
 * Create the Input&Output ports of the figure if the element will be assigned the first time
 * to the workflow/canvas.
 *
 **/
Panel.prototype.setWorkflow=function(/*:Workflow*/ workflow)
{
  Node.prototype.setWorkflow.call(this,workflow);

  if(workflow!=null && this.inputPort==null)
  {
    this.inputPort = new InputPort();
    this.inputPort.setWorkflow(workflow);
    this.inputPort.setName("input");
    this.inputPort.setBackgroundColor(new  Color(115,115,245));
    this.addPort(this.inputPort,this.width,this.height/4*3);
    
    this.outputPort = new OutputPort();
    this.outputPort.setMaxFanOut(5); // It is possible to add "5" Connector to this port
    this.outputPort.setWorkflow(workflow);
    this.outputPort.setName("output");
    this.outputPort.setBackgroundColor(new  Color(245,115,115));
    this.addPort(this.outputPort,this.width,this.height/4);
  }
}


/**
 * Toggle the expand/collapse state of the figure.
 *
 **/
Panel.prototype.toggle=function()
{
  // collapse
  if(this.originalHeight==-1)
  {
     this.originalHeight=this.height;
     this.setDimension(this.width, this.cornerHeight*2);
     this.setResizeable(false);
  }
  // expand
  else
  {
     this.setDimension(this.width, this.originalHeight);
     this.originalHeight=-1;
     this.setResizeable(true);
  }
}