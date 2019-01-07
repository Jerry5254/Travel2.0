$(function () {
    var getCityUrl = '/travel/city/citylist';
    var loginurl='/travel/users/islogin';
    var logouturl='/travel/users/logout'

    getCityList();
    isLogin();

    $('#about_us').click(function () {
        $.alert({
            hasfoot: false,
            backdrop: false,
            title: '关于我们',
            body: '<div style="height: 50px;color:black;margin: 0 auto;">' + '我们是一家旅游网站' + '</div>',
            width: 250,
            height: 100,
        });
    });
    $('#contact_us').click(function () {
        $.alert({
            hasfoot: false,
            backdrop: false,
            title: '联系我们',
            body: '<div style="height: 50px;color:black;margin: 0 auto;">' + '联系方式：j.y5254@foxmail.com' + '</div>',
            width: 250,
            height: 100,
        });
    });

    function isLogin(){
        $.getJSON(loginurl, function (data) {
            if (data.success) {
                var status=data.status;
                if(status==1){
                    var html='<li><a href="http://localhost:8080/travel/admin">欢迎管理员！'+data.users.uname+'</a></li>'+
                    '<li><a id="logout" href="javascript:void(0);t">退出登录</a></li>';
                    $('#islogin').html(html);
                }else if(status==2){
                    var html='<li><a href="http://localhost:8080/travel/users/touserinfo">欢迎您!'+data.users.uname+'</a></li>'+
                        '<li><a id="logout" href="javascript:void(0);">退出登录</a></li>';
                    $('#islogin').html(html);
                }else{
                    var html='<li><a href="http://localhost:8080/travel/users/tologin">登录</a></li>'+
                    '<li><a href="http://localhost:8080/travel/users/registeruser">注册</a></li>';
                    $('#islogin').html(html);
                }
            }
        });
    }

    $('#islogin').on('click', 'li a', function () {
        var text=$(this).text();
        if(text=='退出登录'){
            $.getJSON(logouturl, function (data) {
                console.log(data.success);
                if (data.success) {
                    alert('退出登录成功！');
                    window.location.href='/travel';
                }
            });
        }
    });


    function getCityList() {
        $.getJSON(getCityUrl, function (data) {
            if (data.success) {
                var cityHtml = '';
                data.cityList.map(function (item, index) {
                    cityHtml += '<li role="presentation" ><a role="menuitem" href="javascript:void(0);" tabindex="-1" data-id="' + item.cid + '">' + item.cname + '</a></li>';

                });
                $('#city-nav').html(cityHtml);
            }
        });
    }

    $('#city-nav').on('click', 'li a', function () {
        var id = $(this).data('id');
        $.ajax({
            type: 'get',
            url: '/travel/city/addpoint',
            data: {"cityId": id}
        });
        $(location).attr('href', '/travel/city/getcity?cityId=' + id);
        e.stopPropagation();
    });


})