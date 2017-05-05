//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法) seckill.detail.init(params)

//包
var seckill = {
    //封装秒杀ajax相关的url
    URL:{
        now:function () {
            return '/seckill/time/now';
        },
        exposer:function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution:function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/exposer';
        }
    },
    //验证手机号
    validatePhone:function (phone) {
        if(phone &&phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },

    //获取秒杀地址，控制秒杀逻辑，执行秒杀
    handelSeckill:function (seckillId,node) {
        console.log("Here :"+seckillId);
        //设置秒杀提交按钮
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        //获取秒杀地址
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if(result && result['result']){
                //控制秒杀逻辑
                var exposer = result['data'];
                if(exposer['exposed']){
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    //执行秒杀操作
                    var killURL = seckill.URL.execution(seckillId,md5);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function(){
                        //1.禁用按钮
                        $(this).addClass('disabled');
                        //2.发送秒杀请求，执行秒杀
                        $.post(killURL,{},function (result) {
                            if(result && result['result']) {
                                //1.获取执行情况
                                var execution = result['data'];
                                var state = execution['state'];
                                var stateinfo = execution['stateInfo'];
                                //3.显示秒杀结果
                                node.html("<span class='label label-success'>"+stateinfo+"</span>");
                            }
                        });
                    });
                    node.show();
                }else{
                    //秒杀未开启
                    seckill.countdown(seckillId,exposer['now'],exposer['start'],exposer['end']);
                }
            }else{
                console.log("sss:"+result['result']);
            }
        });
    },
    //计时交互:秒杀未开始之前倒计时
    countdown:function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if(nowTime >endTime){
            seckillBox.html('秒杀结束！');
        }else if(nowTime < startTime){
            //秒杀未开始，计时
            var killTime = new Date(startTime+1000);
            seckillBox.countdown(killTime, function(event) {
                $(this).html(event.strftime('秒杀倒计时: %D天 %H:%M:%S'));
            }).on('finish.countdown', function(){
                //倒计时结束开启秒杀
                seckill.handelSeckill(seckillId,seckillBox);
            });
        }else{
            //开启秒杀
            seckill.handelSeckill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init :function (params) {
            //用户手机验证 在cookie中获取
            var killPhone = $.cookie("killPhone");
            if(!seckill.validatePhone(killPhone)){
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //控制输出
                killPhoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyborad:false//关闭键盘事件
                });

                //提交
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //电话写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            //成功登陆操作
            //ajax 计时交互 请求到 /seckill/time/now
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['result']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result'+result);
                }
            });
        }
    }

}