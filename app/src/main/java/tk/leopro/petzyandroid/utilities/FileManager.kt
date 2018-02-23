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


import android.content.Context

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

import tk.leopro.petzyandroid.interfaces.FactoryInterface


/**
 * Saves delete or read files on internal storage
 */
internal class FileManager(private val mContext: Context, private val mFileName: String, private val mMessage: String, private val mFileCondition: String) : FactoryInterface {

    //Retrieve file from internal storage by name
    private//string temp contains all the data of the file.
    val file: String
        get() {
            var temp = ""
            try {
                val inputStream = mContext.openFileInput(mFileName)
                var c: Int

                while ((c = inputStream.read()) != -1) {
                    temp = temp + Character.toString(c.toChar())
                }
                inputStream.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return temp
        }

    @Override
    fun doTask(): Object? {
        when (mFileCondition) {
            "get" -> return file
            "save" -> return saveFile()
            "append" -> return appendFile()
            "delete" -> return deleteFile()
            else -> return null
        }
    }

    //Save new file to internal storage
    private fun saveFile(): String? {
        val thread = Thread(object : Runnable() {
            @Override
            fun run() {
                try {
                    val outputStream = mContext
                            .openFileOutput(mFileName, Context.MODE_PRIVATE)
                    outputStream.write(mMessage.getBytes("UTF-8"))
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        })

        thread.start()
        return null
    }

    //Appends file to internal storage.
    private fun appendFile(): String? {
        val thread = Thread(object : Runnable() {
            @Override
            fun run() {
                try {
                    val outputStream = mContext
                            .openFileOutput(mFileName, Context.MODE_APPEND)
                    outputStream.write(mMessage.getBytes())
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        })

        thread.start()
        return null
    }

    //delete file by name
    private fun deleteFile(): String? {
        val dir = mContext.getFilesDir()
        val file = File(dir, mFileName)
        file.delete()
        return null
    }
}
