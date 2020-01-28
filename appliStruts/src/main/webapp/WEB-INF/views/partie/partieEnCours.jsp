<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
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
    <sb:head includeScripts="true" includeScriptsValidation="false"/>
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
<p>A vous de jouer</p>

<div class="container" style="width:auto; height:auto;">
    <div class="row" style="margin: 20px 0;">
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color: #008080; border-radius: 10px;">
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
                            <td id="<s:property value="#uneCase.getX"/>.<s:property value="#uneCase.getY"/>"
                                    <s:if test="#uneCase.getPiece.getJoueur.getPseudo == #unPseudo">
                                        class="case"
                                    </s:if>
                            >

                                <s:if test="#uneCase.getPiece == null" >

                                    <s:if test="#uneCase.isLac">
                                        <a>
                                            <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" class="lac" src="img/image_piece/lac.png" />
                                        </a>
                                    </s:if>
                                    <s:else>
                                        <a>
                                            <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" class="terrain" src="img/image_piece/terrain.png" />
                                        </a>

                                    </s:else>

                                </s:if>
                                <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueurCreateur.getPseudo" >
                                    <a>
                                        <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;"
                                        <s:if test="#uneCase.getPiece.getJoueur.getPseudo != #unPseudo">
                                             class="ennemi" src="img/image_piece/croix.png" />
                                            </s:if><s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #unPseudo">
                                        class="myP" src="img/image_piece/<s:property value="#uneCase.getPiece.getTypePiece.getPuissance"/>.png" />
                                        </s:elseif>

                                    </a>
                                </s:elseif>
                                <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueur2.getPseudo" >
                                    <a>
                                        <img style="height: <s:property value="#tailleImg" />px;width: <s:property value="#tailleImg" />px;" <s:if test="#uneCase.getPiece.getJoueur.getPseudo != #unPseudo">
                                             class="ennemi" src="img/image_piece/croixb.png" />
                                            </s:if><s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #unPseudo">
                                        class="myP" src="img/image_piece/<s:property value="#uneCase.getPiece.getTypePiece.getPuissance"/>b.png" />
                                        </s:elseif>

                                    </a>
                                </s:elseif>

                            </td>
                        </s:iterator>
                    </tr>
                </s:iterator>

            </table>

        </div>
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color:#008080; border-radius: 10px;">
            <s:if test="#unePartie.getJoueurCreateur.getPseudo == #unPseudo" >
                <s:include value="listePiecesMortesJ2.jsp"/>
            </s:if>
            <s:else>
                <s:include value="listePiecesMortesJ1.jsp"/>
            </s:else>
        </div>
    </div>
    <s:if test="#unePartie.isPartiePublique">
    <div class="row" style="margin: 30px 0;">
        <div class="col-sm-2 col-sm-offset-4" style="padding: 1%; overflow:auto; height: 200px; background-color: #008080; border-radius: 10px;">
            <s:include value="listeObservateurs.jsp"/>
        </div>
    </div>
    </s:if>
</div>


