package com.example.firstapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firstapp.LocationAdapter;
import com.example.firstapp.LocationSchema;
import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    View root;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<LocationSchema> locationArray;

    LocationAdapter locationAdapter;

    ListView listView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_list, container, false);

        locationArray = new ArrayList<>();
        listView = root.findViewById(R.id.listView);

        getLocations();

        return root;
    }

    private void getLocations() {
        locationArray.clear();
        db.collection("ubication")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                LocationSchema LocationSchema = new LocationSchema(
                                  document.getId(),
                                  document.getString("nombre"),
                                  Double.parseDouble(String.valueOf(document.get("latitud"))),
                                        Double.parseDouble(String.valueOf(document.get("longitud")))
                                );
                                locationArray.add(LocationSchema);
                            }
                            if (task.getResult().size() > 0){
                                locationAdapter = new LocationAdapter(getActivity(), locationArray);
                                listView.setAdapter(locationAdapter);
                            }

                        } else {
                            Toast.makeText(getContext(), "No se pudieron obtener los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}