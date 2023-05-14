function onUpload() {
	var fd = new FormData();
	var totalfiles = document.getElementById('myfile').files.length;
   	for (var index = 0; index < totalfiles; index++) {
      fd.append("file", document.getElementById('myfile').files[index]);
   	}
    fd.append('dateSheetFile', $('#dateSheet')[0].files[0]);
    fd.append('hallFile', $('#hallData')[0].files[0]);
    fd.append('suspendFile', $('#suspendFile')[0].files[0]);
    fd.append('matrixFile', $('#matrixFile')[0].files[0]);
    fd.append('batchMapFile', $('#batchMapFile')[0].files[0]);
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
            //alert("Files Uploaded Successfully!!");
            //window.location.reload();
        }
    };

    xmlhttp1.send(fd);
}

function generateCountFile() {
	var fd = new FormData();
	var totalfiles = document.getElementById('masterfile').files.length;
   	for (var index = 0; index < totalfiles; index++) {
      fd.append("file", document.getElementById('masterfile').files[index]);
   	}
   	fd.append('suspendFile', $('#suspendFile')[0].files[0]);
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
    xmlhttp1.open("POST", "http://localhost:8089" + "/roomallocation/generateCountFile", true);
    xmlhttp1.onreadystatechange = function () {
        if (this.status == 200) {
            var res = this.responseText;
           // alert("Files Uploaded Successfully!!");
           // window.location.reload();
        }
    };

    xmlhttp1.send(fd);
}