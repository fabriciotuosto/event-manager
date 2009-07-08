Ext.onReady(function(){

    // NOTE: This is an example showing simple state management. During development,
    // it is generally best to disable state management as dynamically-generated ids
    // can change across page loads, leading to unpredictable results.  The developer
    // should ensure that stable state ids are set for stateful components in real apps.
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

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
            items:            // this TabPanel is wrapped by another Panel so the title will be applied
            new Ext.TabPanel({
                border: false, // already wrapped so don't add another border
                activeTab: 1, // second tab initially active
                items: [{
                    html: '<p>A TabPanel component can be a region.</p>',
                    title: 'A Tab',
                    autoScroll: true
                }, new Ext.grid.PropertyGrid({
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
            title: 'Center',
            margins: '0 0 0 0'
        }
        ]
    });
    // get a reference to the HTML element with id "hideit" and add a click listener to it
    Ext.get("hideit").on('click', function(){
        // get a reference to the Panel that was created with id = 'west-panel'
        var w = Ext.getCmp('west-panel');
        // expand or collapse that Panel based on its collapsed property state
        w.collapsed ? w.expand() : w.collapse();
    });
});