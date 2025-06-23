package com.datj.mobile.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.datj.mobile.R;
import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.databinding.FragmentProfileBinding;
import com.datj.mobile.ui.customview.LoginActivity;
import com.datj.mobile.viewmodel.AccountViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {
    private FragmentProfileBinding binding;
    private AccountViewModel accountViewModel;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Button storeLocationButton;
    private boolean isMapVisible = false;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private LatLng currentLocation;
    private LatLng storeLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Khởi tạo mapFragment
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Không thể khởi tạo bản đồ", Toast.LENGTH_SHORT).show();
        }

        storeLocationButton = binding.storeLocationButton;

        accountViewModel.getAccount().observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                binding.setAccount(account);
                binding.profileImageView.setImageResource(R.drawable.ic_profile);
            } else {
                Toast.makeText(getContext(), "Không tải được thông tin tài khoản", Toast.LENGTH_SHORT).show();
            }
        });

        binding.logoutButton.setOnClickListener(v -> handleLogout());


        binding.storeLocationButton.setOnClickListener(v -> {
            checkLocationPermission();
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            checkGpsStatus();
            getCurrentLocation();
        }
    }

    private void checkGpsStatus() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(requireContext())
                    .setMessage("GPS chưa được bật. Bạn có muốn bật GPS không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        Toast.makeText(getContext(), "Vị trí có thể không chính xác nếu GPS tắt", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkGpsStatus();
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Quyền truy cập vị trí bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(getContext(), "Không thể lấy vị trí hiện tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
                            currentLocation = new LatLng(latitude, longitude);
                            fusedLocationClient.removeLocationUpdates(this);
                            getAddressFromLocation(currentLocation);
                            openGoogleMapsForDirections(currentLocation);
                        } else {
                            Toast.makeText(getContext(), "Tọa độ không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi lấy vị trí: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAddressFromLocation(LatLng location) {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
            openGoogleMapsForDirections(location);
            return;
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = address.getAddressLine(0);
                Toast.makeText(getContext(), "Địa chỉ hiện tại: " + addressText, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Không thể lấy địa chỉ", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi khi lấy địa chỉ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        openGoogleMapsForDirections(location);
    }

    private void openGoogleMapsForDirections(LatLng origin) {
        storeLocation = new LatLng(10.762622, 106.660172); // TP. Hồ Chí Minh

        String uri = "https://www.google.com/maps/dir/?api=1" +
                "&origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + storeLocation.latitude + "," + storeLocation.longitude +
                "&travelmode=driving";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Ứng dụng Google Maps không được cài đặt", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLogout() {
        TokenManager.getInstance().clearToken();
        Toast.makeText(getContext(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        storeLocation = new LatLng(10.762622, 106.660172);
        mMap.addMarker(new MarkerOptions()
                .position(storeLocation)
                .title("Cửa hàng của bạn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}