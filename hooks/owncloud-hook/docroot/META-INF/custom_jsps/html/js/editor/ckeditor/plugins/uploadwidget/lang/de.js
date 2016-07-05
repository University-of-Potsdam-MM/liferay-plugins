/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.plugins.setLang( 'uploadwidget', 'de', {
	abort: 'Hochladen durch den Benutzer abgebrochen.',
	doneOne: 'Datei erfolgreich hochgeladen.',
	doneMany: '%1 Dateien erfolgreich hochgeladen.',
	uploadOne: 'Datei wird hochgeladen ({percentage}%)...',
	uploadMany: 'Dateien werden hochgeladen, {current} von {max} fertig ({percentage}%)...'
	// BEGIN HOOK CHANGE
	,uploadError: 'Upload fehlgeschlagen. Bitte wenden Sie sich an den Administrator.'
	,authentificationFailed: 'Upload fehlgeschlagen. Bitte geben Sie im Portlet \'Box.UP\' Ihr Passwort ein.'
	// END HOOK CHANGE
} );
