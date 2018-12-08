!function () {
    layui.use(['layer', 'jquery', 'upload'], function () {
        var $ = layui.$
        var upload = layui.upload;
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
                //上传成功
                if (res.code == 200) {
                    //将res返回的图片链接保存到表单的隐藏域
                    $("#photo-path").val(res.data.src);
                }
            }
        });
    });
}();