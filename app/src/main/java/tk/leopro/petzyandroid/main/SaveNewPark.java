package tk.leopro.petzyandroid.main;

import com.firebase.client.Firebase;

import tk.leopro.petzyandroid.interfaces.FactoryInterface;
import tk.leopro.petzyandroid.pojo.FirebaseItem;

/**
 * this class saves object from hash map on firebase server
 */
final class SaveNewPark implements FactoryInterface {

    private FirebaseItem mFirebaseItem;

    public SaveNewPark(FirebaseItem firebaseItem) {
        mFirebaseItem = firebaseItem;
    }

    @Override
    public Object doTask() {
        Firebase myFirebaseRef = new Firebase("https://luminous-fire-5859.firebaseio.com/");
        Firebase postRef = myFirebaseRef.child("input");
        Firebase newPostRef = postRef.push();
        newPostRef.setValue(mFirebaseItem);
        return null;
    }
}
