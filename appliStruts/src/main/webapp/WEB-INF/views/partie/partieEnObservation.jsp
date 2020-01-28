<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>


<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 16/12/2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <sj:head jqueryui="true" jquerytheme="start" />
    <title>Observation partie</title>
    <sj:head jqueryui="true" jquerytheme="start" />
    <title>Stratego - Online</title>
    <sb:head includeScripts="true" includeScriptsValidation="false"/>
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>
    <link href="styles/jeu.css" rel="stylesheet">
</head>
<body>

<s:include value="../salon/bandeau.jsp"/>

<div id="blockPartieEnObservation">

</div>

<script>

    (function() {
        Requete10();
    })();

    setInterval(Requete10,3000);
    function Requete10(){

        jQuery.ajax({
            url : 'partieEnObservationAjax',
            type: "POST",
            dataType: "html",
            success: function(response) {
                jQuery("#blockPartieEnObservation").html(response);
            }

        });
    }

</script>
</body>
</html>
