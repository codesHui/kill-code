
<#macro doctype> doctype-->
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js lt-ie9 "> <!--<![endif]-->
<!--doctype 结束</#macro>

<#-- css/js -->
<#macro cssJs>css/js-->
 <meta http-equiv="x-ua-compatible" content="ie=edge">
<link type="text/css" href="<@s.url '/resources/css/normalize.css'/>" rel="stylesheet" />
<link type="text/css" href="<@s.url '/resources/css/style.css'/>" rel="stylesheet" />
<link type="text/css" href="<@s.url '/resources/css/pager.css'/>" rel="stylesheet" />
<link type="text/css" href="<@s.url '/resources/css/selectize.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<@s.url '/resources/js/jquery.min.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/jquery-json.2.4.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/jquery-jtemplates.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/jquery.pager.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/selectize.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/selectize.js'/>" ></script>
<script type="text/javascript">var IMAGESPATH ="<@s.url '/resources/images'/>/";</script>
<script type="text/javascript" src="<@s.url '/resources/js/zDrag.js'/>" ></script>
<script type="text/javascript" src="<@s.url '/resources/js/zDialog.js'/>" ></script>

<!--[if lt IE 9]>
<link type="text/css" href="<@s.url '/resources/css/ge-ie8.css'/>" rel="stylesheet" />
<![endif]-->


<!--css/js 结束</#macro>

<#-- 顶部 -->
<#macro topFrontHead title="产品查询">顶部 -->
	<div class="first_top" align="center">
			<div class="box">
				<div class="left">
					<#--<span>SIMI</span> -->
					<img src="<@s.url '/resources/img/bjs_logo.jpg'/>" width="96px;" height="40px;"/>
					航运保险产品注册管理平台
					
				</div>
				<ul>
					<li><a href="<@s.url '/front/index'/>"	<#if title=='首页'>class="ullia" </#if> >首页</a></li>
					<li><a href="<@s.url '/front/product/list'/>"	<#if title=='产品查询'>class="ullia" </#if>>产品查询</a></li>
					<li><a href="<@s.url '/front/news'/>"		<#if title=='信息公示'>class="ullia" </#if>>信息公示</a></li>
					<li><a target="_blank" href="https://www.shie.com.cn/index.html"	<#if title=='关于我们'>class="ullia" </#if>>关于我们</a></li>
				</ul>
			</div>
		</div>
<!-- 顶部结束</#macro>

<#macro secondFrontHead>顶部第二栏 -->
<div class="first_two" align="center">
			<div class="centerdiv">
				<div id="playBox">
					<div class="pre" style="display: none;"></div>
					<div class="next" style="display: none;"></div>
					<div class="smalltitle">
						<ul>
							<li class="thistitle"></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<ul class="oUlplay">
						<li>
							<a >
								<img src="<@s.url '/resources/img/20150609180455.jpg'/>"/> 
							</a>
						</li>
								<li>
							<a>
								<img  src="<@s.url '/resources/img/20150609180453.jpg'/>"/> 
							</a>
						</li>
						<li>
							<a >
								<img src="<@s.url '/resources/img/20150609180454.jpg'/>"/>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
<!--顶部第二栏结束</#macro>
		
<#macro threeFrontHead>顶部第三栏 -->
<#--
<div class="first_three" align="center">
			<div class="onebox">
				<div class="left">
					最新信息
					<img src="<@s.url '/resources/img/img_first_news_top.png'/>" />
				</div>
				<p id="news"><span></span>航保协会第五次会议顺利召开！</p>
			</div>
		</div>
		-->
<!--顶部第三栏结束</#macro>


<#-- 登陆后样式 -->

<#-- 顶部 -->
<#macro topheader>header -->
<div class="product_manager_top" align="center">
			<div class="topbox">
				<img src="<@s.url '/resources/img/img_logo.png'/>"/>
				<div class="logo">航运保险产品注册管理平台 </div>
			</div>
		</div>
		<div class="hangxian"></div>
<!-- header结束</#macro>


<#macro select id datas value="" key="" text=""  default="" defaultValue="">
   <select id="${id}" name="${id}">
   	<#if default!="">
      <option value="${defaultValue}">${default}</option>
     </#if>
      <#list datas as data>
        <#if key!="">
          <#if value == data[key]?string>
         	<option value="${data[key]}" selected>${data[text]}</option>
          <#else>
           <option value="${data[key]}">${data[text]}</option>
         </#if>
        <#else>
          <#if value == data>
         	<option value="${data}" selected>${data}</option>
         <#else>
         <option value="${data}">${data}</option>
        </#if>
       </#if> 
      </#list>
   </select>
</#macro>




<#macro logonTop user>登陆成功 -->
	<div class="aftertop" align="center">
			<div class="width_990">
				<div class="float_left" style="">
					<span class="color_lan canclespan" style="">${Session["nickName"]?default(Session["account"])}</span><span class="canclespan">,欢迎回来！</span> 
					<a href="<@s.url '/muser/logout'/>" class="canclea" >退出</a>
				</div>
				<div id="message" class="float_rifht" style="cursor:hand">
					<img src="<@s.url '/resources/img/img_email.png'/>" class="message" />
					<span >站内通知</span>[<span class="color_orgen" id="readNo"></span>/<span id="read_total">20</span>]
				</div>
			</div>
	</div>
	<script>
		$.ajax({
	        type : "post",
	        url : "<@s.url '/message/getReadInfo'/>",
	        data : {},
	        dataType : "json",
	        success : function(data,status) {
	        	if(data.success){
	        		$("#readNo").html(data.readNo);
	        		$("#read_total").html(data.readTotal);
				}else{
					$("#readNo").html(0);
	        		$("#read_total").html(0);
				}
	        }
	   });  
	   
	   $(function(){
	   	$("#message").click(function(){
	   		window.location.href="<@s.url '/message/list'/>";
	   	
	   	});
	   
	   })
	</script>
<!-- 登陆成功</#macro>
		
