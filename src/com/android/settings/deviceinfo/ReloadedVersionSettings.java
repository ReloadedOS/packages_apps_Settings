/*
 * Copyright (C) 2022 ReloadedOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import androidx.preference.Preference;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class ReloadedVersionSettings extends DashboardFragment {

    private static final String KEY_VERSION = "reloaded_os_version";
    private static final String KEY_BUILD_TYPE = "reloaded_build_type";
    private static final String KEY_BUILD_VARIANT = "reloaded_build_variant";
    private static final String KEY_CLO_REVISION = "reloaded_clo_revision";
    private static final String KEY_DEVICE_NAME = "reloaded_device";
    private static final String KEY_MAINTAINER = "reloaded_device_maintainer";

    private static final String PROP_VERSION = "ro.reloaded.version";
    private static final String PROP_BUILD_DATE = "ro.reloaded.build_date";
    private static final String PROP_BUILD_TYPE = "ro.reloaded.build_type";
    private static final String PROP_BUILD_VARIANT = "ro.reloaded.build_variant";
    private static final String PROP_CLO_REVISION = "ro.reloaded.clo_revision";
    private static final String PROP_DEVICE_NAME = "ro.reloaded.device";
    private static final String PROP_MAINTAINER = "ro.reloaded.device_maintainer";
    private static final String PROP_MAINTAINER_URL = "ro.reloaded.device_maintainer_url";

    private Preference mVersionPref, mBuildTypePref, mBuildVariantPref,
            mCloRevisionPref, mDevicePref, mMaintainerPref;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        mVersionPref = findPreference(KEY_VERSION);
        mBuildTypePref = findPreference(KEY_BUILD_TYPE);
        mBuildVariantPref = findPreference(KEY_BUILD_VARIANT);
        mCloRevisionPref = findPreference(KEY_CLO_REVISION);
        mDevicePref = findPreference(KEY_DEVICE_NAME);
        mMaintainerPref = findPreference(KEY_MAINTAINER);

        final Context context = getContext();
        final StringBuilder version = new StringBuilder(getVersion(context))
                .append("-")
                .append(getStringProperty(PROP_BUILD_DATE, context));
        mVersionPref.setSummary(version);

        mBuildTypePref.setSummary(getBuildType(context));
        mBuildVariantPref.setSummary(getBuildVariant(context));
        mCloRevisionPref.setSummary(getStringProperty(PROP_CLO_REVISION, context));

        final StringBuilder device = new StringBuilder(
                SystemProperties.get(PROP_DEVICE_NAME, Build.DEVICE))
                .append(" (")
                .append(Build.MODEL)
                .append(")");
        mDevicePref.setSummary(device);

        mMaintainerPref.setSummary(getStringProperty(PROP_MAINTAINER, context));
        final Intent intent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(SystemProperties.get(PROP_MAINTAINER_URL)));
        if (!context.getPackageManager().queryIntentActivities(intent, 0).isEmpty()) {
            mMaintainerPref.setOnPreferenceClickListener(p -> {
                context.startActivity(intent);
                return true;
            });
        }
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.reloaded_version;
    }

    @Override
    protected String getLogTag() {
        return "ReloadedVersionSettings";
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.RELOADED;
    }

    static String getVersion(Context context) {
        return getStringProperty(PROP_VERSION, context);
    }

    static String getBuildType(Context context) {
        return getStringProperty(PROP_BUILD_TYPE, context);
    }

    static String getBuildVariant(Context context) {
        return getStringProperty(PROP_BUILD_VARIANT, context);
    }

    private static String getStringProperty(String prop, Context context) {
        return SystemProperties.get(prop, context.getString(R.string.device_info_default));
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.reloaded_version);
}
