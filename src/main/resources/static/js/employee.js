! function () {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;

    table.render({
        elem: '#employee',
        height: 'full-200',
        minWidth: '100', 
        align: 'center',
        //toolbar: '#employeetoolbar',
        width: 600,
        url: '/employee/showEmployeeTable', //数据接口
        method:'post',
        cols: [
            [ //表头
                {
                    field: 'id',
                    title: 'ID',
                    sort: true,
                    fixed: 'left',
                    align:'center'
                }, {
                    field: 'name',
                    title: '员工名',
                    sort: true,
                    align:'center'
                }, {
                    field: 'area',
                    title: '管辖区',
                    align:'center'
                }, {
                    field: 'sign',
                    title: '备注',
                    align:'center'
                }
            ]
        ],
        initSort: {
            field: 'id' //排序字段，对应 cols 设定的各字段名
            ,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
        }
    });

    $("#add-employee").click(function(){
        layer.open({
            type: 1,
            title: '添加员工',
            shadeClose: true, //点击遮罩关闭
            offset: '100px',
            content: $('#add-employee-form'),
        });
    });

    form.on('submit(add-employee)', function(data){
        $.ajax({
            type:'post',
            url: '/employee/add',
            data: data.field,
            success:function(data){
                if  (data.code == 200) {
                    layer.msg('添加成功!',{icon:1,time: 2000});
                    layer.closeAll('page');


                    //重载表格
                    var employee = $('#employee');
                    table.reload('employee', {
                        url: '/employee/showEmployeeTable',
                    });
                }else{
                    layer.msg(data.msg,{icon:2,time: 2000});
                }

            }
        });

        //重置表单
        $('#add-employee-form')[0].reset();
        return false; //阻止表单跳转。
    });



}();