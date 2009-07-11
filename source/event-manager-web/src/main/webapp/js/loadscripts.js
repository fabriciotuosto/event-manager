var executable_scripts = new Array('ext-base-all','tab-close-menu','layout');
var head = document.getElementsByTagName('head');
var se;
for (i = 0; i < executable_scripts.length; i++) {
    se = document.createElement('script');
    se.src = './js/'+executable_scripts[i]+'.js';
    se.language = 'javascript';
    se.type = 'text/javascript';
    head[0].appendChild(se);
}