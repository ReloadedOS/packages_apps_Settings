/*
 * Copyright (C) 2022 ReloadedOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.deviceinfo;

import android.content.Context;

import com.android.settings.deviceinfo.firmwareversion.FirmwareVersionDetailPreferenceController;

public class ReloadedVersionDetailPreferenceController
        extends FirmwareVersionDetailPreferenceController {

    private static final String TAG = "ReloadedVersionDetailCtrl";

    public ReloadedVersionDetailPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public CharSequence getSummary() {
        return null;
    }
}
