function queryString(url){
    var arr=[]; //存储参数的数组
    var res={}; //存储最终JSON结果对象
    arr=url.split("?")[1].split("&"); //arr=["a=1", "b=2", "c=test", "d"]

    for(var i=0,len=arr.length;i<len;i++){
        //如果有等号，则执行赋值操作
        if(arr[i].indexOf("=")!=-1){
            var str=arr[i].split("=");
            //str=[a,1];
            res[str[0]]=str[1];
        }else{//没有等号，则赋予空值
            res[arr[i]]="";
        }
    }
    //res=JSON.stringify(res);//转化为JSON字符串
    return res;
}

layui.config({
    base: '/lib/navbar/', //navbar组件js所在目录
}).use('navbar', function () {
    var navbar = layui.navbar();
    var table = layui.table;
    var $ = layui.$;

    navbar.set({
        elem: '#nav',
        url: '/navbar', //数据源地址
        //cached: 'true'
    });
    navbar.render();
    navbar.on('click(menu)', function (data) {
        //如果不在用户列表页，重定向到用户列表
        if(location.pathname!="/user/list"){
            location.href=data.field.href;
        }

        //重载用户表格
        table.reload('user-id', {
            url: '/user/showUserTable',
            where: queryString(data.field.href)
        });

        //定位当前元素的父级元素(区域元素)
        var parent = data.elem.parents("dl").siblings("a").children("cite");

        //操作面包屑导航元素的值
        $("#area-name").text(parent.text());
        $("#department-name").text(data.field.title);

    });
});