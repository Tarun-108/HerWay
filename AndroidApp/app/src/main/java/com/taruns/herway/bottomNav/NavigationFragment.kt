package com.taruns.herway.bottomNav

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.mapbox.maps.Style
import com.taruns.herway.data.APIClient
import com.taruns.herway.data.APIInterface
import com.taruns.herway.databinding.FragmentNavigationBinding
import com.taruns.herway.models.RequestBody
import com.taruns.herway.models.ResponseJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NavigationFragment : Fragment() {

    var apiInterface: APIInterface? = null
    private lateinit var binding: FragmentNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNavigationBinding.inflate(inflater, container, false)
        apiInterface = APIClient.client?.create(APIInterface::class.java)

        val requestBody: RequestBody = RequestBody("delhi airport","connaught place")

        apiInterface?.getResponse(requestBody)?.enqueue(object : Callback<ResponseJson?> {
            override fun onResponse(call: Call<ResponseJson?>?, response: Response<ResponseJson?>) {
                val response: ResponseJson? = response.body()

                Toast.makeText( activity,
                    response.toString(), Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<ResponseJson?>, t: Throwable?) {
                call.cancel()
            }
        })


//        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        val polylineOptions: PolylineOptions = PolylineOptions()
            .add(LatLng(40.7143528, -74.0059731))
            .add(LatLng(37.7749295, -122.4194155))
            .add(LatLng(51.5073509, -0.1277583))

        polylineOptions.color(Color.parseColor("#3bb2d0"));
        polylineOptions.width(5F);
        polylineOptions.jointType(JointType.ROUND);


        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

//        binding.webView.loadUrl("http://www.tutorialspoint.com");

        return binding.root
    }

    companion object {

    }
}