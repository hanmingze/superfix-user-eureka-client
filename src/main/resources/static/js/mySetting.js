/**
 * Created by 韩明泽 on 2017/8/14.
 */
function pageOnLoad() {
    $("#setBtn").attr("disabled","disabled");
}
function chick_userName() {
    var username=$("#set_username").val();
    var pattern=/^[a-zA-Z]{1}[0-9a-zA-Z_]{1,}$/;
    if(username.trim()==''){
        $("#setName").html("<font color='#dc143c'>用户名不能为空</font>");
         $("#set_username").focus();
        $("#setBtn").attr("disabled","disabled");
        return false
    }
    if(!pattern.test(username)){
        $("#setName").html("<font color='#dc143c'>用户名只允许使用大小写字母开头加数字的组合</font>");
        $("#set_username").focus();
        $("#setBtn").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"./findUserByName",
        data:{"username":username},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#setName").html("<font color='#dc143c'>用户名可以使用</font>");
                $("#setBtn").removeAttr("disabled");
            } else {
                $("#setName").html("用户名已使用");
                $("#set_username").focus();
                $("#setBtn").attr("disabled","disabled");
            }
        }
    });
    $("#setName").html("");
    $("#setBtn").removeAttr("disabled");
    return true
}
function chick_pwd() {
    var pwd =$("#set_pwd").val();
    var reg = /^[A-Za-z0-9]{6,20}$/;
    if(pwd.trim()==''){
        $("#setPwd").html("<font color='#dc143c'>密码不能为空</font>");
        $("#set_pwd").focus();
        $("#setBtn").attr("disabled","disabled");
        return false
    }
    if(!reg.test(pwd)){
        $("#setPwd").html("<font color='#dc143c'>密码规则为6-20位字母数字组合</font>");
       $("#set_pwd").focus();
        $("#setBtn").attr("disabled","disabled");
        return false
    }
    $("#setPwd").html("");
    $("#setBtn").removeAttr("disabled");
    return true
    
}
function chick_address() {
    var address =$("#set_address").val();
    if(address.trim()==''){
        $("#set_address").html("<font color='#dc143c'>地址不能为空</font>");
        $("#setAddress").focus();
        $("#setBtn").attr("disabled","disabled");
        return false
    }
    $("#set_address").html("");
    $("#setBtn").removeAttr("disabled");
    return true
}