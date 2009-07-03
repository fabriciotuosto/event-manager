$(function() {

    $("body").html("<div id='dialog' title='Login'><p id='validateTips'>All form fields are required.</p><form action='/event-manager/services/admin/login' method='post'><label for='name'>Name</label><input type='text' name='name' id='name' class='text ui-widget-content ui-corner-all' /><label for='password'>Password</label><input type='password' name='password' id='password' value='' class='text ui-widget-content ui-corner-all' /></form></div>");
    
    var name = $("#name"),
    password = $("#password"),
    allFields = $([]).add(name).add(password),
    tips = $("#validateTips");

    function updateTips(t) {
        tips.text(t).effect("highlight",{},1500);
    }

    function checkLength(o,n,min,max) {

        if ( o.val().length > max || o.val().length < min ) {
            o.addClass('ui-state-error');
            updateTips("Length of " + n + " must be between "+min+" and "+max+".");
            return false;
        } else {
            return true;
        }

    }

    function checkRegexp(o,regexp,n) {

        if ( !( regexp.test( o.val() ) ) ) {
            o.addClass('ui-state-error');
            updateTips(n);
            return false;
        } else {
            return true;
        }

    }
    
    $("#dialog").dialog({
        bgiframe: true,
        autoOpen: false,
        height: 300,
        modal: true,
        buttons: {
            'Login': function() {
                var bValid = true;
                allFields.removeClass('ui-state-error');

                bValid = bValid && checkLength(name,"name",3,16);
                bValid = bValid && checkLength(password,"password",5,16);

                bValid = bValid && checkRegexp(name,/^[a-z]([0-9a-z_])+$/i,"Username may consist of a-z, 0-9, underscores, begin with a letter.");
                bValid = bValid && checkRegexp(password,/^([0-9a-zA-Z])+$/,"Password field only allow : a-z 0-9");

                if (bValid) {
                    $('#dialog form').submit();
                    $(this).dialog('close');
                }
            },
            Cancel: function() {
                $(this).dialog('close');
            }
        },
        close: function() {
            allFields.val('').removeClass('ui-state-error');
        }
    });
    

    $('#dialog').dialog('open');
});
