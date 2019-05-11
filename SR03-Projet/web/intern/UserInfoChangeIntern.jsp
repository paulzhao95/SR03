<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="../css/myStyle.css">
    <title>Créer votre compte</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css">
    <style type="text/css">
        html,
        body {
            height: 100%;
        }
        html {
            display: table;
            margin: auto;
        }
        body {
            background-image: url("../picture/backgroud_login.jpeg");
        }
        body {
            display: table-cell;
            vertical-align: middle;
            color:#404d5b;
        }
        .margin {
            margin: 0 !important;
        }
        .card-panel{ min-width:350px;}
    </style>
</head>
<body class="blue">

<%String user = "Zhaolongen";
    String email = "zhao@126.com";
    String password = "111";
    String telephone = "00000000";
    String company = "aaa";%>


<div id="login-page" class="row">
    <div class="col s12 z-depth-6 card-panel">
        <form action="../test.jsp" class="login-form">
            <div class="row">
                <div class="input-field col s12 center">
                    <img src="../picture/UTC_logo.png" alt="" class="responsive-img valign profile-image-login">
                    <p class="center login-form-text">Changez l'information de votre compte</p>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="username" name = "username" type="text" class="validate" onchange="changeValue()" value = <%=user%>>
                    <label for="username" class="center-align"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-communication-email prefix"></i>
                    <input id="email" name = "email" type="email" class="validate" onchange="changeValue()" value = <%=email%>>
                    <label for="email" class="center-align"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-action-lock-outline prefix"></i>
                    <input id="password" name = "password" type="password" onchange="changeValue()" class="validate" value = <%=password%>>
                    <label for="password"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-action-lock-outline prefix"></i>
                    <input id="password-again" type="password" onchange="changeValue()" value = <%=password%>>
                    <label for="password-again"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="telephone" name = "telephone" type="text" class="validate" onchange="changeValue()" value = <%=telephone%>>
                    <label for="telephone"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="company" name = "company" type="text" onchange="changeValue()" class="validate" value = <%=company%>>
                    <label for="company"></label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input type="submit" value = "Validation" class = "btn waves-effect waves-light col s12">
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    function changeValue(obj){
        $(obj).attr("value",$(obj).val());
    }
</script>
</body>
</html>