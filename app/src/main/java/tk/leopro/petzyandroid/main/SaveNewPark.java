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

    public SaveNewPark(FirebaseItem firebaseItem) {
        mFirebaseItem = firebaseItem;
    }

    @Override
    public Object doTask() {
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(("https://petzy-1001.firebaseio.com/"));
        final DatabaseReference postRef = ref.child("input");
        final DatabaseReference newPostRef = postRef.push();
        newPostRef.setValue(mFirebaseItem);
        return null;
    }
}
