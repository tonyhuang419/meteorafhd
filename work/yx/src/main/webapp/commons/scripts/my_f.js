//��ʾDEMO��JS
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

function change_op(obj) {
	var num = obj.selectedIndex;
	switch(num){
		case 0:	
			break;
		case 1:
			alert("�깺�ɹ�.html");
			break;
		case 2:
			alert("��Э¼��.html");
			break;
		case 3:
			openUrl("��Ʊ�տ�ƻ����.html");
			break;
		case 4:
			openUrl("��ͬ���.html");
			break;				
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


//ȥ��ո�;
function ltrim(s){
	return s.replace( /^\s*/, "");
}
//ȥ�ҿո�;
function rtrim(s){
	return s.replace( /\s*$/, "");
}
//ȥ���ҿո�;
function trim(s){
	return rtrim(ltrim(s));
}


function showtable(tableid){
    var overcolor='#BBBBFF';    //��꾭����ɫ
    var colorx='#FFFFFF';       
	
    var tablename=document.getElementById(tableid)
    var tr=tablename.getElementsByTagName("tr")
    for(var i=0 ;i<tr.length;i++){
        tr[i].onmouseover=function(){
            this.style.backgroundColor=overcolor;
        }
	tr[i].onmouseout=function(){
            this.style.backgroundColor=colorx;
        }
    }
}
