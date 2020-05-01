$(document).ready(function ($) {
    $("#login-form").submit(function (event) {
        event.preventDefault();
        login();
    });
});

function login() {
    var authenticationDto = {}
    authenticationDto["username"] = $("#username").val();
    authenticationDto["password"] = $("#password").val();

    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/resto/V1/auth/login",
        data:JSON.stringify(authenticationDto),
        dataType:'json',
        timeout:10000,
        success:function (data) {
            console.log("SUCCESS",data);
            localStorage.clear();
            localStorage.setItem("accessToken",data.accessToken);
            localStorage.setItem("refreshToken",data.refreshToken);
            display(data);
        },
        error:function (e) {
            console.log("ERROR",e);
            display(e);
        }
    });
}

function display(data) {
    var json = "<h4>Ajax Response</h4><pre>"
        + JSON.stringify(data, null, 4) + "</pre>";
    $('#feedback').html(json);
}
