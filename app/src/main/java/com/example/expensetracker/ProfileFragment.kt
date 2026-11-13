package com.example.expensetracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvProfileInitials: TextView
    private lateinit var tvProfileDisplayName: TextView
    private lateinit var etProfileName: EditText
    private lateinit var btnRemovePhoto: TextView

    // Modern photo picker using ActivityResultContracts.GetContent()
    // Completely optional; works across all Android versions without extra runtime permissions.
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            UserManager.saveProfileImageUri(requireContext(), uri.toString())
            updateProfileAvatar(uri.toString())
            Toast.makeText(requireContext(), "Profile picture updated", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfileImage = view.findViewById(R.id.ivProfileImage)
        tvProfileInitials = view.findViewById(R.id.tvProfileInitials)
        tvProfileDisplayName = view.findViewById(R.id.tvProfileDisplayName)
        etProfileName = view.findViewById(R.id.etProfileName)
        btnRemovePhoto = view.findViewById(R.id.btnRemovePhoto)
        val fabChangePhoto = view.findViewById<FloatingActionButton>(R.id.fabChangePhoto)
        val btnSaveProfileName = view.findViewById<MaterialButton>(R.id.btnSaveProfileName)
        val btnResetProfile = view.findViewById<MaterialButton>(R.id.btnResetProfile)

        // 1. Populate current user info
        val currentName = UserManager.getUserName(requireContext())
        tvProfileDisplayName.text = if (currentName.isNotEmpty()) currentName else "User"
        etProfileName.setText(currentName)

        val savedImageUri = UserManager.getProfileImageUri(requireContext())
        updateProfileAvatar(savedImageUri)

        // 2. Photo Picker Trigger
        fabChangePhoto.setOnClickListener {
            // Allows picking an image from device gallery
            pickImageLauncher.launch("image/*")
        }

        // 3. Remove Photo Option
        btnRemovePhoto.setOnClickListener {
            UserManager.saveProfileImageUri(requireContext(), null)
            updateProfileAvatar(null)
            Toast.makeText(requireContext(), "Photo removed", Toast.LENGTH_SHORT).show()
        }

        // 4. Save Edited Name
        btnSaveProfileName.setOnClickListener {
            val updatedName = etProfileName.text.toString().trim()
            if (updatedName.isEmpty()) {
                etProfileName.error = "Name cannot be empty"
                etProfileName.requestFocus()
                return@setOnClickListener
            }

            UserManager.saveUserName(requireContext(), updatedName)
            tvProfileDisplayName.text = updatedName
            updateInitials(updatedName)
            Toast.makeText(requireContext(), "Profile name saved successfully!", Toast.LENGTH_SHORT).show()
        }

        // 5. Reset Profile (To re-test onboarding flow)
        btnResetProfile.setOnClickListener {
            UserManager.resetUserData(requireContext())
            Toast.makeText(requireContext(), "Profile reset. Returning to onboarding...", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun updateProfileAvatar(uriString: String?) {
        val name = UserManager.getUserName(requireContext())
        if (!uriString.isNullOrEmpty()) {
            try {
                ivProfileImage.setImageURI(Uri.parse(uriString))
                ivProfileImage.visibility = View.VISIBLE
                tvProfileInitials.visibility = View.GONE
                btnRemovePhoto.visibility = View.VISIBLE
                return
            } catch (e: Exception) {
                // If URI is invalid or inaccessible, fallback to initials
            }
        }

        // Default: Show initials
        ivProfileImage.visibility = View.GONE
        tvProfileInitials.visibility = View.VISIBLE
        btnRemovePhoto.visibility = View.GONE
        updateInitials(name)
    }

    private fun updateInitials(name: String) {
        val initials = name.split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .map { it.first().uppercase() }
            .joinToString("")
        tvProfileInitials.text = if (initials.isNotEmpty()) initials else "ET"
    }
}
