$(function() {
    addUser();
    //vali();

    //验证表单
    function vali() {
        $('#form1').validate({
            rules: {
                uname: {
                    required: true,
                    checkName:true
                },
                upass: {
                    required: true,
                    minlength: 6,
                    maxlength: 16,
                    checkPwd:true
                },
                conpass: {
                    required: true,
                    prefill: 'upass',
                    match: 'upass'
                },
                email: {
                    required: true,
                    checkEmail:true
                },
                phone: {
                    required: true,
                    mobile:true
                },
                messages: {
                    email: "这是一个错误的邮箱(╥╯^╰╥)",
                    conpass:{
                            match:"两次密码输入不一致"
                    }
                },
            }
        });
        //自定义正则表达式验证方法
        //验证邮箱
        $.validator.addMethod("checkEmail",function(value,element,params){
            var checkEmail = /^[a-z0-9]+@([a-z0-9]+\.)+[a-z]{2,4}$/i;
            return this.optional(element)||(checkEmail.test(value));
        },"*请输入正确的邮箱！");
        //验证用户名
        $.validator.addMethod("checkName",function(value,element,params){
            var checkName = /^\w{2,10}$/g;
            return this.optional(element)||(checkName.test(value));
        },"*只允许2-20位英文字母、数字或者中文！");
        //验证手机号
        $.validator.addMethod("mobile", function(value, element,params) {
            var length = value.length;
            return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码");
        //验证密码
        $.validator.addMethod("checkPwd",function(value,element,params){
            var checkPwd = /^\w{6,16}$/g;
            return this.optional(element)||(checkPwd.test(value));
        },"*只允许6-16位英文字母、数字或者下画线！");
    }

    //提交表单
    function addUser() {
        var registerUserUrl='/travel/users/adduser';
        $('#submit').click(function () {
            var user = {};
            user.uname = $('#user-name').val();
            user.umail = $('#user-mail').val();
            user.upwd = $('#user-password').val();
            user.umobile = $('#user-phone').val();
            user.ugender = $('#user-gender option:selected').val();
            var userIcon = $('#user-icon')[0].files[0];
            var mail=$('#user-mail').val();

            IsEmail(user.umail);
            checkMobile(user.umobile);
            checkPSW(user.upwd);
            checkName(user.uname);
            checkConPwd(user.upwd, $('#user-conpassword').val());

            if (IsEmail(user.umail) && checkMobile(user.umobile) && checkName(user.uname) && checkPSW(user.upwd) && checkConPwd(user.upwd, $('#user-conpassword').val())) {
                var formData = new FormData();
                formData.append('userInfo', JSON.stringify(user));
                formData.append('userIcon', userIcon);
                formData.append("email", mail);
                $.ajax({
                    url: registerUserUrl,
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            alert('提交成功');
                            window.location.href='/travel';
                        } else {
                            alert('提交失败' + data.errMsg);
                        }
                    }
                });
            }
        });
    }

    function IsEmail(str) {
        if (str.length != 0) {
            reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (!reg.test(str)) {
                $('#wrong-email').css({"display": "inline"});
                return false;
            } else {
                $('#wrong-email').css({"display": "none"});
                return true;
            }
        }
    }

    function checkName(str) {
        if (str.length != 0) {
            reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
            if (!reg.test(str)) {
                $("#wrong-name").css({"display": "inline"});
                return false;
            } else {
                $("#wrong-name").css({"display": "none"});
                return true;
            }
        }
    }

    function checkPSW(str) {
        if (str.length != 0) {
            reg = /^\w{6,16}$/g;
            if (!reg.test(str)) {
                $('#wrong-pwd').css({"display": "inline"});
                return false;
            } else {
                $('#wrong-pwd').css({"display": "none"});
                return true;
            }
        }
    }

    function checkConPwd(pwd, conpwd) {
        if (pwd == conpwd) {
            $('#wrong-conpwd').css({"display": "none"});
            return true;
        } else {
            $('#wrong-conpwd').css({"display": "inline"});
            return false;
        }
    }


    function checkMobile(str) {
        if (str.length != 0) {
            reg = /^1\d{10}$/;
            if (!reg.test(str)) {
                $('#wrong-mobile').css({"display": "inline"});
                return false;
            } else {
                $('#wrong-mobile').css({"display": "none"});
                return true;
            }
        }
    }
})