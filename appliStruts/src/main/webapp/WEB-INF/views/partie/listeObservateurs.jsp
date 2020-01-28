<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="lesObservateurs" value="#session.listeObservateurs"></s:set>

<table width="100%">
    <tr height="30px" width="100%">
        <th style="font-size:medium; text-align: center;" width="100%">
            <s:text name="afficher.observateurs" />
        </th>
    </tr>


    <s:iterator value="#lesObservateurs" var="unObservateur" status="numElement">
        <tr>
            <td style="text-align: center">
                <s:property value="#unObservateur.getPseudo" />
            </td>
        </tr>
    </s:iterator>

</table>

