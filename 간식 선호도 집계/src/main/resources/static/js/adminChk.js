src="https://code.jquery.com/jquery-3.4.1.js"
integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
crossorigin="anonymous"

function adminChk() {
    $.ajax({
        type: "GET",
        url: "/api/adminChk",
        async: false,
        success : function(result){
            console.log(result);
            if(result.admin_chk === "Y") {
                $(".admin").show();
            }
        },
        error : function(a, b, c) {
            alert("error");
        }
    })
}