<%@ taglib prefix="s" uri="/struts-tags" %>

<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 23/11/2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div id="blockLesJoueurs">

</div>

<script>

    (function() {
        Requete2();
    })();

    setInterval(Requete2,3000);
    function Requete2(){

        jQuery.ajax({
            url : 'listeJoueursConnectesAjax',
            type: "POST",
            dataType: "html",
            success: function(response) {
                jQuery("#blockLesJoueurs").html(response);
            }

        });
    }

</script>
