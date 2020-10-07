<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-sm-2">
    <h2 class="col-sm-10 offset-sm-1">Profile</h2>
    <div class="list-group col-sm-10 offset-sm-1">
        <a href="/user?showData=userInfo" class="list-group-item list-group-item-action active">Profile info</a>
        <a href="/user?showData=userLibrary" class="list-group-item list-group-item-action">Library</a>
        <a href="/user?showData=userHistory" class="list-group-item list-group-item-action">Order history</a>
        <a href="/signIn?logout=true" class="list-group-item list-group-item-action">Logout</a>
    </div>
</div>
