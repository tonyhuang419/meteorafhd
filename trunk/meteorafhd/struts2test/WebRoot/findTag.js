
//计算一个标签下的标签数量
function calcSum(baseTagId,tag){
	var serial = 1;
	var levelF;
	levelF =  document.getElementById(baseTagId + serial);
	while(levelF!=null){	
		var tags = levelF.getElementsByTagName(tag);
		alert(tags.length);
		serial++;
		levelF =  document.getElementById(baseTagId + serial);
	}
}