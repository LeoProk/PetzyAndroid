/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.leopro.petzyandroid.pojo

import com.android.volley.toolbox.ImageLoader.ImageCache

import android.graphics.Bitmap
import android.support.v4.util.LruCache

class LruBitmapCache @JvmOverloads constructor(sizeInKiloBytes: Int = defaultLruCacheSize) : LruCache<String, Bitmap>(sizeInKiloBytes), ImageCache {

    @Override
    protected fun sizeOf(key: String, value: Bitmap): Int {
        return value.getRowBytes() * value.getHeight() / 1024
    }

    @Override
    fun getBitmap(url: String): Bitmap {
        return get(url)
    }

    @Override
    fun putBitmap(url: String, bitmap: Bitmap) {
        put(url, bitmap)
    }

    companion object {

        val defaultLruCacheSize: Int
            get() {
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024) as Int

                return maxMemory / 8
            }
    }
}
