var click = device.mobile() ? 'touchstart' : 'click';
$(function() {
	// 加载用户信息
    getSysUserInfo();

	// 初始信息加载
	var username = window.parent.$.cookie("_username");
	if(username){
		$("#user_name").text(username);
	}

});

// 获取用户信息
function getSysUserInfo() {
	// ajax请求
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/index',
        type: 'POST',
        dataType: 'json',
        data: {},
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if(code && '000' == code){
				var result = res.result;
				var toplist = result.top;
				var leftlist = result.left;
                sysMenuBuild(toplist,leftlist);
                // 样式初始化
                cssInit();
                // 菜单动态效果初始化
                menuEventInit();
            }else if('003' == code){
				$.ssm_utils.timeoutAction();
			}else{
                layer.msg(msg);
			}
        },
        error: function(error){
            console.log(error);
        }
    });
}

/**
 * 构建系统菜单
 * @param toplist
 * @param leftlist
 */
function sysMenuBuild(toplist,leftlist) {
	// 构建一级菜单树
    $('#top_menu').empty();
    var menu_str = '<li class="skin-switch">请选择模块切换</li><li class="divider"></li>';
    var li_str = '';
	if(toplist && toplist.length){
		for(var i=0,len=toplist.length;i<len;i++){
			var menu = toplist[i];
            li_str += '<li>' +
                '<a class="waves-effect switch-systems" systemid="'+menu.menuId
				+'" systemtitle="'+menu.menuName+'"><i class="opt-icon mr8">&#x'+menu.menuIcon+'</i>'+menu.menuName+'</a>' +
                '</li>';
		}
        var firstMenu = toplist[0];
        // 保存cookie[ssm-systemid, ssm-systemtitle]
        window.parent.$.cookie("ssm-systemid",firstMenu.menuId);
        window.parent.$.cookie("ssm-systemtitle",firstMenu.menuName);
	}
    $('#top_menu').append(menu_str+li_str);

	// 构建左侧菜单树
	$('#left_menu').empty();
	var left_str = '<li><a class="waves-effect" href="javascript:Tab.addTab(\'首页\', \'home\');"><i class="opt-icon">&#xf015</i>首页</a></li>';
	var left_menus = '';
	var left_end = '<li><div class="upms-version">&copy; SSM-DEMO V0.0.1</div></li>';
	if(leftlist && leftlist.length){
        // 重构左侧菜单数据
        var menuDatas = buildNodeDatas(leftlist);
        sortByMenuSeq(menuDatas);
        // 构建左侧菜单树
        var show_index = toplist[0].menuId;// 显示菜单父菜单ID
        left_menus = buildLeftMenuTreeStr(show_index, menuDatas);
	}
    $('#left_menu').append(left_str+left_menus+left_end);
}

/**
 * 构造左侧菜单树字符串
 * @param show_index 显示菜单父菜单ID
 * @param menuDatas 左侧菜单树数据
 */
function buildLeftMenuTreeStr(show_index,menuDatas) {
	var str = '';
	for(var i=0,ilen=menuDatas.length;i<ilen;i++){
		var menu = menuDatas[i];
		str += '<li class="sub-menu system_menus system_'+menu.supMenuId+'" '+buildLeftMenuUtil_1(show_index,menu)+'>'
			+ '<a class="waves-effect" href="javascript:;"><i class="opt-icon">&#x'+menu.menuIcon+'</i>'+menu.menuName+'</a>';
		if(menu.children && menu.children.length){
            str += '<ul>';
            for(var j=0,jlen=menu.children.length;j<jlen;j++){
            	var cmenu = menu.children[j];
                str += '<li><a class="waves-effect" href="javascript:Tab.addTab(\''+cmenu.menuName+'\', \''+cmenu.menuUrl+'\');">'+cmenu.menuName+'</a></li>';
			}
            str += '</ul>';
		}
        str += '</li>';
	}
	return str;
}

/**
 * 构造左侧菜单树工具方法1
 * @param show_index
 * @param menu
 * @returns {string}
 */
