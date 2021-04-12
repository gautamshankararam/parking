if(sessionStorage.getItem('user') == null){
    window.location.href="../index.html";
}


function logout(){
   sessionStorage.clear();
   window.location.href="../index.html";
}
