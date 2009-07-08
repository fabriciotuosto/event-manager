/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
var Forum = {};

//////////////////////////////////////////////////////////////////////////////////////////////
// The data store for topics
Forum.TopicStore = function(){
    Forum.TopicStore.superclass.constructor.call(this, {
        remoteSort: true,

        proxy: new Ext.data.ScriptTagProxy({
            url: 'http://extjs.com/forum/topics-browse-remote.php'
        }),

        reader: new Ext.data.JsonReader({
            root: 'topics',
            totalProperty: 'totalCount',
            id: 'threadid'
        }, [
        'title', 'forumtitle', 'forumid', 'author',
        {
            name: 'replycount',
            type: 'int'
        },

        {
            name: 'lastpost',
            mapping: 'lastpost',
            type: 'date',
            dateFormat: 'timestamp'
        },
        'lastposter', 'excerpt'
        ])
    });

    this.setDefaultSort('lastpost', 'desc');
};
Ext.extend(Forum.TopicStore, Ext.data.Store, {
    loadForum : function(forumId){
        this.baseParams = {
            forumId: forumId
        };
        this.load({
            params: {
                start:0,
                limit:25
            }
        });
    }
});

//////////////////////////////////////////////////////////////////////////////////////////////

// some renderers
Forum.Renderers = {
    topic : function(value, p, record){
        return String.format(
            '<div class="topic"><b>{0}</b><span class="author">{1}</span></div>',
            value, record.data.author, record.id, record.data.forumid);
    },

    lastPost : function(value, p, r){
        return String.format('<span class="post-date">{0}</span><br/>by {1}', value.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
    }
};