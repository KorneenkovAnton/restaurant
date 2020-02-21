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
            display(data);
            window.location.href = "main.html";
        },
        error:function (e) {
            console.log("ERROR",e);
            display(e);
        },
        done:function (e) {
            display(e);
            enableSearchButton(true);
        }
    });
}
function enableSearchButton(flag) {
    $("#login-btn").prop("disabled", flag);
}

function display(data) {
    var json = "<h4>Ajax Response</h4><pre>"
        + JSON.stringify(data, null, 4) + "</pre>";
    $('#feedback').html(json);
}
