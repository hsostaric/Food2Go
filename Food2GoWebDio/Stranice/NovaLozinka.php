<?php
include ("zaglavlje.php");
include("../Backend/ResetiranjeLozinke.php");


if(isset($_POST["izmijeni"])){
    ResetirajLozinku();

}
?>
<br>
<div class="container h-100" >
    <p class="h2 text-center">Nova lozinka</p><hr><br>
    <div class="row h-100 justify-content-center align-items-center ">
        <form class="col-12" method="post">

            <div class="form-group row">
                <label for="inputUsernameActivateAccount" class="col-sm-2 col-form-label">Korisničko ime:</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="username" id="inputUsernameActivateAccount" placeholder="Unesite korisničko ime">
                </div>
            </div>

            <div class="form-group row">
                <label for="inputActivateCode" class="col-sm-2 col-form-label">Mail:</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control"  name="mail" id="inputActivateCode" placeholder="Unesite mail:">
                </div>
            </div>
            <br>
            <div class="form-group row">

                <div class="col-sm-4 "> <input name="izmijeni" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Resetiraj!"></div
            </div></div>
    </form>
</div>
<?php
include ("podnozje.php");
?>
