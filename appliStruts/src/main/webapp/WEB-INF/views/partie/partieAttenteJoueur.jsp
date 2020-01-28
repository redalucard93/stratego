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
    <title>Partie attente composition adversaire</title>
    <sj:head jqueryui="true" jquerytheme="start" />
    <sb:head includeScripts="true" includeScriptsValidation="false"/>
    <link href="styles/composition.css" rel="stylesheet">
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>
</head>
<body>

<s:include value="../salon/bandeau.jsp"/>

<h2>En attente de la composition des pièces du second joueur</h2>

<div class="container">
    <div class="row">
        <div id="j1" class="col-lg-offset-4 col-lg-2">
            <p>Joueur 1</p>
            <p style="margin-bottom: 22px;">Prêt</p>
            <img height=50 width=50 src="img/check.png" />
        </div>
        <div id="j2" class="col-lg-2">
            <p>Joueur 2</p>
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
    $(function() {

        setInterval(Requete, 2000);
        function Requete() {

            jQuery.ajax({
                url: 'json/ajaxCheckCompositionJoueur',
                type: "POST",
                global: false,
                datatype: 'json',
                contenttype: "application/json",
                success: function (response) {
                    if (response.compositionValide) {
                        document.getElementById('etatJ2').innerHTML = "Prêt";
                        document.getElementById('loading').src = "img/check.png";
                        document.getElementById('loading').height = "50";
                        document.getElementById('loading').width = "50";
                        location.href = "demarrerJeu.action";
                    }
                }
            });
        }
    });

</script>
</body>
</html>
