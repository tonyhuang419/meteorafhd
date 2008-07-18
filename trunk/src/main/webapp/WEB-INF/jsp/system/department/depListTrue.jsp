<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>宝资HR信息系统</title>
<%@ include file="/commons/jsp/ajax_ui.jsp"%>
<script src="<s:url value="/commons/scripts/dtree1.js"/>"
	type="text/javascript"></script>
<style>
* {
	padding: 0;
	margin: 0;
}

.pane-split-left,.pane-split-right,.pane-split-top,.pane-split-bottom {
	position: absolute;
	z-index: 100;
}

.pane-split-left,.pane-split-right {
	cursor: e-resize;
}

.pane-split-top,.pane-split-bottom {
	cursor: n-resize;
}

.pane-split-drag {
	background: #ccc;
	overflow: hidden;
}

.pane-toggle-left,.pane-toggle-right,.pane-toggle-top,.pane-toggle-bottom
	{
	position: absolute;
	z-index: 200;
}

.pane-toggle-left {
	width: 5px;
	height: 25px;
	background: url(< s : url value = "/commons/images/left.gif"/ >)
		no-repeat;
}

.pane-toggle-right {
	width: 5px;
	height: 25px;
	background: url(< s : url value = "/commons/images/right.gif"/ >)
		no-repeat;
}

.pane-toggle-top {
	width: 25px;
	height: 5px;
	background: url(< s : url value = "/commons/images/top.gif"/ >)
		no-repeat;
}

.pane-toggle-bottom {
	width: 25px;
	height: 5px;
	background: url(< s : url value = "/commons/images/bottom.gif"/ >)
		no-repeat;
}
</style>
<script type="text/javascript">
			window.addEvent('domready', function(){
				var container=new Pane({
					el:'outer',
					layout:'border',	
					items:[
<!--					{-->
<!--						el:'t',							-->
<!--						region:'top',-->
<!--						styles:{-->
<!--							'height':'50px',-->
<!--							'border-bottom':'1px solid #aaa',-->
<!--							'background':"#3366cc"-->
<!--						}-->
<!--					},-->
<!--					{-->
<!--						el:'r',-->
<!--						styles:{-->
<!--							'width':'120px',-->
<!--							'border':'1px solid #aaa',-->
<!--							'margin':'3px 3px 3px 0',-->
<!--							'background':'#fff',-->
<!--							'overflow':'auto'  -->
<!--						},-->
<!--						region:'right'-->
<!--					},-->
<!--					{-->
<!--						el:'b',-->
<!--						styles:{-->
<!--							'height':'30px',-->
<!--							'border':'1px solid #aaa',-->
<!--							'margin':'0px 3px 3px 3px',-->
<!--							'background':'#fff',-->
<!--							'overflow':'hidden'  -->
<!--						},-->
<!--						region:'bottom'-->
<!--					},-->
					{
						el:'c',
						region:'center',
						layout:'split',
						orientation:'left',
						styles:{
							'margin':'3px',
							'background':'#eee'
						},
						items:[{
							el:'split-side',
							region:'side',
							minSize:120,
							maxSize:300,
							styles:{
								'width':'200px',
								'border':'1px solid #aaa',
								'background':'#fff',
								'overflow':'auto'  
							}
						},{
							el:'split-center',
							region:'center',
							layout:'split',
							orientation:'bottom',
							splitSize:3,
							items:[{
								el:'split-center-bottom',
								region:'side',
								minSize:50,
								maxSize:300,
								styles:{
									'height':'3px',
									'border':'1px solid #aaa',
									'background':'#fff',
									'overflow':'auto'  
								}
							},{
								el:'split-center-top',
								region:'center',
								styles:{
									'border':'1px solid #aaa',
									'background':'#fff',
									'overflow':'auto'  
								}
							}]										
						}]
					}]
				});
				container.render();
			});
		</script>
</head>
<body id="outer" scroll="no"
	style="position: absolute; background: #eee;">
<!--<div id="t" style="background: #eee"><span style="line-height: 50px; color: #eee; padding-left: 1em">项目资源管理</span></div>-->
<div id="c">
<div id="split-side"><script type="text/javascript">
			a = new dTree1('a');
			a.config.useIcons=false;
			a.config.useStatusText=true;
			a.config.closeSameLevel=true;
			a.config.useCookies=false;
			a.add(0,-1,'部门列表');
			
<s:iterator  value="list">
 a.add(<s:property value="id" />,<s:property value="parentDepartment" />,'<s:property value="departmentName" />','system/depQueryTrue.action?method=doDefault&depId=<s:property value="id" />','<s:property value="departmentName" />','content');
</s:iterator>
			document.write(a);
		</script></div>
<div id="split-center">
<div id="split-center-top"><iframe marginwidth=0 framespacing=0
	marginheight=0 src="#" frameborder=0 scrolling="auto" width=100%
	height="100%" id="contentArea" name="content"></iframe></div>
<div id="split-center-bottom">
<p style="padding: 10px">side region of the inner split layout</p>
</div>
</div>
</div>
<!--<div id="r">-->
<!--	<p style="padding: 10px">This is the right region of borderLayout.</p>-->
<!--</div>-->
<!--<div id="b">-->
<!--	<p style="padding: 6px">This is the bottom region of borderLayout.</p>-->
<!--</div>-->
</body>
</html>