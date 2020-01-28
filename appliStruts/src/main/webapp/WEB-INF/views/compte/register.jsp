<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 06/10/2016
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="struts2, twitter, bootstrap, plugin, showcase" />
    <meta name="description" content="A Showcase for the Struts2 Bootstrap Plugin" />
    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <sb:head includeScripts="false" includeScriptsValidation="false"/>
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>

    <style type="text/css">
        body {
            padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
        }
    </style>
</head>
    <body>

    <s:include value="bandeauStratego.jsp"/>

    <div class="container">
        <div class="row">
            <div class="col-md-offset-2 col-md-8">


        <s:form action="Register" method="POST" validate="true" theme="bootstrap" cssClass="well form-vertical"
                label="Inscription">
            <s:actionerror/>
         <div class="form-group">

            <s:textfield name="pseudo" key="form.register.pseudo"  cssClass="form-control"/>
         </div>
         <div class="form-group">

            <s:password name="password" key="form.register.password" cssClass="form-control"/>
         </div>
         <div class="form-group">

            <s:password name="passwordConfirm" key="form.login.passwordConfirm" cssClass="form-control"/>
         </div>
         <div class="form-group">
         <div class="col-sm-offset-3 col-md-2">
            <s:submit key="form.register.register" cssClass="form-control"/>
         </div>
         </div>
        </s:form>
       </div>
      </div>
     </div>
    </body>
</html>

