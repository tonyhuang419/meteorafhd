<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/card_f.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
<script type="text/javascript">
function tishi(){

   var mainid=document.getElementById("contract_mainid");
   var modifyid=document.getElementById("contract_isModify");
   if(mainid.value!=""&&modifyid.value==0){
     var show= document.getElementById("showtishi");
     show.innerHTML="<p>合同信息已暂存为草稿</p>";

   }
}
function validatormoney(){
    var url='/yx/contract/validatorMoney.action';
    var myAjax=new Ajax.Request(url,{method:'post',onComplete:processResponse,asynchronous:true});
} 
function processResponse(request){
     if(request.responseText!=null){
       alert(request.responseText);
     }
}
function setMethod(name){
	    var hidden=document.getElementById("contract_method");
	    hidden.value=name;

}
function changeColor(){
     for(var i=1;i<7;i++){
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

     
     
}
</script>
</head>
<body>
<div align="center" class="bg_table02">
<s:hidden name="tag"></s:hidden>
<s:hidden name="mainid"/>
<s:hidden name="isModify" ></s:hidden>
<s:hidden name="clietId"/>
 <div id="showtishi" align="left" style="color: #FF0000" >
 </div>
            <ul class="bg_table02">
              <li id="tag1"><a href="#" onclick="javascript:if(!validate()){setNum(1);document.contract.submit();}"><span id="tag01" >主体信息</span></a></li>
              <li id="tag2"><a href="#" onclick="javascript:if(!validate()){setNum(2);document.contract.submit();tishi();}"><span id="tag02">项目信息</span></a></li>
              <li id="tag3"><a href="#" onclick="javascript:if(!validate()){setNum(3);document.contract.submit();}"><span id="tag03">开票收款阶段</span></a></li>
              <li id="tag4"><a href="#" onclick="javascript:if(!validate()){setNum(4);document.contract.submit();}"><span id="tag04">开票收款计划</span></a></li>
              <li id="tag5"><a href="#" onclick="javascript:if(!validate()){setNum(5);document.contract.submit();}"><span id="tag05">自有产品</span></a></li>
              <li id="tag6"><a href="#" onclick="javascript:if(!validate()){setNum(6);document.contract.submit();}"><span id="tag06">资料要求</span></a></li>
            </ul>
</div>  
</body>
<script type="text/javascript">
tishi();
changeColor();
</script>
</html>
