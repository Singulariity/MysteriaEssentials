package com.mysteria.essentials.chatmanager;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.essentials.chatmanager.composers.DefaultComposer;

public class ChatManager {

	private final DefaultComposer defaultComposer;

	public ChatManager() {
		if (EssentialsPlugin.getChatManager() != null) {
			throw new IllegalStateException();
		}
		defaultComposer = new DefaultComposer();
	}

	public DefaultComposer getDefaultComposer() {
		return defaultComposer;
	}

}
