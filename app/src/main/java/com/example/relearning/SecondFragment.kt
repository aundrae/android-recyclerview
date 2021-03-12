package com.example.relearning

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    lateinit var contacts: ArrayList<Contract>
    lateinit var rvContacts: RecyclerView
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvContacts = view.findViewById<RecyclerView>(R.id.rvContacts) as RecyclerView
        progressBar=view.findViewById<ProgressBar>(R.id.progress_circular)
        sendGet()
        // Initialize contacts
        contacts= ArrayList()
    }

    //Sends Get Request and add values to contacts
    private fun sendGet(){
        val queue = Volley.newRequestQueue(this.context)
        val url = "https://jsonplaceholder.typicode.com/albums"
        var i = 0
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val jsonData:JSONArray = JSONArray(response)
            while(i < jsonData.length()){
                contacts.add(0, Contract(jsonData.getJSONObject(i).getString("title"), true))
                updateList()
                i++
            }
            // Display the first 500 characters of the response string.
            //println("Response is: ${response.substring(0, 500)}")
        }, { println("That didn't work!") })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    private fun updateList(){
       if(contacts.isNotEmpty()){
           progressBar.visibility=View.INVISIBLE
           val adapter = ContactsAdapter(contacts)
           // Attach the adapter to the recyclerview to populate items
           rvContacts.adapter = adapter
           // Set layout manager to position the items
           rvContacts.layoutManager = LinearLayoutManager(this.context)

           // Notify the adapter that an item was inserted at position 0
           adapter.notifyItemInserted(0)
       }
    }
}