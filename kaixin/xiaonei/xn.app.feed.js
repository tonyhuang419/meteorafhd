null﻿if(XN.APP.feed)XN.APP.feed = null;
XN.APP.feed = function(){
}; 
XN.APP.feed.prototype = {
	readMiniFeedUrl:'/readMini.do',
	readNewsFeedUrl:'/readNews.do',
	//readHomeFeedUrl:'/feedhome_ajax.do',
	readHomeFeedCount:0,
	setAsRead:function(fid,uid,type){
		if(type == 'newsfeed'){
			this.setNewsFeedAsRead(fid,uid);
		}else if(type == 'minifeed'){
			this.setMiniFeedAsRead(fid,uid);
		}
	},
	setNewsFeedAsRead:function(fid,uid){
		var s = this;
		this.onBeforeReadNewsFeed(fid);
		new XN.NET.xmlhttp().post(this.readNewsFeedUrl,'t=s&i=' + fid,function(){
			s.onreadNewsFeedSuccess(fid);
		},{onError:function(){s.onError()}});
	},
	setAllAsRead:function(){
		var s = this;
		this.onBeforeReadAll();
		new XN.NET.xmlhttp({
			url:this.readNewsFeedUrl,
			data:'t=a',
			onSuccess:function(){
				s.onreadAllSuccess();
			},
			onError:function(){
				XN.DO.showError('通信失败');
			}
		})
	},
	
	setMiniFeedAsRead:function(fid,uid){
		var s = this;
		var p = 't=s&i=' + fid + '&ran=' + Math.random();
		this.onBeforeReadMiniFeed(fid);
		new XN.NET.xmlhttp(this.readMiniFeedUrl,p,function(){
			s.onreadMiniFeedSuccess(fid);
		},{onError:function(){s.onError()}});
	},
	setHomeFeedAsRead:function(fid){
		this.setNewsFeedAsRead(fid);
	},
	getNewFeeds:function(fid){
		var s = this;
		var url = "retrieveNews.do";
		new XN.NET.xmlhttp(url,"",function(r){
			r = r.responseText.split("##@L#");
			try{
				$('feedCountDiv').innerHTML = r[0];
			}catch(e){}
			try{
				$('feedHome').innerHTML = r[1] || '';
			}catch(e){}
		},{onError:function(){s.onError();}});
	},
	onBeforeReadAll:function(){
		
	},
	onreadAllSuccess:function(){
		this.getNewFeeds();
	},
	onBeforeReadMiniFeed:function(fid){
		XN.Element.remove(fid);
	},
	onreadMiniFeedSuccess:function(fid){
		
	},
	onBeforeReadNewsFeed:function(fid){
		XN.Element.remove(fid);
//		var el = $('feedCountDiv');
//		el.innerHTML = parseInt(el.innerHTML) - 1 + '';
	},
	onreadNewsFeedSuccess:function(fid){
//		if($('feedHome').childNodes.length <= 14){
//			this.getNewFeeds();
//		}
	},
	onBeforeReadHomeFeed:function(fid){
		XN.Element.remove(fid);
	},
	onreadHomeFeedSuccess:function(fid){
		
	},
	onError:function(e){
		
	}
};

var feedEditor = new XN.APP.feed();

window.readHomeFeed = function(fid){
	feedEditor.setHomeFeedAsRead(fid);
}

window.readThisFeed = function(fid,uid,type){
	feedEditor.setAsRead(fid,uid,type);
};

function readMyFeed(el){
	$(el.parentNode.parentNode.parentNode.parentNode.parentNode).remove();
	new XN.NET.xmlhttp({
		url:el.href
	});
	return false;
}

window.playFeedAudio = function(el,t,file){
	el = $(el);
	var wrap = $element('div');
	wrap.className = 'media-player';
	if(t == 'mp3'){
		wrap.innerHTML = XN.Template.flashPlayer({filename:file});
	}else{
		wrap.innerHTML = XN.Template.mediaPlayer({filename:file});
	}
	el.addClass('playing');
	XN.DOM.getElementsByClassName('feedbody',el)[0].appendChild(wrap);
};

window.playFeedVideo = function(shareid,feedid,owner,scale){
       var url = "/share/share.do" ;
	   var el = $(feedid);
	   var wrap = $element('div');
	   wrap.className = 'media-player';
	   el.addClass('playing');
	   wrap.setStyle('background:url(http://xnimg.cn/img/upload_progress.gif) no-repeat center center;height:100px;float:none;margin-bottom:10px;');
       XN.DOM.getElementsByClassName('feedbody',el)[0].appendChild(wrap);
	   function playswf(file,scale){
	   		wrap.setStyle('height:auto;');
			var w,h;
			w = el.offsetWidth - 24;
			if(w > 500){
				w = 500;
			}
			h = parseInt(w/scale);
			wrap.innerHTML = XN.Template.flash({width:w,height:h,filename:file});
		};
	   
	   new XN.NET.xmlhttp({
	   		'url':url,
			data:"id=" + shareid + "&owner=" + owner ,
			onSuccess:function(r){
	             try{
					  var ret = XN.JSON.parse(r.responseText);
		              var status = ret.status ;
		              if ( status == 0 ){
		                  playurl = ret.playUrl;
		                  if ( playurl != '' )
		                     playswf (playurl,scale);
		              }
		              else if (status == 1){
					  	XN.DO.showError('该分享不存在或已被删除');
		                  return ;
		              }
				}catch(e){
					XN.DO.showError('该分享不存在或已被删除');
				}
			},
			onError:function(){
				XN.DO.showError('该分享不存在或已被删除');
			}
       });
};
/**
 *  for old feed
 */

window.play = function(el,t,file){
	el = $(el + '');
	if(t == 'mp3'){
		el.innerHTML = XN.Template.flashPlayer({filename:file});
	}else{
		el.innerHTML = XN.Template.mediaPlayer({filename:file});
	}
};

window.playswf = function(el,file,scale){
	var w,h;
	el = $(el + '');
	w = el.parentNode.offsetWidth - 24;
	if(w > 500){
		w = 500;
	}
	h = parseInt(w/scale);
	el.innerHTML = XN.Template.flash({width:w,height:h,filename:file});
	el.onclick = null;
};

window.preplayswf = function(id,owner,elem,scale){
       var url = "/share/share.do" ;
	  if(elem && scale)$(id + '').setStyle('background-image:url(' + XN.ENV.staticRoot + 'img/upload_progress.gif);float:none;margin-bottom:10px;');
       new XN.NET.xmlhttp({
	   		'url':url,
			data: "id=" + id + "&owner=" + owner ,
			onSuccess:function(r){
			   try{
				  var ret = XN.JSON.parse(r.responseText);
	              var status = ret.status ;
	              if ( status == 0 ){
	                  playurl = ret.playUrl;
	                  if ( playurl != '' )
	                     playswf (elem,playurl,scale);
				  }
	              else if (status == 1){
					XN.DO.showError('该分享不存在或已被删除!');
	                  return ;
	              }
				}catch(e){
					XN.DO.showError('该分享不存在或已被删除!');
				}
                
			},
			onError:function(){
				XN.DO.showError('该分享不存在或已被删除!');
			}
 		});
 }
