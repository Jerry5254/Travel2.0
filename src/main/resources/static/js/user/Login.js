$(function(){
    login();
    function login() {
        var loginUrl="/travel/users/login";
        $('#submit').click(function () {
            var email=$('#email').val();
            var password=$("#upass").val();
            var formdata=new FormData();
            formdata.append('email',email);
            formdata.append('password',password);
            $.ajax({
                url:loginUrl ,
                type: 'POST',
                data: formdata,
                contentType: false,
                processData: false,
                cache: false,
                async:false,
                success: function (data) {
                    console.log(data.success);
                    if (data.success) {
                        alert('登陆成功');
                        window.location.href='/travel/users/touserinfo';
                    } else {
                        alert('登陆失败：'+data.errMsg);
                    }
                }
            });
        });
    }
})