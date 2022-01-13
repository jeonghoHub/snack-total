src="https://code.jquery.com/jquery-3.4.1.js"
integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
crossorigin="anonymous"

function adminChk() {
var userInfo;
    $.ajax({
        type: "GET",
        url: "/api/adminChk",
        async: false,
        success : function(result){
            if(result.admin_chk === "Y") {
                $(".admin").show();
            }
            $("#profile-name").text(result.name);
            $("#user-header-img").attr('src', result.profile_img_path);

            userInfo = result;
        },
        error : function(a, b, c) {
            alert("error");
        }
    })
    return userInfo;
}