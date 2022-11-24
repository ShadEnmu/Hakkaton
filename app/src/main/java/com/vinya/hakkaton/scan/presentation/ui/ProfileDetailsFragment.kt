package com.vinya.hakkaton.scan.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.vinya.hakkaton.R
import com.vinya.hakkaton.core.presentation.BaseFragment

class ProfileDetailsFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_profile
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var stateTextView: TextView
    private lateinit var nextButton: ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImageView = view.findViewById(R.id.profileImageView)
        nameTextView = view.findViewById(R.id.nameTextView)
        stateTextView = view.findViewById(R.id.stateTextView)
        val contactInfo = arguments?.getStringArrayList("ContactInfo")
        val fullName = contactInfo?.get(1) + " " + contactInfo?.get(0)
        val group = contactInfo?.get(2)
        nextButton = view.findViewById(R.id.nextImageButton)
        nextButton.setOnClickListener {
            findNavController().navigate(R.id.scanBarcodeFragment)
        }

        when (fullName) {
            "Черных Вадим" -> {
                profileImageView.setImageDrawable(activity?.getDrawable(R.drawable.vadim))
                nameTextView.text = fullName
                stateTextView.text = group
            }
            "Калашников Никита" -> {
                profileImageView.setImageDrawable(activity?.getDrawable(R.drawable.nikita))
                nameTextView.text = fullName
                stateTextView.text = group
            }
            "Хангалова Юлия" -> {
                profileImageView.setImageDrawable(activity?.getDrawable(R.drawable.yulia))
                nameTextView.text = fullName
                stateTextView.text = group
            }
            "Болотов Аюр" -> {
                profileImageView.setImageDrawable(activity?.getDrawable(R.drawable.ayur))
                nameTextView.text = fullName
                stateTextView.text = group
            }
            else -> {
                profileImageView.setImageDrawable(activity?.getDrawable(R.drawable.error))
                nameTextView.text = "Сканирование не удалось"
            }
        }


    }
}