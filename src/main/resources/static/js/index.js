(function() {
    const icon = document.querySelector("#user-menu");
    const menu = document.querySelector("#profile-menu");

    icon.addEventListener('click', function(){
        if (menu.classList.contains("scale3d")) {
            menu.classList.remove("scale3d");
        }
        else {
            menu.classList.add("scale3d");
        }
    })







})()