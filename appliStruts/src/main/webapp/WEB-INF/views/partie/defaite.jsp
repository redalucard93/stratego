<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 23/11/2016
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Partie attente composition adversaire</title>
    <sb:head includeScripts="true" includeScriptsValidation="false"/>
</head>
<body>
<h1 style="text-align: center;">Vous avez gagnez</h1>
<div id="blockQuitterPartie" style="text-align: center;" >
    <s:form action="home">
        <s:submit cssClass="btn-danger" key="partie.quitter" />
    </s:form>
</div>
</body>
</html>
