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
package tk.leopro.petzy.AppSpecific;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
/**
 * Get info from html and put in listview
 */
public class AdoptingHtmlParser extends AsyncTask<ListView,Void,Void> {

    @Override
    protected Void doInBackground(ListView... params) {
        try {
            Document docForSave = Jsoup.connect("http://www.yad2.co.il/Pets/Pets.php?AreaID=&PetTypeID=&PetSubTypeID=&PetDealID=2&Age=&Sex=&fromPrice=&untilPrice=&Info=").get();
            Log.e("yays", docForSave.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void yadParsing(){

    }
    private void agoraParsing(){

    }
}
