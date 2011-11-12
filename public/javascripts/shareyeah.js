/* to do:
 - NewNoteView maakt een model aan en doet een model.save(). Daarna een nieuwe note toevoegen resulteert in een already in database error. Dit kan/moet voorkomen worden door de 'create' view te hiden en een 'display' view er aan te koppelen en in de lijst weer te geven.
 - Dergelijke functionaliteit geeft ook de mogelijkheid om notes inline te editen
*/

/** NEW NOTE view **/


var NewNoteView = Backbone.View.extend({
	model: Note,
	el: $('#new_note'),
	initialize: function() {
		this.render();
	},
	render: function() {
		var variables = { };
		var template = _.template( $("#new_note_template").html(), variables );
		this.el.html( template );
	},
	events: {
		"click a#new_note_submit": "saveNote",
		"keyup #new_note_content": "noteChanged" 
	},
	noteChanged: function ( event ) {
	  if (event.keyCode == 13 && event.shiftKey)
	  {
			this.saveNote();
	  }
	},
	saveNote: function( event ) {
		this.model.set({'content': $('#new_note_content').val()});
		this.model.save();
		//notes.add(this.model);
		$('#new_note_content').val('').blur();
	}
});

/** NOTE model **/

var Note = Backbone.Model.extend({
	defaults: {
    content:	'empty'
  },
  url: function () {
  	if (this.isNew()) {
  		return server + 'notes';
  	} else {
  		return server + 'notes/' + this.get('id');
  	}
  },
  initialize: function(){
		this.sanitizeContent();
		
		return this;
	},
	sanitizeContent: function () {
		var text = this.get('content');
    var exp = /(\b(https?):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
    text = text.replace(exp,"<a href='$1'>$1</a>"); 
	  text = text.replace(/\n/g,'<br />');

	  this.set({'content' : text});
	}
});

var NoteView = Backbone.View.extend({
	model: Note,
	events: {
    "click a.delete": "doDelete"
  },
	initialize: function() {
		this.render();
		this.model.bind('destroy', this.modelDestroyed, this);
		
		return this;
	},
	render: function() {
		var variables = { 
			content: this.model.get('content'), 
			id: this.model.get('id'), 
			postedAt: this.model.get('postedAt')
		};
		$(this.el).html( _.template( $("#note_template").html(), variables ) );
		
		return this;
	},
	doDelete: function( event ) {
		this.model.destroy();
	},
	modelDestroyed: function ( model ) {
		//delete note
		this.remove();
		this.unbind();
	}
});



/** NOTES collection **/

var Notes = Backbone.Collection.extend({
	model: 			Note,
  initialize: function(){
	  this.fetch({
			success: function (collection) {
				new NotesView({collection:collection});
			}
		});
	},
  url: function () {
  	return server + 'notes';
  }  
});

var NotesView = Backbone.View.extend({
	collection: Notes,
	el:					$('#notes'),	
	
	initialize: function() {
		this._viewPointers = new Array();
		
		this.collection.bind('add', this.addOne, this);
		this.collection.bind('destroy', this.removeOne, this);
		
		this.render();
		
		return this;
	},
	render: function() {
		this.el.html('');
		for (note in this.collection.models)
		{
			var tmpNote			= this.collection.models[note];
			var newNoteView = new NoteView ({ model: tmpNote });
			this._viewPointers[tmpNote.id] = newNoteView;
			this.el.append(newNoteView.render().el);
		}
		
		return this;
	},
	addOne: function( newNote ) {
		var newNoteView = new NoteView ({ model: newNote });
		this._viewPointers[newNote.id] = newNoteView;
		var newPostEl = newNoteView.render().el;
		$(newPostEl).hide();
		this.el.prepend(newPostEl);
		$(newPostEl).slideDown('slow');

		return this;
	},
	removeOne: function ( deletedNote ) {
		delete this._viewPointers[deletedNote.id];
		
		return this;
	}
});


var server = 'http://localhost/shareyeah/'; 

var AppView = Backbone.View.extend({
	initialize: function() {
		var newNoteView = new NewNoteView({model: new Note()});
		var notes = new Notes ();
		newNoteView.bind('all', this.showEvent, this);
		notes.bind('all', this.showEvent, this);
	},
	showEvent: function (event, iets, context) {
		console.log ('event: ' + event);
		//console.dir (iets);
		//console.dir (nogiets);
	}
});

new AppView();

