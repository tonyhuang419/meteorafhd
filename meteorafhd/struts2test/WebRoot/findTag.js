

//统计一个标签下的标签数量，并调用下面的方法赋值
function calcSum(baseTagId,tag){
	var serial = 1;
	var levelF;
	levelF =  document.getElementById(baseTagId + serial);
	while(levelF!=null){	
		var tags = levelF.getElementsByTagName(tag);
		superAddition(baseTagId + serial,tags.length);	
		serial++;
		levelF =  document.getElementById(baseTagId + serial);
	}
}

//通过id，找到父节点的第一个子节点，并追加innerHTML
function superAddition(id,content){
alert(content);
	document.getElementById(id).parentNode.firstChild.innerHTML
	= document.getElementById(id).parentNode.firstChild.innerHTML + content;
}