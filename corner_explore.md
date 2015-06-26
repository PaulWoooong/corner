Corner探索

0.0.1

版权 © 2006 北京美讯信息技术开发有限公司

目录

1. 系统初始化
1.1. Hibernate方面
1.2. Tapestry方面
1.3. Spring方面
1.4. 辅助类
1.5. hivemodule过滤
2. 系统运行
2.1. 制作OID
3. One to One操作
3.1. 所需文件
3.2. 简单例子
3.3. 所需文件
第 1 章 系统初始化

1.1. Hibernate方面

包名

corner.orm.hibernate.v3
ObjectRelativeUtils接口

创建ObjectRelativeUtils接口，接口定义了find/load/get/count等方法
HibernateObjectRelativeUtils抽象类

创建HibernateObjectRelativeUtils抽象类，继承HibernateDaoSupport类，实现ObjectRelativeUtils接口，
目前理解：HibernateObjectRelativeUtils类是为了可以在以后的程序中调用ObjectRelativeUtils接口进行操作才重写的。
重写？没有和hibernate的源码对照，无法确认。
HibernateDaoSupport

HibernateDaoSupport是spring提供的类，设置SessionFactory类，可以实现HibernateTemplate功能除了translator/flush 功能
1.2. Tapestry方面

包名

corner.orm.tapestry.data
DataSqueezerFilter接口

创建DataSqueezerFilter接口，中定义String squeeze、String[.md](.md) squeeze、Object unsqueeze、Object[.md](.md) unsqueeze方法
AbstractDataSqueezerFilter抽象类

创建AbstractDataSqueezerFilter抽象类，实现DataSqueezerFilter接口，加入String[.md](.md) squeeze、Object[.md](.md) unsqueeze功能，
功能中调用String squeeze、Object unsqueeze方法。
HibernateSqueezerFilter类

使用HB: + 类名 + :: ＋ DataSqueezer.squeeze序列化方法再次序列化从hibernate.session.Identifier方法得到的序列化字符串。 来确保显示的序列化编码唯一。
创建HibernateSqueezerFilter类，继承AbstractDataSqueezerFilter类，加入String squeeze、Object unsqueeze方法，
调用EntityService类的获得类名，通过DataSqueezer类中的squeeze方法加上EntityService类中Identifier方法获得类到
String的转换。unsqueeze为解码。通过setEntityService获得EntityService。
1.3. Spring方面

Xml文件

配置使用HibernateObjectRelativeUtils类作为sessionFactory，配置使用entityService时使用HibernateObjectRelativeUtils类。
SpringContainer类

创建SpringContainer类，初始化类。因为类中设置静态初始化方法，所以在框架运行时就加载各种配置文件。
1.4. 辅助类

EntityService

EntityService，所以功能的操作类，关系通过ObjectRelativeUtils接口调用操作。
1.5. hivemodule过滤

hivemodule.xml的子文件corner.squeezer.xml


hivemodule.xml文件



&lt;module id="corner.squeezer" version="1.0.0" package="corner.orm.tapestry.data"&gt;



> <!-- 过滤器调用完后执行Terminator使用DataSqueezer接口解释 -->
> <service-point id="DataSqueezerTerminator" visibility="private"
> > interface="org.apache.tapestry.services.DataSqueezer">
> > 

&lt;invoke-factory&gt;


> > > <!-- DataSqueezerImpl类实现将类转换为字符串格式或将字符串转换成类 -->
> > > 

&lt;construct class="org.apache.tapestry.util.io.DataSqueezerImpl"&gt;


> > > > <set-configuration property="squeezeAdaptors"
> > > > > configuration-id="tapestry.data.SqueezeAdaptors"/>
> > > > > <!-- squeezeAdaptors属性表示使用DataSqueezerImpl类的setSqueezeAdaptors方法,
> > > > > > SqueezeAdaptors接口支持将类转换为字符串格式或将字符串转换成类 -->

> > > 

&lt;/construct&gt;



