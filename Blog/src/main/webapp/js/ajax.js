function getTable(firstName, tableId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById(tableId).innerHTML = this.responseText;
    }
  };
 
  xhttp.open("GET", "blog?firstName="+document.getElementById(firstName).value, true);
  xhttp.send();
}

function addPost(content, tableId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById(tableId).innerHTML = this.responseText;
    }
  };
  xhttp.open("GET", "blog?content="+document.getElementById(content).value, true);
  xhttp.send();
}

function login(login, password, info, remember) {
  localStorage.removeItem('logged');
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        text = new String(this.responseText);
        console.log(text);
        console.log(document.getElementById(remember).checked);
        if(text == "logged\r\n")
        {
              location.href = "http://localhost:8080/WebLab3/blog.html";
        }
        else
        {
            document.getElementById(info).innerHTML = "Błąd";
        }
    }
  };
  var rememberMe = "no";
  if(document.getElementById(remember).checked)rememberMe="yes";
  //open for GET method
  //xhttp.open("GET", "login?login=" + document.getElementById(login).value + "&password=" + document.getElementById(password).value+ "&remember=" + rememberMe, true);
  xhttp.open("POST", 'login', true);
  xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhttp.send("login=" + document.getElementById(login).value + "&password=" + document.getElementById(password).value+ "&remember="+rememberMe);
  //Send for GET method
  //xhttp.send();
}

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

function setName(name) {
    let login = getCookie("cookuser");
    if(login!=undefined)
    document.getElementById(name).innerText = "Witaj "+getCookie("cookuser")+" !!!";
    else location.href = "http://localhost:8080/WebLab3/index.html";
}

function getLoginCookies(log,pass,rem) {
  var rememberMe = getCookie("cookrem");
  if(rememberMe=="yes")
  {
    document.getElementById(log).value = getCookie("cookuser");
    document.getElementById(pass).value = getCookie("cookpass");
    document.getElementById(rem).checked = true;
  }
}

function register(login, password, password2, info) {
    pass1 = document.getElementById(password).value;
    pass2 = document.getElementById(password2).value;
    console.log(pass1);
    console.log(pass2);
  if(pass1==pass2)
  {
     var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        text = this.responseText;
        if(text=="registered\r\n")
        {
            location.href = "http://localhost:8080/WebLab3/blog.html";
        }
        else
        {
            document.getElementById(info).innerHTML = text;
        }
    }
  };
  
  xhttp.open("GET", "register?login=" + document.getElementById(login).value + "&password=" + document.getElementById(password).value, true);
  xhttp.send(); 
  }
  else
  {
     document.getElementById(info).innerHTML = "Hasła nie są takie same";
  }
}