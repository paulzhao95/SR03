<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Start doing questions</title>
</head>
<body>
<div style="text-align:center;">
    <!-- 这里传给action index为1，开始答题，-->
    <a href="http://localhost:8080/SR03_Projet_war_exploded/internStartAttempt.action?questionnaireId=<s:property value='questionnaireId'/>&topic=<s:property value='topic'/>" style="display:block;margin:40px auto 0 auto; line-height:32px; font-size:20px; font-weight:bold">
        Please complete the test questions carefully. <br/>
        Are you ready?<br/><br/>
        <span><img src="../picture/start.jpg"></span></a>
</div>

</body>
</html>