> > 

&lt;/invoke-factory&gt;



> 

Unknown end tag for &lt;/service-point&gt;



> <!-- 程序调用的服务 -->
> 

&lt;implementation service-id="tapestry.data.DataSqueezer"&gt;


> > <!-- 使用hivemind.lib.PipelineFactory -->
> > 

&lt;invoke-factory service-id="hivemind.lib.PipelineFactory"&gt;


> > > <create-pipeline filter-interface="DataSqueezerFilter"
> > > > configuration-id="DataSqueezerFilters"
> > > > terminator="service:DataSqueezerTerminator"/>
> > > > <!-- DataSqueezerFilter过滤接口，DataSqueezerFilters过滤调用的类，DataSqueezerTerminator终结者 -->

> > 

&lt;/invoke-factory&gt;



> 

&lt;/implementation&gt;



> <!-- 定义DataSqueezerFilters使用Pipeline -->
> <configuration-point id="DataSqueezerFilters"
> > schema-id="hivemind.lib.Pipeline"> Allows you to plug in your own custom data
> > squeezers in front of the default Tapestry data squeezer. 

Unknown end tag for &lt;/configuration-point&gt;




> <!-- 定义HibernateSqueezerFilter作为过滤器 -->
> 

&lt;contribution configuration-id="DataSqueezerFilters"&gt;


> > <filter name="HibernateSqueezerFilter"
> > > object="service:HibernateSqueezerFilter"/>

> 

&lt;/contribution&gt;



> <!-- 定义HibernateSqueezerFilter为一个服务调用DataSqueezerFilter接口 -->
> 

&lt;service-point id="HibernateSqueezerFilter" interface="DataSqueezerFilter"&gt;


> > 

&lt;invoke-factory&gt;


> > > 

&lt;construct class="HibernateSqueezerFilter"&gt;


> > > > <set-object property="entityService"
> > > > > value="spring:entityService"/>

> > > 

&lt;/construct&gt;



> > 

&lt;/invoke-factory&gt;



> 

&lt;/service-point&gt;





&lt;/module&gt;


第 2 章 系统运行

2.1. 制作OID

包名

corner.orm.hibernate.v3
AbstractDateTimeIDGenerator抽象类

创建AbstractDateTimeIDGenerator抽象类，访问本类需要线程同步，也就是说方法返回的ID要求唯一，方法如果方法同时被访问，
线程休眠千分之1秒。线程醒来后再在重新获得ID。
DateTimeIDGenerator类

创建DateTimeIDGenerator类，继承AbstractDateTimeIDGenerator抽象类，实现IdentifierGenerator、Configurable接口，
IdentifierGenerator中的generate方法是生成ID的样式。因为需要在配置文件中加载一些用户定义的参数所以需要实现，
Configurable接口的configure方法。
可以参考Hibernate扩展\_自定义OID生成器

http://gocom.primeton.com/blog_37.htm
第 3 章 One to One操作

3.1. 所需文件

命名规则

单个提交的页面都是用XXForm.html表示，操作使用的列表页面使用XXList.html表示
3.2. 简单例子

AList.html文件

<span>
<blockquote>

<center>

<br>
<blockquote><a><span /></a><br>
</blockquote>

Unknown end tag for </center>

<br>
<br>
<br>
<center><br>
<br>
<br>
<blockquote>

<form jwcid="AQueryForm">

<br>
<blockquote>name:<br>
<br>
<input jwcid="nameField"/><br>
<br>
<br>
<br>
<br>
<input type="submit"/><br>
<br>
<br>
</blockquote>

</form>

<br>
</blockquote>

</center>

