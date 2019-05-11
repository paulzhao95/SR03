<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
	
	<title>LOG IN</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css">
	<link type="text/css" rel="stylesheet" href="css/myStyle.css">
	<style>
	html,body {
	height: 100%;
	}
	html {
	display: table;
	margin: auto;
	}
	body {
	display: table-cell;
	vertical-align: middle;
	color:#404d5b;
	}
	body {
	background-image: url("picture/backgroud_login.jpeg");
	}</style>
</head>
<body class="red">
	<div id="login-page" class="row">
	    <div class="col s12 z-depth-6 card-panel">
	      <form action="test.jsp" class="login-form">
	        <div class="row">
	          <div class="input-field col s12 center">
	            <img src="picture/UTC_logo.png" alt="" class="responsive-img valign profile-image-login">
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-social-person-outline prefix"></i>
	            <input class="validate" id="email" type="email" placeholder="Email">
	            <label for="email" data-error="wrong" data-success="right" class="center-align"></label>
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-action-lock-outline prefix"></i>
	            <input id="password" type="password" placeholder="Password">
	            <label for="password"></label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s12">
	            <input type="submit" value = "LOG IN" class = "input_control">
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6 m6 l6">
	            <p class="margin medium-small"><a href="SignUp.jsp">Cr&eacute;er votre compte</a></p>
	          </div>
			</div>
	      </form>
	    </div>
	  </div>
	  </div>
</body>
</html>