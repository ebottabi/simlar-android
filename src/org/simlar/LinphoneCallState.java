/**
 * Copyright (C) 2013 - 2014 The Simlar Authors.
 *
 * This file is part of Simlar. (http://www.simlar.org)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.simlar;

import org.linphone.core.LinphoneCall;

import android.util.Log;

/// TODO: think about renaming to SimlarCallState
public enum LinphoneCallState
{
	/// Attention: Keep in sync with linphone
	IDLE, // 0
	INCOMING_RECEIVED, // 1
	OUTGOING_INIT, // 2
	OUTGOING_PROGRESS, // 3
	OUTGOING_RINGING, // 4
	OUTGOING_EARLY_MEDIA, // 5
	CONNECTED, // 6
	STREAMS_RUNNING, // 7
	PAUSING, // 8
	PAUSED, // 9
	RESUMING, // 10
	REFERED, // 11
	ERROR, // 12
	CALL_END, // 13
	PAUSED_BY_REMOTE, // 14
	UPDATED_BY_REMOTE, // 15
	INCOMING_EARLY_MEDIA, // 16
	UPDATING, // 17
	RELEASED, // 18
	UNKONWN;

	private static final String LOGTAG = LinphoneCallState.class.getSimpleName();
	private static final LinphoneCallState[] ALL = LinphoneCallState.values();

	public static LinphoneCallState fromLinphoneCallState(final LinphoneCall.State state)
	{
		if (state == null) {
			Log.e(LOGTAG, "ERROR fromLinphoneCallState: state is null");
			return UNKONWN;
		}

		final int value = state.value();
		if (0 <= value && value < ALL.length - 1) {
			return ALL[value];
		}

		Log.e(LOGTAG, "ERROR: fromLinphoneCallState failed state=" + state);
		return UNKONWN;
	}

	boolean isPossibleCallEndedMessage()
	{
		return this == CALL_END || this == ERROR || this == RELEASED;
	}

	boolean isCallOutgoingConnecting()
	{
		return this == OUTGOING_INIT || this == OUTGOING_PROGRESS;
	}

	boolean isCallOutgoingRinging()
	{
		return this == OUTGOING_RINGING;
	}

	public boolean isTalking()
	{
		return this == CONNECTED || this == STREAMS_RUNNING || this == UPDATED_BY_REMOTE || this == UPDATING;
	}

	public boolean isNewCallJustStarted()
	{
		return this == OUTGOING_INIT || this == INCOMING_RECEIVED;
	}

	public boolean isEndedCall()
	{
		return this == CALL_END;
	}

	public boolean isMyPhoneRinging()
	{
		return this == INCOMING_RECEIVED;
	}

	public boolean isBeforeEncryption()
	{
		return this == LinphoneCallState.CONNECTED;
	}
}
