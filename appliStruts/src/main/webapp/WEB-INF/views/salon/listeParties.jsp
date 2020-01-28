<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 23/11/2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div id="blockLesParties">

</div>

<script>

    (function() {
        listeParties();
    })();

    setInterval(listeParties,3000);
    function listeParties(){
        jQuery.ajax({
            url : 'listePartieAjax',
            type: "POST",
            dataType: "html",
            success: function(response) {
                jQuery("#blockLesParties").html(response);
            }
        });
    }

</script>