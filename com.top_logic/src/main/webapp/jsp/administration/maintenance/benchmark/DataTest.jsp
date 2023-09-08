<%@page language="java" session="true" extends="com.top_logic.util.TopLogicJspBase"
%><%@taglib uri="basic" prefix="basic"
%><!DOCTYPE html>
<html>
	<head>
		<basic:script>
var LOOPS = 1;
var counter = 0;
var dontRuntoFar = true;
var serverTimeList = new Array();
var sLoops = 0;
var givenValue = "";

	
var startTime = 0;
var endTime = 0;
function dataTest(bigString){
	givenValue= bigString;
    var myDate = new Date();
	startTime = myDate.getTime();	
	//log("StartTime : " + startTime);
	var ping = document.getElementById("ImageID"); 
	
	//log("Request Time from Client: " + requestTimeList[counter] + " loop is at position"+ counter);
	ping.contentWindow.location.href="ImageView.jsp?mode=" + bigString;
}

function setServerTime(serverLoops){
	sLoops = serverLoops
	var myDate = new Date();
	endTime = myDate.getTime();
	//log("EndTime : " + endTime);
	serverTimeList[counter] = endTime - startTime;
	//log("Verbrauchte Zeit: " + serverTimeList[counter]);
	//log("Server Time :" + serverTime +" loop is at position"+ counter );
	//log("Response Time from Client: " + responseTimeList[counter]);
	
	counter ++;
	if(counter < LOOPS){
	
	dataTest();
	
	}

	if(counter == LOOPS && dontRuntoFar){
		dontRuntoFar = false;
		calculate();
	}
	
}

function calculate(){
	var neededTime = 0;
	for(var i = 0; i < LOOPS; i++){
	neededTime += serverTimeList[i];
	}
	neededTime = neededTime / sLoops;
	if (givenValue=='big'){
		log("Beim laden von 5 Bildern, wurde im Durchschnitt ein Bild in " + neededTime + "ms aufgebaut.");	
	}else{
		log("Beim laden von 1000 Bildern, wurde im Durchschnitt ein Bild in " + neededTime + "ms aufgebaut.");	
	}
	
}

function log(message) {
	document.getElementById("console").appendChild(document.createTextNode(new Date() + ": " + message + "\r\n"));
}
		
function clearConsole() {
	var c = document.getElementById("console");
	while (c.lastChild != null) {
		c.removeChild(c.lastChild);
	}
	//log("Console cleared.");
	return false;
}

function reset(){
 	counter = 0;
	dontRuntoFar = true;
	serverTimeList = new Array();
	clearConsole();
	document.getElementById("ImageID").contentWindow.location.href="blank.jsp";
	document.getElementById("ImageID").contentWindow.window.location.reload;

}		
</basic:script>
		<title>
			Server Response Site
		</title>
		<link
			href="mainStyle.css"
			rel="stylesheet"
			type="text/css"
		/>
	</head>
	<body>
		<br/>

		<iframe id="ImageID"
			height="350px"
			name="window"
			src="blank.jsp"
			width="100%"
		>
		</iframe>
		<button onclick="dataTest('small');">
			Data Test
		</button>
		<button onclick="dataTest('big');">
			Data Test (big file)
		</button>
		<button onclick="reset();">
			Reset
		</button>
		<br/>
		<br/>
		<button onclick="location.href='ServerTime.html'">
			Back
		</button>
		<h2>
			Console output
		</h2>
		<pre id="console"
			style="border-style: solid; border-width: 1px; border-color: red;"
		>
		</pre>
	</body>
</html>