function buildLeftMenuUtil_1(show_index,menu) {
	var str = '';
	if(show_index != menu.supMenuId){
        str = 'hidden';
	}
	return str;
}

/**
 * 构造菜单节点数据
 * @param treelist
 * @returns {Array}
 */
function buildNodeDatas(treelist) {
    var newTrees = new Array();
    for (var i =0,len=treelist.length ;i<len;i++) {
        var menu = treelist[i];
        if (menu.menuLevel == 2) {// 二级菜单处理
            var obj = {};
            obj["menuId"] = menu.menuId;
            obj["menuName"] = menu.menuName;
            obj["supMenuId"] = menu.supMenuId;
            obj["menuUrl"] = menu.menuUrl;
            obj["menuSeq"] = menu.menuSeq;
            obj["menuIcon"] = menu.menuIcon;
            obj["children"] = getChildrenNode(menu.menuId, treelist);
            newTrees.push(obj);
        }
    }
    return newTrees;
}

/**
 * 构造菜单子节点数据
 * @param pId
 * @param treesList
 * @returns {Array}
 */
function getChildrenNode(pId, treesList) {
    var newTrees = new Array();
    for (var i =0,len=treesList.length ;i<len;i++) {
        var cmenu = treesList[i];
        if (cmenu.supMenuId == null) continue;
        if (cmenu.supMenuId == pId) {
            var cObj = {};
            cObj["menuId"] = cmenu.menuId;
            cObj["menuName"] = cmenu.menuName;
            cObj["supMenuId"] = menu.supMenuId;
            cObj["menuUrl"] = cmenu.menuUrl;
            cObj["menuSeq"] = cmenu.menuSeq;
            cObj["menuIcon"] = menu.menuIcon;
            cObj["children"] = getChildrenNode(cmenu.menuId, treesList);
            newTrees.push(cObj);
        }
    }
    return newTrees;
}

/**
 * 排序
 * @param menus
 */
function sortByMenuSeq(menus) {
    if (menus.length) {
        menus.sort(function (a, b) {
            return a.menuSeq - b.menuSeq
        })
        for (var i = 0,len=menus.length ; i < len; i++) {
            if (menus[i].children && menus[i].children.length) {
                sortByMenuSeq(menus[i].children)
            }
        }
    }
}


// 动态效果初始化
function cssInit() {
    // 侧边栏操作按钮
    $(document).on(click, '#guide', function() {
        $(this).toggleClass('toggled');
        $('#sidebar').toggleClass('toggled');
    });
    // 侧边栏二级菜单
    $(document).on('click', '.sub-menu a', function() {
        $(this).next().slideToggle(200);
        $(this).parent().toggleClass('toggled');
    });
    // 个人资料
    $(document).on('click', '.s-profile a', function() {
        $(this).next().slideToggle(200);
        $(this).parent().toggleClass('toggled');
    });
    // Waves初始化
    Waves.displayEffect();
    // 滚动条初始化
    $('#sidebar').mCustomScrollbar({
        theme: 'minimal-dark',
        scrollInertia: 100,
        axis: 'yx',
        mouseWheel: {
            enable: true,
            axis: 'y',
            preventDefault: true
        }
    });
}

// 菜单动态效果初始化
function menuEventInit() {
    // 切换系统
    $('.switch-systems').click(function () {
        var systemid = $(this).attr('systemid');
        // var systemname = $(this).attr('systemname');
        var systemtitle = $(this).attr('systemtitle');
        $('.system_menus').hide(0, function () {
            $('.system_' + systemid).show();
        });
        // $('body').attr("id", systemname);
        $('#system_title').text(systemtitle);
        window.parent.$.cookie('ssm-systemid', systemid);
        // $.cookie('ssm-systemname', systemname);
        window.parent.$.cookie('ssm-systemtitle', systemtitle);
    });
    // 显示cookie菜单
    var systemid = window.parent.$.cookie('ssm-systemid') || 1;
    // var systemname = $.cookie('ssm-systemname') || 'base-module';
    var systemtitle = window.parent.$.cookie('ssm-systemtitle') || '系统管理';
    $('.system_menus').hide(0, function () {
        $('.system_' + systemid).show();
    });
    // $('body').attr('id', systemname);
    $('#system_title').text(systemtitle);
}



