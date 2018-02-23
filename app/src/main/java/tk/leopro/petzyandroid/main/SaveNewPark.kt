package tk.leopro.petzyandroid.main

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import tk.leopro.petzyandroid.interfaces.FactoryInterface
import tk.leopro.petzyandroid.pojo.FirebaseItem

/**
 * this class saves object from hash map on firebase server
 */
internal class SaveNewPark//constructor to save new item to firebase
(private val mFirebaseItem: FirebaseItem) : FactoryInterface {

    @Override
    fun doTask(): Object? {
        //get reference to firebase database
        val ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://petzy-1001.firebaseio.com/")
        //post new input
        val postRef = ref.child("input")
        //pusher the new post
        val newPostRef = postRef.push()
        newPostRef.setValue(mFirebaseItem)
        return null
    }
}
