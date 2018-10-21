! function () {
    var $ = layui.$
    var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功

    upload = layui.upload;
    upload.render({
        elem: '#photo',
        url: '/user/upload',
        acceptMime: 'image/*',
        before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('.layui-upload-img').attr('src', result); //图片链接（base64）
            });
            $('#upload-help').hide()
        },
        done: function (res) {
            console.log(res)
        }
    });

}();