<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
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
    <title>Partie attente du joueur adversaire</title>
    <sj:head jqueryui="true" jquerytheme="start" />

    <sb:head includeScripts="true" includeScriptsValidation="false"/>
    <link href="styles/composition.css" rel="stylesheet">
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>

    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="pragma" content="no-cache">
</head>
<body>
<s:set var="pseudo" value="#session.pseudo"/>

<s:include value="../salon/bandeau.jsp"/>

<h2>Partie en attente du deuxièmeJoueur</h2>


<div class="container">
    <div class="row">
        <div id="j1" class="col-lg-offset-4 col-lg-2">
            <div id="pseudo"><s:property value="#session.pseudo"/></div>
            <p style="margin-bottom: 22px;">Prêt</p>
            <img height=50 width=50 src="img/check.png" />
        </div>
        <div id="j2" class="col-lg-2">
            <p id="message"></p>
            <p id="etatJ2" style="margin-bottom: 22px;">En attente</p>
            <img id="loading" src="img/loading.gif" />
        </div>
    </div>
    <div class="row" style="margin-top: 10px;">
        <div class="col-lg-offset-4 col-lg-4">
            <div id="blockQuitterPartie" style="text-align: center;" >
                <s:form action="quitterPartie">
                    <s:submit cssClass="btn-danger" key="partie.quitter" />
                </s:form>
            </div>
        </div>
    </div>
</div>


<script>


     var data2 = JSON.stringify({pseudo : $("#pseudo").val()});
      data = {
          pseudo:$("#pseudo").text()
      };

     setInterval(Requete, 2000);
      function Requete() {

          jQuery.ajax({
              url: 'json/ajaxCheckDeuxiemeJoueur',
              type: "POST",
              global: false,
              data : data,
              datatype: 'json',
              contenttype:"application/json",
              success: function (response) {

                  if (response.pseudo2 != "null") {

                  $("#message").text(((response.pseudo2)));
                   document.getElementById('etatJ2').innerHTML = "Prêt";
                   document.getElementById('loading').src = "img/check.png";
                   document.getElementById('loading').height = "50";
                   document.getElementById('loading').width = "50";
                   location.href = "creationComposition.action";
                 }

                  if(response.joueurArefuser){
                      alert("le joueur a refuser");
                      location.href ="quitterPartie.action";
                  }




          }});

}


</script>



</body>
</html>
