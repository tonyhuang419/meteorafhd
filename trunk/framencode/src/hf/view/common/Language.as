package hf.view.common
{
    import hf.model.*;

    public class Language extends Object
    {
        private static var _currentLang:String = "";
        public static const EN:String = "en";
        public static const CN:String = "cn";
        private static var _l:Object = {cn:{warehouseTitle:"仓库", warehouseDirection:"卖出仓库的果实就可以获得金币。", toolTileDirection:"购买化肥，让作物生长时间缩短，提前收获，防止被偷窃。", diyTileDirection:"购买装饰，打造你的个性农场，更可获得经验奖励，更快升级。", shopTitle:"商店", dialTitle:"开心大转盘", gainTitle:"开心大转盘", allSell:"全部卖出", shopTagSell:"种子", shopTagFertilizer:"道具", shopTagDecorate:"装饰", sumGold:"当前果实总价值：", shopSeedWindow:"购买种子", shopToolWindow:"购买道具", sellCropWindow:"卖出果实", alertTitle:"提示", 登录过期，:"登录过期，", 点击重新登录:"点击重新登录", 初始化错误，:"初始化错误，", 点击重试:"点击重试", 请求超时，稍后再试:"请求超时，稍后再试", 请求超时，:"请求超时，", buySeedFnText:"购买成功，共花费金币<font color=\'#FF6600\'>{money}</font>，购买了{num}个{cName}种子。", 购买装饰成功:"购买装饰成功", 设置装饰失败:"设置装饰失败", saleAllFnText:"成功卖出仓库里所有果实，得到金币{money}。", 不可以对空地进行这个操作啦！:"不可以对空地进行这个操作啦！", 这块地不需要浇水啦！:"这块地不需要浇水啦！", 这块地不需要除草啦！:"这块地不需要除草啦！", 这块地不需要除虫啦！:"这块地不需要除虫啦！", 这块地无法种草啦！:"这块地无法种草啦！", 作物这个阶段不会长草！:"作物这个阶段不会长草！", 这块地无法放虫啦！:"这块地无法放虫啦！", 作物这个阶段不会生虫！:"作物这个阶段不会生虫！", 这块地没东西可收获！:"这块地没东西可收获！", 这块地没东西可偷！:"这块地没东西可偷！", 狗盯上了你，别做坏事了。:"这块地不能再偷了！", 做人不能贪得无厌！:"做人不能贪得无厌！", 行行好吧，我所剩无几了！:"行行好吧，我所剩无几了！", 铲除作物:"铲除作物", 作物还没有收获，真的要铲除吗？:"作物还没有收获，真的要铲除吗？", 不可以种在这里哦！:"不可以种在这里哦！", 作物这个阶段不需要施肥:"作物这个阶段不需要施肥", 施过肥了，下个阶段再施吧:"施过肥了，下个阶段再施吧", 按钮:"按钮", 确定:"确定", 取消:"取消", 狗狗粮食:"狗狗粮食", 剩余狗粮还可以给狗狗吃:"剩余狗粮还可以给狗狗吃", 小时:"小时", 补充狗粮需要:" 补充狗粮需要", 狗粮充足，不需要补充狗粮了。:"狗粮充足，不需要补充狗粮了。", 状态(可怕):"状态(可怕) ", 状态(糟糕):"状态(糟糕) ", 状态(欠佳):"状态(欠佳) ", 状态(良好):"状态(良好) ", 状态(优秀):"状态(优秀) ", 对不起，你的金币和等级均不足。:"对不起，你的金币和等级均不足。", 对不起，你的等级不足。:"对不起，你的等级不足。", 对不起，你的金币不足。:"对不起，你的金币不足。", 对不起，你的F币不足。:"对不起，你的F币不足。", 扩建农场:"扩建农场", reclaimPayText:"扩建这块土地需要等级<font color=\'#0099ff\'> {level} </font>级和<br> 金币 <font color=\'#FF6600\'>{money}</font>。", 包裹里什么都没有，去商店里逛逛吧！:"包裹里什么都没有，去商店里逛逛吧！", 没有对好友可以使用的道具！:"               没有对好友可以使用的道具！", 去商店:"去商店", cropSeedName:"{cName}种子", 可拖动屏幕:"可拖动屏幕", 已经购买的物品:"已经购买的物品", 在地里放杂草:"在地里放杂草", 在地里放害虫:"在地里放害虫", 清除地里的杂草:"清除地里的杂草", 可用来翻地:"可用来翻地", 清除地里的害虫:"清除地里的害虫", 用来收获果实:"用来收获果实", 偷窃好友的果实:"偷窃好友的果实", 用来浇水:"用来浇水", 刷新:"刷新", 上一页:"上一页", 下一页:"下一页", 查找:"查找", 当前经验::"当前经验:", 升级还需经验::"升级还需经验:", 我的农场:"我的农场", 仓库（收获的果子会在这里）:"仓库（收获的果子会在这里）", 开心商店（来这里买种子）:"开心商店（来这里买种子）", 装饰农场:"装饰农场", 晴天，有可能长虫、长草或干旱。:"晴天，有可能长虫、长草或干旱。", 雨天，土地不会干旱。:"雨天，土地不会干旱。", 夜晚，你可以好好休息哦。:"夜晚，你可以好好休息哦。", 给TA留言:"给TA留言", 礼包:"礼包", 消息:"消息", 留言:"留言", 公告:"公告", 数据初始化中...:"数据初始化中...", 等级和经验:"等级和经验", 接受:"接受", 欢迎:"欢迎", 到期时间：无限期:"到期时间：无限期", 到期时间：:"到期时间：", 农场装饰:"农场装饰", 背景:"背景", 房子:"房子", 狗窝:"狗窝", 栅栏:"栅栏", 已装饰:"已装饰", 装饰续费:"装饰续费", 续费:"续费", outtime:"你的装饰物还剩{day}天到期", 原价：:"原价：", 折后价：:"折后价：", 立即节省：:"立即节省：", 可获得经验：:"可获得经验：", 今天:"今天 ", 昨天:"昨天 ", 前天:"前天 ", 累计收获::"累计收获:", 累计偷窃::"累计偷窃:", 个人信息:"个人信息", 成果:"成果", 等级::"等级:", 经验::"经验:", 现金::"现金:", 去TA的首页:"去TA的首页", 清空记录:"清空记录", 确认清空消息记录？:"确认清空消息记录？", 清空消息记录:"清空消息记录", 清空留言记录:"清空留言记录", 确认删除你的留言记录？:"确认删除你的留言记录？", 回复:" 回复 ", 购买装饰:"购买装饰", 预览:"预览", 购买:"购买", 用金币购买:"用金币购买", 用F币购买:"用F币购买", 你的金币不足。:"你的金币不足。", 你的F币不足。:"你的F币不足。", 你的F币和金币都不足。:"你的F币和金币都不足。", 有效期：:"有效期：", validTime:"{day}天", 可获得经验：:"可获得经验：", 取消预览:"取消预览", buyNum:"输入购买数量（{minNum}~{maxNum}）", sellNum:"输入卖出数量（{minNum}~{maxNum}）", maturingTime:"{maturingNum}季作物", 化肥:"化肥", 狗:"狗", 仓库里空空:"仓库里空空的，连老鼠都不来光顾，一分耕耘一分收获，赶紧种植去吧!", allSellText:"仓库里所有果实的总价值为金币：<font color=\'#FF6600\'>{goldValue}</font><br>是否全部卖出？", 查看新任务:"查看新任务", 查看当前任务:"查看当前任务", 你已完成全部新手任务！:"你已完成全部新手任务！", 完成任务:"完成任务", 进行下一个任务:"进行下一个任务", 领取任务:"领取任务", 任 务:"任 务", (此任务进行中):" (此任务进行中) ", (每条最多 50 字):"(每条最多 50 字)", 发送留言:"留言", growText:"第{season}季（{timeText}{section}）", hrsText:"{hrs}小时", minsText:"{mins}分钟", ripeText:"第{season}季 （产{units} 剩{left}）", -已偷过:"-已偷过", 青虫血量:"青虫血量", 特殊道具:"特殊道具", 删除:"删除", 这个道具每小时只能用一次。:"这个道具每小时只能用一次。"}, en:{warehouseTitle:"Barn", warehouseDirection:"Sell your harvest in the Barn for cash.", toolTileDirection:"Fertilizers help plants grow faster and reduce its chance of being stolen.", diyTileDirection:"Buy decorations to furnish your farm and earn Exp.", shopTitle:"Store", dialTitle:"开心大转盘", gainTitle:"开心大转盘", allSell:"Sell All", shopTagSell:"Seeds", shopTagFertilizer:"Accessories", shopTagDecorate:"Decorations", sumGold:"Total value of harvest:", shopSeedWindow:"Buy Seeds", shopToolWindow:"Buy Accessories", sellCropWindow:"Sell Harvest", alertTitle:"Notification", 登录过期，:"Your session has expired,", 点击重新登录:"Please log in again.", 初始化错误，:"An error has occurred while initializing,", 点击重试:"Click HERE to retry.", 请求超时，稍后再试:"Loading is taking longer than usual. Pleas try later.", 请求超时，:"Oops...loading is taking longer than usual,", buySeedFnText:"Purchase was successful. You paid <font color=\'#FF6600\'>{money} coins</font> for {num} {cName} seeds.", 购买装饰成功:"You\'ve successfully purchased this accessory!", 设置装饰失败:"An error has occurred while saving.", saleAllFnText:"Congratulations! You sold your harvest for {money}.", 不可以对空地进行这个操作啦！:"Empty land!", 这块地不需要浇水啦！:"Enough water!", 这块地不需要除草啦！:"Enough weed killer!", 这块地不需要除虫啦！:"Enough pesticide!", 这块地无法种草啦！:"Too many weeds!", 作物这个阶段不会长草！:"Weed killer not necessary during this stage!", 这块地无法放虫啦！:"Enough pests!", 作物这个阶段不会生虫！:"Pesticide not necessary during this stage!", 这块地没东西可收获！:"Nothing to harvest!", 这块地没东西可偷！:"Nothing left to steal!", 狗盯上了你，别做坏事了。:"Better Behave!", 做人不能贪得无厌！:"Don\'t be so greedy!", 行行好吧，我所剩无几了！:"There isn\'t much left.", 铲除作物:"Plow out plants", 作物还没有收获，真的要铲除吗？:"The plant has not ripen, are you sure you want to plow it out?", 不可以种在这里哦！:"Nothing can be planted here.", 作物这个阶段不需要施肥:"Fertilizer not necessary during this stage.", 施过肥了，下个阶段再施吧:"Already fertilized. Try again next stage.", 按钮:"Icons", 确定:"Confirm", 取消:"Cancel", 狗狗粮食:"Dog treats", 剩余狗粮还可以给狗狗吃:"Enough remaining dog treats", 小时:"Hour(s)", 补充狗粮需要:"Buy more dog treats", 狗粮充足，不需要补充狗粮了。:"You have enough dog treats.", 状态(可怕):"Condition: Dying ", 状态(糟糕):"Condition: Terrible ", 状态(欠佳):"Condition: Needs care ", 状态(良好):"Condition: Good ", 状态(优秀):"Condition: Excellent ", 对不起，你的金币和等级均不足。:"Oops...you need more cash and a higher level to do this.", 对不起，你的等级不足。:"Sorry, your level is too low.", 对不起，你的金币不足。:"Sorry, you don\'t have enough cash.", 扩建农场:"Expanding Your Farm", reclaimPayText:"You need to reach level <font color=\'#0099ff\'> {level} </font> and pay <font color=\'#FF6600\'>{money}</font> to expand this land.", 包裹里什么都没有，去商店里逛逛吧！:"Your Sack is empty. Go Shopping.", 去商店:"Go to the Store", cropSeedName:"{cName} Seed", 可拖动屏幕:"Drag the screen", 已经购买的物品:"My Items", 在地里放杂草:"Spread Weeds", 在地里放害虫:"Spread Pests", 清除地里的杂草:"Clear Weeds", 可用来翻地:"Plow Land", 清除地里的害虫:"Kill Pests", 用来收获果实:"Collect Harvest", 偷窃好友的果实:"Steal Harvest", 用来浇水:"Water Plants", 刷新:"Refresh", 上一页:"Back", 下一页:"Next", 查找:"Search", 当前经验::"Experience:", 升级还需经验::"Experience needed for upgrading:", 我的农场:"My Farm", 仓库（收获的果子会在这里）:"Barn (your harvest is here)", 开心商店（来这里买种子）:"Happy Store (Buy seeds here)", 装饰农场:"Decorations", 晴天，有可能长虫、长草或干旱。:"It\'s sunny. You may suffer from pests, weeds, or drought. ", 雨天，土地不会干旱。:"Rainy, no worries for drought.", 夜晚，你可以好好休息哦。:"Good night, have a good rest", 给TA留言:"Leave a Message", 礼包:"Gift", 消息:"Notifications", 留言:"Messages", 公告:"Bulletin", 数据初始化中...:"Initializing...", 等级和经验:"Level & Experience", 接受:"Accept", 欢迎:"Welcome", 到期时间：无限期:"Expiration: Never", 到期时间：:"Expiration: ", 农场装饰:"Decorations", 背景:"Background", 房子:"House", 狗窝:"Kennel", 栅栏:"Fence", 已装饰:"Displayed", 装饰续费:"Renew decorations", 续费:"Renew", outtime:"Your decoration expires in {day} days.", 原价：:"Regular Price:", 折后价：:"Sale Price:", 立即节省：:"You Save:", 可获得经验：:"Experience Earned:", 今天:"Today ", 昨天:"Yesterday ", 前天:"Two days ago ", 累计收获::"Total Harvest:", 累计偷窃::"Total Steal:", 个人信息:"Profile", 成果:"Harvest", 等级::"Level:", 经验::"Experience:", 现金::"Cash:", 去TA的首页:"Go to homepage", 清空记录:"Delete Records", 确认清空消息记录？:"Are you sure you want to delete all notifications?", 清空消息记录:"Delete all notifications", 清空留言记录:"Delete all messages", 确认删除你的留言记录？:"Are you sure you want to delete all your messages?", 回复:" Reply ", 购买装饰:"Buy Decorations", 预览:"Preview", 购买:"Buy", 用金币购买:"Buy with cash", 用F币购买:"Buy with credit", 你的金币不足。:"Insufficient cash.", 你的F币不足。:"Insufficient credit.", 你的F币和金币都不足。:"You\'re out of money!", 有效期：:"Useful life:", validTime:"{day} Days", 可获得经验：:"Experience earned:", 取消预览:"Cancel Preview", buyNum:"Type in Units ({minNum}~{maxNum})", sellNum:"Type in Units ({minNum}~{maxNum})", maturingTime:"{maturingNum} Season(s)", 化肥:"Fertilizers", 狗:"Dog", 仓库里空空:"An empty barn? No pain, no gain. Plant something! ", allSellText:"Total value of harvest: <font color=\'#FF6600\'>{goldValue}</font>,<br> sell them all?", 查看新任务:"Check New task", 查看当前任务:"Check current Task:", 你已完成全部新手任务！:"Congratulations! You\'ve completed all beginner\'s tasks!", 完成任务:"Complete this task", 进行下一个任务:"Go to Next Task", 领取任务:"Accept Task", 任 务:" Task ", (此任务进行中):"(In progress)", (每条最多 50 字):"No more than 150 characters.", 发送留言:"Send", growText:"#{season}# ({timeText}from {section})", hrsText:"{hrs} hrs ", minsText:"{mins} mins ", ripeText:"#{season}# Season ({units}units/{left}left)", -已偷过:"-Stolen", 青虫血量:"Big Worm HP:", 特殊道具:"Special tool", 删除:"Delete", 这个道具每小时只能用一次。:"You can only use this accessory once every hour.", 没有对好友可以使用的道具！:"  No accessories to use on your friend\'s farm."}};

        public function Language()
        {
            return;
        }// end function

        public static function replaceText(param1:String, param2:Object) : String
        {
            var _loc_4:String;
            var _loc_5:RegExp;
            var _loc_3:String;
            _loc_3 = Language.L[param1];
            for (_loc_4 in param2)
            {
                // label
                _loc_5 = new RegExp("{" + _loc_4 + "}", "g");
                _loc_3 = _loc_3.replace(_loc_5, param2[_loc_4]);
                _loc_3 = _loc_3.replace("#1#", "1st").replace("#2#", "2nd").replace("#3#", "3rd").replace("#4#", "4th");
            }// end of for ... in
            return _loc_3;
        }// end function

        public static function get L() : Object
        {
            return _l[lang];
        }// end function

        public static function get lang() : String
        {
            if (_currentLang != "")
            {
                return _currentLang;
            }// end if
            var _loc_1:* = INI.getInstance().data;
            var _loc_2:* = _loc_1.version.@language;
            if (_loc_2 == "" || _loc_2 == null)
            {
                _currentLang = "cn";
            }
            else
            {
                _currentLang = _loc_2;
            }// end else if
            return _currentLang;
        }// end function

    }
}
