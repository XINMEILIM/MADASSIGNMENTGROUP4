package com.example.real;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.real.Database.DatabaseHelper_module1;

import java.util.Locale;

public class LanguageFragment extends Fragment {

    private RadioGroup radioGroupLanguages;
    private Button btnConfirmLanguage;
    private DatabaseHelper_module1 dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(); // Load saved language preference when the fragment is created
        dbHelper = new DatabaseHelper_module1(requireContext()); // Initialize database helper
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        // Initialize UI components
        radioGroupLanguages = view.findViewById(R.id.radioGroupLanguages);
        btnConfirmLanguage = view.findViewById(R.id.btnConfirmLanguage);

        // Button to navigate to home
        Button btnNavigateHome = view.findViewById(R.id.btnNavigateHome);
        btnNavigateHome.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_languageFragment_to_homeFragment));

        // Set button click listener
        btnConfirmLanguage.setOnClickListener(v -> {
            int selectedId = radioGroupLanguages.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = view.findViewById(selectedId);

            if (selectedRadioButton != null) {
                // Get selected language code
                String selectedLanguage = getLanguageCode(selectedRadioButton.getText().toString());

                // Save user language setting
                String username = "Test User"; // Replace with actual username retrieval logic
                String languageId = dbHelper.getLanguageIdByCode(selectedLanguage);

                if (languageId != null) {
                    dbHelper.saveUserLanguageSetting(username, languageId);
                    saveLanguagePreference(selectedLanguage); // Save in SharedPreferences
                    setLocale(selectedLanguage); // Update app locale
                    Toast.makeText(requireContext(), "Language preference saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to save language preference", Toast.LENGTH_SHORT).show();
                }

                // Navigate to HomeFragment
                Navigation.findNavController(view).navigate(R.id.action_languageFragment_to_homeFragment);
            } else {
                Toast.makeText(requireContext(), "Please select a language", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * Maps the user-friendly language name to the ISO 639-1 language code.
     *
     * @param language The name of the language selected.
     * @return The ISO 639-1 code for the selected language.
     */
    private String getLanguageCode(String language) {
        switch (language) {
            case "Malay":
                return "ms";
            case "Chinese":
                return "zh";
            case "Hindi":
                return "hi";
            default:
                return "en"; // Default to English
        }
    }

    /**
     * Updates the app's locale to the specified language.
     *
     * @param languageCode The ISO 639-1 code for the language to set.
     */
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        // Update app configuration
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(
                config,
                requireActivity().getResources().getDisplayMetrics()
        );
    }

    /**
     * Saves the selected language in SharedPreferences.
     *
     * @param languageCode The ISO 639-1 code for the selected language.
     */
    private void saveLanguagePreference(String languageCode) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Selected_Language", languageCode);
        editor.apply();
    }

    /**
     * Loads the saved language preference and sets the app's locale accordingly.
     */
    private void loadLocale() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String languageCode = preferences.getString("Selected_Language", "en"); // Default to English
        setLocale(languageCode);
    }

    /**
     * Reloads the current fragment to apply the language change dynamically.
     */
    private void reloadFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
