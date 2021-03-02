(function() {
    const icon = document.querySelector("#user-menu");
    const menu = document.querySelector("#profile-menu");
    const mobileBtn = document.querySelector("#mobile-btn");
    const mobileMenu= document.querySelector("#mobile-menu");

    if (icon !== null) {
        icon.addEventListener('click', function(){
            if (menu.classList.contains("scale3d")) menu.classList.remove("scale3d")
            else menu.classList.add("scale3d")
        })

    }

    mobileBtn.addEventListener('click', function(){
        if (mobileMenu.classList.contains('max-h-0')) {
            mobileMenu.classList.add('max-h-96')
            mobileMenu.classList.remove('max-h-0')
        }
        else {
            mobileMenu.classList.add('max-h-0')
            mobileMenu.classList.remove('max-h-96')
        }

    })



})()