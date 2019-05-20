<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>
<%String user = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String telephone = request.getParameter("tel");
    String company = request.getParameter("company");
    String creating_time = "2012";
    Boolean status = request.getParameter("status").equals("true") ;
    String type = request.getParameter("type");%>
<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                    <div class="personalnformation-content-right">
                        <p class="personalnformation-content-right-p">Nom: <%=user%> </p>
                        <p class="personalnformation-content-right-p">Email: <%=email%> </p>
                        <p class="personalnformation-content-right-p">Password: <%=password%> </p>
                        <p class="personalnformation-content-right-p">Telephone: <%=telephone%> </p>
                        <p class="personalnformation-content-right-p">Company: <%=company%> </p>
                        <p class="personalnformation-content-right-p">Status: <%=status%> </p>
                        <p class="personalnformation-content-right-p">Create Time: <%=creating_time%></p>
                        <p class="personalnformation-content-right-p">Type: <%=type%></p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <a href="http://localhost:8080/SR03_Projet_war_exploded/default/updateUser?user.name=<%=user%>&user.password=<%=password%>&user.email=<%=email%>&user.tel=<%=telephone%>&user.company=<%=company%>&user.type=<%=type%>&user.status=<%=status%>" class="link_class">Change Status</a>
                    <br>
                    <a href="User-Topic_List.jsp" class="link_class">RETURN</a>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    $('.tab-button').click(function() {
        var tab = $(this).data('tab')
        $(this).addClass('cur').siblings('.tab-button').removeClass('cur');
        $('#tab-' + tab + '').addClass('active').siblings('.tab-item').removeClass('active');
    });

</script>
</body>
</html>

