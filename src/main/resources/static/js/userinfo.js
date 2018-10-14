;
! function () {
    var $ = layui.$
    var table = layui.table;

    table.render({
        elem: '#user',
        height: 'full-200',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        url: 'json/user.json', //数据接口
        page: true, //开启分页
        toolbar: '#usertoolbar',
        cols: [
            [ //表头
                {
                    field: 'id',
                    title: 'ID',
                    sort: true,
                    fixed: 'left'
                }, {
                    field: 'username',
                    title: '用户名'
                }, {
                    field: 'sex',
                    title: '性别',
                    sort: true
                }, {
                    field: 'city',
                    title: '城市'
                }, {
                    field: 'sign',
                    title: '签名'
                }, {
                    field: 'experience',
                    title: '积分',
                    sort: true
                }, {
                    field: 'score',
                    title: '评分',
                    sort: true
                }, {
                    field: 'classify',
                    title: '职业'
                }, {
                    field: 'wealth',
                    title: '财富',
                    sort: true
                }, {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#userbar',
                    width: 130
                }
            ]
        ]
    });

    //监听行工具事件
    table.on('tool(user)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 1,
                //title: false, //不显示标题
                title: '修改用户',
                shadeClose: true, //点击遮罩关闭
                content: $('#user-form'),
            });
        }
    });

    //头工具栏事件
    $("#user-btn-add").click(function(){
        window.location.href="/user/add"
    });

    // $.ajax({
    //     type: "get",
    //     url: "json/tree.json",
    //     dataType: 'json',
    //     skin: "mytree",
    //     success: function (d) {
    //         layui.tree({
    //             elem: '#tree',
    //             nodes: d,
    //             click: function (node, a) {
    //                 //console.log(a);                                    
    //             },
    //             success: function () {

    //             }
    //         });
    //     }
    // })
}();