<br>
<blockquote><div>
<blockquote><table>
<blockquote><tr>
<blockquote><td>
<blockquote><span>color</span>
</blockquote></td>
<td>
<blockquote><span></span>
</blockquote></td>
<td>
<blockquote><span>edit</span>
</blockquote></td>
<td>
<blockquote><span>delete</span>
</blockquote></td>
</blockquote></tr>
<tr>
<blockquote><td>
<blockquote><span>color</span>
</blockquote></td>
<td>
<blockquote><span>weight</span>
</blockquote></td>
<td>
<blockquote><a href='AForm.html'>
<blockquote><span>Edit</span>
</blockquote></a>
</blockquote></td>
<td>
<blockquote><a href='#'>
<blockquote><span>Delete</span>
</blockquote></a>
</blockquote></td>
</blockquote></tr>
</blockquote></table>
<div />
</blockquote></div>
<br>
<br>
Unknown end tag for </span><br>
<br>
</blockquote></blockquote>

AList.page文件<br>
<br>
<br>
<br>
<page-specification class="corner.orm.tapestry.page.PoListPage"><br>
<br>
<br>
<blockquote>

<description>

corner.demo.model.one.A Form Page<br>
<br>
</description><br>
<br>
<br>
<br>
<br>
<bean class="org.apache.tapestry.valid.ValidationDelegate" name="delegate"/><br>
<br>
<br>
<!-- entity模板，使用自动生成get/setEntity方法 --><br>
<br>
<br>
<property initial-value="new corner.demo.model.xf.one.apple()" name="entity"/><br>
<br>
<br>
<!-- 查询时使用的queryEntity --><br>
<br>
<br>
<property persist="client" initial-value="new corner.demo.model.xf.one.apple()" name="queryEntity"/><br>
<br>
</blockquote>

<blockquote>

<component type="Form" id="AQueryForm">

<br>
<blockquote>

<binding value="listener:doQueryEntityAction" name="listener"/>

<br>
<br>
<br>
<binding value="ognl:beans.delegate" name="delegate"/><br>
<br>
<br>
</blockquote>

</component>

<br>
<br>
<br>
<component type="TextField" id="nameField"><br>
<br>
<br>
<blockquote>

<binding value="message:Color" name="displayName"/>

<br>
<br>
<br>
<binding value="translator:string" name="translator"/><br>
<br>
<br>
<br>
<br>
<binding value="ognl:queryEntity.color" name="value"/><br>
<br>
<br>
</blockquote>

</component>

<br>
<br>
<br>
</page-specification><br>
<br>
<br>
AForm.html文件</blockquote>

<span title='xfAForm'>
<blockquote><a href='AList.html'>Return A List</a>
<br>
<br>
<form jwcid="AForm" action="AList.html"><br>
<br>
<br>
<blockquote><table>
<blockquote><tr>
<blockquote><td>
<blockquote><span>color</span>
</blockquote></td>
<td>
<blockquote>

<input jwcid="colorField" />

<br>
<br>
<input jwcid="weightField"/><br>
<br>
<br>
</blockquote></td>
</blockquote></blockquote></blockquote><blockquote></tr></blockquote></blockquote>

<blockquote></table>
<br>
<br>
<input jwcid="@Submit" type="Submit" value="message:Okay"/><br>
<br>
<br>
<br>
<br>
<input jwcid="@Any" type="Button" value="message:button.Cancle" onClick="javascript:tapestry.form.cancel(this.form);"/><br>
<br>
<br>
</blockquote><blockquote>

</form>

<br>
</span>
AForm.page文件</blockquote>

如果有特殊操作可以继承AbstractEntityFormPage类，重写类中方法。<br>
<br>
<br>
<page-specification class="corner.orm.tapestry.page.AbstractEntityFormPage"><br>
<br>
<br>
<blockquote>

<description>

corner.demo.model.one.A Form Page<br>
<br>
</description><br>
<br>
<br>
<br>
<br>
<bean class="org.apache.tapestry.valid.ValidationDelegate" name="delegate"/><br>
<br>
<br>
<!-- 创建entity模型，以便在页面中传递类 --><br>
<br>
<br>
<property persist="entity" initial-value="new corner.demo.model.xf.one.apple()" name="entity"/><br>
<br>
<br>
<br>
<br>
<component type="Form" id="AForm"><br>
<br>
<br>
<blockquote>

