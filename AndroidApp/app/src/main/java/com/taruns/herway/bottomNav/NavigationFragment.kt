package com.taruns.herway.bottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taruns.herway.databinding.FragmentNavigationBinding


class NavigationFragment : Fragment() {

    private lateinit var binding: FragmentNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNavigationBinding.inflate(inflater, container, false)

//        var waypoints = route.getGeometry().getWaypoints();
//        Array<LatLng>() points = Array(waypoints.size);
//        for (int i = 0; i < waypoints.size(); i++) {
//            points[i] = new LatLng(
//                    waypoints.get(i).getLatitude(),
//            waypoints.get(i).getLongitude());
//        }

// Draw Points on MapView
//        binding.mapView.addPolyline(new PolylineOptions()
//            .add(points)
//            .color(Color.parseColor(“#3887be”))
//        .width(5));
        return binding.root
    }

    companion object {

    }
}