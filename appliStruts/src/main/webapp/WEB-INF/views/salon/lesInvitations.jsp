<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>


<div id="blockLesInvitations">

</div>
<div id="blockPasswordPartieprive">
    <s:form method="POST" action="rejoindrePartie" theme="bootstrap" cssClass="form-horizontal">
        <s:actionerror/>
        <div class=" form-group">
            <s:password name="passwordPartie" elementCssClass="col-sm-3"
                         key="PartiePrive.motDePasse" cssClass="form-control"/>

        </div>

        <div class="form-group">
            <div class="col-md-2">
                <s:submit name= "partie.prive.rejoindre" key="partie.privee.rejoindre"
                          cssClass="form-control"/>
            </div>
        </div>
        <s:hidden name="joueurCreateur" value="" id="joueurCreateur"/>
    </s:form>
</div>

<script>
    $('#blockPasswordPartieprive').hide().css("visibility", "hidden");

    (function() {
        lesInvitations();
    })();

    setInterval(lesInvitations,3000);
    function lesInvitations(){
        jQuery.ajax({
            url : 'lesInvitationsAjax',
            type: "POST",
            dataType: "html",
            success: function(response) {
                jQuery("#blockLesInvitations").html(response);
                $(".accepter").click(function() {
                        $("#blockPasswordPartieprive").show().css("visibility", "visible");

                    $("#joueurCreateur").val($("#hidden").val());

                });

                $(".refuse").click(function (){
                    jQuery.ajax({
                        url :  'json/refuserInvitation',
                        data: {joueurCreateur : $("#hidden").val()},
                        type : "GET",
                        success:function(response2){
                            $(".refuse").show().css("visibility", "hidden");
                            $(".accepter").show().css("visibility", "hidden");
                                                   }
                    });}


                );
                }
        });
    }
    function validation(){

    }

</script>
