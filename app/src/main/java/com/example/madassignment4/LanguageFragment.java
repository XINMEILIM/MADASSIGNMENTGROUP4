package com.example.real;

import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LanguageFragment extends Fragment {

    private ListView lvLanguages;
    private final ArrayList<String> languageNames = new ArrayList<>();
    private final Map<String, String> languageCodes = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        // Initialize the ListView
        lvLanguages = view.findViewById(R.id.lvLanguages);

        // Populate languages and bind the adapter
        populateLanguages();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_single_choice,
                languageNames
        );

        lvLanguages.setAdapter(adapter);
        lvLanguages.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set the default selected language if saved
        setDefaultSelectedLanguage();

        // Handle item click events
        lvLanguages.setOnItemClickListener((AdapterView<?> parent, View item, int position, long id) -> {
            String selectedLanguage = languageNames.get(position);
            String selectedCode = languageCodes.get(selectedLanguage);
            saveSelectedLanguage(selectedLanguage, selectedCode);
        });

        return view;
    }

    private void populateLanguages() {
        // Populate the list of languages with sample data
        addLanguage("English", "en");
        addLanguage("Spanish", "es");
        addLanguage("French", "fr");
        addLanguage("Indonesia", "id");
        addLanguage("Russian", "ru");
        addLanguage("German", "de");
        addLanguage("Italian", "it");
        addLanguage("Portuguese", "pt");
        addLanguage("Thai", "th");
        addLanguage("Malay", "ms");
        addLanguage("Chinese", "zh");
        addLanguage("Hindi", "hi");
        addLanguage("Arabic", "ar");
    }

    private void addLanguage(String name, String code) {
        languageNames.add(name);
        languageCodes.put(name, code);
    }

    private void saveSelectedLanguage(String languageName, String languageCode) {
        // Save the selected language to shared preferences
        Toast.makeText(getContext(), "Selected: " + languageName + " (" + languageCode + ")", Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selected_language", languageName);
        editor.putString("selected_language_code", languageCode);
        editor.apply();
    }

    private void setDefaultSelectedLanguage() {
        // Retrieve the saved language from shared preferences
        SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String selectedLanguage = preferences.getString("selected_language", null);

        if (selectedLanguage != null) {
            int index = languageNames.indexOf(selectedLanguage);
            if (index != -1) {
                lvLanguages.setItemChecked(index, true);
            }
        }
    }
}
