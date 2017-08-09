/**
 * Created by 韩明泽 on 2017/8/7.
 */
function initPage(){
    $("#subForm").attr("disabled","disabled");
    $("#username").focus();
}
/**
 * 验证用户名
 */
function chickName() {
 var username =$("#username").val();
    var pattern=/^[a-zA-Z]{1}[0-9a-zA-Z_]{1,}$/;
 if(username.trim()==''){
     $("#spaName").html("<font color='#dc143c'>用户名不能为空</font>");
   //  $("#username").focus();
     $("#subForm").attr("disabled","disabled");
     return false
 }
 if(!pattern.test(username)){
     $("#spaName").html("<font color='#dc143c'>用户名只允许使用大小写字母开头加数字的组合</font>");
   //  $("#username").focus();
     $("#subForm").attr("disabled","disabled");
     return false
 }
    $.ajax({
        url:"/user/findUserByName",
        data:{"username":username},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#spaName").html("用户名可以使用");
                $("#subForm").removeAttr("disabled");
            } else {
                $("#spaName").html("用户名已使用");
                $("#username").focus();
                $("#btSave").attr("disabled","disabled");
            }
        }
    });
     $("#spaName").html("");
     $("#subForm").removeAttr("disabled");
     return true
}
/**
 * 密码校验
 */
function chickPwd() {
    var pwd =$("#pwd").val();
    var reg = /^[A-Za-z0-9]{6,20}$/;
    if(pwd.trim()==''){
        $("#spaPwd").html("<font color='#dc143c'>密码不能为空</font>");
    //    $("#pwd").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    if(!reg.test(pwd)){
        $("#spaPwd").html("<font color='#dc143c'>密码规则为6-20位字母数字组合</font>");
     //   $("#pwd").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $("#spaPwd").html("");
    $("#subForm").removeAttr("disabled");
    return true
}
/**
 * 重复密码校验
 */
function chickRepPwd() {
    var pwd =$("#pwd").val();
    var repeatPwd=$("#repeatPwd").val();
    if(repeatPwd.trim()==''){
        $("#spaRepPwd").html("<font color='#dc143c'>重复密码不能为空</font>");
      //  $("#repeatPwd").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    if(repeatPwd!=pwd){
        $("#spaRepPwd").html("<font color='#dc143c'>两次输入的密码不一致</font>");
     //   $("#repeatPwd").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $("#spaRepPwd").html("");
    $("#subForm").removeAttr("disabled");
    return true
}
/**
 * 手机号校验
 */
function chickPhone(){
    var phone=$("#phone").val();
    var rep=/^1[34578]\d{9}$/;
    if(phone.trim()==''){
        $("#spaPhone").html("<font color='#dc143c'>手机号不能为空</font>");
    //    $("#phone").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    if(!rep.test(phone)){
        $("#spaPhone").html("<font color='#dc143c'>手机号输入有误，请重填</font>");
     //   $("#phone").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"/user/findUserByPhone",
        data:{"phone":phone},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#spaPhone").html("手机号可以使用");
                $("#subForm").removeAttr("disabled");
            } else {
                $("#spaPhone").html("手机号已被使用");
                $("#phone").focus();
                $("#subForm").attr("disabled","disabled");
            }
        }
    });
    $("#spaPhone").html("");
    $("#subForm").removeAttr("disabled");
    return true
}
function chickEmail() {
    var email=$("#email").val();
    var ree=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if(email.trim()==''){
        $("#spaEmail").html("<font color='#dc143c'>邮箱不能为空</font>");
     //   $("#email").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    if(!ree.test(email)){
        $("#spaEmail").html("<font color='#dc143c'>邮箱格式不合法，请重填</font>");
   //     $("#email").focus();
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"/user/findUserByEmail",
        data:{"email":email},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#spaEmail").html("邮箱可以使用");
                $("#subForm").removeAttr("disabled");
            } else {
                $("#spaEmail").html("邮箱已被使用");
                $("#subForm").attr("disabled","disabled");
            }
        }
    });
    $("#spaEmail").html("");
    $("#subForm").removeAttr("disabled");
    return true
}
function numCode() {
    var verCode=$("#verCode").val();
    if(verCode.trim()==""){
        $("#spaCode").html("<font color='#dc143c'>请填写验证码</font>");
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"/user/checkVerCode",
        data:{"verCode":verCode},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#spaCode").html("");
                $("#subForm").removeAttr("disabled");
            } else {
                $("#spaCode").html("<font color='#dc143c'>验证码输入有误<font>");
                $("#subForm").attr("disabled","disabled");
            }
        }
    });
    $("#spaCode").html("");
    $("#subForm").removeAttr("disabled");
    return true
}
function clickAgree() {
    if(document.getElementById("agree").checked!=true){
        $("#subForm").attr("disabled","disabled");
        return false
    }
    $("#subForm").removeAttr("disabled");
    return true
}
function btFlush() {
    $.ajax({
        url:"/user/flushImages",
        type:"POST",
        success:function(data){
          $("#codeImg").attr("src",data);
        }
    });
}
/*
function allCheck() {
    if(chickName()&&chickPwd()&&chickRepPwd()&&chickPhone()&&chickEmail()){

    }
}*/
