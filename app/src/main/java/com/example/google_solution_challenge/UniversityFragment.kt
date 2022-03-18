package com.example.google_solution_challenge

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UniversityFragment : Fragment(R.layout.fragment_university) {

    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var continueButton : Button
    var selected = ""
    var callbackFragment : CallbackFragment ?= null
    override fun onAttach(context: Context) {
        //sharedPreferences = context.getSharedPreferences("usersFile", context.MODE_PRIVATE)
        //editor = sharedPreferences.edit()
        super.onAttach(context)
        callbackFragment = try {
            context as CallbackFragment
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement CallbackFragment")
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_university, container, false)
        autoCompleteTextView = view.findViewById(R.id.options)
        continueButton = view.findViewById(R.id.continueButton)

        val db = Firebase.firestore
        val uniResources = db.collection("university-resources")
        val items = getListOfUniversities(uniResources)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
        continueButton.visibility = View.INVISIBLE
        autoCompleteTextView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selected = s.toString()
                continueButton.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        continueButton.setOnClickListener { callbackFragment?.changeFragment() }
        // Inflate the layout for this fragment

        return view
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun getListOfUniversities(collection: CollectionReference) : MutableList<String> {
        val res = mutableListOf<String>()
        collection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                res.add(document.data["Name"] as String)
            }
        }
        return res
    }


}