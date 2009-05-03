null﻿//XN.DEBUG.On();
if(XN.PAGE.home)XN.PAGE.home = null;
XN.PAGE.home = {
	loadCommonFriendUrl:'/ajax_get_common_friend.do',
	delCommonFrendUrl:'/ajax_remove_common_friend.do',
	commonFriends:[],
    statusFeeds : null,
	_isLoadingFriends:false,
	initFeed:function(){
		/**
		 * 点击feed里的链接时标记已读
		 */


		/**
		 * 叉掉所有feed
		 */
		if($('setAllFeedsAsRead')){
			$('setAllFeedsAsRead').onclick = function(){
				XN.DO.confirm({
					title:'标记已读',
					message:'确定将全部新鲜事设置为已读吗?',
					callBack:function(r){
						if(r){
							feedEditor.setAllAsRead();
							if ( $( 'moreFeed' ) ) $( 'moreFeed' ).hide();
						}
					}
				});
			}
		}

		/**
		 * 转换普通和简单模式
		 */
		var feedHolder = $('feedHolder');
		var feedHome = $('feedHome');


		var currentFeedMode = /current/.test($('feedNormal').className) ? 'normal' : 'smode';

		var s2ntv = new XN.UI.tabView({selectedClass:'current'});
		s2ntv.addTab({
			label:'feedNormal',
			active:currentFeedMode == 'normal',
			onActive:function(){
				currentFeedMode = 'normal';
				feedHolder.delClass('s-mode');
				showMoreFeedMenu.hide();
				new XN.NET.xmlhttp('/setFeedStyle.do','s=0');
			}
		});

		s2ntv.addTab({
			label:'feedSmode',
			active:currentFeedMode == 'smode',
			onActive:function(){
				currentFeedMode = 'smode';
				feedHolder.addClass('s-mode');
				showMoreFeedMenu.hide();
				new XN.NET.xmlhttp('/setFeedStyle.do','s=1');
			}
		});

		/**
		 * feed filter
		 */
		var moreFeedType = '分享';
		var moreFeedIcon = 'url(http://app.xnimg.cn/application/20080812/19/44/L147120198188JIA.gif)';
		var currentFeedType = -1;
		var currentFeedName = '';
		var currentPage = 0;
		var feedsRequest = null;
		function loadFeeds(type){
			var flag = -1;
			currentPage = 0;
			currentFeedName = type;
			switch(type){
				case '日志' :
					flag = 6;
					break;
				case '相册':
					flag = 7;
					break;
				case '状态':
					flag = 5;
					break;
				case '活动':
					flag = 3;
					break;
				case '电影':
					flag = 15;
					break;
				case '群组':
					flag = 2;
					break;
				case '班级':
					flag = 16;
					break;
				case '分享':
					flag = 1;
					break;
			}
			currentFeedType = flag;
			getFeedRequest(flag);
		}

		var loadingDiv = $element('div');
		loadingDiv.className = 'filter-loading';
		loadingDiv.innerHTML = '<p>新鲜事读取中...</p>';
		loadingDiv.hide();
		feedHolder.appendChild(loadingDiv);

		function getFeedRequest(flag,page){
			try{
				feedsRequest.abort();
			}catch(e){}
			if(!page)feedHome.innerHTML = '';
			loadingDiv.show();
			$('moreFeed').hide();
			if(page)window.scrollTo(0,loadingDiv.realTop() + 100);
			feedsRequest = new XN.NET.xmlhttp({
				url:'http://home.xiaonei.com/feedretrieve.do',
				data:'f=' + flag + '&host=' + XN.COOKIE.get('hostid') + (page ? '&p=' + page : ''),
				onSuccess:function(r) {
					r = r.responseText.split("##@L#");
					
					if ( !r || isUndefined( r[1] ) ) {
						loadingDiv.hide();
						return;
					}
					
					if(page){
						feedHome.innerHTML += r[1];
						$('moreFeed').show();
					}else{
						if(r[0] == "0"){
							switch(flag){
								case -1 :
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">当你的朋友发表日志、照片或者帖子，这里会有提示。</p>";
									break;
								case 6 :
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有日志相关的新鲜事，去<a target=\"_blank\" href=\"http://blog.xiaonei.com/BlogHome.do\">日志</a>看看吧。</p>";
									break;
								case 7:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有相册相关的新鲜事，去<a target=\"_blank\" href=\"http://photo.xiaonei.com/friendsalbum.do?fb\">相册</a>看看吧。</p>";
									break;
								case 5:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有状态相关的新鲜事</p>";
									break;
								case 3:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有活动相关的新鲜事，去<a target=\"_blank\" href=\"http://event.xiaonei.com/EventHome.do\">活动</a>看看吧。</p>";
									break;
								case 15:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有电影相关的新鲜事，去<a target=\"_blank\" href=\"http://movie.xiaonei.com\">电影</a>看看吧。</p>";
									break;
								case 2:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有群组相关的新鲜事，去<a target=\"_blank\" href=\"http://group.xiaonei.com/tribenav.do\">群组</a>看看吧。</p>";
									break;
								case 16:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有班级相关的新鲜事，去<a target=\"_blank\" href=\"http://class.xiaonei.com/myclasses.do\">班级</a>看看吧。</p>";
									break;
								case 1:
									feedHome.innerHTML = "<p class=\"newsfeed-empty\">没有分享相关的新鲜事，去<a target=\"_blank\" href=\"http://share.xiaonei.com/share/ShareList.do\">分享</a>看看吧。</p>";
									break;
							}
						}else{
							feedHome.innerHTML = r[1];
							$('moreFeed').show();

                                                        
                            // 展开状态回复
                            
                            if ( currentFeedType == 5 || currentFeedType == -1 )
                            {
                                var js = $( 'feedHome' ).getElementsByTagName( 'script' );
                                var code = [];
                                if ( js.length > 0 )
                                {
                                    XN.ARRAY.each( js , function( i , v )
                                    {
                                        if ( v.getAttribute( 'status' ) )
                                        {
                                            code.push( v.innerHTML );
                                        }
                                    });
                                    
                                    //eval( code.join( '\n' ) );
                                    if ( XN.browser.IE )
                                    {
                                    if ( code.length > 5 )
                                    {
                                        var code1 = code.slice( 0 , 4 );
                                        XN.page.home.statusFeeds = code.slice( 4 );
                                        eval( code1.join( '\n' ) );
                                    }
                                    else
                                    {
                                        XN.page.home.statusFeeds = null;
                                        eval( code.join( '\n' ) );
                                    }
                                    }
                                    else
                                    {
                                        eval( code.join( '\n' ) );
                                    }
                                    //eval( 'setTimeout(function(){' + code.join( '},0);\nsetTimeout(function(){' ) + '},0);' );
                                        //eval( code.join( '\n' ) );
                                        /*var sc = $element( 'script' );
                                        try
                                        {
                                            sc.innerHTML = code.join( '\n' );
                                        }catch(e)
                                        {
                                            sc.text = code.join( '\n' );
                                        }
                                        document.body.appendChild( sc );
                                        */
                                }
                            }

						}
					}
					
					if ( r[3] == r[4] ) {
						$('moreFeed').hide();
					}
					
					$('feedEnd').innerHTML = '';
					$('feedEnd').appendChild(document.createTextNode(r[3]));
					if(page){
						if( r[4] >= parseInt($('feedUnReadCount').innerHTML) ){
							$('feedUnReadCount').innerHTML = '';
							$('feedUnReadCount').appendChild(document.createTextNode(r[4]));
						}
					}else{
							$('feedUnReadCount').innerHTML = '';
							$('feedUnReadCount').appendChild(document.createTextNode(r[4]));					
					}

					loadingDiv.hide();
					$('maxPage').value = r[0];
				},
				onError:function(r){
					XN.DO.showError('返回数据失败,请尝试重试');
				}
			});
		}

		function onShowOther(){
				$('switcher').hide();
				$('setAllFeedsAsRead').hide();
				feedHolder.addClass('filter-view');
				feedHolder.delClass('s-mode');
				showMoreFeedMenu.hide();
		}

		var fftv = new XN.UI.tabView({selectedClass:'current'});

		fftv.addTab({
			label:'showAllFeed',
			active:true,
			onActive:function(){
				loadFeeds('所有');
				$('switcher').show();
				$('setAllFeedsAsRead').show();
				showMoreFeedMenu.hide();
				feedHolder.delClass('filter-view');
				if(currentFeedMode == 'smode'){
					feedHolder.addClass('s-mode');
				}
			}
		});

		fftv.addTab({
			label:'showBlogFeed',
			onActive:function(){
				loadFeeds('日志');
				onShowOther();
			}
		});

		fftv.addTab({
			label:'showAlbumFeed',
			onActive:function(){
				loadFeeds('相册');
				onShowOther();
			}
		});

		fftv.addTab({
			label:'showStatusFeed',
			onActive:function(){
				loadFeeds('状态');
				onShowOther();
			}
		});

		fftv.addTab({
			label:'showMenuFeed',
			onActive:function(){
				loadFeeds(moreFeedType);
				onShowOther();
			}
		});
		
		if($('showMoreFeed')){
			var ox = -30;
			var oy = -5;
			var showMoreFeedMenu = new XN.UI.menu({
				bar:'showMoreFeed',
				menu:'moreFeedMenu',
				fireOn:'click',
				alignType:'2-1',
				offsetX:ox,
				offsetY:oy
			});
			if ( $('moreFeed_nff_active_marker') ){
				$('moreFeed_nff_active_marker').onclick = function(){
					showMoreFeedMenu.hide();
				};
			}
		}
		var otherFeedTabA = $('showMenuFeed').getElementsByTagName('a')[0];

		window.showFeed = function(el){
			var tmpIcon = el.style.backgroundImage;
			var tmpType = el.innerHTML;
			el.style.backgroundImage = moreFeedIcon;
			el.innerHTML = moreFeedType;
			var a = $('showMenuFeed').getElementsByTagName('a')[0];
			a.innerHTML = tmpType;
			moreFeedType = tmpType;
			moreFeedIcon = tmpIcon;

            //patch for new framework
		    try
            {
                fftv.refreshTab('showMenuFeed');
            }
            catch ( e )
            {
                fftv.setCurrentTab( 'showMenuFeed' , true );
            }
			XN.Effect.gradient(otherFeedTabA,255,255,150,function(el){
				el.style.backgroundColor = '';
			});
		};

		feedEditor.onreadNewsFeedSuccess = function(){
			var currentCount;
			if ( $( 'feedUnReadCount' ) ) {
				currentCount = parseInt($('feedUnReadCount').innerHTML);
				if(currentCount){
					$('feedUnReadCount').innerHTML = '';
					$('feedUnReadCount').appendChild(document.createTextNode(currentCount - 1));
				}
			}
			if ( $( 'feedEnd' ) ) {
				currentCount = parseInt( $( 'feedEnd' ).innerHTML );
				if( currentCount ) {
					$( 'feedEnd' ).innerHTML = '';
					$( 'feedEnd' ).appendChild( document.createTextNode( currentCount - 1 ) );
				}
			}
 			if($('feedHome').childNodes.length < 1){
				currentPage = 0;
				getMoreFeeds();
			}
		};

		function getMoreFeeds(){
			var maxPage = $('maxPage').value;
			var curpage = ++currentPage;
			if(curpage > maxPage){
				return ;
			}
			if(curpage == maxPage){
				feedHome.innerHTML += '<p class=\"newsfeed-empty-ajax\">没有更多' + (currentFeedName == '所有' ? '' :  currentFeedName) + '新鲜事了</p>';
				$('moreFeed').hide();
				return;
			}
			getFeedRequest(currentFeedType,curpage);
		}

		$('moreFeed').onclick = getMoreFeeds;
	},
	loadCommonFriends:function(uid,num){
		uid = uid || false;
		num = num || 7;
		var s = this;
        new XN.NET.xmlhttp(
        {
            url : this.loadCommonFriendUrl,
            data : 'count=' + num + '&type=1',
            method : 'get',
            onSuccess : function( r )
            {
                var o = XN.JSON.parse(r.responseText);
                s.commonFriends = o.friendCommonList;
                s.preloadHeadPhoto();
                s._isLoadingFriends = false;
                if(uid){
                    s.delCommonFriend(uid);
                }
            }
        });

	},
	delCommonFriend:function(uid){
		var s = this;
		if(this.commonFriends.length < 1){
			if(this._isLoadingFriends)return;
			this.loadCommonFriends(uid);
			return;
		}
		new XN.NET.xmlhttp(this.delCommonFrendUrl,'bid=' + uid);
		var cf = $('commonFriend_' + uid);
		var rf = this.newCommonFriend(this.commonFriends.shift());
		if(XN.BROWSER.IE6){
			cf.parentNode.replaceChild(rf,cf);
			return;
		}
		XN.Effect.fadeOut(cf,function(el){
			el.parentNode.replaceChild(rf,el);
			XN.Effect.fadeIn(rf);
			s.preloadHeadPhoto();
		});
	},
	preloadHeadPhoto:function(){
		XN.DOM.preloadImg(this.commonFriends[0].tinyFullUrl);
	},
	initPeopleMayKnow:function(){
		if(!window.currentUser){
			window.currentUser = new XN.USER.me();
		}
		currentUser.onaddFriendSuccess = function(uid,msg){
			delCommonFriend(uid);
		};
		window.delCommonFriend = function(uid){
			XN.PAGE.home.delCommonFriend(uid);
		};
		window.addCommonFriend = function(uid,name,head){
			currentUser.addFriend({id:uid,name:name,head_url:head});
		};
//		this.loadCommonFriends(false,3);
	},
	newCommonFriend:function(o){
		var li = $element('li');
		li.id = 'commonFriend_' + o.id;
		li.innerHTML = '<span class="headpichold"><a href="http://xiaonei.com/profile.do?id=' + o.id + '" title="查看' + o.name + '的个人主页" style="background-image:url(' + o.tinyFullUrl + ');"></a></span>\n' +
        '<span><a class="addfriend_action" onclick="showRequestFriendDialog(\'' + o.id + '\',\'' + o.name + '\',\'' + o.head_url +　'\')" href="javascript:void(0);"><img src="http://xnimg.cn/imgpro/icons/addfriendicons.gif"/></a>' +
		'<a class="name" href="http://xiaonei.com/profile.do?id=' + o.id + '">' + o.name + '</a></span>';
		return li;
	},
	initAppShortcut:function(){
		var bar = $('showMoreAppShortcuts');
		var menu = $('moreAppShortcuts');
		var hideBar = $('hideMoreAppShortcuts');
		var appUls = menu.getElementsByTagName('ul').length;
		var subMenu = new XN.UI.menu({
			bar:bar,
			menu:menu,
			fireOn:'click',
			alignType:'1-1',
			offsetY:-4
		});
		subMenu.setWidth(135 + (appUls - 2) * 144);
		bar.hover('rounded-hover','appNavHolder');
		hideBar.hover('rounded-hover','moreAppShortcuts');
		hideBar.onclick = function(e) {
			subMenu.hide();
		}
	},
//	initStatus:function(){
//		var statusEditor = $('publisherModule') ? XN.WIDGETS.publisher.statusEditor : new XN.APP.status();
//		statusEditor.beforeShowEditor = function(){
//			XN.Element.hide('statusContainer');
//		};
//		statusEditor.beforeHideEditor = function(){
//			XN.Element.show('statusContainer');
//		};
//	},
	init:function(){
		this.initFeed();
		if($('showMoreAppShortcuts')){this.initAppShortcut();}
		this.initPeopleMayKnow();
	}
};
XN.DOM.readyDo(function(){
	if($('home')){
		XN.PAGE.home.init();
		//getReplyList();
	}

    XN.event.addEvent( window , 'scroll' , function( e )
    {
        if ( XN.PAGE.home.statusFeeds )
        {
            var str = XN.PAGE.home.statusFeeds.shift();
            eval( str );
        }
    });
});
/**
 * 新留言及回复提示 from /js/dispatch.js
 */
