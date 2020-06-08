$(document).ready(function () {
    dodajArtikl();
    obrisiArtikl();
    AzurirajArtikl();
    PromijeniUAdministratora();
    UkloniPravoAdministrator();
});

function dodajArtikl(){
    $("#artiklDodaj").click(function () {
        event.preventDefault();
        if ($("#nazivArtikla").val().length===0)alert("Morate unijeti naziv artikla") ;
        else{
            var nazivArtikla = $("#nazivArtikla").val();
            var cijena= $("input[name=cijenaArtikla]").val();
            var kolicina= $("input[name=kolicinaRobe]").val();
            var opis = $("textarea[name=opisArtikla]").val();
            var kategorija = $("#kategorija option:selected").val();
            console.log(nazivArtikla+" "+cijena+" "+kolicina+" "+opis+" "+kategorija);
            $.ajax({
                url:"../AJAX/dodajNoviArtikl.php",
                type: "POST",
                data: {nazivArtikla:nazivArtikla, cijena:cijena, kolicina:kolicina, opis:opis, kategorija:kategorija},
                dataType: "text",
                success: function (data) {
                    alert(data);
                    location.reload();
                }
            });

        }
    });

}

function obrisiArtikl(){
    $("button[name=delete]").click(function () {
        event.preventDefault();
        var id=$(this).attr('id');
        
       $.ajax({
            url:"../AJAX/izbrisiArtikl.php",
            type: "POST",
            data: {id:id},
            dataType: "text",
            success: function (data) {
                alert(data);
                location.reload();
            }
        });
    });

}

function AzurirajArtikl(){
    $("button[id=artiklUpdate]").click(function () {
        event.preventDefault();

        if ($("#nazivArtikla").val().length===0)alert("Morate unijeti naziv artikla") ;
        else{
            var nazivArtikla = $("#nazivArtikla").val();
            var cijena= $("input[name=cijenaArtikla]").val();
            var kolicina= $("input[name=kolicinaRobe]").val();
            var opis = $("textarea[name=opisArtikla]").val();
            var kategorija = $("#kategorija option:selected").val();
            var id=$("#idArtikla").val();

            console.log(nazivArtikla+" "+cijena+" "+kolicina+" "+opis+" "+kategorija);
            $.ajax({
                url:"../AJAX/urediArtikl.php",
                type: "POST",
                data: {id:id,nazivArtikla:nazivArtikla, cijena:cijena, kolicina:kolicina, opis:opis, kategorija:kategorija},
                dataType: "text",
                success: function (data) {
                    alert(data);
                    window.location='https://airfood2go.000webhostapp.com/Stranice/Artikli.php';
                }
            });

        }
    });

}
function PromijeniUAdministratora() {

    $("button[name=dodijeliAdmina]").click(function () {
     
        event.preventDefault();
        var id =$(this).attr('id');
        $.ajax({
            url:"../AJAX/promijeniUAdmina.php",
            type: "POST",
            data:{id:id},
            success:function (data) {
                alert(data);
               location.reload();
            }}
        );
    });
}

function UkloniPravoAdministrator() {
    $("button[name=ukloniAdmina]").click(function () {
        event.preventDefault();
        var id =$(this).attr('id');
        $.ajax({
            url:"../AJAX/ukloniAdmina.php",
            type: "POST",
            data:{id:id},
            success:function (data) {
                alert(data);
                location.reload();
            }}
        );
    });
}