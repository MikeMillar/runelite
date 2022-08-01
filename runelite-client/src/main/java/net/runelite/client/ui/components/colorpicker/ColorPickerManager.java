/*
 * Copyright (c) 2019, Ron Young <https://github.com/raiyni>
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.ui.components.colorpicker;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowEvent;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.ClientUI;

@Singleton
public class ColorPickerManager
{
	private final ClientUI clientUI;
	private final ConfigManager configManager;

	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private RuneliteColorPicker currentPicker;

	@Inject
	private ColorPickerManager(final ClientUI clientUI, final ConfigManager configManager)
	{
		this.clientUI = clientUI;
		this.configManager = configManager;
	}

	public RuneliteColorPicker create(Window owner, Color previousColor, String title, boolean alphaHidden)
	{
		if (currentPicker != null)
		{
			currentPicker.dispatchEvent(new WindowEvent(currentPicker, WindowEvent.WINDOW_CLOSING));
		}

		currentPicker = new RuneliteColorPicker(owner, previousColor, title, alphaHidden, configManager, this);
		return currentPicker;
	}

	public void setLocation(Point screenLocation)
	{
		Rectangle clientBounds = clientUI.getClientBounds();
		int pickerRightBound = screenLocation.x - clientBounds.x + currentPicker.getWidth();
		if (pickerRightBound > clientBounds.width)
		{
			screenLocation.x -= pickerRightBound - clientBounds.width;
		}
		currentPicker.setLocation(screenLocation);
	}

}
