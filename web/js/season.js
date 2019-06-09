/* -----START DEFAULT----- */

$(document).ready(function () {
    $('.table tbody tr').click(function () {
        $(this).find('input[type="radio"]').prop('checked', true);
        $('.selecionado').removeClass("selecionado");
        $(this).addClass("selecionado");
    });
});

/* -----END DEFAULT----- */


/* -----START produtos.html----- */

function filtroProdutos() {
    var input, filter, table, tr, td, td2, td3, i;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0]; //ID
        td2 = tr[i].getElementsByTagName("td")[1]; //Descrição
        td3 = tr[i].getElementsByTagName("td")[3]; //Código de barras
        if (td || td2 || td3) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || td2.innerHTML.toUpperCase().indexOf(filter) > -1 || td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

/* -----END produtos.html----- */


/* -----START usuarios.html----- */

function filtroUsuarios() {
    var input, filter, table, tr, td, td2, td3, i;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0]; //ID
        td2 = tr[i].getElementsByTagName("td")[1]; //Nome
        td3 = tr[i].getElementsByTagName("td")[2]; //Permissões
        if (td || td2 || td3) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || td2.innerHTML.toUpperCase().indexOf(filter) > -1 || td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

/* -----END usuarios.html----- */


/* -----START vendas.html----- */

function filtroVendas() {
    var input, filter, table, tr, td, td2, td3, i;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0]; //ID
        td2 = tr[i].getElementsByTagName("td")[1]; //Data hora
        td3 = tr[i].getElementsByTagName("td")[6]; //Vendedor
        if (td || td2 || td3) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || td2.innerHTML.toUpperCase().indexOf(filter) > -1 || td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

/* -----END vendas.html----- */


/* -----START principal.html----- */

$(document).ready(function () {
    $("#principal li p").each(function () {
        if ($(this).height() > 20) {
            $(this).css("paddingTop", "25px");
        } else {
            $(this).css("paddingTop", "35px");
        }
    });
});

/* -----END principal.html----- */


/* -----START trocarsenha.html----- */

function mostrarSenha(objeto) {
    if ($(objeto).is(':checked') === false) {
        $("input[type='text']").attr('type', 'password');
    } else {
        $("input[type='password']").attr('type', 'text');
    }
}

/* -----END trocarsenha.html----- */


/* -----START novavenda.html----- */

function mostraOpvenda(opcao) {
    $('.opvenda').each(function () {
            $(this).addClass('ocultar');
    });
    $('.' + opcao + '').toggleClass('ocultar');
}

/* -----END novavenda.html----- */