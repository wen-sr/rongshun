$(function(){
    var screen = $(window).height();
    $("#d").height(parseInt(screen) - 20);
    getUser();
});

function getUser(formData) {
    $("#dg").datagrid({
        url:'/rongshun/weChatUserInfo/info',
        method: 'POST',
        queryParams:formData || '',
        height:'auto',
        fitColumns: true,
        fit: true,
        striped:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        toolbar: '#tb',
        columns:[[{
            field:"id",
            title:"id",
            checkbox:true,
            width:30
        },{
            field:"nickname",
            title:"昵称",
            align:'center',
            width:80
        },{
            field:"auth",
            title:"是否有权限",
            width:30
        }]]
    });
}

function auth(flag) {
    var row = $("#dg").datagrid('getSelected');
    if(row == null){
        return;
    }
    var formData = {
        id  : row.id,
        auth: flag
    };
    $.ajax({
        type:'Post',
        url:'/rongshun/weChatUserInfo/auth',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $("#dg").datagrid('reload');
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}
