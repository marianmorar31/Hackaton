const hamburger = document.querySelector(".hamburger");
const navMenu = document.querySelector(".nav-menu");

hamburger.addEventListener("click", () => {
    hamburger.classList.toggle("active");
    navMenu.classList.toggle("active");

});

function saveLocalStorage(){
  let getName = document.getElementById('full-name').value;
  let getEmail = document.getElementById('e-mail').value;
  console.log(getName);
  let getMessage = document.getElementById('text-message');
  // let getMessage = document.getElementById('text-message').value;
  if( getName.length && getEmail.length ){
    localStorage.setItem("name",getName);
    localStorage.setItem("E-mail",getEmail);
    getMessage.textContent ="Your name is:\n" + localStorage.getItem("name")  + " \nYour E-mail is:\n" + localStorage.getItem("E-mail");
  }
  else{
    alert("Please introduce your data.");
  }
  // localStorage.setItem("Message",getMessage);
  // console.log("salut");
}

let changeToLogout = document.getElementById('loginNavBarLink');
// let x = sessionStorage.getItem('loginStatus')=='true';
// console.log("is:"+x);
if(sessionStorage.getItem('loginStatus')=='true'){
  changeToLogout.textContent ="Logout"
  document.getElementById('numeAcum').innerHTML = sessionStorage.getItem('user');
}
if(sessionStorage.getItem('loginStatus')=='false'){
  changeToLogout.textContent ="Login";
  sessionStorage.setItem('loginStatus','false');
  sessionStorage.setItem('loginStatus','false');
  sessionStorage.setItem('varRoleId',-1);
  sessionStorage.setItem('varId',-1);
  sessionStorage.setItem('varProdId',-1);
  sessionStorage.setItem('user',' ');
}
function checkLogin(){
  if(changeToLogout.textContent == "Logout")
  {
      
      sessionStorage.setItem('loginStatus','false');
      sessionStorage.setItem('varRoleId',-1);
      sessionStorage.setItem('varId',-1);
      sessionStorage.setItem('varProdId',-1);
      changeToLogout.textContent ="Logout"
      window.location.href  = 'login.html';
  }
}