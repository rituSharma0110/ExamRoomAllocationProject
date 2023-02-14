function onUpload() {
	var fd = new FormData();
    fd.append('file', $('#myfile')[0].files[0]);
    console.log(fd)
    var xmlhttp1;
  try {
        xmlhttp1 = new XMLHttpRequest();
    } catch (e) {
        try {
            xmlhttp1 = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                xmlhttp1 = new ActiveXObject("Microsoft.XMLHTTP")
            } catch (e) {
                alert("BROWSER BROKE");
                return false;
            }
        }
    }
    xmlhttp1.open("POST", "http://localhost:8089" + "/roomallocation/generateSeatingArrangement", true);
    xmlhttp1.onreadystatechange = function () {
        if (this.status == 200) {
            var res = this.responseText;
        }
    };

    xmlhttp1.send(fd);
}