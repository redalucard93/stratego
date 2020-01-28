<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:set var="unePartie" value="#session.partie"></s:set>
<s:set var="plateau" value="#session.plateau"></s:set>


<div class="container" style="width:auto; height:auto;">
    <div class="row" style="margin: 20px 0;">
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color: #d2d2d2; border-radius: 10px;">

            <s:include value="listePiecesMortesJ1.jsp"/>

        </div>
        <div class="col-sm-6 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px;">
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
                            <img height=50 width=50 src="img/image_piece/lac.png" />
                        </s:if>
                        <s:else>
                            <a>
                                <img height=50 width=50 src="img/image_piece/terrain.png" />
                            </a>

                        </s:else>

                    </s:if>
                    <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueurCreateur.getPseudo" >
                        <img height=50 width=50 src="img/image_piece/croix.png" />
                    </s:elseif>
                    <s:elseif test="#uneCase.getPiece.getJoueur.getPseudo == #uneCase.getPiece.getJoueur.getPartie.getJoueur2.getPseudo" >
                        <img height=50 width="50" src="img/image_piece/croixb.png" />
                    </s:elseif>

                </td>
            </s:iterator>
        </tr>
    </s:iterator>

</table>

        </div>
        <div class="col-sm-1 col-sm-offset-1" style="padding: 1%; overflow:auto; height: 550px; background-color:#d2d2d2; border-radius: 10px;">

                <s:include value="listePiecesMortesJ2.jsp"/>

        </div>
    </div>
    <s:if test="#unePartie.isPartiePublique">
        <div class="row" style="margin: 30px 0;">
            <div class="col-sm-2 col-sm-offset-4" style="padding: 1%; overflow:auto; height: 200px; background-color: #d2d2d2; border-radius: 10px;">
                <s:include value="listeObservateurs.jsp"/>
            </div>
        </div>
    </s:if>
</div>