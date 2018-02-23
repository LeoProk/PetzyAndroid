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

package tk.leopro.petzyandroid.utilities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 * Creates new database and save the matching contracts there.
 */
internal class SQLDatabase(context: Context, //List of contacts that much the parse database
                           var mParks: List<FirebaseItem>, private val mSaveRetrieveUpdate: String) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), FactoryInterface {

    // Getting All Contacts
    // Select All Query
    // looping through all rows and adding to list
    //FirebaseItem park = new FirebaseItem(userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
    //mParks.add(park);
    // return contact list
    val allContacts: List<FirebaseItem>
        get() {
            val selectQuery = "SELECT  * FROM " + TABLE_CONTACTS

            val db = this.getWritableDatabase()
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val userInfo = cursor.getString(1).split("\n")
                } while (cursor.moveToNext())
            }
            return mParks
        }

    // Create users database from arraylist
    fun addContacts() {
        //clean old database
        val db = this.getWritableDatabase()
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)

        // Create tables again
        onCreate(db)

        val values = ContentValues()
        for (i in 0 until mParks.size()) {
            values.put(COLUMN_USER, mParks[i].toString())
            db.insert(TABLE_CONTACTS, null, values)

        }
        db.close() // Closing database connection

    }


    @Override
    fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE "
                + TABLE_CONTACTS + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY," + COLUMN_USER
                + " TEXT" + ")")
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    @Override
    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)

        // Create tables again
        onCreate(db)
    }

    @Override
    fun doTask(): Object {

        when (mSaveRetrieveUpdate) {
            "save" -> addContacts()
            "retrieve" -> allContacts
            else -> {
            }
        }
        return mParks
    }

    companion object {

        private val TABLE_CONTACTS = "contacts"

        private val COLUMN_ID = "_id"

        private val DATABASE_NAME = "parks.db"

        private val COLUMN_USER = "park"

        private val DATABASE_VERSION = 1
    }

}
