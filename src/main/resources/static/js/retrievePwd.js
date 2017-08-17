/**
 * Created by 韩明泽 on 2017/8/10.
 */
var contxt=0;
function pageLoad(){

    $("#emailDiv").css("display","none");
    $("#codeDiv").css("display","none");
    $("#subDiv").css("display","none");
    $("#btChick1").css("display","none");
    $("#btChick1").attr("disabled","disabled");
    $("#btChick2").attr("disabled","disabled");
    $("#account").focus();

}
function chickAccount() {
    var account=$("#account").val();
    if(account.trim()==''||contxt==0){
        contxt=1;
        $("#spaAccount").html("<font color='#dc143c'>请输入用户名、邮箱或手机号</font>");
        $("#account").focus();
        $("#btChick2").attr("disabled","disabled");
        return false
    }
     contxt=1;
    $("#spaAccount").html("");
    $("#btChick2").removeAttr("disabled");
    return true
}
function nextChick() {
    $("#accountDiv").css("display","none");
    $("#slidingBlock").css("display","none");
    $("#emailDiv").css("display","block");
    $("#codeDiv").css("display","block");
    $("#subDiv").css("display","block");
    $("#btChick1").css("display","block");
    $("#btnDiv").css("display","none");
}
function emailCheck(){
    var account=$("#account").val();

    var email=$("#ret_email").val();
    console.log(account);
    console.log(email);
    var ree=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if(email.trim()==''){
        $("#divEmail").html("<font color='#dc143c'>邮箱不能为空</font>");
        $("#ret_email").focus();
        $("#btChick1").attr("disabled","disabled");
        return false
    }
    if(!ree.test(email)){
        $("#divEmail").html("<font color='#dc143c'>邮箱格式不合法，请重填</font>");
        $("#ret_email").focus();
        $("#btChick1").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"./findUserByEmailAndUsername/"+email+"/"+account,
        data:{"email":email,"account":account},
        success:function(data){
            if(data=="1") {
                $("#divEmail").html("<font color='#dc143c'>验证码已发至您的邮箱请注意查收</font>");
                $("#btChick1").removeAttr("disabled");
            } else {
                $("#divEmail").html("<font color='#dc143c'>邮箱账号不匹配，请填入注册时填写的邮箱，如有疑问请<a href='https://www.baidu.com'>联系客服</a></font>");
                $("#btChick1").attr("disabled","disabled");
            }
        }
    });
    $("#divEmail").html("");
    $("#btChick1").removeAttr("disabled");
    return true
}
function chickCode() {
    var account=$("#account").val();
    var code=$("#code").val();
    var email=$("#ret_email").val();
    if(code.trim()==''){
        $("#divCode").html("<font color='#dc143c'>请输入新的密码</font>");
        $("#code").focus();
        $("#btChick1").attr("disabled","disabled");
        return false
    }
    $.ajax({
        url:"./resetPwd",
        data:{"code":code},
        type:"POST",
        success:function(data){
            if(data=="1") {
                $("#divCode").html("<font color='#dc143c'>验证码正确</font>");
                $("#btChick1").removeAttr("disabled");
            } else {
                $("#divCode").html("<font color='#dc143c'>验证码不正确请<a href='/user/findUserByEmailAndUsername/"+email+"/"+account+"'"+">重新发送</a></font>");
                $("#btChick1").attr("disabled","disabled");
            }
        }
    });
    $("#divCode").html("");
    $("#btChick1").removeAttr("disabled");
    return true
}


