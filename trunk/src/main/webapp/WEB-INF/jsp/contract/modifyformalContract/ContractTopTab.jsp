<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/card_f.js" type="text/javascript"></script>
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
<script type="text/javascript">
function tishi(){

}
function processResponse(request){
     if(request.responseText!=null){
       alert(request.responseText);
     }
}
function setMethod(name){
	    var hidden=document.getElementById("formalContractModify_method");
	    hidden.value=name;

}
function changeColor(){
     for(var i=1;i<9;i++){
      var tago = document.getElementById("tag0"+i);
      tago.className="";
     }
   
     var tag = document.getElementById("tag");
       if(tag.value==0||tag.value==1){
          var tagx=document.getElementById("tag01");
          tagx.className="selectli1";
     } 
     if(tag.value==2){
       var tagx=document.getElementById("tag02");
       tagx.className="selectli1";
     }
     if(tag.value==3){
       var tagx=document.getElementById("tag03");
       tagx.className="selectli1";
     }
     if(tag.value==4){
       var tagx=document.getElementById("tag04");
       tagx.className="selectli1";
     }
     if(tag.value==5){
       var tagx=document.getElementById("tag05");
       tagx.className="selectli1";
     }
     if(tag.value==6){
       var tagx=document.getElementById("tag06");
       tagx.className="selectli1";
     }
     if(tag.value==7){
       var tagx=document.getElementById("tag07");
       tagx.className="selectli1";
     }
     if(tag.value==8){
       var tagx=document.getElementById("tag08");
       tagx.className="selectli1";
     } 
}
</script>
</head>
<body>
<div align="center" class="bg_table02">
<s:hidden name="tag"/>
<s:hidden name="mainid"/>
<s:hidden name="isModify" ></s:hidden>
<s:hidden name="clietId"/>
 <div id="showtishi" align="left" style="color: #FF0000" >
 </div>
            <ul class="bg_table02">
              <li id="tag1"><a href="#" onclick="javascript:if(!validate()){setNum(1);document.formalContractModify.submit();}"><span id="tag01">主体信息</span></a></li>
              <li id="tag2"><a href="#" onclick="javascript:if(!validate()){setNum(2);document.formalContractModify.submit();}"><span id="tag02">项目信息</span></a></li>
              <li id="tag3"><a href="#" onclick="javascript:if(!validate()){setNum(3);document.formalContractModify.submit();}"><span id="tag03">开票收款阶段</span></a></li>
              <li id="tag4"><a href="#" onclick="javascript:if(!validate()){setNum(4);document.formalContractModify.submit();}"><span id="tag04">开票收款计划</span></a></li>
              <li id="tag5"><a href="#" onclick="javascript:if(!validate()){setNum(5);document.formalContractModify.submit();}"><span id="tag05">开票关联</span></a></li>
              <li id="tag6"><a href="#" onclick="javascript:if(!validate()){setNum(6);document.formalContractModify.submit();}"><span id="tag06">申购关联</span></a></li>
              <li id="tag7"><a href="#" onclick="javascript:if(!validate()){setNum(7);document.formalContractModify.submit();}"><span id="tag07">自有产品</span></a></li>
              <li id="tag8"><a href="#" onclick="javascript:if(!validate()){setNum(8);document.formalContractModify.submit();}"><span id="tag08">其他</span></a></li>
            </ul>
</div>  
</body>
<script type="text/javascript">
tishi();
changeColor();
<s:if test="#isRelationSuccess == 'true' "> 
window.close();
</s:if>
</script>
</html>
