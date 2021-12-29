function post(url, param, onSuccess) {
   return ajax(url, param, "post", onSuccess);
}

function get(url, param, onSuccess) {
	left.update();
    return ajax(url, param, "get", onSuccess);
}

function ajax(url, param, type, onSuccess) {
    return $.ajax({
        url: url,
        data: param,
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        type: type,
        cache: "false",
        success: onSuccess,
        error: function (xhr) {
            console.log(xhr);
        }
    });
}

function uploadFile(url, from, onSuccess) {
	return $.ajax({
        url: url,
        type: "POST",
        data: new FormData($(from)[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: onSuccess,
        error: function (xhr) {
            console.log(xhr);
        }
    });
}

function getUplaodFileName(obj, maxSize) {
    if(obj != "[object HTMLInputElement]") {
        obj = document.getElementById(obj);
        obj = document.getElementById(obj)
    }

    try {
        var file = obj.value;
        var fileSize = obj.files[0].size;
    } catch (e) {
        return "파일선택";
    }

    if (file == "") {
        alertModal("업로드 파일을 선택해주세요.", "w");
        return "파일선택";
    }

    if (fileSize > maxSize) {
        alertModal("파일 최대 사이즈는 " + maxSize+ " 입니다.", "e");
        obj.value = null;
        return "파일선택";
    }

    return file.substring(file.lastIndexOf("\\") + 1).replace(/[#]/g, "");
}

function validatorForm(id, submitHandler) {
    $.extend($.validator.messages, {
        required: "필수 항목입니다.",
        email: "유효하지 않은 E-Mail주소입니다.",
        date: "올바른 날짜를 입력하세요.",
        number: "유효한 숫자가 아닙니다.",
        digits: "올바른 전화번호를 입력해주세요.",
        max: $.validator.format("{0} 이하의 값을 입력하세요."),
        min: $.validator.format("{0} 이상의 값을 입력하세요."),
        minlength : $.validator.format("최소 {0} 까지 입력가능합니다."),
        maxlength : $.validator.format("최대 {0} 까지 입력가능합니다.")
    });

    $.validator.methods.digits = function( value, element ) {
        return this.optional( element ) || /[0-9]{3,4}/.test( value );
    };

    $(id).validate({
        rules: {
            date: {
                required: true
            },
            time: {
                required: true
            },
            type: {
                required: true
            },
            name: {
                required: true
            },
            phone: {
                required: true
            },
            phone2: {
                required: true
            },
            phone3: {
                required: true
            },
            title: {
                required: true
            },
            password: {
                required: true
            },
            content: {
                required: true
            },
        },
        errorElement: "em",
        errorPlacement: function (error, element) {
            // Add the `help-block` class to the error element
            error.addClass("help-block-cm");
            error.insertAfter(element);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).addClass("has-success").removeClass("has-error");
        },
        submitHandler : submitHandler
    });
}

function getDigit(num, digit) {
    num = '0' + num;
    return num.slice(-digit);
}

function getWeek(date) {
    var week = ['일', '월', '화', '수', '목', '금', '토'];

    return week[date.getDay()];
}

function ThreeCalendar(c1, c2, c3) {
    this.$datepicker1 = $(c1);
    this.$datepicker2 = $(c2);
    this.$datepicker3 = $(c3);
}

function windowClose() {
    window.close(); // 일반적인 현재 창 닫기
    window.open('about:blank','_self').self.close();  // IE에서 묻지 않고 창 닫기
}

ThreeCalendar.prototype.init = function (method) {
    var that = this;

    this.$datepicker1.datepicker({
        format: 'yyyy-mm-dd',
        language: "kr"
    }).on("changeDate", function (e) {
        that.$datepicker2.find(".active").removeClass("active day");
        that.$datepicker3.find(".active").removeClass("active day");

        //that.$datepicker2.find(".today").removeClass("today");

        method(new Date(e.date))
    }).on("changeYear", function (e) {
        var date2 = new Date(e.date);
        date2.setMonth(date2.getMonth() + 1);
        date2.setFullYear(date2.getFullYear());

        var date3 = new Date(e.date);
        date3.setMonth(date3.getMonth() + 2);
        date3.setFullYear(date3.getFullYear());

        that.$datepicker2.datepicker("setDate", date2);
        that.$datepicker3.datepicker("setDate", date3);
    }).on("changeMonth", function (e) {
        var date2 = new Date(e.date);
        date2.setMonth(date2.getMonth() + 1);

        var date3 = new Date(e.date);
        date3.setMonth(date3.getMonth() + 2);

        that.$datepicker2.datepicker("setDate", date2);
        that.$datepicker3.datepicker("setDate", date3);
    });

    this.$datepicker2.datepicker({
        format: 'yyyy-mm-dd',
        language: "kr",
        todayHighlight: true
    }).on("changeDate", function (e) {
        that.$datepicker1.find(".active").removeClass("active day");
        that.$datepicker3.find(".active").removeClass("active day");

        //that.$datepicker2.find(".today").removeClass("today");

        method(new Date(e.date))
    }).on("changeYear", function (e) {
        var date1 = new Date(e.date);
        date1.setMonth(date1.getMonth() - 1);
        date1.setFullYear(date1.getFullYear());

        var date3 = new Date(e.date);
        date3.setMonth(date3.getMonth() + 1);
        date3.setFullYear(date3.getFullYear());

        that.$datepicker1.datepicker("setDate", date1);
        that.$datepicker3.datepicker("setDate", date3);
    }).on("changeMonth", function (e) {
        var date1 = new Date(e.date);
        date1.setMonth(date1.getMonth() - 1);

        var date3 = new Date(e.date);
        date3.setMonth(date3.getMonth() + 1);


        that.$datepicker1.datepicker("setDate", date1);
        that.$datepicker3.datepicker("setDate", date3);
    });

    this.$datepicker3.datepicker({
        format: 'yyyy-mm-dd',
        language: "kr"
    }).on("changeDate", function (e) {
        that.$datepicker1.find(".active").removeClass("active day");
        that.$datepicker2.find(".active").removeClass("active day");

        //that.$datepicker2.find(".today").removeClass("today");

        method(new Date(e.date))
    }).on("changeYear", function (e) {
        var date1 = new Date(e.date);
        date1.setFullYear(date1.getFullYear());
        date1.setMonth(date1.getMonth() - 2);

        var date2 = new Date(e.date);
        date2.setFullYear(date2.getFullYear());
        date2.setMonth(date2.getMonth() - 1);

        that.$datepicker1.datepicker("setDate", date1);
        that.$datepicker2.datepicker("setDate", date2);

    }).on("changeMonth", function (e) {
        var date1 = new Date(e.date);
        date1.setMonth(date1.getMonth() - 2);

        var date2 = new Date(e.date);
        date2.setMonth(date2.getMonth() - 1);

        that.$datepicker1.datepicker("setDate", date1);
        that.$datepicker2.datepicker("setDate", date2);
    });

    var date1 = new Date();
    date1.setMonth(date1.getMonth() - 1);

    var date3 = new Date();
    date3.setMonth(date3.getMonth() + 1);

    this.$datepicker1.datepicker("setDate", date1);
    this.$datepicker3.datepicker("setDate", date3);

    this.$datepicker2.find(".today").click();
};

ThreeCalendar.prototype.loadSchedule = function (url, params, body) {
    get(url, params, function (data) {
        var tempHtml = '', i;

        for (i = 0; i < data.list.length; i++) {
            var p = data.list[i];
            var os = data.oslist[i];
            var g = p["inspector"];
            
            tempHtml += '<tr>';
            tempHtml += '<th style="text-align:left;" width="250px;">['+ p["groupName"] +']'+ p["agent"] + '(' + g.name +')</th>';
            for (var j = 0; j < 14; j++) {
                var time = getDigit(8 + j, 2);
                var osType ='';
                var col;
                if(os[time] != ''){
    		    	if(os[time] == "Mac"){
    		    		col = "blue";
    			   	}else if(os[time] == "Mo"){
    			   		col = "red";
    				}else{
    					col = "block";
    				} 
                	osType = " -"+os[time];
                }
                tempHtml += '<td><span class="badge ' + getBadgeColorCls(p[time]) + '" data-time="' + time + '" data-agent="' + p["agent"] + '">' + p[time] + '</span><span style="font-size:10px;color:'+col+';">'+osType+'</span></td>';
            }

            tempHtml += '</tr>';
        }

        if (data.list.length == 0) {
            tempHtml += '<tr><td colspan="15">데이터가 존재하지 않습니다.</td></tr>';
        }

        $(body).html(tempHtml)
    });
};

function getBadgeColorCls (str) {
    switch (str) {
	    case "배정":
	        return "bg-blue-line";
	    case "마감": case "완료": 
	    	return "bg-yellow";
	    case "이관":
	    	return "bg-purple";
	    case "불가":
	        return "bg-red";
	    case "대기": case "미배정":case "가능":
	        return "bg-blue";
	    case "부재종결":
	        return "bg-black";
	    case "업무외": 
	    	return "bg-green";
	    case "신청":
	        return "bg-gray";
	    case "일반상담":
	        return "bg-light-pink";
	    case "종료":
	        return "bg-dark-gray";
	    case "준비중":
	        return "bg-yellow";
	    case "점심시간":  case "저녁시간":
	        return "bg-dark-blue";
	    default:
	        return "bg-red";
    }
}

function alertModal(msg, icon, title, fn) {
    switch (icon) {
        case "w":
            icon = 'warning';
            break;
        case "s":
            icon = 'success';
            break;
        case "e":
            icon = 'error';
            break;
        default:
            icon = 'info';
            break;
    }

    if (getBrowserType() == "익스플로러" || typeof Swal === "undefined") {
        alert(msg.replace(/<\/br>/g, "\n"));
        if(typeof fn != "undefined"){
	        setTimeout(function(){
	            fn();
	        }, 100);
        }
        return;
    }

    Swal.fire({
        html: msg,
        title : title,
        icon: icon,
        showCancelButton: false,
        confirmButtonColor: '#4bc0c0',
        cancelButtonColor: '#ff6384',
        confirmButtonText: '확인'
    }).then(function (result){
        if (result.value) {
        	if(typeof fn != "undefined"){
        		fn();
        	}
        }
    })
}

function alertModal2(msg, icon, form, value, fn) {
    switch (icon) {
        case "w":
            icon = 'warning';
            break;
        case "s":
            icon = 'success';
            break;
        case "e":
            icon = 'error';
            break;
        default:
            icon = 'info';
            break;
    }
    
    if (getBrowserType() == "익스플로러" || typeof Swal === "undefined") {
        alert(msg.replace(/<\/br>/g, "\n"));        
        if(typeof fn != "undefined"){
	        setTimeout(function(){
	            fn();
	        }, 100);
        }
        return;
    }
    
    var focus = form.find("input[name='"+value+"']").focus();
    if(value == "description"){
    	focus = form.find("textarea[name='"+value+"']").focus();
    }
    
    Swal.fire({
        html: msg,
        title : "",
        icon: icon,
        showCancelButton: false,
        confirmButtonColor: '#4bc0c0',
        cancelButtonColor: '#ff6384',
        confirmButtonText: '확인',
        /*onAfterClose: () => {
            setTimeout(() => form.find("input[name='"+value+"']").focus(), 110);
        }*/
        onAfterClose: focus
    })
}

function workPlanning() {
    alertModal("작업예정입니다.", "i");
}

function getBrowserType(){
    var agent = navigator.userAgent.toLowerCase(), name = navigator.appName, browser;    
    if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1 || agent.indexOf('edge/') > -1){
        browser = '익스플로러';
    }else if(agent.indexOf('safari') > -1){
        if(agent.indexOf('opr') > -1){
            browser = '오페라';
        }else if(agent.indexOf('chrome') > -1){
            browser = '크롬';
        }else{
            browser = '사파리';
        }
    }else if(agent.indexOf('firefox') > -1){
        browser = '파이어폭스';
    }
    return browser;
}

Date.prototype.format = function (f) {

    /*var _today = new Date();
    console.log(_today.format('yyyy-MM-dd'));
    console.log(_today.format('HH:mm:ss'));
    console.log(_today.format('yyyy-MM-dd(KS) HH:mm:ss'));
    console.log(_today.format('yyyy-MM-dd a/p hh:mm:ss'));*/

    if (!this.valueOf()) return " ";
    var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];
    var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    var d = this;
    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {

        switch ($1) {

            case "yyyy":
                return d.getFullYear(); // 년 (4자리)

            case "yy":
                return (d.getFullYear() % 1000).zf(2); // 년 (2자리)

            case "MM":
                return (d.getMonth() + 1).zf(2); // 월 (2자리)

            case "dd":
                return d.getDate().zf(2); // 일 (2자리)

            case "KS":
                return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)

            case "KL":
                return weekKorName[d.getDay()]; // 요일 (긴 한글)

            case "ES":
                return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)

            case "EL":
                return weekEngName[d.getDay()]; // 요일 (긴 영어)

            case "HH":
                return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)

            case "hh":
                return ((h = d.getHours() % 12) ? h : 12).zf(2); // 시간 (12시간 기준, 2자리)

            case "mm":
                return d.getMinutes().zf(2); // 분 (2자리)

            case "ss":
                return d.getSeconds().zf(2); // 초 (2자리)

            case "a/p":
                return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분

            default:
                return $1;
        }
    });
};

function noBefore(date){
    if (date.format("yyyyMMdd") < new Date().format("yyyyMMdd")){
        return false;
    }else{
    	var day = date.getDay();
    	if(day == 0){
    		return false;	
        }else{
    		return true;
        }
    }
}

function logRegister(url, from, onSuccess) {
	return $.ajax({
        url: url,
        type: "POST",
        data: new FormData($(from)[0]),
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        processData: false,
        contentType: false,
        cache: false,
        success: onSuccess,
        error: function (xhr) {
            console.log(xhr);
        }
    });
}
String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };
String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };
Number.prototype.zf = function (len) { return this.toString().zf(len); };
