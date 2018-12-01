!function () {
    var $ = layui.$;
    var form = layui.form;
    var element = layui.element;
    var table = layui.table;

    $('#edit-password').click(function (obj) {
        layer.open({
            type: 1,
            title: '修改密码',
            content: $('#edit-password-form'),
        });
    })

    //确认密码校验
    $('#repeatpwd').blur(function () {
        var pwd = $('#pwd-input').val()
        var repeatpwd=$(this).val()
        if(pwd!=repeatpwd){
            layer.msg('两次输入的密码不一致', {icon: 5})
        }

    });


    form.on('submit(change-pwd)', function(data){
        $.ajax({
            type:'post',
            url: '/employee/editPassword',
            data: data.field,
            beforeSend:function () {
                var pwd = $('#pwd-input').val();
                var repeatpwd = $('#repeatpwd').val();
                if(pwd!=repeatpwd){
                    return false;
                }
            },
            success:function(data){
                if  (data.code == 200) {
                    layer.msg('密码修改成功!',{icon:1,time: 2000});
                    layer.closeAll('page');

                }else{
                    layer.msg(data.msg,{icon:2,time: 2000});
                }

            }
        });
        return false; //阻止表单跳转。
    });


    //监听导航点击
    element.on('nav(menu)', function(elem){
        alert("aaaa");
        //如果点击的是二级菜单
        if(elem.parent().prop("tagName")=='DD'){

            //点击菜单的样式切换
            $(".layui-nav-tree dd").each(function () {
                if($(this).hasClass("layui-this")){
                    $(this).removeClass("layui-this");
                }
            })
            elem.parent().addClass("layui-this");

            //当前元素的文本
            var current = elem.text();

            //当前元素的父级元素(区域元素)
            var parent = elem.parents("dl").siblings("a");

            //当元素内部还包裹有其他标签时，使用text()获取纯文本
            //console.log(parent.text());

            //获取自定义数据
            var area_id = elem.data("areaid");
            var department_id = elem.data("departmentid");

            //重定向到用户列表
            if(location.pathname!="/user/list"){
                location.href="/user/list?area_id="+area_id+"&department_id="+department_id;
            }

            //操作面包屑导航元素的值
            $("#area-name").text(parent.text());
            $("#department-name").text(current);

            //重载用户表格
            table.reload('user-id', {
                url: '/user/showUserTable',
                where:{
                    area_id: area_id,
                    department_id:department_id,
                }
            });
        }
    });
}();