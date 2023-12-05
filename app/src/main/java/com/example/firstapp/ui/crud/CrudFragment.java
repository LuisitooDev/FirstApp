package com.example.firstapp.ui.crud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.firstapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrudFragment extends Fragment {

    View root;
    private FusedLocationProviderClient fusedLocationClient;

    private FloatingActionButton send;

    private EditText locationName;

    double lat = 0, lng = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_crud, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        send = root.findViewById(R.id.btnSend);
        locationName = root.findViewById(R.id.idNombre);




        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                send(locationName.getText().toString(), lat, lng);
            }
        });




        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                Toast.makeText(getContext(), "Ubicacion precisa", Toast.LENGTH_SHORT).show();
                                getLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Toast.makeText(getContext(), "Ubicacion aproximada", Toast.LENGTH_SHORT).show();
                                getLocation();
                            } else {
                                Toast.makeText(getContext(), "No acepto los permisos", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        return root;
    }

    private void send(String name, double lat, double lng) {
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", name);
        user.put("latitud", lat);
        user.put("longitud", lng);

        // Add a new document with a generated ID
        db.collection("ubication")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Ubicacion enviada satisfactoriamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Hubo un error en el envio" + e, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //Toast.makeText(getContext(), "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                });
    }

}

