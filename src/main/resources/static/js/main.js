function onUpload() {
	var fd = new FormData();
	var totalfiles = document.getElementById('myfile').files.length;
   	for (var index = 0; index < totalfiles; index++) {
      fd.append("file", document.getElementById('myfile').files[index]);
   	}
    fd.append('dateSheetFile', $('#dateSheet')[0].files[0]);
    fd.append('hallFile', $('#hallData')[0].files[0]);
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

function generateOtherFiles() {
	var fd = new FormData();
   	fd.append('outputFile', $('#outputFile')[0].files[0]);
   	var totalfiles = document.getElementById('myfile1').files.length;
   	for (var index = 0; index < totalfiles; index++) {
      fd.append("file", document.getElementById('myfile1').files[index]);
   	}
    fd.append('dateSheetFile', $('#dateSheet1')[0].files[0]);
    fd.append('hallFile', $('#hallData1')[0].files[0]);
    fd.append('suspendFile', $('#suspendFile1')[0].files[0]);
    fd.append('matrixFile', $('#matrixFile1')[0].files[0]);
    fd.append('batchMapFile', $('#batchMapFile1')[0].files[0]);
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
    xmlhttp1.open("POST", "http://localhost:8089" + "/roomallocation/generateOtherFiles", true);
    xmlhttp1.onreadystatechange = function () {
        if (this.status == 200) {
            var res = this.responseText;
           // alert("Files Uploaded Successfully!!");
           // window.location.reload();
        }
    };

    xmlhttp1.send(fd);
}

function toCountFile(){
	document.getElementById("choice").style.display="none";
	document.getElementById("first").style.display="block";
	document.getElementById("second").style.display="none";
	document.getElementById("third").style.display="none";
}

function toAllocateRoom(){
	document.getElementById("choice").style.display="none";
	document.getElementById("first").style.display="none";
	document.getElementById("second").style.display="block";
	document.getElementById("third").style.display="block";
}

function toRefresh(){
	window.location.reload();
}