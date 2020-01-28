<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Stratego</a>
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
            <li class="active"><s:a action="gotoLogout">Deconnexion</s:a></li>
        </ul>
    </div>
</nav>

<style type="text/css">
    body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
    }
</style>