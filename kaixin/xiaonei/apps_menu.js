null
var MAX_APP_LIST_END=275;var MAX_SIDENAV_LINKS=7;var MOVING_THRESHOLD=10;var saved_message=null;function track_moveable(container_obj,link_obj){link_obj.ondrag=function(e){event.cancelBubble=true;return false;}.bind(this);;this.listContainer=container_obj;this.link=link_obj;this.listContainer.onmousedown=function(e){return this._onclick(e?e:window.event);}.bind(this);}
track_moveable.prototype._onclick=function(e){this.clickMouseY=mouseY(e);document.onselectstart=function(e){return false;};document.onmousemove=function(e){return this._track_move(e?e:window.event)}.bind(this);document.onmouseup=function(e){this._track_drop(e?e:window.event)}.bind(this);return false;}
track_moveable.prototype._track_move=function(e){if(Math.abs(mouseY(e)-this.clickMouseY)>MOVING_THRESHOLD){var moveable=new moveable_app(this.listContainer,this.link);moveable._onclick(null,this.clickMouseY);}}
track_moveable.prototype._track_drop=function(e){document.onmouseout=document.onmouseup=document.onmousemove=document.onclick=null;this.link.onclick=function(e){return true;};}
function moveable_app(container_obj,link_obj){this.listContainer=container_obj;this.link=link_obj;this.listContainer.onmousedown=function(e){return this._onclick(e?e:window.event);}.bind(this);}
moveable_app.prototype._onclick=function(e,mouseYCoord){add_css_class_name(this.listContainer,'floating_container');var app_list_node=ge('app_list');this.listContainer.lowerBoundY=elementY(app_list_node.firstChild?app_list_node.firstChild:app_list_node);this.oldListID=this.listContainer.parentNode.parentNode.id;this.justOpened=false;var app_non_nav_list_node=ge('app_non_nav_list');this.listContainer.upperBoundY=elementY(app_non_nav_list_node.lastChild?app_non_nav_list_node.lastChild:app_non_nav_list_node);var listContainerHeight=(ua.ie()||ua.safari())?this.listContainer.offsetHeight:this.listContainer.offsetHeight+1;this.listContainer.parentNode.style.height=(listContainerHeight)+'px';this.listContainer.top=elementY(this.listContainer);mouseYCoord=mouseYCoord?mouseYCoord:mouseY(e);this.mouseOffset=mouseYCoord-this.listContainer.top;this.listContainer.style.top=this.listContainer.top+'px';document.onmousemove=function(e){return this._move(e?e:window.event)}.bind(this);document.onmouseup=function(e){this._drop(e?e:window.event)}.bind(this);this._calculateBoundaries();return false;}
moveable_app.prototype._calculateBoundaries=function(){var list=this.listContainer.parentNode.parentNode;var previousListItem=this.listContainer.parentNode.previousSibling;this.listContainer.prevList=null;this.listContainer.previousNodeY=null;if(previousListItem){this.listContainer.previousNodeY=elementY(previousListItem)+7;this.newList=false;}else if(list.id=='app_non_nav_list'){this.listContainer.prevList=ge('app_list');var elementPos=null;if(this.listContainer.prevList.lastChild){elementPos=this.listContainer.prevList.lastChild;}else{elementPos=this.listContainer.prevList;}
this.newList=true;this.listContainer.previousNodeY=elementY(elementPos)+20;}
var nextListItem=this.listContainer.parentNode.nextSibling;this.listContainer.nextList=null;this.listContainer.nextNodeY=null;if(nextListItem){this.listContainer.nextNodeY=elementY(nextListItem)-7;this.newList=false;}else if(list.id=='app_list'){this.listContainer.nextList=ge('app_non_nav_list');var elementPos=null;this.newList=true;if(this.listContainer.nextList.parentNode.style.display=='none'){this.justOpened=true;this.listContainer.nextNodeY=elementY(ge('more_link'))-18;}else{if(this.listContainer.nextList.firstChild){elementPos=this.listContainer.nextList.firstChild;}else{elementPos=this.listContainer.nextList;}
this.listContainer.nextNodeY=elementY(elementPos)-20;}}}
moveable_app.prototype._move=function(e){this.listContainer.top=mouseY(e)-this.mouseOffset;var oldParent=this.listContainer.parentNode;if(this.listContainer.nextNodeY&&this.listContainer.top>this.listContainer.nextNodeY){if(this.listContainer.nextList==null){var newParent=oldParent.nextSibling;newParent.appendChild(this.listContainer);oldParent.style.height=null;oldParent.appendChild(newParent.firstChild);}else{if(this.newList){expand_more_list();var newParent=document.createElement('div');newParent.className='list_item';this.listContainer.nextList.insertBefore(newParent,this.listContainer.nextList.firstChild);newParent.appendChild(this.listContainer);oldParent.parentNode.removeChild(oldParent);}}}
else if(this.listContainer.previousNodeY&&this.listContainer.top<this.listContainer.previousNodeY){if(this.listContainer.prevList==null){var newParent=oldParent.previousSibling;newParent.appendChild(this.listContainer);oldParent.style.height=null;oldParent.appendChild(newParent.firstChild);}else{var newParent=document.createElement('div');newParent.className='list_item';this.listContainer.prevList.appendChild(newParent);newParent.appendChild(this.listContainer);oldParent.parentNode.removeChild(oldParent);}}
if(this.listContainer.parentNode!=oldParent){oldParent.style.height=null;this.listContainer.parentNode.style.height=(this.listContainer.offsetHeight+1)+'px';this._calculateBoundaries();}
if((is_first_child(this.listContainer.parentNode,'app_list')&&this.listContainer.top<elementY(this.listContainer.parentNode))||(is_last_child(this.listContainer.parentNode,'app_non_nav_list')&&this.listContainer.top>elementY(this.listContainer.parentNode))){this.listContainer.style.top=(elementY(this.listContainer.parentNode)-1)+'px';}else{this.listContainer.style.top=this.listContainer.top+'px';}
return false;}
function is_first_child(elem,parent){return(elem.parentNode.id==parent)&&(elem.parentNode.firstChild==elem);}
function is_last_child(elem,parent){return(elem.parentNode.id==parent)&&(elem.parentNode.lastChild==elem);}
function onload_side_nav_check(){enforce_app_list_limits_and_save(false,'onload_side_nav');}
function enforce_app_list_limits_and_save(force_save,context){var display_list='';var app_list_node=ge('app_list');var more_apps_node=ge('app_non_nav_list');var more_list='';var max_reached=false;var extra_pixel_amount=0;var rearrange_message=ge('rearrange_message');if(rearrange_message){extra_pixel_amount=rearrange_message.offsetHeight+6;}
var threshold=MAX_APP_LIST_END+elementY(ge('sidebar'))+extra_pixel_amount;while(elementY(app_list_node)+app_list_node.offsetHeight>threshold||app_list_node.childNodes.length>MAX_SIDENAV_LINKS){if(more_apps_node.firstChild){more_apps_node.insertBefore(app_list_node.lastChild,more_apps_node.firstChild);}else{more_apps_node.appendChild(app_list_node.lastChild);}
max_reached=true;}
if(max_reached||force_save){for(var i=0;i<app_list_node.childNodes.length;i++){if(i!=0){display_list+=':';}
try{display_list+=app_list_node.childNodes[i].firstChild.id;}catch(e){}
}
for(var i=0;i<more_apps_node.childNodes.length;i++){if(i!=0){more_list+=':';}
try{more_list+=more_apps_node.childNodes[i].firstChild.id;}catch(e){}
}
var ajax=new XnAjax(function(obj,text){eval(text);});var post_vars={'display_list':display_list,'more_list':more_list,'context':context};ajax.post('/savemenu.do',post_vars);}}
moveable_app.prototype._drop=function(e){remove_css_class_name(this.listContainer,'floating_container');this.listContainer.style.top=null;this.listContainer.parentNode.style.height=null;enforce_app_list_limits_and_save(true,'rearrange_order');if(this.listContainer.parentNode.parentNode.id!='app_non_nav_list'&&this.justOpened){window.setTimeout('close_more_list()',500);}
document.onmouseout=document.onmouseup=document.onmousemove=document.onclick=null;if(this.link){this.link.onclick=function(e){return false;};}
return false;}
function change_status_message(className,messageContent){var message=ge('rearrange_message');message.className=className;message.innerHTML=messageContent;}
function change_to_apps_menu(list_item){var container=list_item.firstChild;var action_link=container.firstChild;action_link.setAttribute('onclick','move_lists(this.parentNode.parentNode, \'app_non_nav_list\', change_to_non_menu, true); return false;');action_link.setAttribute('class','action_item');action_link.innerHTML="remove";var handle=document.createElement('div');handle.setAttribute('class','handle');handle.setAttribute('onmousedown','new moveable_app(this.parentNode);');container.insertBefore(handle,container.firstChild.nextSibling);}
function change_to_non_menu(list_item){var container=list_item.firstChild;var action_link=container.firstChild;var handle=container.firstChild.nextSibling;container.removeChild(handle);action_link.setAttribute('onclick','move_lists(this.parentNode.parentNode, \'app_list\', change_to_apps_menu); return false;');action_link.setAttribute('class','action_item_add');action_link.innerHTML="add to menu";}
function move_lists(obj,to_list_id,changeFunction,front_of_list){to_list_obj=ge(to_list_id);if(changeFunction){changeFunction(obj);}
if(front_of_list){to_list_obj.insertBefore(obj,to_list_obj.firstChild);}else{to_list_obj.appendChild(obj);}}
var apps_menu_timout_id;function try_expand(obj){if(obj.innerHTML=='更多'){apps_menu_timout_id=window.setTimeout('expand_more_list()',500);}else{}}
function untry_expand(){window.clearTimeout(apps_menu_timout_id);}