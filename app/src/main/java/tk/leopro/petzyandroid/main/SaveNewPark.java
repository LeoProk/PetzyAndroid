package tk.leopro.petzyandroid.main;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

/**
 * this class saves object from hash map on firebase server
 */
final class SaveNewPark implements FactoryInterface {

    private FirebaseItem mFirebaseItem;

    //constructor to save new item to firebase
    public SaveNewPark(FirebaseItem firebaseItem) {
        mFirebaseItem = firebaseItem;
    }

    @Override
    public Object doTask() {
        //get reference to firebase database
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(("https://petzy-1001.firebaseio.com/"));
        //post new input
        final DatabaseReference postRef = ref.child("input");
        //pusher the new post
        final DatabaseReference newPostRef = postRef.push();
        newPostRef.setValue(mFirebaseItem);
        return null;
    }
}
