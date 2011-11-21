/** NOTE model **/

var Note = Backbone.Model.extend({
  url: function () {
  	if (this.isNew()) {
  		return server + 'notes';
  	} else {
  		return server + 'notes/' + this.get('id');
  	}
  },
  initialize: function(){
		
		return this;
	}
});


var NoteView = Backbone.View.extend({
	model: Note,
	tagName: 	'div',
	className:'note',
	initialize: function() {
		this.render();
		this.model.bind('destroy', this.removeView, this);
		
		return this;
	},
	render: function() {
		var variables = { 
			viewContent: this.getViewContent(this.model.get('content')),
			editContent: this.getEditContent(this.model.get('content')),
			id: this.model.get('id'),
			postedAt: this.model.get('postedAt')
		};
		$(this.el).html( _.template( $("#note_template").html(), variables ) );
		
		return this;
	},
	events: {
    'click a#delete'							: 'deleteNote',
    'click a#edit' 								: 'editNote',
		'click a.note_submit'					:	'saveNote',
		'keyup textarea.note_content'	: 'noteChanged' 
  },
	noteChanged: function ( event ) {
	  if (event.keyCode == 13 && event.shiftKey)
	  {
			this.saveNote();
	  }
	},
	editNote: function( event ) {
		console.dir (event);
		$(this.el).find('.view_note').hide();
		$(this.el).find('.edit_note').show();
	},
	saveNote: function( event ) {
		this.model.set({'content': $(this.el).find('.note_content').val()},{silent: true});
		this.model.save();
		$(this.el).find('.edit_note').hide();
		$(this.el).find('.view_note').show();
		this.render();
		
		//this.trigger('save',this);
		
		this.remove();
		this.unbind();
	},
	deleteNote: function( event ) {
		this.model.destroy();
	},
	removeView: function ( model ) {
		//delete note
		this.remove();
		this.unbind();
	},
	getViewContent: function (text) {
		if (text == undefined)
			return;
			
    var exp = /(\b(https?):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
    text = text.replace(exp,"<a href='$1'>$1</a>"); 

	  return text;
	},
	getEditContent: function (text) {
		if (text == undefined)
			return '';

	  return text.unescapeHtml();
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
		
		this.collection.bind('add', this.addModel, this);
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
	addModel: function( newNote ) {
		var newNoteView = new NoteView ({ model: newNote });
		this._viewPointers[newNote.id] = newNoteView;
		var newPostEl = newNoteView.render().el;
		$(newPostEl).hide();
		this.el.prepend(newPostEl);
		$(newPostEl).slideDown('slow');

		return this;
	},
	addView: function( newNoteView ) {
		collection.add(newNoteView.model, {silent:true});
		this._viewPointers[newNoteView.model.id] = newNoteView;
		
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
		console.log ('here');
		this.newNote = new Note();
		this.newNoteView = new NoteView({model: this.newNote});
		$(this.newNoteView.el).appendTo ($('#new_note'));
		this.notes = new Notes ();
		
		this.newNote.bind('change', this.addNote, this);
		//this.newNoteView.bind('save', this.addNoteView, this);
	},
	addNote: function (note) {
		console.log ('adding note');
		this.notes.add(note);
		
		//create a new note with a view
		this.newNote = new Note();
		this.newNoteView = new NoteView({model: this.newNote});
		$(this.newNoteView.el).appendTo ($('#new_note'));
		this.newNote.bind('change', this.addNote, this);
	},
	addNoteView: function (noteView) {
		console.log ('adding note');
		this.notes.add(note);
		
		//create a new note with a view
		this.newNote = new Note();
		this.newNoteView = new NoteView({model: this.newNote});
		$(this.newNoteView.el).appendTo ($('#new_note'));
		this.newNote.bind('change', this.addNote, this);
	}
});

String.prototype.unescapeHtml = function () {
    var temp = document.createElement("div");
    temp.innerHTML = this;
    var result = temp.childNodes[0].nodeValue;
    temp.removeChild(temp.firstChild);
    return result;
} 

/*
$(document).ready(function() {
	var appView = new AppView();
	
  $('div.content').embedly({
    maxWidth: 450,
    wmode: 'transparent',
    method: 'after',
    key:'ed39be7e143611e1bae54040d3dc5c07'
  });

	$('textarea').elastic();
});
*/