! function () {
    var $ = layui.$  
    var form = layui.form;
    
     // 表单提交
    form.on('submit(login-submit)', function (obj) {

    var field = obj.field;
    $.ajax({
        url: '/login',
        data: field,
        type: 'post',
        dataType: 'JSON',
        success: function (data) {
            if (data.code == 200) {
                layer.msg('登录成功', {icon: 1}, function () {
                    location.replace('/');
                });
            } else {
                layer.closeAll('loading');
                layer.msg(data.msg, {icon: 5});
            }
        },
        error: function (xhr) {
            layer.closeAll('loading');
            if (xhr.status == 400) {
                layer.msg('登录失败，请重试', {icon: 5});
            }
        }
    });

        
    });

}();