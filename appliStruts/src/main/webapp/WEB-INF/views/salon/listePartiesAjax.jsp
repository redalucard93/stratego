<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<s:set var="lesParties" value="#session.listeParties"></s:set>
<table width="100%">
    <tr height="30px">
        <th colspan="4" style="font-size:large; text-align: center;">
            <s:text name="afficher.parties" />
        </th>
    </tr>

    <tr height="30px">
        <th style="text-align: center;" width="25%">Joueur1</th>
        <th style="text-align: center;" width="25%">Joueur2</th>
        <th style="text-align: center;" width="25%">Rejoindre</th>
        <th style="text-align: center;" width="25%">Observer</th>
    </tr>

    <s:iterator value="#lesParties" var="unePartie" status="numElement">
        <tr>
            <td style="text-align: center;">
                <s:property value="#unePartie.getJoueurCreateur.getPseudo" />
            </td>
            <td style="text-align: center;">
                <s:if test="#unePartie.getJoueur2 != null">
                <s:property value="#unePartie.getJoueur2.getPseudo" />
                </s:if>
                <s:else>
                    <s:text name="partie.pasDeJoueur2"></s:text>
                </s:else>
            </td>
            <td style="text-align: center;">
                <s:if test="#unePartie.getJoueur2 == null">
                <s:url value="rejoindrePartie.action" var="rejoindrePartie">
                    <s:param name="joueurCreateur" value="#unePartie.getJoueurCreateur.getPseudo"></s:param>
                </s:url>

                <s:a href="%{rejoindrePartie}">
                    <s:text name="partie.rejoindre"></s:text>
                </s:a>
                </s:if>
                <s:else>
                    <s:text name="partie.complete"></s:text>
                </s:else>
            </td>
            <td style="text-align: center;">
                <s:if test="#unePartie.getPlateau != null">
                <s:url value="observerPartie.action" var="observerPartie">
                    <s:param name="joueurCreateur" value="#unePartie.getJoueurCreateur.getPseudo"></s:param>
                </s:url>

                <s:a href="%{observerPartie}">
                    <s:text name="partie.observer"></s:text>
                </s:a>
                </s:if>
                <s:else>
                    <s:text name="partie.nonLancee"></s:text>
                </s:else>
            </td>
        </tr>
    </s:iterator>
</table>