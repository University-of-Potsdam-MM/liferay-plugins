AUI.add(
	'liferay-so-invite-members',
	function(A) {
		var InviteMembers = function() {
			InviteMembers.superclass.constructor.apply(this, arguments);
		};

		InviteMembers.NAME = 'soinvitemembers';

		InviteMembers.ATTRS = {
			dialog: {
				value: null
			},

			portletNamespace: {
				value: ''
			},
			
			forPublishment: {
				value: true
			}
		};

		A.extend(
			InviteMembers,
			A.Base,
			{
				initializer: function(params) {
					var instance = this;

					instance._inviteMembersContainer = A.one('#' + instance.get('portletNamespace') + 'inviteMembersContainer');

					if (!instance._inviteMembersContainer) {
						return;
					}

					instance._findMembersList = instance._inviteMembersContainer.one('.search .list');
					instance._invitedMembersList = instance._inviteMembersContainer.one('.user-invited .list');

					var form = instance._inviteMembersContainer.one('form');

					form.on(
						'submit',
						function(event) {
							instance._syncFields(form);

							var dialog = instance.get('dialog');

							if (!dialog && !dialog.io) {
								return;
							}

							event.halt();

							dialog.io.set(
								'form',
								{
									id: form.getDOM()
								}
							);

							dialog.io.set('uri', form.getAttribute('action'));

							dialog.io.start();
						}
					);

					instance._inviteMembersContainer.delegate(
						'click',
						function(event) {
							var user = event.currentTarget;

							var userId = user.attr('data-userId');

							if (userId && (!instance.get('forPublishment') || !user.hasClass('feedbackRequested'))) {
								if (user.hasClass('invited')) {
									instance._removeMemberInvite(user, userId);
								}
								else {
									instance._addMemberInvite(user);
								}
							}
						},
						'.user'
					);

					instance._inviteMembersContainer.delegate(
						'keyup',
						function(event) {
							if (event.keyCode == 13) {
								instance._addMemberEmail();
							}
						},
						'.controls'
					);

					instance._inviteMembersContainer.delegate(
						'click',
						function(event) {
							instance._addMemberEmail();

							Liferay.Util.focusFormField(instance._emailInput.getDOM());
						},
						'#so-add-email-address'
					);
				},

				_addMemberInvite: function(user) {
					var instance = this;
					
					instance._removeGlobalPublishment();

					user.addClass('invited').cloneNode(true).appendTo(instance._invitedMembersList);
				},
				
				_removeGlobalPublishment: function(){
					var instance = this;
					
					var globalPublishment = instance._invitedMembersList.one('.global');
					if(globalPublishment){
						globalPublishment.remove();
					}
				},

				_removeMemberInvite: function(user, userId) {
					var instance = this;

					userId = userId || user.getAttribute('data-userId');

					var user = instance._findMembersList.one('[data-userId="' + userId + '"]');
					var invitedUser = instance._invitedMembersList.one('[data-userId="' + userId + '"]');

					user.removeClass('invited');
					invitedUser.remove();
				},

				_syncFields: function(form) {
					var instance = this;

					var userIds = [];

					instance._invitedMembersList.all('.user').each(
						function(item, index) {
							userIds.push(item.attr('data-userId'));
						}
					);
					
					form.one('input[name="' + instance.get('portletNamespace') + 'receiverUserIds"]').val(userIds.join());
					
					if(instance._invitedMembersList.one('.global')){
						form.one('input[name="' + instance.get('portletNamespace') + 'globalPublishment"]').val(true);
					}
					else {
						var globalFormElement = form.one('input[name="' + instance.get('portletNamespace') + 'globalPublishment"]');
						if (globalFormElement){
							globalFormElement.val(false);
						}
					}
				}
			}
		);

		Liferay.namespace('SO');

		Liferay.SO.InviteMembers = InviteMembers;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-deprecated', 'liferay-util-window']
	}
);

AUI.add(
	'liferay-so-invite-members-list',
	function(A) {
		var InviteMembersList = A.Base.create(
			'inviteMembersList',
			A.Base,
			[A.AutoCompleteBase],
			{
				initializer: function(config) {
					this._listNode = A.one(config.listNode);

					this._bindUIACBase();
					this._syncUIACBase();
				}
			}
		);

		Liferay.namespace('SO');

		Liferay.SO.InviteMembersList = InviteMembersList;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'node-core']
	}
);