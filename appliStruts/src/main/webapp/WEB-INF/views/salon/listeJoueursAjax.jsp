<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set var="lesJoueurs" value="#session.lesJoueursConnectes"></s:set>

<table width="100%">
    <tr height="30px">
        <th style="font-size:large; text-align: center;" width="100%">
            <s:text name="afficher.joueurs" />
        </th>
    </tr>

        <s:iterator value="#lesJoueurs" var="unJoueur" status="numElement">
        <tr>
            <td style="text-align: center;" width="100%">
                <s:property value="#unJoueur.getPseudo" />
            </td>
        </tr>
        </s:iterator>

</table>