<binding value="ognl:beans.delegate" name="delegate"/>

<br>
<br>
<br>
<binding value="ognl:true" name="clientValidationEnabled"/><br>
<br>
<br>
<!-- doSaveEntityAction是corner提供的保存方法 --><br>
<br>
<br>
<binding value="listener:doSaveEntityAction" name="success"/><br>
<br>
<br>
<!-- doCancelEntityAction是corner提供的取消方法 --><br>
<br>
<br>
<binding value="listener:doCancelEntityAction" name="cancel"/><br>
<br>
<br>
</blockquote>

</component>

<br>
<br>
<br>
<component type="TextField" id="colorField"><br>
<br>
<br>
<blockquote>

<binding value="message:Color" name="displayName"/>

<br>
<br>
<br>
<binding value="translator:string" name="translator"/><br>
<br>
<br>
<br>
<br>
<binding value="ognl:entity.color" name="value"/><br>
<br>
<br>
<br>
<br>
<binding name="validators" value="validators:required"/><br>
<br>
<br>
</blockquote>

</component>

<br>
<blockquote>

<component type="TextField" id="weightField">

<br>
<br>
<br>
<binding value="message:Weight" name="displayName"/><br>
<br>
<br>
<br>
<br>
<binding value="translator:string" name="translator"/><br>
<br>
<br>
<br>
<br>
<binding value="ognl:entity.weight" name="value"/><br>
<br>
<br>
</blockquote>

</component>

<br>
<br>
<br>
</page-specification><br>
<br>
<br>
AbstractModel.java文件</blockquote>

包：corner.demo.model.xf.one；spring配置文件中将corner.demo.model包及其子包设置成为注释配置hibernate表结构的目标包<br>
@MappedSuperclass<br>
public class AbstractModel extends AbstractPersistDomain implements Serializable {<br>
<br>
<blockquote>private static final long serialVersionUID = 772729287261953568L;</blockquote>

<blockquote>/<br>
<ul><li>@hibernate.id generator-class="uuid"<br>
</li><li>
</li></ul>private String id;</blockquote>

<blockquote>/<br>
<ul><li>@hibernate.property<br>
</li><li>
</li></ul>private String name;</blockquote>

<blockquote>/<br>
<ul><li>@return 获得id<br>
</li><li>
</li></ul>@Id @GeneratedValue(generator="system-uuid")<br>
@GenericGenerator(name="system-uuid", strategy = "uuid")<br>
@Column(columnDefinition="char(32)")<br>
public String getId() {<br>
<blockquote>return id;<br>
</blockquote>}</blockquote>

<blockquote>/<br>
<ul><li>@param id 设置id<br>
</li><li>
</li></ul>public void setId(String id) {<br>
<blockquote>this.id = id;<br>
</blockquote>}</blockquote>

<blockquote>public String getName() {<br>
<blockquote>return name;<br>
</blockquote>}</blockquote>

<blockquote>public void setName(String name) {<br>
<blockquote>this.name = name;<br>
</blockquote>}<br>
}<br>
继承AbstractPersistDomain抽象类，AbstractPersistDomain类是实例操作的基础类，实现了对单一记录操作的save、update、saveOrUpdate、delete方法，和从spring获得Entity的getEntityService方法<br>
apple.java文件</blockquote>

继承AbstractModel类，@Entity(name = "apple")表示使用apple作为表名，继承的属性和方法不能重复<br>
@Entity(name = "apple")<br>
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)<br>
public class apple extends AbstractModel {<br>
<br>
<blockquote>private static final long serialVersionUID = 8533936694005181003L;</blockquote>

<blockquote>private String color;</blockquote>

<blockquote>private double weight;</blockquote>

<blockquote>//geter和seter略<br>
}<br>
3.3. 所需文件</blockquote>

命名规则<br>
<br>
单个提交的页面都是用XXForm.html表示，操作使用的列表页面使用XXList.html表示