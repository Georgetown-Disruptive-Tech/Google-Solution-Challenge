package com.example.google_solution_challenge

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


class UniversityFragment : Fragment(R.layout.fragment_university) {

    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var continueButton : Button
    var selected = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_university, container, false)
        autoCompleteTextView = view.findViewById(R.id.options)
        continueButton = view.findViewById(R.id.continueButton)

        val items = resources.getStringArray(R.array.universities)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
        continueButton.setVisibility(View.INVISIBLE)
        autoCompleteTextView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selected = s.toString()
                continueButton.setVisibility(View.VISIBLE)
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
        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun onContinue(view: View)
    {
        //proceed to next screen with the selected university.
    }
}