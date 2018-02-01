<#macro icrmnav title second  role >
	<!-- header -->
		<div class="product_manager_top" align="center">
			<div class="topbox">
				<#--<img src="<@s.url'/resources/img/img_logo.png'/>"/>-->
				<img src="<@s.url '/resources/img/bjs_logo.jpg'/>" width="96px;" height="40px;"/>
				<div class="logo">航运保险产品注册管理平台</div>
			</div>
		</div>
		<div class="hangxian"></div>
		<!-- 导航栏 -->
		<div class="product_manager_one" align="center">
			<!-- 一级 -->
			<div class="yijinav">
				<ul>
				    <li id="btn0_0" <#if title=='用户中心'>class="liselected" </#if> onmouseover="navOver(0)" onmouseout="navOut(0)"><a	href="<@s.url'/icrm/modifyPassword_init'/>">用户中心</a></li>
					<li id="btn0_1" <#if title=='信息管理'>class="liselected" </#if> onmouseover="navOver(1)" onmouseout="navOut(1)"><a	href="<@s.url '/icrm/list'/>" >信息管理</a></li>
				</ul>
			</div>
			<!-- 二级 -->

			<div  class="eryinav" align="center" >
			<div style="width: 990px;"   >
					
					<ul  id="js00" <#if title!='用户中心'> style="display: none" </#if> >
						<li  <#if second=='修改密码'>class="erliselected" </#if> onmouseout="eryinavOut(0)"   onmouseover="eryinavOver(0)"><a  href="<@s.url "/icrm/modifyPassword_init"/>">修改密码</a></li>
					</ul>
					<ul  id="js01"  <#if title !='信息管理'>style="display: none"</#if>>
						<li <#if second=='保单信息查询'>class="erliselected" </#if>onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)"><a href="<@s.url '/icrm/list'/>">保单信息查询</a></li>
						<li <#if second=='信息上传'>class="erliselected" </#if>onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)"><a href="<@s.url '/icrm/policy/reportPolicyInit'/>">信息上传</a></li>
						<li <#if second=='汇总信息查询'>class="erliselected" </#if>onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)"><a href="<@s.url'/icrm/summaryLst'/>">汇总信息查询</a></li>
					</ul>
				</div>
				
			</div>
		</div>
		
	<script type="text/javascript">
		
		function navOver(navNumber) {
	 }
		  function navOut(navNumber) {
			  $("#btn0_"+navNumber).find("a").removeClass("acitve");
			  
		  }
		  
		  function  eryinavOver(navNumber){
		
		  if($("#js0"+navNumber).is(":visible"))
			  $("#btn0_"+navNumber).find("a").addClass("acitve");
			  
		  }
		function  eryinavOut(navNumber){
			}
		  
			  
		
		      
		   
	//  jQuery(function() {
	//		jQuery(".eryinav").hover(function() {
			//
	//		}, function() {
	//			$(".eryinav").find("ul").hide();
				
	//			$(".yijinav").find("a").removeClass("acitve");
				
	//			$(".erliselected").parent().show();
	//		});
			
	//	  }); 
		

		
		</script>
		
		
<!-- admin导航结束</#macro>
		