// iframe高度自适应
function changeFrameHeight(ifm) {
	ifm.height = document.documentElement.clientHeight - 118;
}
function resizeFrameHeight() {
	$('.tab_iframe').css('height', document.documentElement.clientHeight - 118);
	$('md-tab-content').css('left', '0');
}
window.onresize = function() {
	resizeFrameHeight();
	initScrollShow();
	initScrollState();
}

// ========== 选项卡操作 ==========
$(function() {
	// 选项卡点击
	$(document).on('click', '.content_tab li', function() {
		// 切换选项卡
		$('.content_tab li').removeClass('cur');
		$(this).addClass('cur');
		// 切换iframe
		$('.iframe').removeClass('cur');
		$('#iframe_' + $(this).data('index')).addClass('cur');
		var marginLeft = ($('#tabs').css('marginLeft').replace('px', ''));
		// 滚动到可视区域:在左侧
		if ($(this).position().left < marginLeft) {
			var left = $('.content_tab>ul').scrollLeft() + $(this).position().left - marginLeft;
			$('.content_tab>ul').animate({scrollLeft: left}, 200, function() {
				initScrollState();
			});
		}
		// 滚动到可视区域:在右侧
		if(($(this).position().left + $(this).width() - marginLeft) > document.getElementById('tabs').clientWidth) {
			var left = $('.content_tab>ul').scrollLeft() + (($(this).position().left + $(this).width() - marginLeft) - document.getElementById('tabs').clientWidth);
			$('.content_tab>ul').animate({scrollLeft: left}, 200, function() {
				initScrollState();
			});
		}
	});
	// 控制选项卡滚动位置 
	$(document).on('click', '.tab_left>a', function() {
		$('.content_tab>ul').animate({scrollLeft: $('.content_tab>ul').scrollLeft() - 300}, 200, function() {
			initScrollState();
		});
	});
	// 向右箭头
	$(document).on('click', '.tab_right>a', function() {
		$('.content_tab>ul').animate({scrollLeft: $('.content_tab>ul').scrollLeft() + 300}, 200, function() {
			initScrollState();
		});
	});
	// 初始化箭头状态
	
	// 选项卡右键菜单
	var menu = new BootstrapMenu('.tabs li', {
		fetchElementData: function(item) {
			return item;
		},
		actionsGroups: [
			['close', 'refresh'],
			['closeOther', 'closeAll'],
			['closeRight', 'closeLeft']
		],
		actions: {
			close: {
				name: '关闭',
				iconClass: 'zmdi zmdi-close',
				onClick: function(item) {
					Tab.closeTab($(item));
				}
			},
			closeOther: {
				name: '关闭其他',
				iconClass: 'zmdi zmdi-arrow-split',
				onClick: function(item) {
					var index = $(item).data('index');
					$('.content_tab li').each(function() {
						if ($(this).data('index') != index) {
							Tab.closeTab($(this));
						}
					});
				}
			},
			closeAll: {
				name: '关闭全部',
				iconClass: 'zmdi zmdi-swap',
				onClick: function() {
					$('.content_tab li').each(function() {
						Tab.closeTab($(this));
					});
				}
			},
			closeRight: {
				name: '关闭右侧所有',
				iconClass: 'zmdi zmdi-arrow-right',
				onClick: function(item) {
					var index = $(item).data('index');
					$($('.content_tab li').toArray().reverse()).each(function() {
						if ($(this).data('index') != index) {
							Tab.closeTab($(this));
						} else {
							return false;
						}
					});
				}
			},
			closeLeft: {
				name: '关闭左侧所有',
				iconClass: 'zmdi zmdi-arrow-left',
				onClick: function(item) {
					var index = $(item).data('index');
					$('.content_tab li').each(function() {
						if ($(this).data('index') != index) {
							Tab.closeTab($(this));
						} else {
							return false;
						}
					});
				}
			},
			refresh: {
				name: '刷新',
				iconClass: 'zmdi zmdi-refresh',
				onClick: function(item) {
					var index = $(item).data('index');
					var $iframe = $('#iframe_' + index).find('iframe');
					$iframe.attr('src', $iframe.attr('src'));
				}
			}
		}
	});
});
// 选项卡对象
var Tab = {
	addTab: function(title, url) {
		var index = url.replace(/\./g, '_').replace(/\//g, '_').replace(/:/g, '_').replace(/\?/g, '_').replace(/,/g, '_').replace(/=/g, '_').replace(/&/g, '_');
		// 如果存在选项卡，则激活，否则创建新选项卡
		if ($('#tab_' + index).length == 0) {
			// 添加选项卡
			$('.content_tab li').removeClass('cur');
			var tab = '<li id="tab_' + index +'" data-index="' + index + '" class="cur"><a class="waves-effect waves-light">' + title + '</a></li>';//<i class="zmdi zmdi-close"></i><
			$('.content_tab>ul').append(tab);
			// 添加iframe
			$('.iframe').removeClass('cur');
			var iframe = '<div id="iframe_' + index + '" class="iframe cur"><iframe class="tab_iframe" src="' + url + '" width="100%" frameborder="0" scrolling="auto" onload="changeFrameHeight(this)"></iframe></div>';
			$('.content_main').append(iframe);
			initScrollShow();
			$('.content_tab>ul').animate({scrollLeft: document.getElementById('tabs').scrollWidth - document.getElementById('tabs').clientWidth}, 200, function() {
				initScrollState();
			});
		} else {
			$('#tab_' + index).trigger('click');
		}
		// 关闭侧边栏
		$('#guide').trigger(click);
	},
	closeTab: function($item) {
		var closeable = $item.data('closeable');
		if (closeable != false) {
			// 如果当前时激活状态则关闭后激活左边选项卡
			if($item.hasClass('cur')) {
				$item.prev().trigger('click');
			}
			// 关闭当前选项卡
			var index = $item.data('index');
			$('#iframe_' + index).remove();
			$item.remove();
		}
		initScrollShow();
	}
}
function initScrollShow() {
	if (document.getElementById('tabs').scrollWidth > document.getElementById('tabs').clientWidth) {
		$('.content_tab').addClass('scroll');
	} else {
		$('.content_tab').removeClass('scroll');
	}
}
function initScrollState() {
	if ($('.content_tab>ul').scrollLeft() == 0) {
		$('.tab_left>a').removeClass('active');
	} else {
		$('.tab_left>a').addClass('active');
	}
	if (($('.content_tab>ul').scrollLeft() + document.getElementById('tabs').clientWidth) >= document.getElementById('tabs').scrollWidth) {
		$('.tab_right>a').removeClass('active');
	} else {
		$('.tab_right>a').addClass('active');
	}
}

function fullPage() {

	if ($.util.supportsFullScreen) {
		if ($.util.isFullScreen()) {
			$.util.cancelFullScreen();
		} else {
			$.util.requestFullScreen();
		}
	} else {
		alert("当前浏览器不支持全屏 API，请更换至最新的 Chrome/Firefox/Safari 浏览器或通过 F11 快捷键进行操作。");
	}
}

// 退出登录
function sys_exit(){
	// 清除session
    $.ajax({
        url: $.ssm_utils.getRootURL() + '/ssm/userinfo/logout',
        type: 'POST',
        dataType: 'json',
        data: {},
        beforeSend: function() {
        },
        success: function(res){
            var code = res.code;
            var msg = res.msg;
            if('002'==code){
            	console.log(msg);
			}
        },
        error: function(error){
            console.log(error);
        }
    });
    // 清楚cookie
    window.parent.$.cookie('ssm-systemid','');
    window.parent.$.cookie('ssm-systemtitle','');
    window.parent.$.cookie("_userid",'');
    window.parent.$.cookie('_username','');
    window.parent.$.cookie('_distcode','');
    window.parent.$.cookie('JSESSIONID','');
    window.parent.location.href = 'login.html';
}