/* 
 * 销售合同主表
 */
Root=function()
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
Root.prototype = new Panel;
Root.prototype.type="Root";