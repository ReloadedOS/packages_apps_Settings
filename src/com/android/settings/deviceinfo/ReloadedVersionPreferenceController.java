/*
 * Copyright (C) 2022 ReloadedOS
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.deviceinfo;

import android.content.Context;
import android.os.SystemProperties;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class ReloadedVersionPreferenceController extends BasePreferenceController {

    private static final String RELOADED_VERSION_PROP = "ro.reloaded.version";
    private static final String RELOADED_BUILDTYPE_PROP = "ro.reloaded.build_type";
    private static final String RELOADED_VARIANT_PROP = "ro.reloaded.build_variant";

    private final Context mContext;

    public ReloadedVersionPreferenceController(Context context, String key) {
        super(context, key);
        mContext = context;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE_UNSEARCHABLE;
    }

    @Override
    public CharSequence getSummary() {
        String version = SystemProperties.get(RELOADED_VERSION_PROP,
                mContext.getString(R.string.device_info_default));
        String buildType = SystemProperties.get(RELOADED_BUILDTYPE_PROP,
                mContext.getString(R.string.device_info_default));
        String variant = SystemProperties.get(RELOADED_VARIANT_PROP,
                mContext.getString(R.string.device_info_default));

        // Example: 13.0 | OFFICIAL | GAPPS
        return version + " | " + buildType + " | " + variant;
    }
}
