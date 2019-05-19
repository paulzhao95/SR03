<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="css/myStyle.css">
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
		background-image: url("picture/backgroud_login.jpeg");
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
	<div id="login-page" class="row">
	    <div class="col s12 z-depth-6 card-panel">
	      <form action="test.jsp" class="login-form">
	        <div class="row">
	          <div class="input-field col s12 center">
	            <img src="picture/UTC_logo.png" alt="" class="responsive-img valign profile-image-login">
	            <p class="center login-form-text">Cr&eacute;er votre compte</p>
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-social-person-outline prefix"></i>
	            <input id="username" name = "user.name" type="text" class="validate" placeholder="User Name">
	            <label for="username" class="center-align"></label>
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-communication-email prefix"></i>
	            <input id="email" name = "user.email" type="email" class="validate" placeholder="Email">
	            <label for="email" class="center-align"></label>
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-action-lock-outline prefix"></i>
	            <input id="password" name = "user.password" type="password" class="validate" placeholder="Password">
	            <label for="password"></label>
	          </div>
	        </div>
	        <div class="row margin">
	          <div class="input-field col s12">
	            <i class="mdi-action-lock-outline prefix"></i>
	            <input id="password-again" type="password" placeholder="Re-type password">
	            <label for="password-again"></label>
	          </div>
	        </div>
			  <div class="row margin">
				  <div class="input-field col s12">
					  <i class="mdi-social-person-outline prefix"></i>
					  <input id="telephone" name = "user.tel" type="text" class="validate" placeholder="T&eacute;l&eacute;phone">
					  <label for="telephone"></label>
				  </div>
			  </div>
			  <div class="row margin">
				  <div class="input-field col s12">
					  <i class="mdi-social-person-outline prefix"></i>
					  <input id="company" name = "user.company" type="text" class="validate" placeholder="Company">
					  <label for="company"></label>
				  </div>
			  </div>
			  <div class="row margin">
				  <div class="input-field col s12">
					  <i class="mdi-social-person-outline prefix"></i>
					  <input type="radio" id="Amdin" name="user.status" value="Admin"
							 checked>
					  <label for="Amdin">Amdin</label>
					  <input type="radio" id="Intern" name="user.status" value="Intern">
					  <label for="Intern">Intern</label>
				  </div>
			  </div>
	        <div class="row">
	          <div class="input-field col s12">
				<input type="submit" value = "S'inscrire" class = "btn waves-effect waves-light col s12">
			</div>
	          <div class="input-field col s12">
	            <p class="margin center medium-small sign-up">D&eacute;ja avoir le compte? <a href="index.jsp">LOG IN</a></p>
	          </div>
	        </div>
	      </form>
	    </div>
	  </div>
</body>
</html>