Ext.onReady(function(){

    // NOTE: This is an example showing simple state management. During development,
    // it is generally best to disable state management as dynamically-generated ids
    // can change across page loads, leading to unpredictable results.  The developer
    // should ensure that stable state ids are set for stateful components in real apps.
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // helper function to dinamically add tabs
    function addTab(){
        tabs.add({
            title: 'New Tab ',
            iconCls: 'tabs',
            html: 'Tab Body <br/><br/>',
            closable:true
        }).show();
    }

    var addTabButton = new Ext.Button({
        text: 'Add Tab',
        handler: addTab,
        iconCls:'new-tab'
    }).render(document.body, 'tabs');
    // The tab panel
    var tabs = new Ext.TabPanel({
        border: false, // already wrapped so don't add another border
        renderTo:'tabs',
        resizable: true,
        enableTabScroll:true,
        activeTab: 0, // second tab initially active
        items: [{
            html: '<p>A TabPanel component can be a region.</p>',
            title: 'A Tab',
            autoScroll: true,
            iconCls: 'tabs',
            closable: true
        }, new Ext.grid.PropertyGrid({
            iconCls: 'tabs',
            title: 'Property Grid',
            closable: true,
            source: {
                "(name)": "Properties Grid",
                "grouping": false,
                "autoFitColumns": true,
                "productionQuality": false,
                "created": new Date(Date.parse('10/15/2006')),
                "tested": false,
                "version": 0.01,
                "borderWidth": 1
            }
        })]
    })

    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [{
            // lazily created panel (xtype:'panel' is default)
            region: 'south',
            contentEl: 'south',
            split: true,
            height: 50,
            minSize: 40,
            maxSize: 100,
            collapsible: true,
            collapsed: true,
            title: 'South',
            margins: '0 0 0 0'
        }, {
            region: 'east',
            title: 'East Side',
            split: true,
            width: 700, // give east and west regions a width
            minWidth: 600,
            maxWidth: 1000,
            margins: '0 5 0 0',
            layout: 'fit', // specify layout manager for items
            items:  tabs     // this TabPanel is wrapped by another Panel so the title will be applied

        }, {
            region: 'west',
            id: 'west-panel', // see Ext.getCmp() below
            title: 'West',
            split: true,
            width: 300,
            maxWidth: 400,
            minWidth: 300,
            minSize: 150,
            maxSize: 200,
            collapsible: true,
            margins: '0 0 0 5',
            layout: {
                type: 'accordion',
                animate: true
            },
            items: [{
                contentEl: 'west',
                title: 'Main',
                border: false,
                iconCls: 'nav' // see the HEAD section for style used
            }, {
                title: 'Contacts',
                html: '<p>Some settings in here.</p>',
                border: false,
                iconCls: 'settings'
            }]
        },
        // in this instance the TabPanel is not wrapped by another panel
        // since no title is needed, this Panel is added directly
        // as a Container
        {

            region: 'center', // a center region is ALWAYS required for border layout
            deferredRender: false,
            contentEl: 'center',
            width: 300,
            maxWidth: 300,
            minWidth: 200,
            title: 'Events',
            margins: '0 0 0 0',
            viewConfig: {
                forceFit:true,
                enableRowBody:true,
                showPreview:true
            },
            tbar:[
            {
                text:'New Tab',
                iconCls: 'new-tab',
                handler:addTab
            }],
            bbar: new Ext.PagingToolbar({
                pageSize: 25,
                displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
            })

        }
        ]
    });

});