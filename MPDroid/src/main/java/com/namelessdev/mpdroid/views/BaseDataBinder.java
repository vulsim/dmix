/*
 * Copyright (C) 2010-2014 The MPDroid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.namelessdev.mpdroid.views;

import com.namelessdev.mpdroid.MPDApplication;
import com.namelessdev.mpdroid.adapters.ArrayDataBinder;
import com.namelessdev.mpdroid.helpers.CoverAsyncHelper;
import com.namelessdev.mpdroid.helpers.CoverManager;
import com.namelessdev.mpdroid.views.holders.AbstractViewHolder;

import org.a0z.mpd.AlbumInfo;
import org.a0z.mpd.item.Item;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.List;

public abstract class BaseDataBinder implements ArrayDataBinder {

    boolean mEnableCache = true;

    boolean mLightTheme = false;

    boolean mOnlyDownloadOnWifi = true;

    public BaseDataBinder(final boolean isLightTheme) {
        super();
        mLightTheme = isLightTheme;
        final MPDApplication app = MPDApplication.getInstance();
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(app);

        mEnableCache = settings.getBoolean(CoverManager.PREFERENCE_CACHE, true);
        mOnlyDownloadOnWifi = settings.getBoolean(CoverManager.PREFERENCE_ONLY_WIFI, false);
    }

    protected static void loadArtwork(final CoverAsyncHelper coverHelper,
            final AlbumInfo albumInfo) {
        coverHelper.downloadCover(albumInfo);
    }

    protected static void loadPlaceholder(final CoverAsyncHelper coverHelper) {
        coverHelper.obtainMessage(CoverAsyncHelper.EVENT_COVER_NOT_FOUND).sendToTarget();
    }

    @Override
    public abstract AbstractViewHolder findInnerViews(View targetView);

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract boolean isEnabled(int position, List<? extends Item> items, Object item);

    @Override
    public abstract void onDataBind(Context context, View targetView,
            AbstractViewHolder viewHolder, List<? extends Item> items,
            Object item,
            int position);

    @Override
    public abstract View onLayoutInflation(Context context, View targetView,
            List<? extends Item> items);

}
