package tk.leopro.petzyandroid.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.ArrayList
import java.util.UUID

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import tk.leopro.petzyandroid.AppController
import tk.leopro.petzyandroid.MainActivity
import tk.leopro.petzyandroid.R
import tk.leopro.petzyandroid.interfaces.RequestPlaceInterface
import tk.leopro.petzyandroid.main.AppFactory
import tk.leopro.petzyandroid.pojo.FirebaseItem
import tk.leopro.petzyandroid.pojo.GooglePredictionData
import tk.leopro.petzyandroid.utilities.UtilitiesFactory

/**
 * this app create new item in firebase database with the folowing params
 * subject : picked from subject spinner cant be default
 * date : 2date taken from website to avoid wrong date on devices
 * text : input from the user about the item
 * address : autocompletet text view that uses google places prediction to show street
 * address to the user that he must chose from
 * phone : phone of the user
 * title : title for the item by user
 * user :
 */
class NewParkFragment : Fragment() {

    private var mTitle: EditText? = null
    //auto comeplete address
    private var mAddress: AutoCompleteTextView? = null
    //location of the chosen street address
    private var mChosenLocation: tk.leopro.petzyandroid.pojo.Location? = null
    //subscribtion for google place prediction
    private var mSubscription: Subscription? = null
    //patch to the user picked iamge location
    private var mImagePath: Uri? = null
    //imageview of user image input
    private var mImageView: ImageView? = null
    //check if user picked picture
    private var mUserImage: Boolean = false
    //check if user picked address
    private var mUserAddress: Boolean = false
    //popup displaing that the user park is saving
    private var mSavePopup: PopupWindow? = null

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                     savedInstanceState: Bundle): View {
        val rootView = inflater.inflate(R.layout.fragment_new_park, container, false)
        //get the application class
        val appController = getActivity().getApplicationContext() as AppController
        //initializes views
        mTitle = rootView.findViewById(R.id.title) as EditText
        mAddress = rootView.findViewById(R.id.address) as AutoCompleteTextView
        //lang lat array of predictions
        val predictedLocation = ArrayList()
        val arrayAdapter = ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayOf<String>())
        // let the user to input image for park on click
        mImageView = rootView.findViewById(R.id.image) as ImageView
        mImageView!!.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(view: View) {
                pickImage()
            }
        })
        mAddress!!.setAdapter(arrayAdapter)
        mAddress!!.setOnItemClickListener(object : AdapterView.OnItemClickListener() {
            @Override
            fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mUserAddress = true
                mChosenLocation = predictedLocation.get(position)
            }
        })
        //get info from google place prediction using rxandroid and retrofit
        val rxAdapter = RxJavaCallAdapterFactory.create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mAddress!!.addTextChangedListener(object : TextWatcher() {
            @Override
            fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            @Override
            fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            @Override
            fun afterTextChanged(editable: Editable) {
                if (mSubscription != null) {
                    if (!mSubscription!!.isUnsubscribed()) {
                        mSubscription!!.unsubscribe()
                    }
                }
                val requestPlace = retrofit.create(RequestPlaceInterface::class.java)
                val call = requestPlace.getJSON(editable.toString(), 31.977682.toString() + "," + 34.764381,
                        "5000", "iw", "AIzaSyDGTKCSCY_lpKtVrA1bJYctJdrJhjzGMlE")
                mSubscription = call
                        .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<GooglePredictionData>() {
                            @Override
                            fun onCompleted() {

                            }

                            @Override
                            fun onError(e: Throwable) {

                            }

                            @Override
                            fun onNext(googlePredictionData: GooglePredictionData) {
                                predictedLocation.clear()
                                val temp = ArrayList()
                                //loops true all the prediction
                                for (i in 0 until googlePredictionData.getResults().size()) {
                                    temp.add(googlePredictionData.getResults().get(i).getFormattedAddress())
                                    predictedLocation.add(googlePredictionData.getResults().get(i).getGeometry().getLocation())
                                }
                                val data = temp.toArray(arrayOfNulls<String>(temp.size()))
                                val arrayAdapter = ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data)
                                mAddress!!.setAdapter(arrayAdapter)
                                arrayAdapter.notifyDataSetChanged()
                            }
                        })
            }
        })
        val submit = rootView.findViewById(R.id.submit) as Button
        submit.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                //check if all field are full if yes create new parse item and goes back to last window
                if (mTitle!!.getText().toString().isEmpty()) {
                    AppFactory.titlePopUp(mTitle, getActivity()).doTask()
                } else {
                    if (mAddress!!.getText().toString().isEmpty() && mUserAddress == false) {
                        AppFactory.addressPopUp(mTitle, getActivity()).doTask()
                    } else {
                        if (mUserImage == false) {
                            AppFactory.infoPopUp(mTitle, getActivity()).doTask()
                        } else {
                            //calls loading popup
                            mSavePopup = AppFactory.getLoadingPopup(getActivity(), mTitle, "save").doTask() as PopupWindow
                            //upload image to database
                            uploadImage()
                        }
                    }
                }
            }
        })
        return rootView
    }

    @Override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY || requestCode == REQUEST_GALLERY) {
            if (data == null) {
                //Display an error
            } else {
                try {
                    mUserImage = true
                    val inputStream = getActivity().getContentResolver().openInputStream(data!!.getData())
                    mImageView!!.setImageBitmap(BitmapFactory.decodeStream(BufferedInputStream(inputStream)))
                    mImagePath = data!!.getData()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }

    //call the image gallery
    fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    //calls the camera
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    //upload the image to firebase database
    fun uploadImage() {
        try {
            val inputStream = getActivity().getContentResolver().openInputStream(mImagePath)
            //upload the input stream we get from user choice to firebase storage and saves the url to
            // firebase object
            val storageRef = FirebaseStorage.getInstance()
                    .getReferenceFromUrl("gs://petzy-1001.appspot.com")
            val mountainsRef = storageRef.child(UUID.randomUUID().toString())

            val uploadTask = mountainsRef.putStream(inputStream)
            uploadTask.addOnFailureListener(object : OnFailureListener() {
                @Override
                fun onFailure(@NonNull e: Exception) {
                    Log.e("imageUpload error", e.toString())
                }
            })
            uploadTask.addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                    //save to firebase after creating hashmap of the new items array list
                    val itemForSave = FirebaseItem(mAddress!!.getText().toString(), mTitle!!.getText().toString(), UtilitiesFactory.getFile(getActivity(), "user").doTask() as String, mChosenLocation, taskSnapshot.getDownloadUrl().toString(), "user")
                    AppFactory.saveNewPark(itemForSave).doTask()
                    //remove wating for save popup
                    mSavePopup!!.dismiss()
                    //remove fragment when done
                    UtilitiesFactory.removeFragment(getActivity()).doTask()
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    companion object {
        //request code for gallery intent
        private val REQUEST_GALLERY = 1
        //request code for camera intent
        private val REQUEST_IMAGE_CAPTURE = 2
    }
}
