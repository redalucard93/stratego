<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="value" uri="/struts-tags" %>

<s:set var="lesInvitations" value="#session.lesInvitations"></s:set>
<table width="100%" style="border-radius: 10px;">
    <tr height="30px">
        <th style="font-size:large; text-align: center;" width="100%">
            <s:text name="afficher.invitations" />
        </th>
    </tr>

    <s:iterator value="#lesInvitations" var="uneInvitation" status="numElement">
        <tr id="<s:property value="#numElement.index"/>">
             <td style="text-align: center;" width="100%">
                <s:property value="#uneInvitation.getPseudo"/> <s:text name="partie.demandeInvite"></s:text>
                 <s:set var="pseudoJ" value="#uneInvitation.getPseudo"/>
                  <s:form>
                    <s:hidden name="pseudoCreateurEnvoye" value="%{pseudoJ}" id="hidden"/>
                 </s:form>
                    <s:submit name="accepte" key="partie.invitation.accepte"  class="accepter"/>
                    <s:submit name="refuse" key="partie.invitation.refuse"  class="refuse"/>


            </td>
        </tr>
    </s:iterator>
</table>


