/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 

//window.addEventListener('DOMContentLoaded', event => {
//
//    // Toggle the side navigation
//    const sidebarToggle = document.body.querySelector('#sidebarToggle');
//    if (sidebarToggle) {
//        // Uncomment Below to persist sidebar toggle between refreshes
//        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
//        //     document.body.classList.toggle('sb-sidenav-toggled');
//        // }
//        sidebarToggle.addEventListener('click', event => {
//            event.preventDefault();
//            document.body.classList.toggle('sb-sidenav-toggled');
//            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
//        });
//    }
//
//});

window.addEventListener('DOMContentLoaded', event => {
    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    const body = document.body;

    // 기본적으로 사이드바를 숨김
    body.classList.add('sb-sidenav-toggled');

    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     body.classList.remove('sb-sidenav-toggled'); // 저장된 값이 true라면 사이드바 보이게 설정
        // }

        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', body.classList.contains('sb-sidenav-toggled'));
        });
    }
});
