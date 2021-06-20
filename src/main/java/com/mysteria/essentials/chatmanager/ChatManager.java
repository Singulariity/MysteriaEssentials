package com.mysteria.essentials.chatmanager;

import com.mysteria.essentials.EssentialsPlugin;
import com.mysteria.essentials.chatmanager.composers.DefaultRenderer;

public class ChatManager {

	private final DefaultRenderer defaultRenderer;

	public ChatManager() {
		if (EssentialsPlugin.getChatManager() != null) {
			throw new IllegalStateException();
		}
		defaultRenderer = new DefaultRenderer();
	}

	public DefaultRenderer getdefaultRenderer() {
		return defaultRenderer;
	}

}