function today(dt) {
	var result = dt;

	var nnn = new Date();
	var nmm = nnn.getMonth() + 1;
	var ndd = nnn.getDate();
	if(nmm < 10) nmm = "0" + nmm;
	if(ndd < 10) ndd = "0" + ndd;

	var dmm = dt.substring(0, 2);
	var ddd = dt.substring(3, 5);

	if(nmm == dmm && ndd == ddd) {
		result = dt.substring(6);
	} else {
		result = dt.substring(0, 6);
	}
	return result;
}


var alldata;
var REPLY = 1, FANGLE=2, ALL=0, GROUP=3, SCHOOLCLASS=6;
var NUMBER_PER_PAGE = 30;
var showtype = 0, bAtHome = false, gCurPage;
var myDate = new Date();
var feedUserId = 0;
var fdCount = 0;
domainImg = "img.xiaonei.com";
domainStatic = "xnimg.cn";
domainGroup = "";
myDomain = "";
var CampusInfo = new Object();
CampusInfo.NewsIsEmpty = 0;
//新留言以及回复提示
CampusInfo.buildNewRepInfo=function(type){
try{
	var gossipReplyCount;
	var total;
	if ((!alldatanew.replys) && (!alldatanew.fangles)){

		if(showtype==0) {
	 		gossipReplyCount= alldatanew.count.replyc;
		} else {
	 		gossipReplyCount= alldatanew.count;
		}
		if(gossipReplyCount == 0) {
			CampusInfo.NewsIsEmpty++;
			if($("gossipReplyFeed"))
			{
			  $('opilist').innerHTML = "当有人回复了你发表的内容或者给你留言，这里会有提示" ;
			  $('opilist').style.display="";
			}
			if(bAtHome && (alldatanew.count.replytc > 0) ) {
				//更多
				if($("replyMoreDiv")) $("replyMoreDiv").innerHTML = '<a href="/myreplylist.do">更多</a>' ;
			}
			if($('replyDiv')) $('replyDiv').style.display="none";
			return;
 		}else{
 			if($('replyDiv')&&$('replyDiv').style.display=="none") $('replyDiv').style.display="";
 		}
	}
	var data;
	if(alldatanew.replys) {
		data = alldatanew.replys;
	}else {
		data = alldatanew.fangles;
	}
	var pages = 0;
	//显示数量

	var rep = "";

	for(var i = 0 ;i<data.length;i++){
		rep +="<div class=\"feed\"><div class=\"feed-icon\"><img src=\"http://xnimg.cn/imgpro/icons/wall_post.gif\"/></div>"
					+"<div class=\"feed-content\"><div class=\"headline\"><div class=\"title\"><span class=\"share-n-hide\">"
					+"<a class=\"x-to-hide\" href=\"javascript:void(0)\" onclick=\"CampusInfo.readOneNew("+type+","+data[i].recordNo+","+data[i].type+","+data[i].sourceNo+",'"+data[i].link+"',"+type+","+type+","+data[i].unread+" )\">&nbsp</a></span>";
		if (data[i].type==48){
			rep +="<h4><a href='"+data[i].link+"' target=\"_blank\" onclick=\"CampusInfo.readOneNew("+type+","+data[i].recordNo+","+data[i].type+","+data[i].sourceNo+",'"+data[i].link+"',"+type+","+type+","+data[i].unread+" )\">"+CampusUtils.resizeTitle(data[i].title)+"</a> "+data[i].sufix+" <span class='date'>"+today(data[i].createTime)+"</span></h4>";
		}else if (data[i].type==65540){
				if(data[i].fromUserName=="一个TA")
					rep +="<h4>"+CampusUtils.resizeName(data[i].fromUserName)+data[i].prefix+" <a href='"+data[i].link+"' target=\"_blank\" onclick=\"CampusInfo.readOneNew("+type+","+data[i].recordNo+","+data[i].type+","+data[i].sourceNo+",'"+data[i].link+"',"+type+","+type+","+data[i].unread+" )\">"+CampusUtils.resizeTitle(data[i].title)+"</a> "+data[i].sufix+" <span class='date'>"+today(data[i].createTime)+"</span></h4>";
		}else if (data[i].type==61){
					rep +="<h4>神秘的TA"+data[i].prefix+" <a href='"+data[i].link+"' target=\"_blank\" onclick=\"CampusInfo.readOneNew("+type+","+data[i].recordNo+","+data[i].type+","+data[i].sourceNo+",'"+data[i].link+"',"+type+","+type+","+data[i].unread+" )\">"+CampusUtils.resizeTitle(data[i].title)+"</a> "+data[i].sufix+" <span class='date'>"+today(data[i].createTime)+"</span></h4>";		
		}else{
			rep +="<h4><a href='http://xiaonei.com/profile.do?id="+data[i].fromUserNo+"' target='_blank'>"+CampusUtils.resizeName(data[i].fromUserName)+"</a> "+data[i].prefix+
			" <a href=\""+data[i].link+"\" target='_blank' onclick=\"CampusInfo.readOneNew("+type+","+data[i].recordNo+","+data[i].type+","+data[i].sourceNo+",'"+data[i].link+"',"+type+","+type+","+data[i].unread+" )\">"+CampusUtils.resizeTitle(data[i].title)+"</a> "+data[i].sufix+" <span class=\"date\">"+today(data[i].createTime)+"</span></h4>";
		}
		rep +="</div></div></div></div>";
	}

	if(showtype==0) {
 		gossipReplyCount= alldatanew.count.replyc;
	} else {
 		gossipReplyCount= alldatanew.count;
	}

	if($("gossipReplyCount"))
	    $("gossipReplyCount").innerHTML = "(" + gossipReplyCount + ")";

	if(bAtHome) {
		total = alldatanew.count.replytc;
	}
	if(bAtHome && (total > 0) ) {
		//更多
		if($("replyMoreDiv")) $("replyMoreDiv").innerHTML = '<a href="/myreplylist.do">更多</a>' ;
	}

 	if((!bAtHome) && gossipReplyCount > NUMBER_PER_PAGE) {
		rep +='<div id="pNav">';
		if(alldatanew.page>0) {
 			rep +='<a href="javascript:fnFeed('+showtype+',' + (alldatanew.page - 1) + ',0)">上一页</a>';
		}else if (bAtHome==0){
			rep += '<span  style="color: #999999; text-decoration:none">上一页</span>';
		}
 		if(alldatanew.page < ((gossipReplyCount- (gossipReplyCount % NUMBER_PER_PAGE) )/NUMBER_PER_PAGE)){
			if(alldatanew.page>0) rep +="<span class='separator'> </span>";
			rep += '<a href="javascript:fnFeed('+showtype+',' + (alldatanew.page + 1) + ',0)">下一页</a>';
		}else if (bAtHome==0){
			if(alldatanew.page>0) rep +="<span class='separator'> </span>";
			rep += '<span style="color:#999999;text-decoration:none">下一页</span>';
		}

		rep +='</div>';
	}
	if($('replyDiv')) $('replyDiv').style.display="";
  if($("opilist"))
	  $("opilist").innerHTML = rep;
	}catch(E){
	    $('opilist').innerHTML = "当有人回复了你发表的内容或者给你留言，这里会有提示" ;
			if($('replyDiv'))
				$('replyDiv').style.display="";
			if($('opilist'))
				$('opilist').style.display="";
  }
}

