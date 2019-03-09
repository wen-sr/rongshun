$(function () {
    getUserinfo();
});

function getUserinfo() {
    $("#list").html('');
    tools.request({
        url: "/rongshun/weChatUserInfo/info",
        success: function (data, msg) {
            if (data != null && data.length > 0) {
                var tpl =
                    '{{#list}}' +
                    '<div class="table-view">' +
                    '<div class="table-colum1">{{nickname}}</div>' +
                    '<div class="table-colum2">{{auth}}</div>' +
                    '<div class="table-colum3">' +
                    '<a href="javascript:;" onclick="go(\'{{id}}\', \'{{auth}}\')">' +
                    '<i class="fa fa-recycle" style="color: #D9534F" aria-hidden="true"></i>' +
                    '   </a></div>' +
                    '</div>'+
                    '{{/list}}';
                var result = tools.renderHtml(tpl, {list: data});
                $("#list").append(result);
            }
        }
    });
}

function go(id, auth) {
    tools.request({
       url      : "/rongshun/weChatUserInfo/auth",
       data    : {id : id, auth : auth},
       success  : function (data, msg) {
           $.alert({
               title: '提示',
               text: msg,
               onOK: function () {
                   getUserinfo();
               }
           });
       }
    });
}
