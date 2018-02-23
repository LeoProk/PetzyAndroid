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
package tk.leopro.petzyandroid.main

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import android.os.AsyncTask
import android.util.Log

import java.io.IOException

/**
 * Get info from html and put in listview
 */
internal class AdoptingHtmlParser : AsyncTask<Void, Void, Void>() {

    @Override
    protected fun doInBackground(vararg params: Void): Void? {
        yadParsing()
        return null
    }

    private fun yadParsing() {
        try {
            val doc = Jsoup.connect("http://www.yad2.co.il/Pets/Pets.php?AreaID=&PetTypeID=&PetSubTypeID=&PetDealID=2&Age=&Sex=&fromPrice=&untilPrice=&Info=").get()
            val messages = doc.select("tbody")

            for (petInfo in messages) {

                Log.e("yays", petInfo.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun agoraParsing() {

    }
}
