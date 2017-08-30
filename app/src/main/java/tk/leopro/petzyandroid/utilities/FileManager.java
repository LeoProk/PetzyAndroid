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

package tk.leopro.petzyandroid.utilities;


import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;


/**
 * Saves delete or read files on internal storage
 */
final class FileManager implements FactoryInterface {

    private String mFileName;

    private Context mContext;

    private String mMessage;

    private String mFileCondition;

    public FileManager(Context context, String fileName, String message, String fileCondition) {
        mContext = context;
        mFileName = fileName;
        mMessage = message;
        mFileCondition = fileCondition;
    }

    @Override
    public Object doTask() {
        switch (mFileCondition) {
            case "get":
                return getFile();
            case "save":
                return saveFile();
            case "append":
                return appendFile();
            case "delete":
                return deleteFile();
            default:
                return null;
        }
    }

    //Retrieve file from internal storage by name
    private String getFile() {
        String temp = "";
        try {
            FileInputStream inputStream = mContext.openFileInput(mFileName);
            int c;

            while ((c = inputStream.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            //string temp contains all the data of the file.
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    //Save new file to internal storage
    private String saveFile() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream outputStream = mContext
                            .openFileOutput(mFileName, Context.MODE_PRIVATE);
                    outputStream.write(mMessage.getBytes("UTF-8"));
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return null;
    }

    //Appends file to internal storage.
    private String appendFile() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream outputStream = mContext
                            .openFileOutput(mFileName, Context.MODE_APPEND);
                    outputStream.write(mMessage.getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return null;
    }

    //delete file by name
    private String deleteFile() {
        File dir = mContext.getFilesDir();
        File file = new File(dir, mFileName);
        file.delete();
        return null;
    }
}
