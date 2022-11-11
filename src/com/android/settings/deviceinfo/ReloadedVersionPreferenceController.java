/*
 * Copyright (C) 2022 ReloadedOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.deviceinfo;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class ReloadedVersionPreferenceController extends BasePreferenceController {

    private final Context mContext;

    public ReloadedVersionPreferenceController(Context context, String key) {
        super(context, key);
        mContext = context;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public CharSequence getSummary() {
        // Example: 13.0 | OFFICIAL | GAPPS
        StringBuilder sb = new StringBuilder(ReloadedVersionSettings.getVersion(mContext))
                .append(" | ")
                .append(ReloadedVersionSettings.getBuildType(mContext))
                .append(" | ")
                .append(ReloadedVersionSettings.getBuildVariant(mContext));

        return sb.toString();
    }
}