var CampusUtils = new Object();
CampusUtils.iconRep=[    {atype:1,ln:"http://"+domainStatic+"/img/dispatch/icon04.gif"},
	                     {atype:2,ln:"http://"+domainStatic+"/img/dispatch/icon05.gif"},
	                     {atype:4,ln:"http://"+domainStatic+"/img/dispatch/icon05.gif"},
	                     {atype:8,ln:"http://"+domainStatic+"/img/dispatch/icon02.gif"},
	                     {atype:16,ln:"http://"+domainStatic+"/img/dispatch/icon06.gif"},
	                     {atype:32,ln:"http://"+domainStatic+"/img/dispatch/icon04.gif"},
	                     {atype:64,ln:"http://"+domainStatic+"/img/dispatch/icon05.gif"},
	                     {atype:128,ln:"http://"+domainStatic+"/img/dispatch/icon05.gif"},
	                     {atype:256,ln:"http://"+domainStatic+"/img/dispatch/icon02.gif"},
	                     {atype:512,ln:"http://"+domainStatic+"/img/dispatch/icon06.gif"}];
CampusUtils.resizeName = function (name, limit){
	return CampusUtils.substring(name,3,null);
}
CampusUtils.resizeTitle = function (title, limit){

   try{
    if(title==null||title=="")
        //return "无标题" ;
        return "去看看" ;
    return CampusUtils.substringForTitle(title,16,null) ;
   }catch(e){
   	//return "无标题" ;
   	return "去看看" ;
   }
}
CampusUtils.resizeTitle2 = function (title){
    if(title==null)
        return "" ;
    return CampusUtils.substring(title,15,null);
}
CampusUtils.substring = function(str, len, postfix) {
	var res = "";
	var p = 0;
	for (var i=0; i<str.length; i++) {
		var ch = str.charAt(i);
		if (ch > '!' && ch < '~') {
			p += 1;
		} else {
			p += 2;
		}
		if (p <= len*2) {
			res += ch;
		}
	}
	if (p > len*2) {
		if (postfix != null)
			res += postfix;
		else
			res += "...";
	}
	return res;
}
CampusUtils.substringForTitle = function(str, len, postfix) {
	var res = "";
	var p = 0;
	var pattern =/<img.*?\/>/ig;
	var result = str.match(pattern);
	var replacedText = str.replace(pattern,'\t');
	for (var i=0; i<replacedText.length; i++) {
		var ch = replacedText.charAt(i);
		if (ch > '!' && ch < '~') {
			p += 1;
		} else {
			p += 2;
		}
		if (p <= len*2) {
			res += ch;
		}
	}
	if (p > len*2) {
		if (postfix != null)
			res += postfix;
		else
			res += "...";
	}
	for (var i=0; result!=null && i<result.length; i++) {
		res = res.replace(/\t/,result[i]);
	}
	return res;
}
CampusUtils.shortString = function (str){
	document.write(str.substring(0,5));
}
function showResponseHomeReply(r) {
	eval("alldatanew="+r.responseText);
	CampusInfo.buildNewRepInfo(1);
}
function getReplyList(){
	showtype = 1;
	bAtHome = 2;
	gCurPage = 0;
 	//if(getEl("fangle_curpage_hid")) {getEl("fangle_curpage_hid").value=0;}
	//if(getEl("fangle_first_hid")) {getEl("fangle_first_hid").value=2;}
	var url = '/dispatchview.do';
	var pars = "type=1&curpage=0&isfirst=2&t="+myDate.getTime();
	var myAjax = new Ajax.Request(
				url,
				{
							method: 'post',
							parameters: pars,
							onComplete: showResponseHomeReply,
							onFailure: showError
				});
}
function showError(t) {
	var errmsg = t.status + ' -- ' + t.statusText;
	alert("抱歉，出错了；错误信息: " + errmsg + " 麻烦点页面底部的 “给管理员留言”报告错误");
}
CampusInfo.readOneNew = function(type,no,atttype,sourceno,link,orgtype,unread){
	if (type==1){
		isreadone1 = 'read';
	}
	if(unread){
		var cur, first;
		if($("fangle_curpage_hid")) {
			cur = $("fangle_curpage_hid").value ;
		} else {
			cur = 0;
		}
		if($("fangle_first_hid")) {
		 	first = 0 ;
		} else {
			first = 1;
		}
		var url="/dispatchreadone.do";
		var pars="type="+type+"&cur="+cur+"&isfrist="+first+"&dispatchNo="+no+"&atttype="+atttype+"&sourceno="+sourceno+"&ran="+Math.random() ;

		var myAjax = new Ajax.Request(
					url,
					{
								method: 'get',
								parameters: pars,
								onComplete: getReplyList,
								onFailure: showError
					});
	}
}