<script>

    var elementClique = null;

    var caseAdj1 = null;
    var caseAdj2 = null;
    var caseAdj3 = null;
    var caseAdj4 = null;

    for (var i = 0; i <= 9; i++) {

        for (var z = 0; z <= 9; z++) {

            var img = document.getElementById(i + "." + z)

                img.addEventListener('click', function (e) {
                    // Permet de ne pas mettre d'event sur les cases qui n'ont pas l''attribute class myP
                    if(e.currentTarget.firstElementChild.firstElementChild.className == "myP") {
                        // Permet de ne pas mettre d'event sur les cases non mobile
                        var testMobilite = e.currentTarget.firstElementChild.firstElementChild.getAttribute("src");
                        if ( testMobilite != "img/image_piece/11.png" &&
                                testMobilite != "img/image_piece/11b.png" &&
                                testMobilite != "img/image_piece/0.png" &&
                                testMobilite != "img/image_piece/0b.png" &&
                                testMobilite !="img/image_piece/2.png"&&
                                testMobilite!="img/image_piece/2b.png")
                        {

                            e.target.setAttribute("style", "-webkit-filter:grayscale(150%)");

                            // On réinitialise le filter de l'ancienne pièce
                            if (elementClique != null)
                                elementClique.target.setAttribute("style", "-webkit-filter: none");

                            elementClique = e;

                            var position = e.currentTarget.id.split(".");
                            position[0] = parseInt(position[0]);
                            position[1] = parseInt(position[1]);

                            if (caseAdj1 != null) {
                                caseAdj1.setAttribute("style", "-webkit-filter: none")
                                caseAdj1 = null;
                            }

                            if (caseAdj2 != null) {
                                caseAdj2.setAttribute("style", "-webkit-filter: none")
                                caseAdj2 = null;
                            }

                            if (caseAdj3 != null) {
                                caseAdj3.setAttribute("style", "-webkit-filter: none")
                                caseAdj3 = null;
                            }

                            if (caseAdj4 != null) {
                                caseAdj4.setAttribute("style", "-webkit-filter: none")
                                caseAdj4 = null;
                            }


                            caseAdj1 = document.getElementById((position[0] + 1) + "." + (position[1]));
                            if (caseAdj1 != null) {
                                if (caseAdj1.firstElementChild.firstElementChild.className == "terrain" || caseAdj1.firstElementChild.firstElementChild.className == "ennemi") {
                                    caseAdj1.setAttribute("style", "-webkit-filter:sepia(60%)");
                                    var xCible = position[0] + 1;
                                    var yCible = position[1];
                                    var url = "<s:url action="deplacementJeu"></s:url>?xCible=" + xCible + "&yCible=" + yCible + "&xCase=" + position[0] + "&yCase=" + position[1] + "";
                                    caseAdj1.firstElementChild.href = url;
                                }
                            }


                            caseAdj2 = document.getElementById((position[0]) + "." + (position[1] - 1));
                            if (caseAdj2 != null) {
                                if (caseAdj2.firstElementChild.firstElementChild.className == "terrain" || caseAdj2.firstElementChild.firstElementChild.className == "ennemi") {
                                    caseAdj2.setAttribute("style", "-webkit-filter:sepia(60%)");
                                    xCible = position[0];
                                    yCible = position[1] - 1;
                                    url = "<s:url action="deplacementJeu"></s:url>?xCible=" + xCible + "&yCible=" + yCible + "&xCase=" + position[0] + "&yCase=" + position[1] + "";
                                    caseAdj2.firstElementChild.href = url;
                                }
                            }


                            caseAdj3 = document.getElementById((position[0] - 1) + "." + (position[1]));
                            if (caseAdj3 != null) {
                                if (caseAdj3.firstElementChild.firstElementChild.className == "terrain" || caseAdj3.firstElementChild.firstElementChild.className == "ennemi") {
                                    caseAdj3.setAttribute("style", "-webkit-filter:sepia(60%)");
                                    xCible = position[0] - 1;
                                    yCible = position[1];
                                    url = "<s:url action="deplacementJeu"></s:url>?xCible=" + xCible + "&yCible=" + yCible + "&xCase=" + position[0] + "&yCase=" + position[1] + "";
                                    caseAdj3.firstElementChild.href = url;
                                }
                            }


                            caseAdj4 = document.getElementById((position[0]) + "." + (position[1] + 1));
                            if (caseAdj4 != null) {
                                if (caseAdj4.firstElementChild.firstElementChild.className == "terrain" || caseAdj4.firstElementChild.firstElementChild.className == "ennemi") {
                                    caseAdj4.setAttribute("style", "-webkit-filter:sepia(60%)");
                                    xCible = position[0];
                                    yCible = position[1] + 1;
                                    url = "<s:url action="deplacementJeu"></s:url>?xCible=" + xCible + "&yCible=" + yCible + "&xCase=" + position[0] + "&yCase=" + position[1] + "";
                                    caseAdj4.firstElementChild.href = url;
                                }
                            }
                        }


                        else if(testMobilite !="img/image_piece/2.png"&&
                                testMobilite!="img/image_piece/2b.png"){




                            e.target.setAttribute("style", "-webkit-filter:grayscale(150%)");
                            if (elementClique != null)
                                elementClique.target.setAttribute("style", "-webkit-filter: none");

                            elementClique = e;

                            var position = e.currentTarget.id.split(".");
                            position[0] = parseInt(position[0]);
                            position[1] = parseInt(position[1]);
/*
                                function Requete() {

                                    jQuery.ajax({
                                        url: 'json/ajaxcheckCoordonneesEclereur',
                                        type: "POST",
                                        global: false,
                                        datatype: 'json',
                                        data: {x : position[0], y : position[1]},
                                        contenttype: "application/json",
                                        success: function (response) {

                                            var z = false;
                                            var i = 0;
                                            do {
                                                response.caseAccessible[i];
                                                i++;

                                            } while (z == false);
                                        }
                                    });

*/




                        }
                    }
                });


            }
    }

    setInterval(Requete, 15000);
    function Requete() {
        location.href = "changerTour.action";
        // alert("votre tour passé");
    }


</script>

</body>
</html>
