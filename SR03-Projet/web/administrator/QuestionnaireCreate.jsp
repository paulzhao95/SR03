<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>Changement du Topic</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle2.css" />
</head>

<body>
<%String topic = request.getParameter("topic");%>
<img class="bgone" src="../picture/1.jpg" />
<img class="pic" src="../picture/a.png" />

<div class="table">
    <%String hint = "Name of Questionnaire";%>
    <div class="wel">Cr&eacute;er une nouvelle questionnaire</div>
    <form action="../test.jsp">
        <div class="password">
            <div id="yonghu"><img src="../picture/yhm.png" /></div>
            <input type="text" name="questionnaire.name" placeholder=<%=hint%>/>
            <input type="hidden" name="questionnaire.topic" value=<%=topic%>/>
        </div>
        <input class="btn" type="submit" name="Validation" value="Validation" />
    </form>
</div>

</body>
</html>
