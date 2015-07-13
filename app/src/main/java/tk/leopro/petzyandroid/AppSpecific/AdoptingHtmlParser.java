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
package tk.leopro.petzyandroid.AppSpecific;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
/**
 * Get info from html and put in listview
 * */
final class AdoptingHtmlParser extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... params) {
        yadParsing();
        return null;
    }

    private void yadParsing(){
        try {
            Document doc = Jsoup.connect("http://www.yad2.co.il/Pets/Pets.php?AreaID=&PetTypeID=&PetSubTypeID=&PetDealID=2&Age=&Sex=&fromPrice=&untilPrice=&Info=").get();
            Elements messages = doc.select("tbody");

            for(Element petInfo : messages){

                Log.e("yays", petInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void agoraParsing(){

    }
}
