<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%--
  Created by IntelliJ IDEA.
  User: alucard
  Date: 22/11/2016
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>


    <head>

        <sj:head jqueryui="true" jquerytheme="start" />
        <title>Title</title>
        <sj:head jqueryui="true" jquerytheme="start" />

        <sb:head includeScripts="true" includeScriptsValidation="false"/>

        <link rel="stylesheet" href="<s:url value="/styles/bootstrap-superhero.css" />" type="text/css"/>


        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="pragma" content="no-cache">


        <meta http-equiv="X-UA-Compatible" content="IE=edge">


        <style type="text/css">
            body {
                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
            }
            .selected{
                box-shadow:0px 12px 22px 1px #ffff00;
            }
        </style>

    </head>


    <title>Composition</title>

    <body>

    <s:include value="../salon/bandeau.jsp"/>


    <s:actionerror></s:actionerror>

    <s:set var="cases" value="#session.plateauVide"></s:set>
    <s:set var="choix" value="#session.listePiece"></s:set>
    <s:set var="pseudoCreateur" value="#application.pseudoCreateur"></s:set>
    <s:set var="pseudo" value="#session.pseudo"></s:set>
    <div class="container" style="width:auto; height:auto;">
    <div class="row">
    <div class="col-sm-5 col-sm-offset-3" style="padding-top: 1%;padding-bottom: 0%; overflow:auto; height: 525px;">

    <table>
        <s:iterator value="#cases.plateauStrategoSansPieces" var="cases" status="numLigne">
            <tr id="itemRow" height="50px">
                <s:iterator value="#cases" var="uneCase" status="numColonne">
                    <s:if test="%{#uneCase.isLac}">
                        <td width=50>
                            <img height=50 width=50  src="img/image_piece/lac.png"/>
                        </td>
                    </s:if>
                    <s:else>
                        <s:if test="%{#numLigne.index>5}">
                            <td id="itemCol" width=50>
                                <img id="case"src="img/image_piece/terrain.png" height=50 width=50 numLigne="<s:property
                                 value = "#numLigne.index"/>" numColonne="<s:property value="#numColonne.index"/>"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td width=50 >
                                <img src="img/image_piece/terrain.png" height=50 width=50/>
                            </td>
                        </s:else>
                    </s:else>
                </s:iterator>
            </tr>
        </s:iterator>
      </table>
     </div>
    </div>
    <div class="row">
    <div class="col-sm-5 col-sm-offset-3" style="padding-top: 1.7%; padding-bottom: 0.2%;
            padding-left: 1.7%;padding-right: auto; overflow:auto; height: 250px; background-color:#008080; border-radius: 10px;">
    <table>
        <tr height="50px">
            <s:iterator value="#choix" var="unePuissance" status="numElement">
            <s:if test="#numElement.index == 10 || #numElement.index == 20 ||
                            #numElement.index == 30" >
        </tr><tr height="50px">
        </s:if>
        <td width=50>
            <s:if test="%{#pseudo==#pseudoCreateur}">
                <img id="piece" class="selecte" height=50 width=50 src="img/image_piece/<s:property value="#unePuissance" />.png"
                     data="<s:property value="#numElement.index"/> " alt="0" numLignePiece="null" numColonnePiece="null"
                     puissance="<s:property value="#unePuissance" />"/>
            </s:if>
            <s:else>
                <img id="piece" class="selecte" height=50 width=50 src="img/image_piece/<s:property value="#unePuissance"/>b.png"
                     data="<s:property value="#numElement.index"/> " alt="0" numLignePiece="null" numColonnePiece="null"
                     puissance="<s:property value="#unePuissance"/>"/>
            </s:else>
        </td>
        </s:iterator>
     </tr>
     </table>
     </div>
    </div>

    <div class="col-sm-12" style="padding-bottom: 0.5%; padding-top: 0.5%"></div>

    <div class="row">
    <div id="envoyer" class="col-sm-1 col-md-1 col-sm-offset-5">
    <s:form method = "POST" action="validationComposition" cssClass="horizontal-form">
        <s:hidden name="lesPieces" value=""/>
        <s:submit key="validation" onclick="submitValues();" cssClass="form-control"
                  elementCssClass="col-sm-1 col-md-1"/>
    </s:form>
    </div>
    </div>
    </div>
</body>

<script>
    /**$(window).load(function() {
        $("#envoyer").hide().css("visibility", "hidden");
    });**/
    var element;
    var caseApermuter;
    var PlacerPiece=0;
    var PermutterPieceCase=0;
    var permutationImage=0;
    var compteurPlacement=0;

    $('body').on('click','#piece',function(){

        if($(this).attr('data')==$(element).attr('data')) {

            if($(element).attr('alt')=="1") {
                PlacerPiece = 0;
                PermutterPieceCase = 1;
            }
            element = this;
            permutationImage=1;
        }
        else {
            if(($(element).attr('id')=="piece") && ($(element).attr('alt')=="1")&&($(event.target).attr('alt')=="1")&&(permutationImage==1)){
                permuter();
                permutationImage=0;
            }
            else {
                $(element).removeClass("selected");
                $(this).addClass("selected");
                element = this;

                if (PlacerPiece == 0 && PermutterPieceCase == 0) {

                    PlacerPiece = 1;
                    PermutterPieceCase = 0;

                }
                else if (PlacerPiece == 1 && PermutterPieceCase == 1) {
                    PlacerPiece = 0;
                    PermutterPieceCase = 1;
                }
            }
            permutationImage=1;
        }});

    $('body').on('click','#case',function() {

                if (PlacerPiece == 1 && PermutterPieceCase == 0) {
                    {
                        if($(element).attr('alt')=="0") {
                            placer();
                            PlacerPiece = 0;
                            PermutterPieceCase = 0;

                            $(element).attr('alt',"1");
                        }
                        else{
                            permuter();
                            PlacerPiece =1;
                            PermutterPieceCase=1;
                        }
                    }
                }
                else if (PlacerPiece == 0 && PermutterPieceCase==1) {
                    if($(element).attr("alt")=="1"){
                        permuter();
                        PlacerPiece = 1;
                        PermutterPieceCase = 1;
                    }
                    else{
                        placer();
                        PlacerPiece=0;
                        PermutterPieceCase=0;
                        $(element).attr('alt',"1");
                    }
                }
                permutationImage=0;

            }
    );
    function placer(){

        numCol =  $(event.target).attr('numColonne');
        numLigne = $(event.target).attr('numLigne');


        $(event.target).replaceWith(element);

        $(element).attr('numLignePiece',numLigne);
        $(element).attr('numColonnePiece',numCol);
        compteurPlacement++;
        if(compteurPlacement==40){

            $("#envoyer").show().css("visibility", "visible");


        }


    }
    function permuter(){

        numCol =  $(event.target).attr('numColonne');
        numLigne = $(event.target).attr('numLigne');

        caseApermuter = $(event.target).clone(true);
        $(element).replaceWith($(caseApermuter));
        $(event.target).replaceWith(element);

        $(element).attr('numLignePiece',numLigne);
        $(element).attr('numColonnePiece',numCol);

    }

    function submitValues(){
        var i=0;
        var lignesValue=[];
        var colonnesValues=[] ;
        var puissances=[];

        $("img[id='piece']").each(function(index,element) {
            puissances[i]= $(element).attr('puissance');
            i++;
        });
        $('input[name="lesPieces"]').val(puissances.toString());
    }

</script>
</html>
