<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 23/11/2016
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <sj:head jqueryui="true" jquerytheme="start" />
    <title>Stratego - Online</title>
    <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>
    <link href="styles/jeu.css" rel="stylesheet">
</head>
<body>

<s:include value="../salon/bandeau.jsp"/>

<s:set var="tailleImg" value="50"></s:set>

<s:set var="unePartie" value="#session.partie"></s:set>
<s:set var="unPseudo" value="#session.pseudo"></s:set>
<s:set var="plateauJ1" value="#session.plateauJ1"></s:set>
<s:set var="plateauJ2" value="#session.plateauJ2"></s:set>

<s:if test="#unePartie.getJoueurCreateur.getPseudo == #unPseudo" >
    <s:set var="plateau" value="#plateauJ1"></s:set>
</s:if>
<s:else>
    <s:set var="plateau" value="#plateauJ2"></s:set>
</s:else>
<p>Votre adversaire est en train de jouer</p>

<div class="container" style="width:auto; height:auto;">
    <div class="row" style="margin: 20px 0;">
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color: #d2d2d2; border-radius: 10px;">
            <s:if test="#unePartie.getJoueurCreateur.getPseudo == #unPseudo" >
                <s:include value="listePiecesMortesJ1.jsp"/>
            </s:if>
            <s:else>
                <s:include value="listePiecesMortesJ2.jsp"/>
            </s:else>
        </div>
        <div class="col-sm-5 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px;">

<table>
    <s:iterator value="#plateau" var="ligne" status="numLigne">

        <tr>
            <s:iterator value="ligne" var="uneCase" status="numCase">
                <td id="<s:property value="#uneCase.getX"/>.<s:property value="#uneCase.getY"/>">

                    <s:if test="#uneCase.getPiece == null" >

                        <s:if test="#uneCase.isLac">
                                <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" class="lac" src="img/image_piece/lac.png" />
                        </s:if>
                        <s:else>
                                <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" class="terrain" src="img/image_piece/terrain.png" />

                        </s:else>

                    </s:if>
                    <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueurCreateur.getPseudo" >
                            <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" <s:if test="#uneCase.getPiece.getJoueur.getPseudo != #unPseudo">
                                 class="ennemi" src="img/image_piece/croix.png" />
                                </s:if><s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #unPseudo">
                            class="myP" src="img/image_piece/<s:property value="#uneCase.getPiece.getTypePiece.getPuissance"/>.png" />
                            </s:elseif>

                    </s:elseif>
                    <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueur2.getPseudo" >
                            <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" <s:if test="#uneCase.getPiece.getJoueur.getPseudo != #unPseudo">
                                 class="ennemi" src="img/image_piece/croixb.png" />
                                </s:if><s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #unPseudo">
                            class="myP" src="img/image_piece/<s:property value="#uneCase.getPiece.getTypePiece.getPuissance"/>b.png" />
                            </s:elseif>

                    </s:elseif>

                </td>
            </s:iterator>
        </tr>
    </s:iterator>

</table>
            </div>
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color:#d2d2d2; border-radius: 10px;">
            <s:if test="#unePartie.getJoueurCreateur.getPseudo == #unPseudo" >
                <s:include value="listePiecesMortesJ2.jsp"/>
            </s:if>
            <s:else>
                <s:include value="listePiecesMortesJ1.jsp"/>
            </s:else>
        </div>
    </div>
    <s:if test="#unePartie.isPartiePublique()">
    <div class="row" style="margin: 30px 0;">
        <div class="col-sm-2 col-sm-offset-4" style="padding: 1%; overflow:auto; height: 200px; background-color: #d2d2d2; border-radius: 10px;">
            <s:include value="listeObservateurs.jsp"/>
        </div>
    </div>
    </s:if>
</div>

<script>

        setInterval(Requete, 2000);
        function Requete() {

            jQuery.ajax({
                url: 'json/ajaxCheckTour',
                type: "POST",
                global: false,
                datatype: 'json',
                contenttype: "application/json",
                success: function (response) {
                    if (response.checkTour) {
                        location.href = "partieEnCours.action";
                    }
                }
            });
        }


</script>

</body>
</html>
