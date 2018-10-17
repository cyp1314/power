!function () {
    var $ = layui.$;
    var form = layui.form;


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
}();