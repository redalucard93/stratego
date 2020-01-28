<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 22/11/2016
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <sj:head jqueryui="true" jquerytheme="start" />
    <title>Connexion</title>
    <sb:head includeScripts="true" includeStyles="false" includeScriptsValidation="false"/>
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>


    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="pragma" content="no-cache">


    <meta http-equiv="X-UA-Compatible" content="IE=edge">


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


            <s:form action="login" method="POST" validate="true" theme="bootstrap" cssClass="form-horizontal"
                    label="Connexion">
                <s:actionerror/>
                <div class="form-group">

                    <s:textfield elementCssClass="col-sm-5"
                                 name="pseudo" key="form.login.pseudo"
                                 cssClass="form-control"
                    />
                </div>
                <div class="form-group">
                    <s:password elementCssClass="col-sm-5"
                                name="password" key="form.login.password" cssClass="form-control"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-6 col-md-2">
                        <s:submit name= "submit" key="form.login.submit" elementCssClass="col-sm-2 col-md-2 "
                                  cssClass="form-control"/>
                    </div>
                </div>
            </s:form>
            <s:a action="gotoRegister">
                <s:text name="form.register.register"></s:text>
            </s:a>
        </div>
    </div>
</div>
</body>
<footer/>

</html>