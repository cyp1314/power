;
! function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;

    table.render({
        elem: '#user',
        height: 'full-200',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        url: '/user/showUserTable', //
        method:'post',
        page: true, //开启分页
        toolbar: '#usertoolbar',
        where:{
            area_id:1,
            deparetment_id:1,
            employee_type:0
                // [[${session.loginInfo.type}]]
        },
        cols: [
            [ //表头
                {
                    field: 'id',
                    title: 'ID',
                    sort: true,
                    fixed: 'left'
                }, {
                    field: 'name',
                    title: '用户名'
                }, {
                    field: 'number',
                    title: '工号',
                    sort: true
                }, {
                    field: 'telphone',
                    title: '电话'
                }, {
                    field: 'description',
                    title: '备注'
                }, {
                    field: 'area',
                    title: '所在区',
                    sort: true
                }, {
                field: 'department',
                title: '所在部门',
                sort: true
                }
                // }, {
                //     field: "departmentId",
                //     title: "部门ID",
                //     hiden: true
                // }, {
                ,{
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#userbar',
                    width: 130
                },
            ]
        ]
    });

    //监听行工具事件
    table.on('tool(user)', function (obj) {
        var data = obj.data;//获得当前行数据

        //更新form渲染
        form.render();

        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 1,
                offset: '100px',
                title: '修改用户',
                shadeClose: true, //点击遮罩关闭
                content: $('#user-form'),
                success:function (layero, index) {
                    form.val("user-form",{
                        "name":data.name,
                        "number":data.number,
                        "telphone":data.telphone,
                    })
                    console.log(data.areaID)
                }
            });
        }
    });



    //头工具栏事件
    $("#user-btn-add").click(function(){
        window.location.href="/user/add"
    });


}();