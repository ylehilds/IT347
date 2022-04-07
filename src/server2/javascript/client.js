



function refresh()
{


	if (window.XMLHttpRequest)
  	{// code for IE7+, Firefox, Chrome, Opera, Safari
  	xmlhttp=new XMLHttpRequest();
  	}
	else
  	{// code for IE6, IE5
  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
	xmlhttp.onreadystatechange=function()
  	{
  		if (xmlhttp.readyState==4 && xmlhttp.status==200)
    		{
    		document.getElementById("chatarea").innerHTML=xmlhttp.responseText;

		var objDiv = document.getElementById("chatarea");
		objDiv.scrollTop = objDiv.scrollHeight;

		counter();

    		}
  	}
xmlhttp.open("GET","/REFRESH",true);

xmlhttp.send(null);




}

function getBottom(){
var objDiv = document.getElementById("chatarea");
objDiv.scrollTop = objDiv.scrollHeight;

counter();
}


function counter(){


   clearInterval();
   setInterval(refresh,10000);
}


function url(){




//window.location="http://www.google.com";
line = document.getElementById('line').value;
line = line.replace(/ /gi,"+");

window.location='/CHAT?name=' + document.getElementById('name').value + ';line=' + line;


}



