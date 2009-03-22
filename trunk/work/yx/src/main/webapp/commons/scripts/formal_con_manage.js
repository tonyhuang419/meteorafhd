function selectX(){
	document.getElementById("departNum").selectedIndex = 1;
	document.getElementById("stage").selectedIndex = 2;
}

function openUrlSelf(url){
	window.open(url,'_self','');
}

/*   abandon mothed
function showlist(obj){
	if(document.getElementById(obj).style.display  == "block"){
		document.getElementById(obj).style.display = "none";
	}
	else{
		document.getElementById(obj).style.display = "block";
	}	
}

function displayX(obj,s){
	if(obj.checked){	
 		document.getElementById(s).style.display="block";	
	}
	else{
		document.getElementById(s).style.display="none";
	}
}
*/

/*   abandon mothed
function mark(s){
	var x = "<font color='#FF0000'>* </font>";
	document.getElementById(s);
	var ttext = s.innerHTML;
	if(ttext.length>10){
		s.innerHTML = ttext.substr (29,10);
	}
	else{
		s.innerHTML = x + ttext;
	}
}
*/

function ltrim(s){
	return s.replace( /^\s*/, "");
}
function rtrim(s){
	return s.replace( /\s*$/, "");
}
function trim(s){
	return rtrim(ltrim(s));
}


function showtable(tableid){
    var overcolor='#BBBBFF';
    var colorx='#FFFFFF';       
	
    var tablename=document.getElementById(tableid);
    var tr=tablename.getElementsByTagName("tr");
    for(var i=0 ;i<tr.length;i++){
        tr[i].onmouseover=function(){
            this.style.backgroundColor=overcolor;
        }
		tr[i].onmouseout=function(){
            this.style.backgroundColor=colorx;
        }
    }
}

function showtable2(tableid){
    var overcolor='#BBBBFF';
    var colorx='#E7F7FE';       
	
    var tablename=document.getElementById(tableid);
    var tr=tablename.getElementsByTagName("tr");
    for(var i=0 ;i<tr.length;i++){
        tr[i].onmouseover=function(){
            this.style.backgroundColor=overcolor;
        }
		tr[i].onmouseout=function(){
            this.style.backgroundColor=colorx;
        }
    }
}

//用于正式合同明细
function openCon(obj){
	var conUrl="/yx/contract/formalContractManage/formalContractManage.action?cmisysid="+obj+"&randomNum="+Math.random();
	//var con_sid = obj.all.con_sid.value;
	//var con_url = baseURL + con_sid;
	openUrlSelf(conUrl);				
}




//用于合同确认明细
function openConSure(obj){
	var form = document.forms[0];  
	
	var baseURL="/yx/contract/formalContractManage/formalContractManage.action?whereCome=1&cmisysid=";
	var con_sid = obj.parentNode.all.conMainId.value;
	var con_url = baseURL + con_sid;
	
	form.action = con_url;
	form.submit();
	
	//openUrlSelf(con_url);				
}


//用于合同工程明细
function openConSure3(obj){
	var baseURL="/yx/contract/formalContractManage/formalContractManage.action?whereCome=3&cmisysid=";
	var con_sid = obj.parentNode.all.operation2.value;
	var con_url = baseURL + con_sid;
	openUrlSelf(con_url);				
}


/*   abandon mothed
//从实际开票和收款计划，提交申请
function trav_rcplanckbox(){

	var baseurl = "/yx/contract/formalContractManage/formalContractInvoiceApply.action?rclist=";
	var cks = document.getElementsByName("kpskjh");
	var len = cks.length;
	var getString = "";
	for(var i=0;i<len;i++){ 
		if(cks[i].checked){
			getString =cks[i].parentNode.parentNode.parentNode.all.rcplan.value;
			var rcform = document.getElementById("rcplanform");
			rcform.target = "_blank";
        	rcform.action = baseurl + getString;
        	rcform.submit();
			return
		}	
	}
	alert("请先选择实际开票和收款计划");
}
*/


//从合同系统号，提交合同变更
function con_change(){
	var baseurl = "/yx/contract/formalContractModify.action?method=Modify";
	
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	openUrl(baseurl+'&mainid='+contractmainsid);
}



//提取合同系统号、项目系统号，提交外协申请
function con_assistance(){
	var baseurl = "/yx/contract/assistanceApplyQuery.action?";
	
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	
	//参数2：合同项目系统号
	var itemsysid = 0;
	
	//获得项目数组
	iir = document.getElementsByName("item_info_radio");
	var len = iir.length;
	

	for(var i=0;i<len;i++){        
		if(iir[i].checked){
			itemsysid = iir[i].parentNode.parentNode.all.itemsid.value;	
			
			var conItemId = iir[i].parentNode.parentNode.all.conItemId.value;			
			if ( conItemId.length < 1){
				alert("项目号不存在，无法进行外协申请");
				return;	
			}	
		}
		
	}
	if(itemsysid != 0){
		openUrl(baseurl+'mainId='+contractmainsid+"&eventId="+itemsysid);
	}
	else{
		alert("请先选择项目")
	}	
}



function openUrlCustomer(url,height,width){
		window.open(url,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,' +
				'resizable=1,height='+height+',width='+width+"'");
}


