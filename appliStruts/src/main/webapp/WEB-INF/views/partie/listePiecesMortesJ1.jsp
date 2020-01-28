<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="lesPiecesMortesJ1" value="#session.listePiecesMortesJ1"></s:set>

<table>

    <tr height="100px">
        <th colspan="2" style="font-size:medium; text-align: center;" width="100%">
            <s:text name="afficher.piecesMortesJ1" />
        </th>
    </tr>

    <tr>
        <s:iterator value="#lesPiecesMortesJ1" var="unePiece" status="numElement">
        <s:if test="(#numElement.index % 2) == 0 && (#numElement.index) != 1">
    </tr>
    <tr>
        </s:if>
        <td width=50>
            <img height=50 width=50
                 src="img/image_piece/<s:property value="#unePiece.getTypePiece.getPuissance"/>.png" />
        </td>
        </s:iterator>
    </tr>

</table>

