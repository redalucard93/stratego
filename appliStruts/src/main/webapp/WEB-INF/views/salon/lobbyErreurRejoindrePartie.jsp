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
    <title>Salon Stratégo</title>
    <sb:head includeScripts="true" includeStyles="false"/>
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

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Stratego</a>
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
            <li class="active"><s:a action="gotoLogout">Deconnexion</s:a></li>
               <li class="dropdown" id="dropdownn">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Creer Partie
                    <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><s:a action="creerPartiePublique">Partie publique</s:a></li>
                    <li id="btnShowBlockPwd"><a href="#"><s:text name="afficher.partiePrive"/></a></li>
                </ul>
            </li>
            </ul>
    </div>
</nav>

<div class="container" style="width:auto; height:50px;">
    <div class="row">
        <div class="col-sm-12">
            Bienvenu <s:property value="#session.pseudo"></s:property>
        </div>
    </div>
</div>
<div class="container-fluid" id="blockPassword">
    <div class="row" >
        <div class="col-md-7">

            <s:form method="POST" action="creerPartiePrivee" theme="bootstrap" cssClass="form-horizontal">
            <s:actionerror/>
                <div class=" form-group">
                    <s:password name="passwordPartie" elementCssClass="col-sm-4"
                                 key="PartiePrive.motDePasse" cssClass="form-control"/>
                </div>
                <div class="form-group">
                    <s:select name="selectJoueurList" id="select" list="{}"  label ="selection joueur" multiple="true"
                              cssClass="form-control"/>

                </div>
        </div>
    </div>
            <div class="row form-group">
                <div class="col-md-1">
                    <s:submit name= "partie.prive.creer" key="partie.privee.cree"
                              cssClass="form-control" elementCssClass="col-sm-2"/>


                </div>
            </s:form>
        <div class="col-md-1">
            <button name="annuler" type="button" onclick="annuler()" Class="form-control" >annuler</button>
        </div>
    </div>
</div>
<div class="container" style="width:auto; height:auto;">
    <div class="row" style="margin: 20px 0;">
        <div class="col-sm-4 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 300px; background-color: #008080; border-radius: 10px;">
            <s:include value="listeJoueurs.jsp"/>
        </div>
        <div class="col-sm-5 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 300px; background-color:#008080; border-radius: 10px;">
            <s:include value="listeParties.jsp"/>
        </div>
    </div>
    <div class="row" style="margin: 30px 0;">
        <div class="col-sm-10 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 300px; background-color: #008080; border-radius: 10px;">
            <div id="blockPasswordPartieprive">
                <s:form method="POST" action="rejoindrePartie" theme="bootstrap" cssClass="form-horizontal">
                <s:set value="#session.joueurCreateur" var="pseudoJoueurCreateur" />
                    <s:actionerror/>
                    <div class=" form-group">
                        <s:password name="passwordPartie" elementCssClass="col-sm-3"
                                    key="PartiePrive.motDePasse" cssClass="form-control"/>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2">
                            <s:hidden name="joueurCreateur" value="%{pseudoJoueurCreateur}"/>
                            <s:submit name= "partie.prive.rejoindre" key="partie.privee.rejoindre"
                                      cssClass="form-control"/>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>



</body>
<script>
    $(window).load(function() {
     $("#blockPassword").hide().css("visibility", "hidden")
 });

     var myBtn = document.getElementById('btnShowBlockPwd');
      myBtn.addEventListener('click', function(e) {
    $("#blockPassword").show().css("visibility", "visible");
});
 setInterval(req, 1500);

 function req(){
     $.getJSON(
             'json/ajaxCheckJoueurs',
             data={ pseudo : "<s:property value="#session.pseudo"/>" }
             ,
             function (JsonResponse) {

                if  (JsonResponse.pseudos.length!=$('#select').children('option').length){
                 $('#select').find('option').remove();
                 $.each(JsonResponse.pseudos, function(key,value) {
                     $('#select').append("<option value = "+value+">"+value+"</option>");
                         }
                 );
             }}
     );
 }

 function annuler(){
     $("#blockPassword").hide();
 }
</script>
</html>



