<#macro usernav title second  role >

<!-- header -->
		<div class="product_manager_top" align="center">
			<div class="topbox">
				<!--<img src="<@s.url'/resources/img/img_logo.png'/>"/>-->
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
					<li id="btn0_0" <#if title=='用户中心'>class="liselected" </#if> onmouseover="navOver(0)" onmouseout="navOut(0)"><a    href="<@s.url'/muser/detail'/>" >用户中心</a></li>
					<li id="btn0_1" <#if title=='产品管理'>class="liselected" </#if> onmouseover="navOver(1)" onmouseout="navOut(1)"><a  href="<@s.url'/user/list'/>" >产品管理</a></li>
					<li id="btn0_2" <#if title=='核查处理'>class="liselected" </#if>  onmouseover="navOver(2)" onmouseout="navOut(2)"><a  href="<@s.url'/user/verifys/list'/>" >核查处理</a></li>
				</ul>
			</div>
			<!-- 二级 -->
			<div class="eryinav" align="center">
				<div style="width: 990px;">
					<ul id="js00"  <#if title!='用户中心'> style="display: none" </#if>>
						<li onmouseout="eryinavOut(0)"   onmouseover="eryinavOver(0)" <#if second=='基本信息'>class="erliselected" </#if>><a   href="<@s.url '/muser/detail'/>">基本信息</a></li>
						<li onmouseout="eryinavOut(0)"   onmouseover="eryinavOver(0)" <#if second=='修改密码'>class="erliselected" </#if>><a   href="<@s.url'/muser/modifyPassword_init'/>">修改密码</a></li>
					</ul>
					<ul id="js01"  <#if title!='产品管理'> style="display: none" </#if>>
						<li onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)" <#if second=='产品查询'>class="erliselected" </#if>><a   href="<@s.url '/user/list'/>" >产品查询</a></li>
						<li onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)" <#if second=='产品注册'>class="erliselected" </#if>><a  href="<@s.url '/user/reg'/>" >产品注册</a></li>
						<li onmouseout="eryinavOut(1)"   onmouseover="eryinavOver(1)" <#if second=='产品注销'>class="erliselected" </#if>><a href="<@s.url "/user/uproduct_cancel_list"/>">产品注销</a></li>
					
					</ul>
					<ul id="js02"  <#if title!='核查处理'> style="display: none" </#if>>
						<li onmouseout="eryinavOut(2)"   onmouseover="eryinavOver(2)" <#if second=='核查处理'>class="erliselected" </#if>><a  href="<@s.url'/user/list'/>" >核查处理</a></li>
					</ul>
				</div>
			</div>
		</div>



<script type="text/javascript">
		
		function navOver(navNumber) {
		      //二三级菜单栏
		     // $("#btn0_"+navNumber).find("a").attr("class","acitve");
		   		
		   		
		   		
		  		
		  		 $("#btn0_"+navNumber).find("a").addClass("acitve");
		    //  var navDiv =  document.getElementById("js0" + navNumber);
		    // if(navDiv){
			//		$(".eryinav").find("ul").hide();
		    //		$("#js0"+navNumber).stop(true, true).show();
		   //  }
		     
		      
		  }
		  function navOut(navNumber) {
			  $("#btn0_"+navNumber).find("a").removeClass("acitve");
			  
		  }
		  
		  function  eryinavOver(navNumber){
		
		  if($("#js0"+navNumber).is(":visible"))
			  $("#btn0_"+navNumber).find("a").addClass("acitve");
			  
		  }
		function  eryinavOut(navNumber){
			//	 $("#btn0_"+navNumber).removeClass("acitve");
				
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






<!-- 注册用户导航结束</#macro>






















