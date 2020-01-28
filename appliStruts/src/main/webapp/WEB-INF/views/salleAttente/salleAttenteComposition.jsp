<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 23/11/2016
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Composition</title>
</head>
<body>

<s:include value="../salon/bandeau.jsp"/>

<s:actionerror></s:actionerror>

<s:form action="validationComposition" theme="simple">
    <s:set var="lesPieces" value="#session.listePiece"></s:set>
    <s:set var="listChoix" value="#session.listChoix"></s:set>

    <table>
        <tr>
            <s:iterator value="#lesPieces" var="unePiece" status="numElement">
                <s:if test="#numElement.index == 10 || #numElement.index == 20 ||
                            #numElement.index == 30" >
                    </tr><tr>
                </s:if>

                <td>
                    <s:select label=""
                              headerKey="-1" headerValue="-- choix --"
                              list="listChoix"
                              listKey="puissance"
                              listValue="name"
                              name="lesPieces[%{#numElement.index}]"
                              value="unePiece"
                    />
                </td>

            </s:iterator>
        </tr>
    </table>
    <s:submit/>
</s:form>

<scrip>

</scrip>

</body>
</html>
