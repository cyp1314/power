;
!function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var upload = layui.upload;


    table.render({
        id: "user-id",
        elem: '#user',
        height: 'full-200',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        url: '/user/showUserTable', //
        method: 'post',
        page: true, //开启分页
        toolbar: '#usertoolbar',
        where: {
            area_id: $('#area-id').text(),
            department_id: $('#department-id').text(),
        },
        cols: [
            [ //表头
                {
                    field: 'id',
                    title: 'ID',
                    sort: true,
                    hide: true,
                    fixed: 'left'
                }, {
                field: 'name',
                title: '用户名'
            }, {
                field: 'number',
                title: '工号',
                sort: true
            }, {
                field: 'sex',
                title: '性别'
            },
                {
                    field: 'idNumber',
                    title: '身份证号',
                    width: 150
                }, {
                field: 'address',
                title: '籍贯'
            }, {
                field: 'telphone',
                title: '电话'
            }, {
                field: 'description',
                title: '备注',
                hide: 'true'
            }, {
                field: 'area',
                title: '所在区',
                sort: true
            }, {
                field: 'department',
                title: '所在部门',
                sort: true,
                hide: true
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#userbar',
                width: 190
            },
            ]
        ],
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            , type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        },
        title: '用户信息',
        defaultToolbar: ['filter', 'print']
    });


    //修改用户
    form.on('submit(user-form-submit)', function (data) {
        $.ajax({
            type: 'post',
            url: '/user/editUser',
            data: data.field,
            success: function (data) {
                if (data.code == 200) {
                    layer.msg('修改成功!', {icon: 1, time: 2000});
                    layer.closeAll('page');

                    //重载表格
                    table.reload('user-id', {
                        url: '/user/showUserTable',
                    });

                } else {
                    layer.msg(data.msg, {icon: 2, time: 2000});
                }

            },

        });

        return false; //阻止表单跳转。
    });


    //监听行工具事件
    table.on('tool(user)', function (obj) {
        var data = obj.data;//获得当前行数据

        //更新form渲染
        form.render();

        if (obj.event === 'del') {
            layer.confirm('真的删除该行数据吗？', function (index) {
                obj.del();
                layer.close(index);
                //向服务端发送删除指令

                $.ajax({
                    type: 'post',
                    url: '/user/deleteUser',
                    data: {"id": data.id},
                    success: function (data) {
                        layer.msg(data.msg, {icon: 1, time: 2000})
                    },

                });
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 1,
                offset: '50px',
                title: '修改用户',
                shadeClose: true, //点击遮罩关闭
                content: $('#user-form'),
                area: '700px',
                success: function (layero, index) {
                    form.val("user-edit-form", {
                        "id": data.id,
                        "name": data.name,
                        "number": data.number,
                        "sex": data.sex,
                        "idNumber": data.idNumber,
                        "address": data.address,
                        "telphone": data.telphone,
                        "areaId": data.areaId,
                        "departmentId": data.departmentId
                    });

                    //先清空上一次点击显示的图片
                    $("#photoImg").removeAttr('src');
                    if (data.photo != null) {
                        $("#photoImg").attr('src', '/' + data.photo)
                    }

                },

            });
        } else if (obj.event === 'view') {
            var index = layer.open({
                type: 2,
                title: '查看用户详情',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['860px', '600px'],
                content: '/user/view?id=' + data.id
            });
            //弹出即全屏
            layer.full(index);

        }


        //这里有个坑！！！！！！！！！！
        //要把该函数放在table.on()行监听事件中
        //否则点击上传按钮无反应
        //编辑弹出层中，上传照片
        upload.render({
            elem: '#photo1',
            url: '/user/upload',
            done: function (res) {
                //如果上传失败
                if (res.code != 200) {
                    return layer.msg('上传失败');
                }
                //上传成功
                //将res返回的图片链接保存到表单的隐藏域
                $("#photo-path").val(res.data.src);
                //将上传成功的图片显示出来
                $("#photoImg").attr('src', '/' + res.data.src);
            }

        });

    });


    //监听头工具栏事件
    table.on('toolbar(user)', function (obj) {
        switch (obj.event) {
            case 'user-add':
                window.location.href = "/user/toAdd";
                break;
            //导出数据
            case 'user-download':
                var areaId = $('#area-id').text();
                var departmentId = $('#department-id').text();
                window.location.href = "/user/exportExcel?area_id=" + areaId + "&department_id=" + departmentId;
                break;
        }
        ;
    });


    //搜索事件
    form.on('submit(user-search)', function (data) {

        table.reload('user-id', {
            page: {
                curr: 1 //重新从第 1 页开始
            }
            , where: data.field,
            url: '/user/search'
        });

        //为搜索框赋初值(数据回显)
        form.val('search-user', data.field);
    });


    //上传excel，用作数据导入
    upload.render({
        elem: '#user-btn-excel' //绑定元素
        , url: '/user/importExcel' //上传接口
        , accept: 'file'
        , acceptMime: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        , before: function (obj) { //obj参数包含的信息，
            layer.load(); //上传loading
        }
        , done: function (res) {
            layer.closeAll('loading'); //关闭loading
            layer.msg("已经成功导入了" + res.data + "条员工信息", {icon: 1, time: 2000});
            //重载表格
            table.reload('user-id', {
                url: '/user/showUserTable',
            });
        }
    });

}();