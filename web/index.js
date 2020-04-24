var webSocket = new WebSocket("ws://localhost:9090/ShazamApp_war_exploded/ws")
//webSocket.binaryType = "arraybuffer"
const URL = "/ws";
var message = document.getElementById("message")
var echoText = document.getElementById("echoText")
var rec;
let fileReader = new FileReader();
echoText.value = "";
/*navigator.webkitGetUserMedia({audio:true}, callback, function ({}) {
    alert("error")
})*/

/*function callback(stream) {
    var context = new AudioContext();
    var mediaStreamSource = context.createMediaStreamSource(stream);
    rec = new Recorder(mediaStreamSource);
}

document.getElementById("start").addEventListener('click', function () {
    echoText.value +="Start recording!";
    rec.record();
    //ws.send("start");

    // export a wav every second, so we can send it using websockets
    let intervalKey = setInterval(function () {
        rec.exportWAV(function (blob) {
            rec.clear();
            webSocket.send(blob);
        });
    }, 1000);
});*/

/*document.getElementById("stop").addEventListener("click", function(){
    alert("stop recording")
    rec.stop();
});*/

var options = {mimeType: 'audio/wav'};

navigator.mediaDevices.getUserMedia({audio:true})
    .then((stream) => {
        echoText.value += "Initialize";

        let mediaRecorder = new MediaRecorder(stream);
        var recorder = new Recorder(stream);
        var inp = audioContext.createMediaStreamSource(stream);
        /* Create the Recorder object and configure to record mono sound (1 channel) Recording 2 channels will double the file size */
        var rec = new Recorder(inp, {
            numChannels: 1
        })

        document.getElementById("start").addEventListener('click', function () {

        echoText.value +="Start recording!";
        mediaRecorder.start(700);
        mediaRecorder.export
    });
    let audioChunks = [];
    mediaRecorder.addEventListener("dataavailable", function (event) {
        //send chunk to server
        console.log(event.data)
        //const audioBlob = new Blob(event.data, {type: 'audio/wav'})
        let audioInBase64;
        //event.data.type = 'audio/wav'
        fileReader.readAsDataURL(event.data)
        fileReader.onloadend = function() {
            audioInBase64 = fileReader.result
            console.log("Base64data: " + audioInBase64)
            //saveRecordingToServer(audioInBase64)
            webSocket.send(audioInBase64)
        };
        //audioChunks.push(event.data);
    });

    document.getElementById("stop").addEventListener("click", function(){
        alert("stop recording")
        mediaRecorder.stop();
    });

    mediaRecorder.addEventListener("stop", function () {
            const audioBlob = new Blob(audioChunks, { type : 'audio/wav'});

            let fd = new FormData();
            fd.append('voice', audioBlob);
            //sendRecord(audioBlob);
            audioChunks = [];
            webSocket.send("end")
        }
    )
}).catch(reason => {
    echoText.value += reason.message;
})

async function sendRecord(form) {
    /*let promise =  await fetch(URL, {
        method: "POST",
        body: form
    }).then(result => {
        echoText.value += "Record sent!"
    }).catch(error => {
        echoText.value += "Error sending audio!"
    })*/

    let audioInBase64;
    fileReader.readAsDataURL(form)
    fileReader.onloadend = function() {
        audioInBase64 = fileReader.result
        console.log("Base64data: " + audioInBase64)
        //saveRecordingToServer(audioInBase64)
        webSocket.send(audioInBase64)
    };
    //webSocket.send(form)
}

saveRecordingToServer = function (audioArrayBuffer) {
    var bytes = btoa(audioArrayBuffer);
    webSocket.send(bytes)
}

webSocket.onopen = function(message){ wsOpen(message);};
webSocket.onmessage = function(message){ wsGetMessage(message);};
webSocket.onclose = function(message){ wsClose(message);};
webSocket.onerror = function(event) {
    console.error("WebSocket error observed:", event);
};

function wsOpen(message){
    echoText.value += "Connected ... \n";
}
function wsSendMessage(){
    webSocket.send(message.value);
    echoText.value += "Message sended to the server : " + message.value + "\n";
    message.value = "";
}
function wsCloseConnection(){
    webSocket.close();
}
function wsGetMessage(message){
    echoText.value += "Message received from to the server : " + message.data + "\n";
}
function wsClose(message){
    echoText.value += "Disconnect ... \n";
}

function wsError(message){
    echoText.value += "Error ... \n";
}

function startRecord() {

}