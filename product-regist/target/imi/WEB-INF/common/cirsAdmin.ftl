<#-- admin导航 -->
<#macro adminnav title second  role >admin导航 -->
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
				
					<li id="btn0_0" <#if title=='用户中心'>class="liselected" </#if> onmouseover="navOver(0)" onmouseout="navOut(0)"><a	href="<@s.url'/cirsAdmin/modifyPassword_init'/>">用户中心</a></li>
					<li id="btn0_1" <#if title=='用户查询'>class="liselected" </#if> onmouseover="navOver(1)" onmouseout="navOut(1)"><a	href="<@s.url'/cirsAdmin/muser_list'/>" >用户查询</a></li>
					<li id="btn0_2" <#if title=='产品查询'>class="liselected" </#if> onmouseover="navOver(2)" onmouseout="navOut(2)"><a 	href="<@s.url'/cirsAdmin/list'/>" >产品查询</a></li>
					<li id="btn0_4" <#if title=='核查查询'>class="liselected" </#if> onmouseover="navOver(4)" onmouseout="navOut(4)"><a	href="<@s.url'/cirsAdmin/verifys_list'/>">核查查询</a></li>
					<li id="btn0_5" <#if title=='反馈处理'>class="liselected" </#if> onmouseover="navOver(5)" onmouseout="navOut(5)"><a   href="<@s.url'/cirsAdmin/complaints_list'/>" >反馈处理</a></li>
					<li id="btn0_6" <#if title=='发布查询'>class="liselected" </#if> onmouseover="navOver(6)" onmouseout="navOut(6)"><a	href="<@s.url'/cirsAdmin/news_list'/>" >发布查询</a></li>
					<li id="btn0_7" <#if title=='报表管理'>class="liselected" </#if> onmouseover="navOver(7)" onmouseout="navOut(7)"><a	href="<@s.url'/cirsAdmin/report/test'/>" >报表处理</a></li>
					<!--<li id="btn0_8" <#if title=='信息管理'>class="liselected" </#if> onmouseover="navOver(8)" onmouseout="navOut(8)"><a	href="<@s.url'/cirsAdmin/policy/list'/>" >信息管理</a></li>-->
				</ul>
			</div>
			<!-- 二级 -->

			<div  class="eryinav" align="center" >
			<div style="width: 990px;"   >
					<ul  id="js00" <#if title!='用户中心'> style="display: none" </#if> >
						<li  <#if second=='修改密码'>class="erliselected" </#if> onmouseout="eryinavOut(0)"   onmouseover="eryinavOver(0)"><a>修改密码</a></li>
					</ul>
					<ul  id="js01" <#if title!='用户查询'> style="display: none"</#if>>
						<li  <#if second=='用户列表'>class="erliselected" </#if>  onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)"><a href="<@s.url'/cirsAdmin/muser_list'/>">用户列表</a></li>
					</ul> 
					
					<ul  id="js02"  <#if title!='产品查询'>style="display: none"</#if>>
						<li <#if second=='产品查询'>class="erliselected" </#if> onmouseout="eryinavOut(2)"   onmouseover="eryinavOver(2)"><a href="<@s.url'/cirsAdmin/list'/>">产品查询</a></li>
					</ul> 
					
					
				   
			
					
				  	<ul  id="js04"  <#if title!='核查查询'>style="display: none"</#if>>
						<li <#if second=='核查查询'>class="erliselected" </#if>  onmouseout="eryinavOut(4)"   onmouseover="eryinavOver(4)"><a href="<@s.url'/cirsAdmin/verifys_list'/>">核查查询</a></li>
					</ul> 
				   
					<ul  id="js05"  <#if title!='反馈处理'>style="display: none"</#if>>
						<li <#if second=='反馈处理'>class="erliselected" </#if>onmouseout="eryinavOut(5)"   onmouseover="eryinavOver(5)"><a href="<@s.url'/cirsAdmin/complaints_list'/>">反馈处理</a></li>
					</ul>
					<ul  id="js06"  <#if title !='发布查询'>style="display: none"</#if>>
						<li <#if second=='发布查询'>class="erliselected" </#if>onmouseout="eryinavOut(6)"   onmouseover="eryinavOver(6)"><a href="<@s.url'/cirsAdmin/news_list'/>">发布查询</a></li>

					</ul>
					
					<ul  id="js07"  <#if title !='报表管理'>style="display: none"</#if>>
						<li <#if second=='报表一'>class="erliselected" </#if>onmouseout="eryinavOut(6)"   onmouseover="eryinavOver(6)"><a href="<@s.url'/cirsAdmin/report/test'/>">报表一</a></li>
						<li <#if second=='报表二'>class="erliselected" </#if>onmouseout="eryinavOut(6)"   onmouseover="eryinavOver(6)"><a href="<@s.url'/cirsAdmin/report/test2'/>">报表二</a></li>
				
					</ul>
					<!--<ul  id="js08"  <#if title !='信息管理'>style="display: none"</#if>>
						<li <#if second=='保单信息查询'>class="erliselected" </#if>onmouseout="eryinavOut(8)"   onmouseover="eryinavOver(8)"><a href="<@s.url'/cirsAdmin/policy/list'/>">保单信息查询</a></li>
						<li <#if second=='汇总报表'>class="erliselected" </#if>onmouseout="eryinavOut(8)"   onmouseover="eryinavOver(8)"><a href="<@s.url'/cirsAdmin/policy/summaryLst'/>">汇总报表</a></li>
					</ul>-->
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
		  
			  
	</script>
		
		
<!-- admin导航结束</#macro>
		