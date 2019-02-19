$(function(){
});

function register() {
    var id = $.trim($("#id").textbox('getValue'));
    var pwd = $.trim($("#pwd").textbox('getValue'));
    var pwd_2 = $.trim($("#pwd_2").textbox('getValue'));
    var organization_id = $.trim($("#organization_id").textbox('getValue'));
    if(id == ''){
        $.messager.alert("操作提示","客户代码不能为空！","error");
        return;
    }
    if(organization_id == ''){
        $.messager.alert("操作提示","请选择客户类型！","error");
        return;
    }
    if(pwd == ''){
        $.messager.alert("操作提示","密码不能为空！","error");
        return;
    }
    if(pwd_2 == ''){
        $.messager.alert("操作提示","第二次输入的密码不能为空！","error");
        return;
    }
    if(pwd != pwd_2){
        $.messager.alert("操作提示","两次次输入的密码不相同！","error");
        return;
    }
    var formData = {
        id                  : id,
        pwd                 : pwd,
        organization_id     :   organization_id
    }

    $.ajax({
        type:'Post',
        url:'/management/wechat/common/register',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                if(res.status == 0){
                    window.location.href="http://www.baidu.com";
                }else if(res.status == 1){
                    $.messager.alert("提示",res.msg,"error");
                }

            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}