function openUrlReclBill(){
   var pram=document.getElementById("contractmainsid").value;
   var beaseUrl="/yx/contract/realContractBillandRecePlan.action?mainId="+pram;
   window.open(beaseUrl,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,' +
				'resizable=1,height=800,width=600');
}


//合同确认用method、转正
function checkGC() {
		var form = document.getElementById("preparation");
	
		var conMainSid = document.getElementById("contractmainsid").value;
		var cthje = window.confirm("您确定要转正式合同吗？");
		if (cthje == true) {
			form.action = "/yx/contract/searchContractOkList.action?method=changeTrueState&conMainId="+conMainSid;
			form.submit();
			
			//location.href  = "/yx/contract/searchContractOkList.action?method=changeTrueState&conMainId="+conMainSid;
		}
}

//合同确认用method、转正 
function checkGC2() {
		var form = document.getElementById("preparation");
	
		var conMainSid = document.getElementById("contractmainsid").value;
		var cthje = window.confirm("您确定要转正式合同吗？");
		if (cthje == true) {
			//form.action = "/yx/contract/searchContractOkList.action?method=changeTrueAndJC&conMainId="+conMainSid;
			//form.submit();
			
			
			window.open('','xx','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=170,width=300');
	
			var url = "/yx/contract/searchContractOkJCList.action?method=changeTrueAndJC&conMainId="+conMainSid;
			var form = document.getElementById("preparation");
			form.target = "xx";
			form.action = url;
			form.submit();  
	
			//window.open('/yx/contract/searchContractOkJCList.action?method=changeTrueAndJC&conMainId='+conMainSid,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=170,width=300');
			
			//location.href  = "/yx/contract/searchContractOkList.action?method=changeTrueState&conMainId="+conMainSid;
		}
}



//合同确认用method、退回、转预、取消确认...工程
function chageState(convar) {
	
	var form = document.getElementById("preparation");
	
	if(form != null){
	
	var conMainSid = document.getElementById("contractmainsid").value;

	if (convar.value == '确认退回') {
		var chegthj = window.confirm("您确定要退回该合同吗？");
		if (chegthj == true) {
			form.action = "/yx/contract/searchContractOkList.action?method=confirmReturn&conMainId="+conMainSid;
			form.submit();
			
			//location.href = "/yx/contract/searchContractOkList.action?method=confirmReturn&conMainId="+conMainSid;
		}
	} else if (convar.value == '转预合同') {
		var chegth = window.confirm("您确定转预合同吗？");
		if (chegth == true) {
			form.action = "/yx/contract/searchContractOkList.action?method=changeOrderState&conMainId="+conMainSid;
			form.submit();	
			//location.href = "/yx/contract/searchContractOkList.action?method=changeOrderState&conMainId="+conMainSid;
		}
	} else {
		var chefrgth = window.confirm("你确定要取消确认该合同吗?");
		if (chefrgth == true) {		
			form.action = "/yx/contract/searchContractOkList.action?method=cancelConfirm&conMainId="+conMainSid;
			form.submit();
			//location.href = "/yx/contract/searchContractOkList.action?method=cancelConfirm&conMainId="+conMainSid;
		}
	}
	}
}


//合同确认用method、退回、转预、取消确认...集成
function chageState2(convar) {
	
	var form = document.getElementById("preparation");
	
	if(form != null){
	
	var conMainSid = document.getElementById("contractmainsid").value;

	if (convar.value == '确认退回') {
		var chegthj = window.confirm("您确定要退回该合同吗？");
		if (chegthj == true) {
			form.action = "/yx/contract/searchContractOkJCList.action?method=confirmReturn&conMainId="+conMainSid;
			form.submit();
			
			//location.href = "/yx/contract/searchContractOkList.action?method=confirmReturn&conMainId="+conMainSid;
		}
	} else if (convar.value == '转预合同') {
		var chegth = window.confirm("您确定转预合同吗？");
		if (chegth == true) {
			form.action = "/yx/contract/searchContractOkJCList.action?method=changeOrderState&conMainId="+conMainSid;
			form.submit();
		
			//location.href = "/yx/contract/searchContractOkList.action?method=changeOrderState&conMainId="+conMainSid;
		}
	} else {
		var chefrgth = window.confirm("你确定要取消确认该合同吗?");
		if (chefrgth == true) {
			
			form.action = "/yx/contract/searchContractOkJCList.action?method=cancelConfirm&conMainId="+conMainSid;
			form.submit();

			//location.href = "/yx/contract/searchContractOkList.action?method=cancelConfirm&conMainId="+conMainSid;
		}
	}
	}
}


//合同确认返回用..工程
function goBackConSure(){
	var form = document.getElementById("preparation");
	if(form != null){
		//var conMainSid = document.getElementById("contractmainsid").value;
		form.action = "/yx/contract/searchContractOkList.action";
		form.submit();
	}
}


//合同确认返回用..集成
function goBackConSure2(){
	var form = document.getElementById("preparation");
	if(form != null){
		//var conMainSid = document.getElementById("contractmainsid").value;
		form.action = "/yx/contract/searchContractOkJCList.action";
		form.submit();
	}
}
