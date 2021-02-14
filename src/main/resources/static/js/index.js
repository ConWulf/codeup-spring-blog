(function() {
    const icon = document.querySelector("#profile-icon");
    const menu = document.querySelector("#profile-menu");
    const triangle = document.querySelector("#triangle");

    icon.addEventListener('click', function(){
        if (menu.classList.contains("scale3d")) {
            menu.classList.remove("scale3d");
            triangle.classList.remove("scale3d");
        }
        else {
            menu.classList.add("scale3d");
            triangle.classList.add("scale3d");
        }
    })







})()