/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    layout = null;
    settings = {
        defaults: {
            applyDefaultStyles:				true, // basic styling for testing & demo purposes
            south_size: 50,
            center_size: 50
        },
        west:{
          size:300,
          maxSize: 300
        },
        center: {
            width: 300,
            maxWidht:300,
            resizable: false
        },
        east:{
            size:500,
            minWidth:500
        }

    }
    layout = $('body').layout(settings);
});


