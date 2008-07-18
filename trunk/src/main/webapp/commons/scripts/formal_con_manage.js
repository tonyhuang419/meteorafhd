function selectX(){
	document.getElementById("departNum").selectedIndex = 1;
	document.getElementById("stage").selectedIndex = 2;
}

function openUrlSelf(url){
	window.open(url,'_self','');
}


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


var baseURL="/yx/contract/formalContractManage/formalContractManage.action?cmisysid=";
function openCon(obj){
	var con_sid = obj.all.con_sid.value;
	var con_url = baseURL + con_sid;
	openUrlSelf(con_url);				
}


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


//从合同系统号，提交合同变更
function con_change(){
	var baseurl = "/yx/contract/formalContractModify.action?method=Modify";
	
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	openUrl(baseurl+'&mainid='+contractmainsid);
}


//提取合同系统号、项目系统号，提交申购申请
function con_purchase(){
	var baseurl = "/yx/purchase/purchase.action?method=newPurchase";
	
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
		}
		
	}
	openUrl(baseurl+'&mainId='+contractmainsid+"&eventId="+itemsysid);
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

function closeCon(){
	var baseurl = "/yx/contract/formalContractManage/formalContractManage.action?method=closeCon&cmisysid=";	
	var contractMainSid = document.getElementById("contractmainsid").value;
	window.open(baseurl + contractMainSid,"_self");
}
